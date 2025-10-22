package com.ncpbails.modestmining.integration.jei;

import com.ncpbails.modestmining.ModestMining;
import com.ncpbails.modestmining.block.ModBlocks;
import com.ncpbails.modestmining.recipe.ForgeRecipe;
import com.ncpbails.modestmining.recipe.ForgeShapedRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIModestMiningPlugin implements IModPlugin {
    public static RecipeType<ForgeRecipe> FORGING_TYPE =
            new RecipeType<>(ForgingRecipeCategory.UID, ForgeRecipe.class);

    public static RecipeType<ForgeShapedRecipe> FORGING_SHAPED_TYPE =
            new RecipeType<>(ForgingShapedRecipeCategory.UID, ForgeShapedRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ModestMining.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ForgingRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new ForgingShapedRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<ForgeRecipe> recipes = rm.getAllRecipesFor(ForgeRecipe.Type.INSTANCE);
        registration.addRecipes(FORGING_TYPE, recipes);

        List<ForgeShapedRecipe> recipesShaped = rm.getAllRecipesFor(ForgeShapedRecipe.Type.INSTANCE);
        registration.addRecipes(FORGING_SHAPED_TYPE, recipesShaped);
    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        var stack = ModBlocks.FORGE.get().asItem().getDefaultInstance();
        registration.addRecipeCatalyst(stack, FORGING_SHAPED_TYPE);
        registration.addRecipeCatalyst(stack, FORGING_TYPE);
    }
}
