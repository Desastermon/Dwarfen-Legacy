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

import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeRegistry {

	public static CustomFurnaceRecipe.Serializer customFurnaceRecipeSerializer;
	public static RecipeType<CustomFurnaceRecipe> customFurnaceRecipeType;
	
	public static void register() {
		customFurnaceRecipeSerializer = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("dwarfen_legacy", "custom_furnace"), new CustomFurnaceRecipe.Serializer());
		customFurnaceRecipeType = Registry.register(Registry.RECIPE_TYPE, new Identifier("dwarfen_legacy", "custom_furnace"), new RecipeType<CustomFurnaceRecipe>() {
			@Override
			public String toString() {
				return "dwarfen_legacy:custom_furnace";
			}
		});
	}
	
}
