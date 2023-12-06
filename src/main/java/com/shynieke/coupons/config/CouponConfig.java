package com.shynieke.coupons.config;

import com.shynieke.coupons.Coupons;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;

public class CouponConfig {
	public static class Common {
		public final BooleanValue doubleBossLoot;
		public final ConfigValue<List<? extends String>> entityBlacklist;

		public final BooleanValue enableBrewingCoupon;
		public final BooleanValue enableCraftingCoupon;
		public final BooleanValue enableExperienceCoupon;
		public final BooleanValue enableFurnaceCoupon;
		public final BooleanValue enableLootCoupon;
		public final BooleanValue enableTradingCoupon;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			doubleBossLoot = builder
					.comment("When enabled the loot coupon can double boss loot")
					.define("doubleBossLoot", false);

			entityBlacklist = builder
					.comment("A list of entities that can't have loot doubled [Syntax: 'minecraft:bat']")
					.defineListAllowEmpty(Collections.singletonList("entityBlacklist"), () -> Collections.singletonList(""), o -> (o instanceof String));

			builder.pop();
			builder.comment("Wandering Trader Trades")
					.push("Trades");

			enableBrewingCoupon = builder
					.comment("Defines whether or not the Brewing Coupon is obtainable from the Wandering Trader")
					.worldRestart()
					.define("enableBrewingCoupon", true);

			enableCraftingCoupon = builder
					.comment("Defines whether or not the Crafting Coupon is obtainable from the Wandering Trader")
					.worldRestart()
					.define("enableCraftingCoupon", true);

			enableExperienceCoupon = builder
					.comment("Defines whether or not the Experience Coupon is obtainable from the Wandering Trader")
					.worldRestart()
					.define("enableExperienceCoupon", true);

			enableFurnaceCoupon = builder
					.comment("Defines whether or not the Furnace Coupon is obtainable from the Wandering Trader")
					.worldRestart()
					.define("enableFurnaceCoupon", true);

			enableLootCoupon = builder
					.comment("Defines whether or not the Loot Coupon is obtainable from the Wandering Trader")
					.worldRestart()
					.define("enableLootCoupon", true);

			enableTradingCoupon = builder
					.comment("Defines whether or not the Trading Coupon is obtainable from the Wandering Trader")
					.worldRestart()
					.define("enableTradingCoupon", true);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final CouponConfig.Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		Coupons.LOGGER.debug("Loaded Coupon's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		Coupons.LOGGER.debug("Coupon's config just got changed on the file system!");
	}
}
