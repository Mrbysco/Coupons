package com.shynieke.coupons.handler;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.registry.CouponRegistry;
import com.shynieke.coupons.util.InventoryCheck;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.fluids.FluidUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CouponHandler {

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		Player player = event.getEntity();
		ItemStack result = event.getCrafting();
		ResourceLocation location = BuiltInRegistries.ITEM.getKey(result.getItem());
		if (location != null && !location.getNamespace().equalsIgnoreCase(Reference.MOD_ID) &&
				InventoryCheck.hasCoupon(player, CouponRegistry.CRAFTING_COUPON)) {
			Container inventory = event.getInventory();
			ItemStack refundStack = ItemStack.EMPTY;
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack foundStack = inventory.getItem(i);
				if (!foundStack.isEmpty() && !foundStack.isDamageableItem() &&
						!foundStack.hasCraftingRemainingItem() && !FluidUtil.getFluidHandler(foundStack).isPresent() &&
						!hasEnergy(foundStack) && foundStack.getRarity() == Rarity.COMMON) {
					refundStack = foundStack.copy();
					refundStack.setCount(1);
					break;
				}
			}
			if (!refundStack.isEmpty() && player.addItem(refundStack)) {
				InventoryCheck.shrinkCoupon(player, CouponRegistry.CRAFTING_COUPON);
				Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), refundStack);
			}
		}
	}

	public static boolean hasEnergy(ItemStack itemStack) {
		return itemStack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
	}

	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		CompoundTag nbt = target.getPersistentData();
		ItemStack stack = event.getItemStack();
		if (stack.getItem() == CouponRegistry.LOOT_COUPON.get() && !nbt.contains(Reference.doubleLootTag) && target instanceof LivingEntity) {
			List<? extends String> blacklist = CouponConfig.COMMON.entityBlacklist.get();
			ResourceLocation location = BuiltInRegistries.ENTITY_TYPE.getKey(target.getType());
			if ((!blacklist.isEmpty() && location != null && !blacklist.contains(location.toString())) ||
					(CouponConfig.COMMON.doubleBossLoot.get() && !target.canChangeDimensions())) {
				nbt.putBoolean(Reference.doubleLootTag, true);
				if (!player.getAbilities().instabuild)
					stack.shrink(1);
			}
		}
	}

	@SubscribeEvent
	public void livingDropsEvent(LivingDropsEvent event) {
		LivingEntity entity = event.getEntity();
		CompoundTag nbt = entity.getPersistentData();
		if (nbt.contains(Reference.doubleLootTag) && nbt.getBoolean(Reference.doubleLootTag)) {
			Collection<ItemEntity> drops = event.getDrops();
			Collection<ItemEntity> extra = new ArrayList<>();
			for (ItemEntity drop : drops) {
				extra.add(new ItemEntity(drop.level(), drop.getX(), drop.getY(), drop.getZ(), drop.getItem()));
			}

			drops.addAll(extra);
		}
	}

	@SubscribeEvent
	public void onContainer(PlayerContainerEvent.Open event) {
		Player player = event.getEntity();
		if (event.getContainer() instanceof MerchantMenu container && InventoryCheck.hasCoupon(player, CouponRegistry.TRADING_COUPON)) {
			if (!(container.trader instanceof WanderingTrader)) {
				int offerSlot = player.getRandom().nextInt(container.getOffers().size());
				MerchantOffer randomOffer = container.getOffers().get(offerSlot);
				int uses = randomOffer.getUses();

				if (randomOffer.getBaseCostA().getCount() > 1) {
					CompoundTag tag = player.getPersistentData();
					tag.putInt(Reference.offerSlotTag, offerSlot);
					tag.putInt(Reference.offerUsesTag, uses);
					tag.putInt(Reference.offerSpecialPrice, randomOffer.getSpecialPriceDiff());

					randomOffer.addToSpecialPriceDiff(-64);
				}
			}
		}
	}

	@SubscribeEvent
	public void onContainerClose(PlayerContainerEvent.Close event) {
		Player player = event.getEntity();
		if (event.getContainer() instanceof MerchantMenu container && InventoryCheck.hasCoupon(player, CouponRegistry.TRADING_COUPON)) {
			CompoundTag tag = player.getPersistentData();
			if (tag.contains(Reference.offerSlotTag) && tag.contains(Reference.offerUsesTag) && tag.contains(Reference.offerSpecialPrice)) {
				int offerSlot = tag.getInt(Reference.offerSlotTag);
				int previousUses = tag.getInt(Reference.offerUsesTag);
				int specialPrice = tag.getInt(Reference.offerSpecialPrice);
				tag.remove(Reference.offerSlotTag);
				tag.remove(Reference.offerUsesTag);
				tag.remove(Reference.offerSpecialPrice);
				MerchantOffer randomOffer = container.getOffers().get(offerSlot);
				if (previousUses != -1 && randomOffer.getUses() != previousUses) {
					randomOffer.setSpecialPriceDiff(specialPrice);
					InventoryCheck.shrinkCoupon(player, CouponRegistry.TRADING_COUPON);
				}
			}
		}
	}

}
