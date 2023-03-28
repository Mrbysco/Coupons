package com.shynieke.coupons.handler;

import com.shynieke.coupons.CouponRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class BrewingHandler {
	public static void registerBrewingRecipes() {
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(CouponRegistry.BREWING_COUPON.get()), Potions.WATER)),
				Ingredient.of(Items.NETHER_WART), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD));
	}
}
