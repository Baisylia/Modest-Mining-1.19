package com.baisylia.modestmining.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractMillstoneRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    protected final String group;
    protected final MillingBookCategory category;

    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;

    public AbstractMillstoneRecipe(ResourceLocation id, String group, MillingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime) {
        this.id = id;
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

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getCookTime() {
        return this.cookTime;
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
