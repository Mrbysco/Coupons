package com.shynieke.coupons;

import net.minecraft.resources.Identifier;

public class Reference {
	public static final String MOD_ID = "coupons";

	public static final String offerSlotTag = MOD_ID + ":offerSlot";
	public static final String offerUsesTag = MOD_ID + ":offerUses";
	public static final String offerSpecialPrice = MOD_ID + ":offerSpecialPrice";

	public static Identifier modLoc(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
