package com.baisylia.modestmining.integration.emi;

import com.baisylia.modestmining.recipe.AbstractForgeRecipe;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.screen.ModMenuTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;

@EmiEntrypoint
public class EMIModestMiningPlugin implements EmiPlugin {

    public static final EmiRecipeCategory FORGING =
        new EmiRecipeCategory(new ResourceLocation(ModestMining.MOD_ID, "forging"), EmiStack.of(ModBlocks.FORGE.get()));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(FORGING);
        registry.addWorkstation(FORGING, EmiStack.of(ModBlocks.FORGE.get()));
        for (AbstractForgeRecipe recipe : registry.getRecipeManager().getAllRecipesFor(ModRecipes.FORGING_TYPE.get())) {
            registry.addRecipe(new ForgingEmiRecipe(recipe));
        }
        registry.addRecipeHandler(ModMenuTypes.FORGE_MENU.get(), new ForgingEmiRecipeHandler());
    }

}
