package com.baisylia.modestmining.integration.jei;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import com.baisylia.modestmining.recipe.MillstoneRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MillingRecipeCategory implements IRecipeCategory<AbstractMillstoneRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "milling");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "textures/gui/millstone_gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final int regularCookTime = 400;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public MillingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MILLSTONE.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(TEXTURE, 122, 0, 23, 19)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Override
    public void draw(AbstractMillstoneRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(guiGraphics, 25, 21);
        drawCookTime(recipe, guiGraphics);
    }

    protected void drawCookTime(AbstractMillstoneRecipe recipe, GuiGraphics guiGraphics) {
        int cookTime = recipe.getCookTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, getWidth() - stringWidth, 48, 0xFF808080, false);
        }
    }

    protected IDrawableAnimated getArrow(AbstractMillstoneRecipe recipe) {
        int cookTime = recipe.getCookTime();
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    @Override
    public RecipeType<AbstractMillstoneRecipe> getRecipeType() {
        return JEIModestMiningPlugin.MILLING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.modestmining.milling");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AbstractMillstoneRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 21)
                .setStandardSlotBackground()
                .addIngredients(recipe.getIngredients().get(0));

        List<ItemStack> results = new ArrayList<>();
        if (recipe instanceof MillstoneRecipe millstoneRecipe) {
            results.addAll(millstoneRecipe.results);
        } else {
            results.add(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        }

        for (int i = 0; i < 9; i++) {
            int col = i % 3;
            int row = i / 3;
            var slot = builder.addSlot(RecipeIngredientRole.OUTPUT, 50 + col * 18, 3 + row * 18)
                    .setStandardSlotBackground();
            if (i < results.size()) {
                slot.addItemStack(results.get(i));
            }
        }
    }
}
