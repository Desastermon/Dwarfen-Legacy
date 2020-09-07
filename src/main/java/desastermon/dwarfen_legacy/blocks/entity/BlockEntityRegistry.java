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

import desastermon.dwarfen_legacy.blocks.BlockRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntityRegistry {
	
	public static BlockEntityType<CustomFurnaceBlockEntity> CUSTOM_FURNACE_ENTITY;
	public static ScreenHandlerType<CustomFurnaceScreenHandler> CUSTOM_FURNACE_SCREENHANDLER;
	
	public static void register() {
		CUSTOM_FURNACE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("dwarfen_legacy", "custom_furnace_block_entity"), BlockEntityType.Builder.create(CustomFurnaceBlockEntity::new,  BlockRegistry.CUSTOM_FURNACE).build(null));
		CUSTOM_FURNACE_SCREENHANDLER = ScreenHandlerRegistry.registerExtended(new Identifier("dwarfen_legacy", "custom_furnace_screenhandler"), CustomFurnaceScreenHandler::new);
	}
}
