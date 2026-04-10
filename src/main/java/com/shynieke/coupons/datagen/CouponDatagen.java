package com.shynieke.coupons.datagen;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.datagen.client.CouponLanguageProvider;
import com.shynieke.coupons.datagen.client.CouponModelProvider;
import com.shynieke.coupons.datagen.server.CouponDatapackProvider;
import com.shynieke.coupons.datagen.server.CouponTradeTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class CouponDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new CouponLanguageProvider(packOutput));
		generator.addProvider(true, new CouponModelProvider(packOutput));

		generator.addProvider(true, new CouponTradeTagsProvider(packOutput, lookupProvider));
		generator.addProvider(true, new CouponDatapackProvider(
				packOutput,
				event.getLookupProvider(),
				Set.of(Reference.MOD_ID)
		));
	}
}
