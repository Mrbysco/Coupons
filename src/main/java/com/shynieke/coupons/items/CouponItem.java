package com.shynieke.coupons.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CouponItem extends Item {
    public CouponItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 200;
    }
}