package com.shynieke.coupons;

import com.shynieke.coupons.items.BrewingCouponItem;
import com.shynieke.coupons.items.CraftingCouponItem;
import com.shynieke.coupons.items.ExperienceCouponItem;
import com.shynieke.coupons.items.FurnaceCouponItem;
import com.shynieke.coupons.items.LootCouponItem;
import com.shynieke.coupons.items.TradingCouponItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CouponRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CouponReference.MOD_ID);

    public static final RegistryObject<Item> BREWING_COUPON = ITEMS.register("brewing_coupon", () -> new BrewingCouponItem(new Item.Properties().tab(CouponGroup.GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CRAFTING_COUPON = ITEMS.register("crafting_coupon", () -> new CraftingCouponItem(new Item.Properties().tab(CouponGroup.GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EXPERIENCE_COUPON = ITEMS.register("experience_coupon", () -> new ExperienceCouponItem(new Item.Properties().tab(CouponGroup.GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> FURNACE_COUPON = ITEMS.register("furnace_coupon", () -> new FurnaceCouponItem(new Item.Properties().tab(CouponGroup.GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LOOT_COUPON = ITEMS.register("loot_coupon", () -> new LootCouponItem(new Item.Properties().tab(CouponGroup.GROUP).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TRADING_COUPON = ITEMS.register("trading_coupon", () -> new TradingCouponItem(new Item.Properties().tab(CouponGroup.GROUP).rarity(Rarity.RARE)));

}
