package com.shynieke.coupons.items;

import com.shynieke.coupons.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;

public class BrewingCouponItem extends CouponItem {
	public BrewingCouponItem(Properties properties) {
		super(properties.stacksTo(1));
	}

	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		stack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(Reference.MOD_ID + ":brewing_coupon_text").withStyle(ChatFormatting.GOLD));
		super.appendHoverText(stack, context, tooltip, flagIn);
	}
}
