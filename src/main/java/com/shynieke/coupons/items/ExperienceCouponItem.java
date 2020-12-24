package com.shynieke.coupons.items;

import com.shynieke.coupons.CouponReference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ExperienceCouponItem extends CouponItem {
    public ExperienceCouponItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!worldIn.isRemote()) {
            boolean creative = playerIn.abilities.isCreativeMode;
            if (playerIn.isCrouching()) {
                playerIn.addExperienceLevel(stack.getCount());
                if(!creative)
                    stack.shrink(stack.getCount());
            } else {
                playerIn.addExperienceLevel(1);
                if(!creative)
                    stack.shrink(1);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(CouponReference.MOD_ID + ":experience_coupon_text").mergeStyle(TextFormatting.GOLD));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
