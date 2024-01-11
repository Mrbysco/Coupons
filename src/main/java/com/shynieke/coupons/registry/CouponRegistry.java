package com.shynieke.coupons.registry;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.items.BrewingCouponItem;
import com.shynieke.coupons.items.CraftingCouponItem;
import com.shynieke.coupons.items.ExperienceCouponItem;
import com.shynieke.coupons.items.FurnaceCouponItem;
import com.shynieke.coupons.items.LootCouponItem;
import com.shynieke.coupons.items.TradingCouponItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class CouponRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);

	public static final DeferredItem<BrewingCouponItem> BREWING_COUPON = ITEMS.register("brewing_coupon", () -> new BrewingCouponItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<CraftingCouponItem> CRAFTING_COUPON = ITEMS.register("crafting_coupon", () -> new CraftingCouponItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<ExperienceCouponItem> EXPERIENCE_COUPON = ITEMS.register("experience_coupon", () -> new ExperienceCouponItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<FurnaceCouponItem> FURNACE_COUPON = ITEMS.register("furnace_coupon", () -> new FurnaceCouponItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<LootCouponItem> LOOT_COUPON = ITEMS.register("loot_coupon", () -> new LootCouponItem(new Item.Properties().rarity(Rarity.RARE)));
	public static final DeferredItem<TradingCouponItem> TRADING_COUPON = ITEMS.register("trading_coupon", () -> new TradingCouponItem(new Item.Properties().rarity(Rarity.RARE)));


	public static final Supplier<CreativeModeTab> COUPON_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(CouponRegistry.CRAFTING_COUPON.get()))
			.title(Component.translatable("itemGroup.coupons"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = CouponRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
