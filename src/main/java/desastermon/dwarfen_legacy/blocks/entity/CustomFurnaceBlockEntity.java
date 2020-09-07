/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy.blocks.entity;

import java.util.HashMap;

import desastermon.dwarfen_legacy.blocks.CustomFurnace;
import desastermon.dwarfen_legacy.items.ItemRegistry;
import desastermon.dwarfen_legacy.recipe.CustomFurnaceRecipe;
import desastermon.dwarfen_legacy.recipe.RecipeRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;

public class CustomFurnaceBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory, Tickable{

	DefaultedList<ItemStack> stacks = DefaultedList.ofSize(5, ItemStack.EMPTY);
	int burntime = 0;
	int cooktime = 0;
	private int fueltime;
	private int cookTimeTotal;
	private Item lastUsed;
	private PropertyDelegate propertyDelegate;

	public CustomFurnaceBlockEntity() {
		super(BlockEntityRegistry.CUSTOM_FURNACE_ENTITY);
		this.propertyDelegate = new PropertyDelegate() {
			public int get(int index) {
				switch(index) {
				case 0:
					return CustomFurnaceBlockEntity.this.burntime;
				case 1:
					return CustomFurnaceBlockEntity.this.fueltime;
				case 2:
					return CustomFurnaceBlockEntity.this.cooktime;
				case 3:
					return CustomFurnaceBlockEntity.this.cookTimeTotal;
				default:
					return 0;
				}
			}

			public void set(int index, int value) {
				switch(index) {
				case 0:
					CustomFurnaceBlockEntity.this.burntime = value;
					break;
				case 1:
					CustomFurnaceBlockEntity.this.fueltime = value;
					break;
				case 2:
					CustomFurnaceBlockEntity.this.cooktime = value;
					break;
				case 3:
					CustomFurnaceBlockEntity.this.cookTimeTotal = value;
				}

			}

			public int size() {
				return 4;
			}
		};
	}

	@Override
	public void clear() {
		stacks = DefaultedList.ofSize(5, ItemStack.EMPTY);

	}

	@Override
	public int size() {
		return 5;
	}

	@Override
	public boolean isEmpty() {
		return stacks.stream().allMatch(a -> a == ItemStack.EMPTY);
	}

