package com.shynieke.coupons.datagen.server;

import com.shynieke.coupons.handler.TraderHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.VillagerTradeTags;

import java.util.concurrent.CompletableFuture;

public class CouponTradeTagsProvider extends VillagerTradesTagsProvider {
	public CouponTradeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		tag(VillagerTradeTags.WANDERING_TRADER_UNCOMMON).add(
				TraderHandler.WANDERING_TRADER_EMERALD_COUPON,
				TraderHandler.WANDERING_TRADER_CRAFTING_COUPON,
				TraderHandler.WANDERING_TRADER_EXPERIENCE_COUPON,
				TraderHandler.WANDERING_TRADER_FURNACE_COUPON,
				TraderHandler.WANDERING_TRADER_LOOT_COUPON,
				TraderHandler.WANDERING_TRADER_TRADING_COUPON
		);
	}
}
