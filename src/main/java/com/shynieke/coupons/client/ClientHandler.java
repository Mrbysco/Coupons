package com.shynieke.coupons.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.coupons.CouponReference;
import com.shynieke.coupons.CouponRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RenderNameTagEvent;

public class ClientHandler {
	public static void registerItemColors(final RegisterColorHandlersEvent.Item event) {
		event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack), CouponRegistry.BREWING_COUPON.get());
	}

	public static void nameplateEvent(RenderNameTagEvent event) {
		Entity entity = event.getEntity();
		PoseStack poseStack = event.getPoseStack();
		final ItemStack stack = new ItemStack(CouponRegistry.LOOT_COUPON.get());
		CompoundTag nbt = entity.getPersistentData();
		if (entity.isAlive() && entity instanceof LivingEntity livingEntity && nbt.contains(CouponReference.doubleLootTag)) {
			final float f = livingEntity.getBbHeight() + 0.3F;
			poseStack.pushPose();
			poseStack.translate(0.0D, (double) f, 0.0D);
			poseStack.scale(0.3F, 0.3F, 0.3F);
			float angle = 180 - Mth.lerp(event.getPartialTick(), livingEntity.yHeadRotO, livingEntity.yHeadRot);
			poseStack.mulPose(Axis.YP.rotationDegrees(angle));

			Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(livingEntity, stack,
					ItemDisplayContext.NONE, false, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
			poseStack.popPose();
		}
	}
}
