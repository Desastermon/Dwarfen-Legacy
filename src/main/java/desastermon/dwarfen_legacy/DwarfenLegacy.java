/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy;

import de.schneider_oliver.doomedfabric.recipe.ConditionalRecipeRegistry;
import de.schneider_oliver.doomedfabric.recipe.ConditionalRecipeRegistry.RecipeCondition;
import desastermon.dwarfen_legacy.blocks.BlockRegistry;
import desastermon.dwarfen_legacy.blocks.entity.BlockEntityRegistry;
import desastermon.dwarfen_legacy.items.ItemRegistry;
import desastermon.dwarfen_legacy.items.LootTable;
import desastermon.dwarfen_legacy.recipe.RecipeRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DwarfenLegacy implements ModInitializer {
	
	@Override
	public void onInitialize() {
		ItemRegistry.register();
		BlockRegistry.register();
		LootTable.register();
		BlockEntityRegistry.register();
		RecipeRegistry.register();
		ConditionalRecipeRegistry.registerConditionForId(new Identifier("dwarfen_legacy", "ancient_pickaxe"), new RecipeCondition() {
			
			@Override
			public boolean canCraft(World world, ServerPlayerEntity player, Recipe<?> recipe) {
				return world.getTimeOfDay() > 16000 && world.getTimeOfDay() < 20000 && player.getRootVehicle() != null && player.getRootVehicle() instanceof MinecartEntity ;
			}
		});
	}
}
