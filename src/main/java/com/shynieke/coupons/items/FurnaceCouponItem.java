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

public class FurnaceCouponItem extends CouponItem {
    public FurnaceCouponItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(CouponReference.MOD_ID + ":furnace_coupon_text").withStyle(TextFormatting.GOLD));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
