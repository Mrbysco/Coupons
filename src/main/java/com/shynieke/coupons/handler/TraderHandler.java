package com.shynieke.coupons.handler;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class TraderHandler {
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EMERALD_COUPON = resourceKey("wandering_trader/brewing_coupon");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_CRAFTING_COUPON = resourceKey("wandering_trader/crafting_coupon");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_EXPERIENCE_COUPON = resourceKey("wandering_trader/experience_coupon");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_FURNACE_COUPON = resourceKey("wandering_trader/furnace_coupon");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_LOOT_COUPON = resourceKey("wandering_trader/loot_coupon");
	public static final ResourceKey<VillagerTrade> WANDERING_TRADER_TRADING_COUPON = resourceKey("wandering_trader/trading_coupon");

	public static Holder<VillagerTrade> bootstrap(BootstrapContext<VillagerTrade> context) {
		context.register(WANDERING_TRADER_EMERALD_COUPON, new VillagerTrade.Builder(
				new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(CouponRegistry.BREWING_COUPON.get(), 1),
				ContextIntProviders.exactly(12), ContextIntProviders.exactly( 30), ContextFloatProviders.exactly(.05F)).build());

		context.register(WANDERING_TRADER_CRAFTING_COUPON, new VillagerTrade.Builder(
				new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(CouponRegistry.CRAFTING_COUPON.get(), 1),
				ContextIntProviders.exactly(12), ContextIntProviders.exactly( 30), ContextFloatProviders.exactly(.05F)).build());

		context.register(WANDERING_TRADER_EXPERIENCE_COUPON, new VillagerTrade.Builder(
				new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(CouponRegistry.EXPERIENCE_COUPON.get(), 1),
				ContextIntProviders.exactly(12), ContextIntProviders.exactly( 30), ContextFloatProviders.exactly(.05F)).build());

		context.register(WANDERING_TRADER_FURNACE_COUPON, new VillagerTrade.Builder(
				new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(CouponRegistry.FURNACE_COUPON.get(), 1),
				ContextIntProviders.exactly(12), ContextIntProviders.exactly( 30), ContextFloatProviders.exactly(.05F)).build());

		context.register(WANDERING_TRADER_LOOT_COUPON, new VillagerTrade.Builder(
				new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(CouponRegistry.LOOT_COUPON.get(), 1),
				ContextIntProviders.exactly(12), ContextIntProviders.exactly( 30), ContextFloatProviders.exactly(.05F)).build());

		return context.register(WANDERING_TRADER_TRADING_COUPON, new VillagerTrade.Builder(
				new TradeCost(Items.EMERALD, 3), new ItemStackTemplate(CouponRegistry.TRADING_COUPON.get(), 1),
				ContextIntProviders.exactly(12), ContextIntProviders.exactly( 30), ContextFloatProviders.exactly(.05F)).build());
	}

	public static ResourceKey<VillagerTrade> resourceKey(String path) {
		return ResourceKey.create(Registries.VILLAGER_TRADE, Reference.modLoc(path));
	}
}
