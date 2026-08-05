package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.block.entity.custom.ForgeBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Optional;

public abstract class AbstractForgeRecipe implements Recipe<ForgeBlockEntity.SingleRecipeInputContainer> {

    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<Ingredient>> FUEL_STREAM_CODEC =
            ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC);

    protected final String group;
    protected final ForgingBookCategory category;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final Optional<Ingredient> fuel;
    private final int cookTime;

    public AbstractForgeRecipe(String group, ForgingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, Optional<Ingredient> fuel, int cookTime) {
        this.group = group;
        this.category = category;
        this.output = output;
        this.recipeItems = recipeItems;
        this.fuel = fuel;
        this.cookTime = cookTime;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public ForgingBookCategory getCategory() {
        return this.category;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public Optional<Ingredient> getFuel() {
        return this.fuel;
    }

    public boolean fuelMatches(ItemStack stack) {
        if (stack.isEmpty() || stack.getBurnTime(RecipeType.BLASTING) <= 0) {
            return false;
        }
        return this.fuel.map(ingredient -> ingredient.test(stack)).orElse(true);
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
