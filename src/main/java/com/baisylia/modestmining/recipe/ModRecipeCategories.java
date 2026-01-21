package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.item.ModItems;
import com.google.common.base.Suppliers;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModRecipeCategories {

	public static final Supplier<RecipeBookCategories> FORGING_SEARCH =
		Suppliers.memoize(() -> RecipeBookCategories.create("FORGING_SEARCH", new ItemStack(Items.COMPASS)));
	public static final Supplier<RecipeBookCategories> FORGING_EQUIPMENT =
		Suppliers.memoize(() -> RecipeBookCategories.create("FORGING_EQUIPMENT", new ItemStack(Items.IRON_AXE), new ItemStack(Items.GOLDEN_SWORD)));
	public static final Supplier<RecipeBookCategories> FORGING_BUILDING =
		Suppliers.memoize(() -> RecipeBookCategories.create("FORGING_BUILDING", new ItemStack(Items.CHAIN)));
	public static final Supplier<RecipeBookCategories> FORGING_MISC =
		Suppliers.memoize(() -> RecipeBookCategories.create("FORGING_MISC", new ItemStack(ModItems.COKE.get())));

	public static final Map<ForgingBookCategory, Supplier<RecipeBookCategories>> RECIPE_BOOK_TAB_SUPPLIERS = Map.of(
		ForgingBookCategory.EQUIPMENT, FORGING_EQUIPMENT,
		ForgingBookCategory.BUILDING, FORGING_BUILDING,
		ForgingBookCategory.MISC, FORGING_MISC
	);

	public static void init(RegisterRecipeBookCategoriesEvent event) {
		event.registerBookCategories(ModestMining.FORGING_RECIPE_BOOK_TYPE,
			List.of(FORGING_SEARCH.get(), FORGING_EQUIPMENT.get(), FORGING_BUILDING.get(), FORGING_MISC.get())
		);
		event.registerAggregateCategory(FORGING_SEARCH.get(),
			List.of(FORGING_EQUIPMENT.get(), FORGING_BUILDING.get(), FORGING_MISC.get())
		);
		event.registerRecipeCategoryFinder(ModRecipes.FORGING_TYPE.get(), ModRecipeCategories::findForgingCategory);
	}

	public static RecipeBookCategories findForgingCategory(Recipe<?> rawRecipe) {
		if (rawRecipe instanceof AbstractForgeRecipe recipe) {
			ForgingBookCategory tab = recipe.getCategory();
			if (tab != null) return RECIPE_BOOK_TAB_SUPPLIERS.get(tab).get();
		}
		return FORGING_MISC.get();
	}

}
