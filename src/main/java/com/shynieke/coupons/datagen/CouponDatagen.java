package com.shynieke.coupons.datagen;

import com.shynieke.coupons.datagen.client.CouponLanguageProvider;
import com.shynieke.coupons.datagen.client.CouponModelProvider;
import com.shynieke.coupons.datagen.server.CouponTradeTagsProvider;
import com.shynieke.coupons.handler.TraderHandler;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class CouponDatagen {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.VILLAGER_TRADE, TraderHandler::bootstrap);

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createDatapackRegistryObjects(BUILDER);

		event.createProvider(CouponLanguageProvider::new);
		event.createProvider(CouponModelProvider::new);
		event.createProvider(CouponTradeTagsProvider::new);
	}
}
