/*******************************************************************************
 * Copyright (c) 2020 Nick Pardun.
 *  This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3 (GPLv3)
 * which accompanies this distribution, and is available at
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * SPDX-License-Identifier: GPL-3.0-only
 *******************************************************************************/
package desastermon.dwarfen_legacy.client;

import com.mojang.blaze3d.systems.RenderSystem;

import desastermon.dwarfen_legacy.blocks.entity.CustomFurnaceScreenHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomFurnaceScreen extends HandledScreen<ScreenHandler>{
	
	private static final Identifier TEXTURE = new Identifier("dwarfen_legacy", "textures/screen/dwarfen_furnace_gui.png");

	public CustomFurnaceScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	@Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int l;
        if (((CustomFurnaceScreenHandler)this.handler).isBurning()) {
           l = ((CustomFurnaceScreenHandler)this.handler).getFuelProgress();
           this.drawTexture(matrices, x + 56, y + 36 + 12 - l, 176, 12 - l, 14, l + 1);
        }

        l = ((CustomFurnaceScreenHandler)this.handler).getCookProgress();
        this.drawTexture(matrices, x + 79, y + 34, 176, 14, l + 1, 16);
    }
 
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
 
    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
	
}
