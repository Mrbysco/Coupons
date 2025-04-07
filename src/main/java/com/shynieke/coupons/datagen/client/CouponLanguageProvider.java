package com.shynieke.coupons.datagen.client;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

public class CouponLanguageProvider extends LanguageProvider {
	public CouponLanguageProvider(PackOutput packOutput) {
		super(packOutput, Reference.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add("itemGroup.coupons", "Coupons Tab");

		addItem(CouponRegistry.CRAFTING_COUPON, "Crafting Coupon");
		addItem(CouponRegistry.FURNACE_COUPON, "Furnace Coupon");
		addItem(CouponRegistry.TRADING_COUPON, "Trading Coupon");
		addItem(CouponRegistry.BREWING_COUPON, "Brewing Coupon");
		addItem(CouponRegistry.LOOT_COUPON, "Loot Coupon");
		addItem(CouponRegistry.EXPERIENCE_COUPON, "Experience Coupon");

		add("coupons:crafting_coupon_text", "Craft an item get an ingredient back");
		add("coupons:furnace_coupon_text", "Smelt one item for FREE");
		add("coupons:trading_coupon_text", "Get a nice deal while trading");
		add("coupons:brewing_coupon_text", "Use one less water bottle while brewing");
		add("coupons:loot_coupon_text", "Double the loot of the mob you apply this coupon on");
		add("coupons:experience_coupon_text", "Get one free level of experience");

		addConfig("General", "General", "General Settings");
		addConfig("doubleBossLoot", "Double Boss Loot", "When enabled the loot coupon can double boss loot");
		addConfig("entityBlacklist", "Entity Blacklist", "A list of entities that can't have loot doubled [Syntax: 'minecraft:bat']");
		addConfig("Trades", "Trades", "Trade Settings");
		addConfig("enableBrewingCoupon", "Enable Brewing Coupon", "Defines whether or not the Brewing Coupon is obtainable from the Wandering Trader");
		addConfig("enableCraftingCoupon", "Enable Crafting Coupon", "Defines whether or not the Crafting Coupon is obtainable from the Wandering Trader");
		addConfig("enableExperienceCoupon", "Enable Experience Coupon", "Defines whether or not the Experience Coupon is obtainable from the Wandering Trader");
		addConfig("enableFurnaceCoupon", "Enable Furnace Coupon", "Defines whether or not the Furnace Coupon is obtainable from the Wandering Trader");
		addConfig("enableLootCoupon", "Enable Loot Coupon", "Defines whether or not the Loot Coupon is obtainable from the Wandering Trader");
		addConfig("enableTradingCoupon", "Enable Trading Coupon", "Defines whether or not the Trading Coupon is obtainable from the Wandering Trader");
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(Reference.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(Reference.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
