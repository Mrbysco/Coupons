package com.shynieke.coupons.client;

import com.google.common.reflect.TypeToken;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;

public class ClientHandler {
	public static final ContextKey<Boolean> DOUBLE_LOOT = new ContextKey<>(
			ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "double_loot"));
	public static final ContextKey<ItemStackRenderState> COUPON_RENDER_STATE = new ContextKey<>(
			ResourceLocation.fromNamespaceAndPath(Reference.MOD_ID, "coupon_render_state"));

	public static void registerCustomRenderData(RegisterRenderStateModifiersEvent event) {
		event.registerEntityModifier(new TypeToken<LivingEntityRenderer<?, ?, ?>>() {
		}, (living, state) -> {
			if (living instanceof Player) return;

			state.setRenderData(DOUBLE_LOOT, living.getData(CouponRegistry.DOUBLE_LOOT));
			state.setRenderData(COUPON_RENDER_STATE, new ItemStackRenderState());
		});
	}

	public static void nameplateEvent(RenderLivingEvent.Pre<?, ?, ?> event) {
		LivingEntityRenderState renderState = event.getRenderState();
		PoseStack poseStack = event.getPoseStack();
		boolean doubleLoot = renderState.getRenderDataOrDefault(DOUBLE_LOOT, false);
		if (doubleLoot) {
			final ItemStack stack = new ItemStack(CouponRegistry.LOOT_COUPON.get());
			final double f = renderState.boundingBoxHeight + 0.3F;
			final Minecraft mc = Minecraft.getInstance();
			poseStack.pushPose();
			poseStack.translate(0.0D, f, 0.0D);
			poseStack.scale(0.3F, 0.3F, 0.3F);
			float angle = renderState.bodyRot;
			poseStack.mulPose(Axis.YP.rotationDegrees(angle));
			ItemStackRenderState stackState = renderState.getRenderDataOrDefault(COUPON_RENDER_STATE, new ItemStackRenderState());
			mc.getItemModelResolver()
					.updateForTopItem(stackState, stack, ItemDisplayContext.GUI, false, null, null, 0);

			stackState.render(poseStack, event.getMultiBufferSource(), event.getPackedLight(), OverlayTexture.NO_OVERLAY);
			poseStack.popPose();
		}
	}
}
