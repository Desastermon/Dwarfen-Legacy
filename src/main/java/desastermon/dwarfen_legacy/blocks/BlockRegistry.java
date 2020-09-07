/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy.blocks;

import de.schneider_oliver.doomedfabric.block.AutoOre.RepeatMode;
import de.schneider_oliver.doomedfabric.block.AutoOreBlock;
import de.schneider_oliver.doomedfabric.block.AutoOreRegistry;
import desastermon.dwarfen_legacy.items.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {

	public static final Block RUBY_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 2).strength(5, 6).requiresTool());
	public static final Block RUBLIL_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 2).strength(15, 1200).requiresTool());
	public static final Block ORICHALCUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 2).strength(15, 1200).requiresTool());
	public static final Block MITHRIL_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 2).strength(15, 1200).requiresTool());
	public static final Block ADAMANT_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).breakByTool(FabricToolTags.PICKAXES, 2).strength(15, 1200).requiresTool());
	public static final Block ORICHALCUM_GLASS = new GlassBlock(FabricBlockSettings.of(Material.GLASS).breakByTool(FabricToolTags.PICKAXES, 0).strength(5, 1200).requiresTool().nonOpaque());
	public static final AutoOreBlock RUBY_ORE = new AutoOreBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 3).strength(15, 10).requiresTool()).maxHeight(25).minHeight(10).repeats(4).repeatMode(RepeatMode.REPEAT_RANDOM);
	public static final AutoOreBlock ORICHALCUM_ORE = new AutoOreBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 4).strength(16, 12).requiresTool()).maxHeight(18).minHeight(0).repeats(3).repeatMode(RepeatMode.REPEAT_RANDOM);
	public static final AutoOreBlock MITHRIL_ORE = new AutoOreBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 4).strength(17, 13).requiresTool()).maxHeight(18).minHeight(0).repeats(3).repeatMode(RepeatMode.REPEAT_RANDOM);
	public static final AutoOreBlock ADAMANT_ORE = new AutoOreBlock(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 5).strength(19, 15).requiresTool()).maxHeight(10).minHeight(0).repeats(2).repeatMode(RepeatMode.REPEAT_RANDOM);
	public static final Block CUSTOM_FURNACE = new CustomFurnace(FabricBlockSettings.of(Material.STONE).breakByTool(FabricToolTags.PICKAXES, 0).strength(5, 10).requiresTool().lightLevel(state -> state.get(CustomFurnace.LIT) ? 13 : 0));

	public static void register() {
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "ruby_block"), RUBY_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "ruby_block"), new BlockItem(RUBY_BLOCK, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "rublil_block"), RUBLIL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "rublil_block"), new BlockItem(RUBLIL_BLOCK, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "orichalcum_block"), ORICHALCUM_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "orichalcum_block"), new BlockItem(ORICHALCUM_BLOCK, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "mithril_block"), MITHRIL_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "mithril_block"), new BlockItem(MITHRIL_BLOCK, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "adamant_block"), ADAMANT_BLOCK);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "adamant_block"), new BlockItem(ADAMANT_BLOCK, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "orichalcum_glass"), ORICHALCUM_GLASS);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "orichalcum_glass"), new BlockItem(ORICHALCUM_GLASS, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "ruby_ore"), RUBY_ORE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "ruby_ore"), new BlockItem(RUBY_ORE, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		AutoOreRegistry.registerOre(RUBY_ORE);
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "orichalcum_ore"), ORICHALCUM_ORE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "orichalcum_ore"), new BlockItem(ORICHALCUM_ORE, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		AutoOreRegistry.registerOre(ORICHALCUM_ORE);
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "mithril_ore"), MITHRIL_ORE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "mithril_ore"), new BlockItem(MITHRIL_ORE, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		AutoOreRegistry.registerOre(MITHRIL_ORE);
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "adamant_ore"), ADAMANT_ORE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "adamant_ore"), new BlockItem(ADAMANT_ORE, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
		AutoOreRegistry.registerOre(ADAMANT_ORE);
		Registry.register(Registry.BLOCK, new Identifier("dwarfen_legacy", "dwarfen_furnace"), CUSTOM_FURNACE);
		Registry.register(Registry.ITEM, new Identifier("dwarfen_legacy", "dwarfen_furnace"), new BlockItem(CUSTOM_FURNACE, new Item.Settings().group(ItemRegistry.DWARFEN_LEGACY)));
	}	

}	
