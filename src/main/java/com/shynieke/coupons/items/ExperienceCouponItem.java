package com.shynieke.coupons.items;

import com.shynieke.coupons.CouponReference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ExperienceCouponItem extends CouponItem {
	public ExperienceCouponItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);

		if (!worldIn.isClientSide()) {
			boolean creative = playerIn.getAbilities().instabuild;
			if (playerIn.isCrouching()) {
				playerIn.giveExperienceLevels(stack.getCount());
				if (!creative)
					stack.shrink(stack.getCount());
			} else {
				playerIn.giveExperienceLevels(1);
				if (!creative)
					stack.shrink(1);
			}
		}

		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand) {
		return super.interactLivingEntity(stack, playerIn, target, hand);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent(CouponReference.MOD_ID + ":experience_coupon_text").withStyle(ChatFormatting.GOLD));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}
}
