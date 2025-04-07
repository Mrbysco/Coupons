package com.shynieke.coupons.datagen.client;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class CouponModelProvider extends ItemModelProvider {
	public CouponModelProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Reference.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		for (DeferredHolder<Item, ? extends Item> registryObject : CouponRegistry.ITEMS.getEntries()) {
			generatedItem(registryObject.getId());
		}
	}

	private void generatedItem(ResourceLocation location) {
		if (location.getPath().equalsIgnoreCase("brewing_coupon")) {
			singleTexture(location.getPath(), ResourceLocation.withDefaultNamespace("item/generated"),
					"layer0", Reference.modLoc("item/" + location.getPath()).withSuffix("_overlay")).texture("layer1", Reference.modLoc("item/" + location.getPath()));
		} else {
			singleTexture(location.getPath(), ResourceLocation.withDefaultNamespace("item/generated"),
					"layer0", Reference.modLoc("item/" + location.getPath()));
		}
	}
}
