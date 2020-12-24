package com.shynieke.coupons.items;

import com.shynieke.coupons.CouponReference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TradingCouponItem extends CouponItem {
    public TradingCouponItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(CouponReference.MOD_ID + ":trading_coupon_text").mergeStyle(TextFormatting.GOLD));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
