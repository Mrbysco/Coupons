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

        Common(ForgeConfigSpec.Builder builder) {
            builder.comment("Server settings")
                    .push("Server");

            doubleBossLoot = builder
                    .comment("When enabled the loot coupon can double boss loot")
                    .define("doubleBossLoot", false);

            String[] entities = new String[] { "" };

            entityBlacklist = builder
                    .comment("A list of entities that can't have loot doubled [Syntax: 'minecraft:bat']")
                    .defineList("entityBlacklist", Arrays.asList(entities), o -> (o instanceof String));

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
