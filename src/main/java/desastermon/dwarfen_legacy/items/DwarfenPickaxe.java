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

import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class DwarfenPickaxe extends PickaxeItem {
	
	private final boolean ENCHANTABLE;

	protected DwarfenPickaxe(ToolMaterials material, float attackSpeed, Settings settings) {
		super(material, (int) material.getAttackDamage(), attackSpeed, settings.maxDamage(material.getDurability()));
		ENCHANTABLE = !material.equals(ToolMaterials.BROKEN_ANCIENT);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return super.isEnchantable(stack) && ENCHANTABLE;
	}	

}
