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

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ItemRegistry {
		
	public static final ItemGroup DWARFEN_LEGACY = FabricItemGroupBuilder.build(new Identifier("dwarfen_legacy", "dwarfen_tab"), ()-> new ItemStack(ItemRegistry.ANCIENT_PICKAXE));

	public static final DwarfenPickaxe RUBY_PICKAXE = new DwarfenPickaxe(ToolMaterials.RUBY, -2.8F, new Item.Settings().group(DWARFEN_LEGACY));
	public static final DwarfenPickaxe RUBLIL_PICKAXE = new DwarfenPickaxe(ToolMaterials.RUBLIL, -2.8F, new Item.Settings().group(DWARFEN_LEGACY));
	public static final DwarfenPickaxe ORICHALCUM_PICKAXE = new DwarfenPickaxe(ToolMaterials.ORICHALCUM, -2.8F, new Item.Settings().group(DWARFEN_LEGACY));
	public static final DwarfenPickaxe MITHRIL_PICKAXE = new DwarfenPickaxe(ToolMaterials.MITHRIL, -2.8F, new Item.Settings().group(DWARFEN_LEGACY));
	public static final DwarfenPickaxe ADAMANT_PICKAXE = new DwarfenPickaxe(ToolMaterials.ADAMANT, -2.8F, new Item.Settings().group(DWARFEN_LEGACY));
	public static final DwarfenPickaxe BROKEN_ANCIENT_PICKAXE = new DwarfenPickaxe(ToolMaterials.BROKEN_ANCIENT, -2.8F, new Item.Settings().group(DWARFEN_LEGACY));
	public static final DwarfenPickaxe ANCIENT_PICKAXE = new DwarfenPickaxe(ToolMaterials.ANCIENT, -2.8F, new Item.Settings().group(DWARFEN_LEGACY).fireproof().rarity(Rarity.EPIC));
	public static final Item ASH = new BoneMealItem(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item RUBY = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item RUBLIL_MIXTURE = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item CLUMP_OF_RUBLIL = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item RUBLIL_INGOT = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item ORICHALCUM_INGOT = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item MITHRIL_INGOT = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	public static final Item ADAMANT_INGOT = new Item(new Item.Settings().group(DWARFEN_LEGACY));
	
	public static void register() {
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "ruby_pickaxe"), RUBY_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "rublil_pickaxe"), RUBLIL_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "orichalcum_pickaxe"), ORICHALCUM_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "mithril_pickaxe"), MITHRIL_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "adamant_pickaxe"), ADAMANT_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "broken_pickaxe"), BROKEN_ANCIENT_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "ancient_pickaxe"), ANCIENT_PICKAXE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "ash"), ASH);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "ruby"), RUBY);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "rublil_mixture"), RUBLIL_MIXTURE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "clump_of_rublil"), CLUMP_OF_RUBLIL);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "rublil_ingot"), RUBLIL_INGOT);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "orichalcum_ingot"), ORICHALCUM_INGOT);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "mithril_ingot"), MITHRIL_INGOT);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "adamant_ingot"), ADAMANT_INGOT);
	}

}
