package com.shynieke.coupons.items;

import com.shynieke.coupons.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CraftingCouponItem extends CouponItem {
	public CraftingCouponItem(Properties properties) {
		super(properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(Reference.MOD_ID + ":crafting_coupon_text").withStyle(ChatFormatting.GOLD));
		super.appendHoverText(stack, level, tooltip, flagIn);
	}
}
