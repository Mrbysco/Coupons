package com.shynieke.coupons.datagen.client;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class CouponModelProvider extends ModelProvider {
	public CouponModelProvider(PackOutput packOutput) {
		super(packOutput, Reference.MOD_ID);
	}

	@Override
	protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
		for (DeferredHolder<Item, ? extends Item> registryObject : CouponRegistry.ITEMS.getEntries()) {
			if (registryObject.getId().equals(CouponRegistry.BREWING_COUPON.getId())) {
				generatePotionCoupon(itemModels);
			} else {
				itemModels.generateFlatItem(registryObject.get(), ModelTemplates.FLAT_ITEM);
			}
		}
	}

	public void generatePotionCoupon(ItemModelGenerators generators) {
		Item item = CouponRegistry.BREWING_COUPON.get();
		Identifier resourcelocation = generators.generateLayeredItem(
				item, ModelLocationUtils.decorateItemModelLocation("coupons:brewing_coupon_overlay"),
				ModelLocationUtils.getModelLocation(item)
		);
		generators.addPotionTint(item, resourcelocation);
	}

}
