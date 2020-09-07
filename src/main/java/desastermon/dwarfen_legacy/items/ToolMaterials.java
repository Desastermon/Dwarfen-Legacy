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

import java.util.function.Supplier;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Lazy;

public enum ToolMaterials implements ToolMaterial{
	RUBY(3, 1600, 8.5F, 2.5F, 11, () -> {
		return Ingredient.ofItems(ItemRegistry.RUBY);
	}),
	RUBLIL(4, 750, 11.0F, 2.0F, 24, () -> {
		return Ingredient.ofItems(ItemRegistry.RUBLIL_INGOT);
	}), //Decide between Netherite or crafting Rublil
	ORICHALCUM(5, 1700, 11.0F, 4.0F, 4, () -> {
		return Ingredient.ofItems(ItemRegistry.ORICHALCUM_INGOT);
	}), //Can be mined with Level 4
	MITHRIL(5, 5000, 8.0F, 3.0F, 28, () -> {
		return Ingredient.ofItems(ItemRegistry.MITHRIL_INGOT);
	}), //Ore is mining level 5
	ADAMANT(6, 2500, 13.0F, 4.0F, 16, () -> {
		return Ingredient.ofItems(ItemRegistry.ADAMANT_INGOT);
	}), //Ore is mining level 5
	BROKEN_ANCIENT(0, -1, 0.0F, 0.0F, 0, () -> {
		return Ingredient.ofItems(Items.BEDROCK);
	}), //can be found in chests, is not enchantable (unbreakable)
	ANCIENT(7, 7500, 20.0F, 10.0F, 30, () -> {
		return Ingredient.ofItems(Items.OBSIDIAN);
	}); //must be repaired from broken dwarfen tools (with essence of every ore), if breaks returns to broken ancient

	private final int miningLevel;
	private final int itemDurability;
	private final float miningSpeed;
	private final float attackDamage;
	private final int enchantability;
	private final Lazy<Ingredient> repairIngredient;

	private ToolMaterials(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
		this.miningLevel = miningLevel;
		this.itemDurability = itemDurability;
		this.miningSpeed = miningSpeed;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairIngredient = new Lazy<>(repairIngredient);
	}

	public int getDurability() {
		return this.itemDurability;
	}

	public float getMiningSpeedMultiplier() {
		return this.miningSpeed;
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public int getMiningLevel() {
		return this.miningLevel;
	}

	public int getEnchantability() {
		return this.enchantability;
	}

	public Ingredient getRepairIngredient() {
		return (Ingredient)this.repairIngredient.get();
	}



}
