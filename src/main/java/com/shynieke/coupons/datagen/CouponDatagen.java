package com.shynieke.coupons.datagen;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.datagen.client.CouponLanguageProvider;
import com.shynieke.coupons.datagen.client.CouponModelProvider;
import com.shynieke.coupons.datagen.server.CouponTradeTagsProvider;
import com.shynieke.coupons.handler.TraderHandler;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

@EventBusSubscriber
public class CouponDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {

		event.createProvider(CouponLanguageProvider::new);
		event.createProvider(CouponModelProvider::new);
		event.createProvider(CouponTradeTagsProvider::new);

	}

	@SubscribeEvent
	public static void onGatherRegistries(GatherDataRegistryEntriesEvent event) {
		event.gatherFor(Reference.MOD_ID)
				.add(Registries.VILLAGER_TRADE, TraderHandler::bootstrap);
		event.gatherFor(Reference.MOD_ID)
				.add(CouponRecipeProvider.create());
	}
}
