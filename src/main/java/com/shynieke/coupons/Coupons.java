package com.shynieke.coupons;

import com.mojang.logging.LogUtils;
import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.handler.CouponHandler;
import com.shynieke.coupons.registry.CouponRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Reference.MOD_ID)
public class Coupons {
	public static final Logger LOGGER = LogUtils.getLogger();

	public Coupons(IEventBus eventBus, Dist dist, ModContainer container) {
		container.registerConfig(ModConfig.Type.COMMON, CouponConfig.commonSpec);
		eventBus.register(CouponConfig.class);

		CouponRegistry.ATTACHMENT_TYPES.register(eventBus);
		CouponRegistry.ITEMS.register(eventBus);
		CouponRegistry.CREATIVE_MODE_TABS.register(eventBus);
		NeoForge.EVENT_BUS.register(new CouponHandler());

		if (dist.isClient()) {
			container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		}
	}
}
