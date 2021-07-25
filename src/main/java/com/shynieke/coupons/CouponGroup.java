package com.shynieke.coupons;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CouponGroup {
    public static final CreativeModeTab GROUP = new CreativeModeTab(CouponReference.MOD_ID + ".couponGroup") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(CouponRegistry.CRAFTING_COUPON.get());
        }
    };
}
