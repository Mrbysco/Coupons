package com.shynieke.coupons;

import com.mojang.logging.LogUtils;
import com.shynieke.coupons.client.ClientHandler;
import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.handler.BrewingHandler;
import com.shynieke.coupons.handler.CouponHandler;
import com.shynieke.coupons.handler.TraderHandler;
import com.shynieke.coupons.registry.CouponRegistry;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(CouponReference.MOD_ID)
public class Coupons {
	public static final Logger LOGGER = LogUtils.getLogger();

	public Coupons() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CouponConfig.commonSpec);
		eventBus.register(CouponConfig.class);

		eventBus.addListener(this::setup);
		CouponRegistry.ITEMS.register(eventBus);
		CouponRegistry.CREATIVE_MODE_TABS.register(eventBus);
		NeoForge.EVENT_BUS.register(new CouponHandler());
		NeoForge.EVENT_BUS.register(new TraderHandler());

		if (FMLEnvironment.dist.isClient()) {
			eventBus.addListener(ClientHandler::registerItemColors);
			NeoForge.EVENT_BUS.addListener(ClientHandler::nameplateEvent);
		}
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			BrewingHandler.registerBrewingRecipes();
		});
	}
}
