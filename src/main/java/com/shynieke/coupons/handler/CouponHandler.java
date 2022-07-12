package com.shynieke.coupons.handler;

import com.shynieke.coupons.CouponReference;
import com.shynieke.coupons.CouponRegistry;
import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.util.InventoryCheck;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CouponHandler {

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		Player player = event.getEntity();
		ItemStack result = event.getCrafting();
		ResourceLocation location = ForgeRegistries.ITEMS.getKey(result.getItem());
		if (location != null && !location.getNamespace().equalsIgnoreCase(CouponReference.MOD_ID) &&
				InventoryCheck.hasCoupon(player, CouponRegistry.CRAFTING_COUPON)) {
			Container inventory = event.getInventory();
			ItemStack refundStack = ItemStack.EMPTY;
			for (int i = 0; i < inventory.getContainerSize(); i++) {
				ItemStack foundStack = inventory.getItem(i);
				if (!foundStack.isEmpty() && !foundStack.isDamageableItem() &&
						!foundStack.hasCraftingRemainingItem() && !FluidUtil.getFluidHandler(foundStack).isPresent() &&
						!hasEnergy(foundStack) && foundStack.getRarity() == Rarity.COMMON) {
					refundStack = foundStack.copy();
					break;
				}
			}
			if (!refundStack.isEmpty() && player.addItem(refundStack)) {
				InventoryCheck.shrinkCoupon(player, CouponRegistry.CRAFTING_COUPON);
				Containers.dropItemStack(player.level, player.getX(), player.getY(), player.getZ(), refundStack);
			}
		}
	}

	public static boolean hasEnergy(ItemStack itemStack) {
		return itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent();
	}

	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent.EntityInteract event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		CompoundTag nbt = target.getPersistentData();
		ItemStack stack = event.getItemStack();
		if (stack.getItem() == CouponRegistry.LOOT_COUPON.get() && !nbt.contains(CouponReference.doubleLootTag) && target instanceof LivingEntity) {
			List<? extends String> blacklist = CouponConfig.COMMON.entityBlacklist.get();
			ResourceLocation location = ForgeRegistries.ENTITY_TYPES.getKey(target.getType());
			if ((!blacklist.isEmpty() && location != null && !blacklist.contains(location.toString())) ||
					(CouponConfig.COMMON.doubleBossLoot.get() && !target.canChangeDimensions())) {
				nbt.putBoolean(CouponReference.doubleLootTag, true);
				if (!player.getAbilities().instabuild)
					stack.shrink(1);
			}
		}
	}

	@SubscribeEvent
	public void livingDropsEvent(LivingDropsEvent event) {
		LivingEntity entity = event.getEntity();
		CompoundTag nbt = entity.getPersistentData();
		if (nbt.contains(CouponReference.doubleLootTag) && nbt.getBoolean(CouponReference.doubleLootTag)) {
			Collection<ItemEntity> drops = event.getDrops();
			Collection<ItemEntity> extra = new ArrayList<>();
			for (ItemEntity drop : drops) {
				extra.add(new ItemEntity(drop.level, drop.getX(), drop.getY(), drop.getZ(), drop.getItem()));
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
					tag.putInt(CouponReference.offerSlotTag, offerSlot);
					tag.putInt(CouponReference.offerUsesTag, uses);
					tag.putInt(CouponReference.offerSpecialPrice, randomOffer.getSpecialPriceDiff());

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
			if (tag.contains(CouponReference.offerSlotTag) && tag.contains(CouponReference.offerUsesTag) && tag.contains(CouponReference.offerSpecialPrice)) {
				int offerSlot = tag.getInt(CouponReference.offerSlotTag);
				int previousUses = tag.getInt(CouponReference.offerUsesTag);
				int specialPrice = tag.getInt(CouponReference.offerSpecialPrice);
				tag.remove(CouponReference.offerSlotTag);
				tag.remove(CouponReference.offerUsesTag);
				tag.remove(CouponReference.offerSpecialPrice);
				MerchantOffer randomOffer = container.getOffers().get(offerSlot);
				if (previousUses != -1 && randomOffer.getUses() != previousUses) {
					randomOffer.setSpecialPriceDiff(specialPrice);
					InventoryCheck.shrinkCoupon(player, CouponRegistry.TRADING_COUPON);
				}
			}
		}
	}

}
