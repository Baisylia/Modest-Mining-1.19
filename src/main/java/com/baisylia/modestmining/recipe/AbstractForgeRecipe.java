package com.baisylia.modestmining.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractForgeRecipe implements Recipe<Container> {

    protected final String group;
    protected final ForgingBookCategory category;
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;
    private final int fuelTier;

    public AbstractForgeRecipe(ResourceLocation id, String group, ForgingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime, int fuelTier) {
        this.id = id;
        this.group = group;
        this.category = category;
        this.output = output;
        this.recipeItems = recipeItems;
        this.cookTime = cookTime;
        this.fuelTier = Math.max(0, fuelTier);
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public ForgingBookCategory getCategory() {
        return this.category;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public int getFuelTier() {
        return this.fuelTier;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

}
