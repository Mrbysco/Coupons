package com.shynieke.coupons.handler;

import com.shynieke.coupons.CouponRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;

public class BrewingHandler {
    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(Ingredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(CouponRegistry.BREWING_COUPON.get()), Potions.WATER)), Ingredient.fromItems(Items.NETHER_WART), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD));
    }
}
