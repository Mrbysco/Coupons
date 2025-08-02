package com.shynieke.coupons.registry;

import com.mojang.serialization.Codec;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.function.Supplier;

public class CouponRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MOD_ID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Reference.MOD_ID);

	public static final DeferredItem<BrewingCouponItem> BREWING_COUPON = ITEMS.registerItem("brewing_coupon", (properties) -> new BrewingCouponItem(properties.rarity(Rarity.RARE)));
	public static final DeferredItem<CraftingCouponItem> CRAFTING_COUPON = ITEMS.registerItem("crafting_coupon", (properties) -> new CraftingCouponItem(properties.rarity(Rarity.RARE)));
	public static final DeferredItem<ExperienceCouponItem> EXPERIENCE_COUPON = ITEMS.registerItem("experience_coupon", (properties) -> new ExperienceCouponItem(properties.rarity(Rarity.RARE)));
	public static final DeferredItem<FurnaceCouponItem> FURNACE_COUPON = ITEMS.registerItem("furnace_coupon", (properties) -> new FurnaceCouponItem(properties.rarity(Rarity.RARE)));
	public static final DeferredItem<LootCouponItem> LOOT_COUPON = ITEMS.registerItem("loot_coupon", (properties) -> new LootCouponItem(properties.rarity(Rarity.RARE)));
	public static final DeferredItem<TradingCouponItem> TRADING_COUPON = ITEMS.registerItem("trading_coupon", (properties) -> new TradingCouponItem(properties.rarity(Rarity.RARE)));

	public static final Supplier<AttachmentType<Boolean>> DOUBLE_LOOT = ATTACHMENT_TYPES.register(
			"double_loot", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL.fieldOf("value")).build());

	public static final Supplier<CreativeModeTab> COUPON_TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> new ItemStack(CouponRegistry.CRAFTING_COUPON.get()))
			.title(Component.translatable("itemGroup.coupons"))
			.displayItems((displayParameters, output) -> {
				List<ItemStack> stacks = CouponRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
				output.acceptAll(stacks);
			}).build());
}
