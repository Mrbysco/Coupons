package com.shynieke.coupons.datagen;

import com.shynieke.coupons.datagen.client.CouponLanguageProvider;
import com.shynieke.coupons.datagen.client.CouponModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CouponDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();

		generator.addProvider(true, new CouponLanguageProvider(packOutput));
		generator.addProvider(true, new CouponModelProvider(packOutput, helper));
	}
}
