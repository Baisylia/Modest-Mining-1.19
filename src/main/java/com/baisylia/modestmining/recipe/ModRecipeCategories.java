package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.client.event.RegisterRecipeBookCategoriesEvent;

import java.util.List;
import java.util.Map;

public class ModRecipeCategories {

    public static final Map<ForgingBookCategory, RecipeBookCategories> RECIPE_BOOK_TAB_SUPPLIERS = Map.of(
            ForgingBookCategory.EQUIPMENT, RecipeBookCategories.CRAFTING_EQUIPMENT,
            ForgingBookCategory.BUILDING, RecipeBookCategories.CRAFTING_BUILDING_BLOCKS,
            ForgingBookCategory.MISC, RecipeBookCategories.FURNACE_MISC
    );

    public static final Map<MillingBookCategory, RecipeBookCategories> RECIPE_BOOK_TAB_SUPPLIERS_MILL = Map.of(
            MillingBookCategory.ORES, RecipeBookCategories.BLAST_FURNACE_BLOCKS,
            MillingBookCategory.PLANTS, RecipeBookCategories.FURNACE_FOOD,
            MillingBookCategory.BLOCKS, RecipeBookCategories.FURNACE_BLOCKS,
            MillingBookCategory.TOOLS, RecipeBookCategories.CRAFTING_EQUIPMENT,
            MillingBookCategory.MISC, RecipeBookCategories.FURNACE_MISC
    );

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(ModestMining.FORGING_RECIPE_BOOK_TYPE,
                List.of(RecipeBookCategories.FURNACE_SEARCH, RecipeBookCategories.CRAFTING_EQUIPMENT, RecipeBookCategories.CRAFTING_BUILDING_BLOCKS, RecipeBookCategories.FURNACE_MISC)
        );
        event.registerAggregateCategory(RecipeBookCategories.FURNACE_SEARCH,
                List.of(RecipeBookCategories.CRAFTING_EQUIPMENT, RecipeBookCategories.CRAFTING_BUILDING_BLOCKS, RecipeBookCategories.FURNACE_MISC)
        );
        event.registerBookCategories(ModestMining.MILLING_RECIPE_BOOK_TYPE,
                List.of(RecipeBookCategories.BLAST_FURNACE_SEARCH, RecipeBookCategories.BLAST_FURNACE_BLOCKS, RecipeBookCategories.FURNACE_FOOD, RecipeBookCategories.FURNACE_BLOCKS, RecipeBookCategories.CRAFTING_EQUIPMENT, RecipeBookCategories.FURNACE_MISC)
        );
        event.registerAggregateCategory(RecipeBookCategories.BLAST_FURNACE_SEARCH,
                List.of(RecipeBookCategories.BLAST_FURNACE_BLOCKS, RecipeBookCategories.FURNACE_FOOD, RecipeBookCategories.FURNACE_BLOCKS, RecipeBookCategories.CRAFTING_EQUIPMENT, RecipeBookCategories.FURNACE_MISC)
        );
        event.registerRecipeCategoryFinder(ModRecipes.FORGING_TYPE.get(), ModRecipeCategories::findForgingCategory);
        event.registerRecipeCategoryFinder(ModRecipes.MILLING_TYPE.get(), ModRecipeCategories::findMillingCategory);
    }

    public static RecipeBookCategories findForgingCategory(RecipeHolder<?> rawRecipe) {
        if (rawRecipe.value() instanceof AbstractForgeRecipe recipe) {
            ForgingBookCategory tab = recipe.getCategory();
            if (tab != null && RECIPE_BOOK_TAB_SUPPLIERS.containsKey(tab)) {
                return RECIPE_BOOK_TAB_SUPPLIERS.get(tab);
            }
        }
        return RecipeBookCategories.FURNACE_MISC;
    }

    public static RecipeBookCategories findMillingCategory(RecipeHolder<?> rawRecipe) {
        if (rawRecipe.value() instanceof AbstractMillstoneRecipe recipe) {
            MillingBookCategory tab = recipe.getCategory();
            if (tab != null && RECIPE_BOOK_TAB_SUPPLIERS_MILL.containsKey(tab)) {
                return RECIPE_BOOK_TAB_SUPPLIERS_MILL.get(tab);
            }
        }
        return RecipeBookCategories.FURNACE_MISC;
    }
}
