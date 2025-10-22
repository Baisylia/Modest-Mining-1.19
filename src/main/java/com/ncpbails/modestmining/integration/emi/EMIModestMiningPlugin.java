package com.ncpbails.modestmining.integration.emi;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ncpbails.modestmining.ModestMining;
import com.ncpbails.modestmining.block.ModBlocks;
import com.ncpbails.modestmining.recipe.ForgeRecipe;
import com.ncpbails.modestmining.recipe.ForgeShapedRecipe;
import com.ncpbails.modestmining.screen.ModMenuTypes;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@EmiEntrypoint
public class EMIModestMiningPlugin implements EmiPlugin {

    static final ResourceLocation TEXTURE = new ResourceLocation(ModestMining.MOD_ID, "textures/gui/forge_gui_jei.png");

    public static final EmiRecipeCategory SHAPELESS_FORGING = new EmiRecipeCategory(new ResourceLocation(ModestMining.MOD_ID, "shapeless_forging"), EmiStack.of(ModBlocks.FORGE.get()), simplifiedRenderer(0, 0));
    public static final EmiRecipeCategory SHAPED_FORGING = new EmiRecipeCategory(new ResourceLocation(ModestMining.MOD_ID, "shaped_forging"), EmiStack.of(ModBlocks.FORGE.get()), simplifiedRenderer(0, 0));

    private static EmiRenderable simplifiedRenderer(int u, int v) {
        return (draw, x, y, delta) -> {
            RenderSystem.setShaderTexture(0, TEXTURE);
            GuiComponent.blit(draw, x, y, u, v, 120, 60, 120, 60);
        };
    }

    @Override
    public void register(EmiRegistry registry) {
        var forge = EmiStack.of(ModBlocks.FORGE.get());
        registry.addCategory(SHAPELESS_FORGING);
        registry.addWorkstation(SHAPELESS_FORGING, forge);
        registry.addCategory(SHAPED_FORGING);
        registry.addWorkstation(SHAPED_FORGING, forge);
        for (ForgeRecipe recipe : registry.getRecipeManager().getAllRecipesFor(ForgeRecipe.Type.INSTANCE)) {
            registry.addRecipe(new ForgingEmiRecipe(recipe));
        }
        for (var recipe : registry.getRecipeManager().getAllRecipesFor(ForgeShapedRecipe.Type.INSTANCE)) {
            registry.addRecipe(new ForgingShapedEmiRecipe(recipe));
        }
        registry.addRecipeHandler(ModMenuTypes.FORGE_MENU.get(), new ForgingRecipeHandler());
    }
}
