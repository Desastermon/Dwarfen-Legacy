/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy.items;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class LootTable {
	
	private static final Identifier ABANDONED_MINESHAFT_LOOT_TABLE_ID = new Identifier("minecraft", "chests/abandoned_mineshaft");
	
	public static void register () {
		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
		    if (ABANDONED_MINESHAFT_LOOT_TABLE_ID.equals(id)) {
		    	FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
		                .rolls(ConstantLootTableRange.create(1))
		                .withEntry(ItemEntry.builder(ItemRegistry.BROKEN_ANCIENT_PICKAXE).weight(1).build())
		                .withEntry(ItemEntry.builder(Items.AIR).weight(99).build());
		 
		        supplier.pool(poolBuilder);
		    }
		});
	}

}
