package com.shynieke.coupons.handler;

import com.shynieke.coupons.CouponReference;
import com.shynieke.coupons.CouponRegistry;
import com.shynieke.coupons.config.CouponConfig;
import com.shynieke.coupons.util.InventoryCheck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CouponHandler {

    @SubscribeEvent
    public void onCrafting(ItemCraftedEvent event) {
        PlayerEntity player = event.getPlayer();
        ItemStack result = event.getCrafting();
        if((result.getItem().getRegistryName() != null && !result.getItem().getRegistryName().getNamespace().equalsIgnoreCase(CouponReference.MOD_ID)) &&
                InventoryCheck.hasCoupon(player, CouponRegistry.CRAFTING_COUPON)) {
            IInventory inventory = event.getInventory();
            ItemStack refundStack = ItemStack.EMPTY;
            for(int i = 0; i < inventory.getSizeInventory(); i++) {
                ItemStack foundStack = inventory.getStackInSlot(i);
                if(!foundStack.isEmpty() && !foundStack.isDamageable() &&
                        !foundStack.hasContainerItem() && !FluidUtil.getFluidHandler(foundStack).isPresent() &&
                        !hasEnergy(foundStack) && foundStack.getRarity() == Rarity.COMMON) {
                    refundStack = foundStack.copy();
                    break;
                }
            }
            if(!refundStack.isEmpty() && player.addItemStackToInventory(refundStack)) {
                InventoryCheck.shrinkCoupon(player, CouponRegistry.CRAFTING_COUPON);
                InventoryHelper.spawnItemStack(player.world, player.getPosX(), player.getPosY(), player.getPosZ(), refundStack);
            }
        }
    }

    public static boolean hasEnergy(ItemStack itemStack) {
        return itemStack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.EntityInteract event) {
        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();
        CompoundNBT nbt = target.getPersistentData();
        ItemStack stack = event.getItemStack();
        if(stack.getItem() == CouponRegistry.LOOT_COUPON.get() && !nbt.contains(CouponReference.doubleLootTag) && target instanceof LivingEntity) {
            List<? extends String> blacklist = CouponConfig.COMMON.entityBlacklist.get();
            if((!blacklist.isEmpty() && !blacklist.contains(target.getType().getRegistryName().toString())) ||
                    (CouponConfig.COMMON.doubleBossLoot.get() && !target.isNonBoss())) {
                nbt.putBoolean(CouponReference.doubleLootTag, true);
                if(!player.abilities.isCreativeMode)
                    stack.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public void livingDropsEvent(LivingDropsEvent event) {
        LivingEntity entity = event.getEntityLiving();
        CompoundNBT nbt = entity.getPersistentData();
        if(nbt.contains(CouponReference.doubleLootTag) && nbt.getBoolean(CouponReference.doubleLootTag)) {
            Collection<ItemEntity> drops = event.getDrops();
            Collection<ItemEntity> extra = new ArrayList<>();
            for(ItemEntity drop : drops) {
                extra.add(new ItemEntity(drop.world, drop.getPosX(), drop.getPosY(), drop.getPosZ(), drop.getItem()));
            }

            drops.addAll(extra);
        }
    }

    @SubscribeEvent
    public void onContainer(PlayerContainerEvent.Open event) {
        PlayerEntity player = event.getPlayer();
        if(event.getContainer() instanceof MerchantContainer && InventoryCheck.hasCoupon(player, CouponRegistry.TRADING_COUPON)) {
            MerchantContainer container = (MerchantContainer)event.getContainer();
            if(!(container.merchant instanceof WanderingTraderEntity)) {
                int offerSlot = player.getRNG().nextInt(container.getOffers().size());
                MerchantOffer randomOffer = container.getOffers().get(offerSlot);
                int uses = randomOffer.getUses();

                if(randomOffer.getBuyingStackFirst().getCount() > 1) {
                    CompoundNBT tag = player.getPersistentData();
                    tag.putInt(CouponReference.offerSlotTag, offerSlot);
                    tag.putInt(CouponReference.offerUsesTag, uses);
                    tag.putInt(CouponReference.offerSpecialPrice, randomOffer.getSpecialPrice());

                    randomOffer.increaseSpecialPrice(-64);
                }
            }
        }
    }

    @SubscribeEvent
    public void onContainerClose(PlayerContainerEvent.Close event) {
        PlayerEntity player = event.getPlayer();
        if(event.getContainer() instanceof MerchantContainer && InventoryCheck.hasCoupon(player, CouponRegistry.TRADING_COUPON)) {
            MerchantContainer container = (MerchantContainer)event.getContainer();
            CompoundNBT tag = player.getPersistentData();
            if(tag.contains(CouponReference.offerSlotTag) && tag.contains(CouponReference.offerUsesTag) && tag.contains(CouponReference.offerSpecialPrice)) {
                int offerSlot = tag.getInt(CouponReference.offerSlotTag);
                int previousUses = tag.getInt(CouponReference.offerUsesTag);
                int specialPrice = tag.getInt(CouponReference.offerSpecialPrice);
                tag.remove(CouponReference.offerSlotTag);
                tag.remove(CouponReference.offerUsesTag);
                tag.remove(CouponReference.offerSpecialPrice);
                MerchantOffer randomOffer = container.getOffers().get(offerSlot);
                if(previousUses != -1 && randomOffer.getUses() != previousUses) {
                    randomOffer.setSpecialPrice(specialPrice);
                    InventoryCheck.shrinkCoupon(player, CouponRegistry.TRADING_COUPON);
                }
            }
        }
    }

}
