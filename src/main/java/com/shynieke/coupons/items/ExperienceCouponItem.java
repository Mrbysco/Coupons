package com.shynieke.coupons.items;

import com.shynieke.coupons.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class ExperienceCouponItem extends CouponItem {
	public ExperienceCouponItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);

		if (!level.isClientSide()) {
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

		return super.use(level, playerIn, handIn);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
		return super.interactLivingEntity(stack, player, target, hand);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
		consumer.accept(Component.translatable(Reference.MOD_ID + ":experience_coupon_text").withStyle(ChatFormatting.GOLD));
	}
}
