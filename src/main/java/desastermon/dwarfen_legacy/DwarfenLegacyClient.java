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

import desastermon.dwarfen_legacy.blocks.BlockRegistry;
import desastermon.dwarfen_legacy.blocks.entity.BlockEntityRegistry;
import desastermon.dwarfen_legacy.client.CustomFurnaceScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

public class DwarfenLegacyClient implements ClientModInitializer{

	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(BlockEntityRegistry.CUSTOM_FURNACE_SCREENHANDLER, CustomFurnaceScreen::new);
		BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ORICHALCUM_GLASS, RenderLayer.getTranslucent());
	}

}
