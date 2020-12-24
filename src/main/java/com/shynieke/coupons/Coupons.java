package com.shynieke.coupons;

import com.shynieke.coupons.client.ClientHandler;
import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.handler.BrewingHandler;
import com.shynieke.coupons.handler.CouponHandler;
import com.shynieke.coupons.handler.TraderHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CouponReference.MOD_ID)
public class Coupons {
    public static final Logger LOGGER = LogManager.getLogger(CouponReference.MOD_ID);

    public Coupons() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CouponConfig.commonSpec);
        eventBus.register(CouponConfig.class);

        eventBus.addListener(this::setup);
        CouponRegistry.ITEMS.register(eventBus);
        MinecraftForge.EVENT_BUS.register(new CouponHandler());
        MinecraftForge.EVENT_BUS.register(new TraderHandler());

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::registerItemColors);
            MinecraftForge.EVENT_BUS.addListener(ClientHandler::nameplateEvent);
        });
    }

    private void setup(final FMLCommonSetupEvent event) {
        BrewingHandler.registerBrewingRecipes();
    }
}
