/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import desastermon.dwarfen_legacy.blocks.entity.CustomFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class CustomFurnaceRecipe implements Recipe<CustomFurnaceBlockEntity>{
	
	private final Identifier ID;
	private final Ingredient INPUT;
	private final ItemStack OUTPUT;
	private final int TIME;
	private final String GROUP;
	private final float EXPERIENCE;
	private final int INPUT_COUNT;
	
	public CustomFurnaceRecipe(Identifier id, Ingredient input, ItemStack output, int time, String group, float experience, int inputcount) {
		this.ID=id;
		this.INPUT=input;
		this.OUTPUT=output;
		this.TIME=time;
		this.GROUP=group;
		this.EXPERIENCE=experience;
		this.INPUT_COUNT=inputcount;
	}

	@Override
	public boolean matches(CustomFurnaceBlockEntity inv, World world) {
		return INPUT.test(inv.getStack(0)) && INPUT_COUNT <= inv.getStack(0).getCount();
	}
	
	public int getCookTime() {
		return TIME;
	}

	@Override
	public ItemStack craft(CustomFurnaceBlockEntity inv) {
		return OUTPUT.copy();
	}

	@Override
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getOutput() {
		return this.OUTPUT;
	}

	@Override
	public Identifier getId() {
		return ID;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeRegistry.customFurnaceRecipeSerializer;
	}
	
	public int getInputCount() {
		return INPUT_COUNT;
	}

	@Override
	public RecipeType<?> getType() {
		return RecipeRegistry.customFurnaceRecipeType;
	}
	
	public static class Serializer implements RecipeSerializer<CustomFurnaceRecipe>{

		@Override
		public CustomFurnaceRecipe read(Identifier id, JsonObject json) {
			String group = JsonHelper.getString(json, "group", "");
			JsonElement jsonElement = JsonHelper.hasArray(json, "ingredient") ? JsonHelper.getArray(json, "ingredient") : JsonHelper.getObject(json, "ingredient");
			Ingredient ingredient = Ingredient.fromJson(jsonElement);
			int resultCount = JsonHelper.getInt(json, "resultCount", 1);
			String result = JsonHelper.getString(json, "result");
			Identifier resultID = new Identifier(result);
			ItemStack resultStack = new ItemStack(Registry.ITEM.getOrEmpty(resultID).orElseThrow(() -> {
				return new IllegalStateException("Item: " + result + " does not exist");
			}), resultCount);
			float xp = JsonHelper.getFloat(json, "experience", 0.0F);
			int i = JsonHelper.getInt(json, "time", 300);
			int i2 = JsonHelper.getInt(json, "count", 1);
			return new CustomFurnaceRecipe(id, ingredient, resultStack, i, group, xp, i2);
		}

		@Override
		public CustomFurnaceRecipe read(Identifier id, PacketByteBuf buf) {
			Ingredient ingredient = Ingredient.fromPacket(buf);
			ItemStack result = buf.readItemStack();
			int i = buf.readInt();
			String group = buf.readString();
			float xp = buf.readFloat();
			int i2 = buf.readInt();
			return new CustomFurnaceRecipe(id, ingredient, result, i, group, xp, i2);
		}

		@Override
		public void write(PacketByteBuf buf, CustomFurnaceRecipe recipe) {
			recipe.INPUT.write(buf);
			buf.writeItemStack(recipe.OUTPUT);
			buf.writeInt(recipe.TIME);
			buf.writeString(recipe.GROUP);
			buf.writeFloat(recipe.EXPERIENCE);
			buf.writeInt(recipe.INPUT_COUNT);
		}
		
	}

}
