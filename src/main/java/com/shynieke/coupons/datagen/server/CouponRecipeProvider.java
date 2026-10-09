package com.shynieke.coupons.datagen.server;

import com.shynieke.coupons.Reference;
import com.shynieke.coupons.registry.CouponRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.BrewingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PotionIngredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;
import java.util.Set;

public class CouponRecipeProvider extends RecipeProvider {

	protected CouponRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
		super(recipeOutput, advancementOutput);
	}

	public static MultiRegistryBootstrap create() {
		return new MultiRegistryBootstrap() {

			@Override
			public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
				return Set.of(
						Registries.RECIPE,
						Registries.ADVANCEMENT
				);
			}


			@Override
			public void run(BootstrapGetter bootstrapGetter) {
				new CouponRecipeProvider(
						bootstrapGetter.get(Registries.RECIPE),
						bootstrapGetter.get(Registries.ADVANCEMENT)
				).buildRecipes();
			}
		};
	}

	@Override
	protected void buildRecipes() {
		this.output.accept(ResourceKey.create(Registries.RECIPE, Reference.modLoc("brewing/coupon_to_awkward")),
				new BrewingRecipe(
						new PotionIngredient(Ingredient.of(CouponRegistry.BREWING_COUPON.get()), Optional.empty()),
						new PotionIngredient(Ingredient.of(Items.NETHER_WART), Optional.empty()),
						new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.AWKWARD)).build())
				), null
		);
	}
}
