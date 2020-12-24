package com.shynieke.coupons.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.shynieke.coupons.CouponReference;
import com.shynieke.coupons.CouponRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;

public class ClientHandler {
    public static void registerItemColors(final ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        colors.register((p_198141_1_, p_198141_2_) -> {
            return p_198141_2_ > 0 ? -1 : PotionUtils.getColor(p_198141_1_);
        }, CouponRegistry.BREWING_COUPON.get());
    }

    public static void nameplateEvent(RenderNameplateEvent event) {
        Entity entity = event.getEntity();
        MatrixStack matrixStack = event.getMatrixStack();
        final ItemStack stack = new ItemStack(CouponRegistry.LOOT_COUPON.get());
        CompoundNBT nbt = entity.getPersistentData();
        if(entity.isAlive() && entity instanceof LivingEntity && nbt.contains(CouponReference.doubleLootTag)) {
            final LivingEntity livingEntity = (LivingEntity)entity;
            final float f = livingEntity.getHeight() + 0.3F;
            matrixStack.push();
            matrixStack.translate(0.0D, (double)f, 0.0D);
            matrixStack.scale(0.3F, 0.3F, 0.3F);
            float angle = 180 - MathHelper.lerp(event.getPartialTicks(), livingEntity.prevRotationYawHead, livingEntity.rotationYawHead);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(angle));

            Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(livingEntity, stack, ItemCameraTransforms.TransformType.NONE, false, event.getMatrixStack(), event.getRenderTypeBuffer(), event.getPackedLight());
            matrixStack.pop();
        }
    }
}
