package com.shynieke.coupons.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.shynieke.coupons.CouponReference;
import com.shynieke.coupons.CouponRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;

public class ClientHandler {
	public static void registerItemColors(final ColorHandlerEvent.Item event) {
		ItemColors colors = event.getItemColors();
		colors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack), CouponRegistry.BREWING_COUPON.get());
	}

	public static void nameplateEvent(RenderNameplateEvent event) {
		Entity entity = event.getEntity();
		PoseStack poseStack = event.getPoseStack();
		final ItemStack stack = new ItemStack(CouponRegistry.LOOT_COUPON.get());
		CompoundTag nbt = entity.getPersistentData();
		if (entity.isAlive() && entity instanceof final LivingEntity livingEntity && nbt.contains(CouponReference.doubleLootTag)) {
			final float f = livingEntity.getBbHeight() + 0.3F;
			poseStack.pushPose();
			poseStack.translate(0.0D, (double) f, 0.0D);
			poseStack.scale(0.3F, 0.3F, 0.3F);
			float angle = 180 - Mth.lerp(event.getPartialTick(), livingEntity.yHeadRotO, livingEntity.yHeadRot);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));

			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(livingEntity, stack,
					ItemTransforms.TransformType.NONE, false, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
			poseStack.popPose();
		}
	}
}
