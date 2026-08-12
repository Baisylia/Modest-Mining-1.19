package com.baisylia.modestmining.integration.jei;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.recipe.AbstractForgeRecipe;
import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.ForgeMenu;
import com.baisylia.modestmining.screen.MillstoneMenu;
import com.baisylia.modestmining.screen.ModMenuTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIModestMiningPlugin implements IModPlugin {
    public static RecipeType<AbstractForgeRecipe> FORGING_TYPE =
            new RecipeType<>(ForgingRecipeCategory.UID, AbstractForgeRecipe.class);
    public static RecipeType<AbstractMillstoneRecipe> MILLING_TYPE =
            new RecipeType<>(MillingRecipeCategory.UID, AbstractMillstoneRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ForgingRecipeCategory(registration.getJeiHelpers().getGuiHelper()),
                new MillingRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AbstractForgeRecipe> recipes = rm.getAllRecipesFor(ModRecipes.FORGING_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(FORGING_TYPE, recipes);

        List<AbstractMillstoneRecipe> recipes_M = rm.getAllRecipesFor(ModRecipes.MILLING_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
        registration.addRecipes(MILLING_TYPE, recipes_M);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        var stack = ModBlocks.FORGE.get().asItem().getDefaultInstance();
        registration.addRecipeCatalyst(stack, FORGING_TYPE);
        var stack_M = ModBlocks.MILLSTONE.get().asItem().getDefaultInstance();
        registration.addRecipeCatalyst(stack_M, MILLING_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ForgeMenu.class, ModMenuTypes.FORGE_MENU.get(), FORGING_TYPE, 0, 10, 11, 36);
        registration.addRecipeTransferHandler(MillstoneMenu.class, ModMenuTypes.MILLSTONE_MENU.get(), MILLING_TYPE, 0, 1, 10, 36);
    }
}
