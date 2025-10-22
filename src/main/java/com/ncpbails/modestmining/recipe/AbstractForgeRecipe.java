package com.ncpbails.modestmining.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractForgeRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;

    public AbstractForgeRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.cookTime = cookTime;
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
