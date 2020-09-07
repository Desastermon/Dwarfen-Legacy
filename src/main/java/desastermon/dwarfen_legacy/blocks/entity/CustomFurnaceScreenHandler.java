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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CustomFurnaceScreenHandler extends ScreenHandler{

	private final Inventory inventory;
	private final PropertyDelegate PROPERTY_DELEGATE;

	public CustomFurnaceScreenHandler(int syncId, PlayerInventory inv) {
		this(syncId, inv, new CustomFurnaceBlockEntity(), new ArrayPropertyDelegate(4));
	}

	public CustomFurnaceScreenHandler(int syncId, PlayerInventory inv, CustomFurnaceBlockEntity customFurnaceBlockEntity, PropertyDelegate propertydelegate) {
		super(BlockEntityRegistry.CUSTOM_FURNACE_SCREENHANDLER, syncId);
		this.PROPERTY_DELEGATE=propertydelegate;
		checkSize(customFurnaceBlockEntity, 5);
		inventory = customFurnaceBlockEntity;
		this.addSlot(new Slot(inventory, 0, 56, 17));
		this.addSlot(new Slot(inventory, 1, 45, 53));
		this.addSlot(new Slot(inventory, 2, 67, 53));
		this.addSlot(new Slot(inventory, 3, 116, 35) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});
		this.addSlot(new Slot(inventory, 4, 142, 51) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}
		});
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlot(new Slot(inv, i, 8 + i * 18, 142));
		}
		this.addProperties(propertydelegate);
	}

	public CustomFurnaceScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf buf) {
		this(syncId, inv, new CustomFurnaceBlockEntity(), new ArrayPropertyDelegate(4));
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot != null && slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (invSlot < inventory.size()) {
				if (!this.insertItem(originalStack, inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, inventory.size(), false)) {
				return ItemStack.EMPTY;
			}

			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}

		return newStack;
	}

	@Environment(EnvType.CLIENT)
	public int getCookProgress() {
		int i = this.PROPERTY_DELEGATE.get(2);
		int j = this.PROPERTY_DELEGATE.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}

	@Environment(EnvType.CLIENT)
	public int getFuelProgress() {
		int i = this.PROPERTY_DELEGATE.get(1);
		if (i == 0) {
			i = 200;
		}

		return this.PROPERTY_DELEGATE.get(0) * 13 / i;
	}

	@Environment(EnvType.CLIENT)
	public boolean isBurning() {
		return this.PROPERTY_DELEGATE.get(0) > 0;
	}

}
