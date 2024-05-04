package com.shynieke.coupons.items;

import com.shynieke.coupons.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class TradingCouponItem extends CouponItem {
	public TradingCouponItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(Reference.MOD_ID + ":trading_coupon_text").withStyle(ChatFormatting.GOLD));
		super.appendHoverText(stack, context, tooltip, flagIn);
	}
}
