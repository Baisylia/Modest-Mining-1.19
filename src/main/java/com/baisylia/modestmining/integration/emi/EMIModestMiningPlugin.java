package com.baisylia.modestmining.integration.emi;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.recipe.AbstractForgeRecipe;
import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.ModMenuTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

@EmiEntrypoint
public class EMIModestMiningPlugin implements EmiPlugin {

    public static final EmiRecipeCategory FORGING =
            new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "forging"), EmiStack.of(ModBlocks.FORGE.get()));

    public static final EmiRecipeCategory MILLING =
            new EmiRecipeCategory(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "milling"), EmiStack.of(ModBlocks.MILLSTONE.get()));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FORGING);
        registry.addWorkstation(FORGING, EmiStack.of(ModBlocks.FORGE.get()));
        for (RecipeHolder<AbstractForgeRecipe> recipe : registry.getRecipeManager().getAllRecipesFor(ModRecipes.FORGING_TYPE.get())) {
            registry.addRecipe(new ForgingEmiRecipe(recipe.id(), recipe.value()));
        }
        registry.addRecipeHandler(ModMenuTypes.FORGE_MENU.get(), new ForgingEmiRecipeHandler());

        registry.addCategory(MILLING);
        registry.addWorkstation(MILLING, EmiStack.of(ModBlocks.MILLSTONE.get()));
        for (RecipeHolder<AbstractMillstoneRecipe> recipe : registry.getRecipeManager().getAllRecipesFor(ModRecipes.MILLING_TYPE.get())) {
            registry.addRecipe(new MillingEmiRecipe(recipe.id(), recipe.value()));
        }
        registry.addRecipeHandler(ModMenuTypes.MILLSTONE_MENU.get(), new MillingEmiRecipeHandler());
    }
}