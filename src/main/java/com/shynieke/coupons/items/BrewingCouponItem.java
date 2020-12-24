package com.shynieke.coupons.items;

import com.shynieke.coupons.CouponReference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BrewingCouponItem extends CouponItem {
    public BrewingCouponItem(Properties properties) {
        super(properties.maxStackSize(1));
    }

    @Override
    public ItemStack getDefaultInstance() {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), Potions.WATER);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(CouponReference.MOD_ID + ":brewing_coupon_text").mergeStyle(TextFormatting.GOLD));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
