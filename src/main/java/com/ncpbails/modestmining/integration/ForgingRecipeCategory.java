package com.ncpbails.modestmining.integration;

import com.ncpbails.modestmining.ModestMining;
import com.ncpbails.modestmining.block.ModBlocks;
import com.ncpbails.modestmining.recipe.ForgeRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ForgingRecipeCategory implements IRecipeCategory<ForgeRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ModestMining.MOD_ID, "forging");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(ModestMining.MOD_ID, "textures/gui/forge_gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ForgingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FORGE.get()));
    }

    @Override
    public RecipeType<ForgeRecipe> getRecipeType() {
        return JEIModestMiningPlugin.FORGING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.modestmining.shapeless_forging");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ForgeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 5).addIngredients(recipe.getIngredients().get(0));
        if (recipe.getIngredients().size() > 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 21, 5).addIngredients(recipe.getIngredients().get(1));
            if (recipe.getIngredients().size() > 2) {
                builder.addSlot(RecipeIngredientRole.INPUT, 39, 5).addIngredients(recipe.getIngredients().get(2));
                if (recipe.getIngredients().size() > 3) {
                    builder.addSlot(RecipeIngredientRole.INPUT, 3, 23).addIngredients(recipe.getIngredients().get(3));
                    if (recipe.getIngredients().size() > 4) {
                        builder.addSlot(RecipeIngredientRole.INPUT, 21, 23).addIngredients(recipe.getIngredients().get(4));
                        if (recipe.getIngredients().size() > 5) {
                            builder.addSlot(RecipeIngredientRole.INPUT, 39, 23).addIngredients(recipe.getIngredients().get(5));
                            if (recipe.getIngredients().size() > 6) {
                                builder.addSlot(RecipeIngredientRole.INPUT, 3, 41).addIngredients(recipe.getIngredients().get(6));
                                if (recipe.getIngredients().size() > 7) {
                                    builder.addSlot(RecipeIngredientRole.INPUT, 21, 41).addIngredients(recipe.getIngredients().get(7));
                                    if (recipe.getIngredients().size() > 8) {
                                        builder.addSlot(RecipeIngredientRole.INPUT, 39, 41).addIngredients(recipe.getIngredients().get(8));
        }}}}}}}}
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 6).addItemStack(recipe.getResultItem());
    }
}