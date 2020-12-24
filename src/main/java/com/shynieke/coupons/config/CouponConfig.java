package com.shynieke.coupons.config;

import com.shynieke.coupons.Coupons;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
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

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("General settings")
                    .push("General");

            doubleBossLoot = builder
                    .comment("When enabled the loot coupon can double boss loot")
                    .define("doubleBossLoot", false);

            String[] entities = new String[] { "" };

            entityBlacklist = builder
                    .comment("A list of entities that can't have loot doubled [Syntax: 'minecraft:bat']")
                    .defineList("entityBlacklist", Arrays.asList(entities), o -> (o instanceof String));

            builder.pop();
            builder.comment("Wandering Trader Trades")
                    .push("Trades");

            enableBrewingCoupon = builder
                    .comment("Defines whether or not the Brewing Coupon is obtainable from the Wandering Trader")
                    .define("enableBrewingCoupon", true);

            enableCraftingCoupon = builder
                    .comment("Defines whether or not the Crafting Coupon is obtainable from the Wandering Trader")
                    .define("enableCraftingCoupon", true);

            enableExperienceCoupon = builder
                    .comment("Defines whether or not the Experience Coupon is obtainable from the Wandering Trader")
                    .define("enableExperienceCoupon", true);

            enableFurnaceCoupon = builder
                    .comment("Defines whether or not the Furnace Coupon is obtainable from the Wandering Trader")
                    .define("enableFurnaceCoupon", true);

            enableLootCoupon = builder
                    .comment("Defines whether or not the Loot Coupon is obtainable from the Wandering Trader")
                    .define("enableLootCoupon", true);

            enableTradingCoupon = builder
                    .comment("Defines whether or not the Trading Coupon is obtainable from the Wandering Trader")
                    .define("enableTradingCoupon", true);

            builder.pop();
        }
    }

    public static final ForgeConfigSpec commonSpec;
    public static final CouponConfig.Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        Coupons.LOGGER.debug("Loaded Coupon's config file {}", configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        Coupons.LOGGER.debug("Coupon's config just got changed on the file system!");
    }
}
