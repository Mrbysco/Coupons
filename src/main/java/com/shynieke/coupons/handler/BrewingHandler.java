package com.shynieke.coupons.handler;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class BrewingHandler {

	@SubscribeEvent
	public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
		var builder = event.getBuilder();
		ItemStack awkwardStack = Items.POTION.getDefaultInstance();
		awkwardStack.update(DataComponents.POTION_CONTENTS, PotionContents.EMPTY, Potions.AWKWARD, PotionContents::withPotion);

		builder.addRecipe(Ingredient.of(CouponRegistry.BREWING_COUPON.get()),
				Ingredient.of(Items.NETHER_WART), awkwardStack);
	}
}
