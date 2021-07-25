package com.shynieke.coupons.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class InventoryCheck {
    public static boolean hasCoupon(Player player, Supplier<Item> item) {
        if(item.get() != null) {
            ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);
            ItemStack mainStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if ((!offStack.isEmpty() && offStack.getItem() == item.get()) || (!mainStack.isEmpty() && mainStack.getItem() == item.get())) {
                return true;
            } else {
                for(int i = 0; i < player.getInventory().items.size(); ++i) {
                    ItemStack itemstack = player.getInventory().items.get(i);
                    if (!itemstack.isEmpty() && itemstack.getItem() == item.get()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void shrinkCoupon(Player player, Supplier<Item> item) {
        if(!player.getAbilities().instabuild) {
            if(item.get() != null) {
                ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);
                ItemStack mainStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (!offStack.isEmpty() && offStack.getItem() == item.get()) {
                    offStack.shrink(1);
                } else if (!mainStack.isEmpty() && mainStack.getItem() == item.get()) {
                    mainStack.shrink(1);
                } else {
                    for(int i = 0; i < player.getInventory().items.size(); ++i) {
                        ItemStack itemstack = player.getInventory().items.get(i);
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
