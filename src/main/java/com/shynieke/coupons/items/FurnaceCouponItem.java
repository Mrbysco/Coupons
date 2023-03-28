package com.shynieke.coupons.items;

import com.shynieke.coupons.CouponReference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class FurnaceCouponItem extends CouponItem {
	public FurnaceCouponItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
		return 200;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent(CouponReference.MOD_ID + ":furnace_coupon_text").withStyle(ChatFormatting.GOLD));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
