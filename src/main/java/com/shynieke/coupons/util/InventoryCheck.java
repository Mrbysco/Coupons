package com.shynieke.coupons.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.function.Supplier;

public class InventoryCheck {
    public static boolean hasCoupon(PlayerEntity player, Supplier<Item> item) {
        if(item.get() != null) {
            ItemStack offStack = player.getHeldItem(Hand.OFF_HAND);
            ItemStack mainStack = player.getHeldItem(Hand.MAIN_HAND);
            if ((!offStack.isEmpty() && offStack.getItem() == item.get()) || (!mainStack.isEmpty() && mainStack.getItem() == item.get())) {
                return true;
            } else {
                for(int i = 0; i < player.inventory.mainInventory.size(); ++i) {
                    ItemStack itemstack = player.inventory.mainInventory.get(i);
                    if (!itemstack.isEmpty() && itemstack.getItem() == item.get()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void shrinkCoupon(PlayerEntity player, Supplier<Item> item) {
        if(!player.abilities.isCreativeMode) {
            if(item.get() != null) {
                ItemStack offStack = player.getHeldItem(Hand.OFF_HAND);
                ItemStack mainStack = player.getHeldItem(Hand.MAIN_HAND);
                if (!offStack.isEmpty() && offStack.getItem() == item.get()) {
                    offStack.shrink(1);
                } else if (!mainStack.isEmpty() && mainStack.getItem() == item.get()) {
                    mainStack.shrink(1);
                } else {
                    for(int i = 0; i < player.inventory.mainInventory.size(); ++i) {
                        ItemStack itemstack = player.inventory.mainInventory.get(i);
                        if (!itemstack.isEmpty() && itemstack.getItem() == item.get()) {
                            itemstack.shrink(1);
                            break;
                        }
                    }
                }
            }
        }
    }
}
