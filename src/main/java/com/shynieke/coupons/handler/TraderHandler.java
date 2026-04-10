package com.shynieke.coupons.handler;

import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerTrades.ItemListing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;
import java.util.Random;

public class TraderHandler {
	private final Random rand = new Random();

	@SubscribeEvent
	public void onWandererTradesEvent(WandererTradesEvent event) {
		if (CouponConfig.COMMON.enableBrewingCoupon.get()) {
			event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.BREWING_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
		}
		if (CouponConfig.COMMON.enableCraftingCoupon.get()) {
			event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.CRAFTING_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
		}
		if (CouponConfig.COMMON.enableExperienceCoupon.get()) {
			event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.EXPERIENCE_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
		}
		if (CouponConfig.COMMON.enableFurnaceCoupon.get()) {
			event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.FURNACE_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
		}
		if (CouponConfig.COMMON.enableLootCoupon.get()) {
			event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.LOOT_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
		}
		if (CouponConfig.COMMON.enableTradingCoupon.get()) {
			event.getGenericTrades().add(new TraderHandler.ItemsForEmeraldsTrade(CouponRegistry.TRADING_COUPON.get(), getRandInRange(1, 8), getRandInRange(10, 28), getRandInRange(1, 12), 5));
		}
	}

	public int getRandInRange(int min, int max) {
		OptionalInt randomNumber = rand.ints(min, (max + 1)).findFirst();
		return randomNumber.isPresent() ? randomNumber.getAsInt() : min;
	}

	public static class ItemsForEmeraldsTrade implements ItemListing {
		private final ItemStack outputStack;
		private final int outputAmount;
		private final int priceAmount;
		private final int maxUses;
		private final int givenExp;
		private final float priceMultiplier;

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

		@Override
		public @Nullable MerchantOffer getOffer(ServerLevel serverLevel, Entity entity, RandomSource randomSource) {
			return new MerchantOffer(new ItemCost(Items.EMERALD, this.priceAmount),
					new ItemStack(this.outputStack.getItem(), this.outputAmount),
					this.maxUses, this.givenExp, this.priceMultiplier);
		}
	}
}
