package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.item.ModItems;
import com.google.common.base.Suppliers;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
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


	public static final Supplier<RecipeBookCategories> MILLING_SEARCH =
			Suppliers.memoize(() -> RecipeBookCategories.create("MILLING_SEARCH", new ItemStack(Items.COMPASS)));
	public static final Supplier<RecipeBookCategories> MILLING_ORES =
			Suppliers.memoize(() -> RecipeBookCategories.create("MILLING_ORES", new ItemStack(ModItems.IRON_DUST.get()), new ItemStack(Items.GOLDEN_SWORD)));
	public static final Supplier<RecipeBookCategories> MILLING_PLANTS =
			Suppliers.memoize(() -> RecipeBookCategories.create("MILLING_PLANTS", new ItemStack(Items.SUGAR)));
	public static final Supplier<RecipeBookCategories> MILLING_BLOCKS =
			Suppliers.memoize(() -> RecipeBookCategories.create("MILLING_BLOCKS", new ItemStack(Blocks.GRAVEL)));
	public static final Supplier<RecipeBookCategories> MILLING_TOOLS =
			Suppliers.memoize(() -> RecipeBookCategories.create("MILLING_TOOLS", new ItemStack(Items.DIAMOND_SWORD)));
	public static final Supplier<RecipeBookCategories> MILLING_MISC =
			Suppliers.memoize(() -> RecipeBookCategories.create("MILLING_MISC", new ItemStack(Items.BLAZE_POWDER)));

	public static final Map<ForgingBookCategory, Supplier<RecipeBookCategories>> RECIPE_BOOK_TAB_SUPPLIERS = Map.of(
		ForgingBookCategory.EQUIPMENT, FORGING_EQUIPMENT,
		ForgingBookCategory.BUILDING, FORGING_BUILDING,
		ForgingBookCategory.MISC, FORGING_MISC
	);
	public static final Map<MillingBookCategory, Supplier<RecipeBookCategories>> RECIPE_BOOK_TAB_SUPPLIERS_MILL = Map.of(
			MillingBookCategory.ORES, MILLING_ORES,
			MillingBookCategory.PLANTS, MILLING_PLANTS,
			MillingBookCategory.BLOCKS, MILLING_BLOCKS,
			MillingBookCategory.TOOLS, MILLING_TOOLS,
			MillingBookCategory.MISC, FORGING_MISC
	);

	public static void init(RegisterRecipeBookCategoriesEvent event) {
		event.registerBookCategories(ModestMining.FORGING_RECIPE_BOOK_TYPE,
			List.of(FORGING_SEARCH.get(), FORGING_EQUIPMENT.get(), FORGING_BUILDING.get(), FORGING_MISC.get())
		);
		event.registerAggregateCategory(FORGING_SEARCH.get(),
			List.of(FORGING_EQUIPMENT.get(), FORGING_BUILDING.get(), FORGING_MISC.get())
		);
		event.registerBookCategories(ModestMining.MILLING_RECIPE_BOOK_TYPE,
				List.of(MILLING_SEARCH.get(), MILLING_ORES.get(), MILLING_PLANTS.get(), MILLING_BLOCKS.get(), MILLING_TOOLS.get(), MILLING_MISC.get())
		);
		event.registerAggregateCategory(MILLING_SEARCH.get(),
				List.of(MILLING_ORES.get(), MILLING_PLANTS.get(), MILLING_BLOCKS.get(), MILLING_TOOLS.get(), MILLING_MISC.get())
		);
		event.registerRecipeCategoryFinder(ModRecipes.FORGING_TYPE.get(), ModRecipeCategories::findForgingCategory);
		event.registerRecipeCategoryFinder(ModRecipes.MILLING_TYPE.get(), ModRecipeCategories::findMillingCategory);
	}

	public static RecipeBookCategories findForgingCategory(Recipe<?> rawRecipe) {
		if (rawRecipe instanceof AbstractForgeRecipe recipe) {
			ForgingBookCategory tab = recipe.getCategory();
			if (tab != null) return RECIPE_BOOK_TAB_SUPPLIERS.get(tab).get();
		}
		return FORGING_MISC.get();
	}
	public static RecipeBookCategories findMillingCategory(Recipe<?> rawRecipe) {
		if (rawRecipe instanceof AbstractMillstoneRecipe recipe) {
			MillingBookCategory tab = recipe.getCategory();
			if (tab != null) return RECIPE_BOOK_TAB_SUPPLIERS_MILL.get(tab).get();
		}
		return MILLING_MISC.get();
	}

}
