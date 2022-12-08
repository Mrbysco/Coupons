package com.shynieke.coupons;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class CouponGroup {
	private static CreativeModeTab GROUP;

	@SubscribeEvent
	public void registerCreativeTabs(final CreativeModeTabEvent.Register event) {
		GROUP = event.registerCreativeModeTab(new ResourceLocation(CouponReference.MOD_ID, "coupon"), builder ->
				builder.icon(() -> new ItemStack(CouponRegistry.CRAFTING_COUPON.get()))
						.title(Component.translatable("itemGroup.coupons.couponGroup"))
						.displayItems((features, output, hasPermissions) -> {
							List<ItemStack> stacks = CouponRegistry.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).toList();
							output.acceptAll(stacks);
						}));
	}
}
