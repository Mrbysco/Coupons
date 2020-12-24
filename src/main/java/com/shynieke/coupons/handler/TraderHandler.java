package com.shynieke.coupons.handler;

import com.shynieke.coupons.CouponRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.OptionalInt;
import java.util.Random;

public class TraderHandler {
    private final Random rand = new Random();

    @SubscribeEvent
    public void onWandererTradesEvent(WandererTradesEvent event) {
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.BREWING_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.CRAFTING_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.EXPERIENCE_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.FURNACE_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.LOOT_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
        event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.TRADING_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
    }

    public int getRandInRange(int min, int max) {
        OptionalInt randomNumber = rand.ints(min, (max + 1)).findFirst();
        return randomNumber.isPresent() ? randomNumber.getAsInt() : min;
    }

    public static class ItemsForEmeraldsTrade implements ITrade {
        private final ItemStack outputStack;
        private final int outputAmount;
        private final int priceAmount;
        private final int maxUses;
        private final int givenExp;
        private final float priceMultiplier;

        public ItemsForEmeraldsTrade(Block block, int outputAmount, int priceAmount, int maxUses, int givenExp) {
            this(new ItemStack(block), priceAmount, outputAmount, maxUses, givenExp);
        }

        public ItemsForEmeraldsTrade(Item item, int outputAmount, int priceAmount, int givenExp) {
            this(new ItemStack(item), priceAmount, outputAmount, 12, givenExp);
        }

        public ItemsForEmeraldsTrade(Item item, int outputAmount, int priceAmount, int maxUses, int givenExp) {
            this(new ItemStack(item), priceAmount, outputAmount, maxUses, givenExp);
        }

        public ItemsForEmeraldsTrade(ItemStack outputStack, int priceAmount, int outputAmount, int maxUses, int givenExp) {
            this(outputStack, priceAmount, outputAmount, maxUses, givenExp, 0.05F);
        }

        public ItemsForEmeraldsTrade(ItemStack outputStack, int priceAmount, int outputAmount, int maxUses, int givenExp, float priceMultiplier) {
            this.priceAmount = priceAmount;
            this.outputStack = outputStack;
            this.outputAmount = outputAmount;
            this.maxUses = maxUses;
            this.givenExp = givenExp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.priceAmount), new ItemStack(this.outputStack.getItem(), this.outputAmount), this.maxUses, this.givenExp, this.priceMultiplier);
        }
    }
}