	@Override
	public ItemStack getStack(int slot) {
		return stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack result = Inventories.splitStack(stacks, slot, amount);
		if (!result.isEmpty()) {
			markDirty();
		}
		return result;
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(stacks, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		ItemStack itemStack = (ItemStack)this.getStack(slot);
		boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
		stacks.set(slot, stack);
		if (stack.getCount() > getMaxCountPerStack()) {
			stack.setCount(getMaxCountPerStack());
		}		
		if ((slot == 0 || slot == 2) && !bl) {
			this.cookTimeTotal = this.getCookTime();
			this.cooktime = 0;
			this.markDirty();
		}
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return true;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		Inventories.fromTag(tag, stacks);
		burntime = tag.getInt("burntime");
		cooktime = tag.getInt("cooktime");
		fueltime = tag.getInt("fueltime");
		cookTimeTotal = tag.getInt("totalcooktime");
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		Inventories.toTag(tag, stacks);
		tag.putInt("burntime", burntime);
		tag.putInt("cooktime", cooktime);
		tag.putInt("fueltime", fueltime);
		tag.putInt("totalcooktime", cookTimeTotal);
		return super.toTag(tag);
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new CustomFurnaceScreenHandler(syncId, inv, this, this.propertyDelegate);
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {

	}

	@Override
	public void tick() {
		boolean bl = this.isBurning();
		boolean bl2 = false;
		if (this.isBurning()) {
			--this.burntime;
			if(!this.isBurning()) {
				this.processExtra();
			}
		}

		if (!this.world.isClient) {
			ItemStack itemStack = this.getStack(1);
			ItemStack itemStack2 = this.getStack(2);
			if (!this.isBurning() && (itemStack.isEmpty() || itemStack2.isEmpty() || this.getStack(0).isEmpty())) {
				if (!this.isBurning() && this.cooktime > 0) {
					this.cooktime = 0;
				}
			} else {
				CustomFurnaceRecipe recipe = (CustomFurnaceRecipe)this.world.getRecipeManager().getFirstMatch(RecipeRegistry.customFurnaceRecipeType, this, this.world).orElse((CustomFurnaceRecipe)null);
				if (!this.isBurning() && this.canAcceptRecipeOutput(recipe) && this.canAcceptExtraOutput()) {
					this.burntime = this.getFuelTime(itemStack, itemStack2);
					this.fueltime = this.burntime;
					if (this.isBurning()) {
						bl2 = true;
						if (!itemStack.isEmpty()) {
							Item item = itemStack.getItem();
							itemStack.decrement(1);
							lastUsed = itemStack2.getItem();
							itemStack2.decrement(1);
							if (itemStack.isEmpty()) {
								Item item2 = item.getRecipeRemainder();
								this.setStack(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
							}
						}
					}
				}

				if (this.isBurning() && this.canAcceptRecipeOutput(recipe)) {
					++this.cooktime;
					if (this.cooktime == this.cookTimeTotal) {
						this.cooktime = 0;
						this.cookTimeTotal = this.getCookTime();
						this.craftRecipe(recipe);
						bl2 = true;
					}
				} else {
					this.cooktime = 0;
				}
			}

			if (bl != this.isBurning()) {
				bl2 = true;
				this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(CustomFurnace.LIT, this.isBurning()), 3);
			}
		}

		if (bl2) {
			this.markDirty();
		}

	}

	private void craftRecipe(CustomFurnaceRecipe recipe) {
		if(recipe != null && this.canAcceptRecipeOutput(recipe)) {
			ItemStack itemStack = this.getStack(0);
			ItemStack itemStack2 = recipe.craft(this);
			ItemStack itemStack3 = this.getStack(3);
			if(itemStack3.isEmpty()) {
				this.setStack(3, itemStack2);
			}
			else if(itemStack3.getItem() == itemStack2.getItem()) {
				itemStack3.increment(itemStack2.getCount());
			}
			itemStack.decrement(recipe.getInputCount());
		}
	}

	private void processExtra() {
		ItemStack itemStack4 = CustomFuelTypes.map.getOrDefault(lastUsed, CustomFuelTypes.NONE).extraOutput.copy();
		ItemStack itemStack5 = this.getStack(4);
		if(itemStack5.isEmpty()) {
			this.setStack(4, itemStack4);
		}
		else if(itemStack5.getItem() == itemStack4.getItem()) {
			itemStack5.increment(itemStack4.getCount());
		}
	}

	private int getCookTime() {
		if(!this.hasWorld()) return -1;
		float f = CustomFuelTypes.map.getOrDefault(this.getStack(2).getItem(), CustomFuelTypes.NONE).efficiencyMultiplier;
		int i1 = (Integer)this.world.getRecipeManager().getFirstMatch(RecipeRegistry.customFurnaceRecipeType, this, this.world).map(CustomFurnaceRecipe::getCookTime).orElse(200);
		return (int)(i1 / f);
	}

	private int getFuelTime(ItemStack itemStack, ItemStack itemStack2) {
		float f = CustomFuelTypes.map.getOrDefault(itemStack2.getItem(), CustomFuelTypes.NONE).fuelMultiplier;
		int i1 = AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(itemStack.getItem(), 0);
		return (int)(f * (float)i1);
	}

	private boolean canAcceptRecipeOutput(Recipe<?> recipe) {
		if(!this.getStack(0).isEmpty() && recipe != null) {
			boolean output = true;
			ItemStack itemStack = recipe.getOutput();
			if (itemStack.isEmpty()) {
				output = false;
			} else {
				ItemStack itemStack2 = (ItemStack)this.getStack(3);
				if (itemStack2.isEmpty()) {
					return true;
				} else if (!itemStack2.isItemEqualIgnoreDamage(itemStack)) {
					output = false;
				} else if (itemStack2.getCount() < this.getMaxCountPerStack() && itemStack2.getCount() < itemStack2.getMaxCount()) {
					return true;
				} else {
					if(itemStack2.getCount() < itemStack.getMaxCount() == false) {
						output = false;
					}
				}
			} 

			return output;
		} else {
			return false;
		}
	}

	private boolean canAcceptExtraOutput() {
		boolean output = true;
		ItemStack itemStack3 = CustomFuelTypes.map.getOrDefault(lastUsed == null ? this.getStack(2) : lastUsed, CustomFuelTypes.NONE).extraOutput;
		ItemStack itemStack4 = (ItemStack)this.getStack(4);
		if (itemStack4.isEmpty() || itemStack3.isEmpty()) {
			return true;
		} else if (!itemStack4.isItemEqualIgnoreDamage(itemStack3)) {
			output = false;
		} else if (itemStack4.getCount() <= (this.getMaxCountPerStack() - itemStack3.getCount()) && itemStack4.getCount() <= (itemStack4.getMaxCount() - itemStack3.getCount())) {
			return true;
		} else {
			if(itemStack4.getCount() < itemStack3.getMaxCount() == false) {
				output = false;
			}
		}
		return output;
	}

	public boolean isBurning() {
		return this.burntime > 0;
	}

	public static class CustomFuelTypes {
		public Item fuelItem;
		public float fuelMultiplier;
		public float efficiencyMultiplier;
		public ItemStack extraOutput;
		public static HashMap<Item, CustomFuelTypes> map = new HashMap<>();
		public static final CustomFuelTypes NONE = new CustomFuelTypes(Items.AIR, 0, 0, ItemStack.EMPTY);

		public CustomFuelTypes(Item fuel, float fueltime, float efficiency, ItemStack extraOutput) {
			this.fuelItem=fuel;
			this.fuelMultiplier=fueltime;
			this.efficiencyMultiplier=efficiency;
			this.extraOutput=extraOutput;
		}

		static {
			fuel(Items.CLAY, 1.5F, 1, new ItemStack(Items.TERRACOTTA));
			fuel(Items.HAY_BLOCK, 0.8F, 1.3F, new ItemStack(ItemRegistry.ASH, 3));
		}

		private static void fuel(Item fuel, float fueltime, float efficiency, ItemStack extraOutput) {
			map.put(fuel, new CustomFuelTypes(fuel, fueltime, efficiency, extraOutput));
		}
	}

}
