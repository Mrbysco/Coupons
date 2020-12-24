package com.shynieke.coupons;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CouponGroup {
    public static final ItemGroup GROUP = new ItemGroup(CouponReference.MOD_ID + ".couponGroup") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(CouponRegistry.CRAFTING_COUPON.get());
        }
    };
}
