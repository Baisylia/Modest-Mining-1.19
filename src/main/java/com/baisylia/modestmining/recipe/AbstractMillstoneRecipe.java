package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.block.entity.custom.ForgeBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractMillstoneRecipe implements Recipe<ForgeBlockEntity.SingleRecipeInputContainer> {

    protected final String group;
    protected final MillingBookCategory category;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;

    public AbstractMillstoneRecipe(String group, MillingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime) {
        this.group = group;
        this.category = category;
        this.output = output;
        this.recipeItems = recipeItems;
        this.cookTime = cookTime;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public MillingBookCategory getCategory() {
        return this.category;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }
}
