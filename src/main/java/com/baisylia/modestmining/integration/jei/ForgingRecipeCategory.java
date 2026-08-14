package com.baisylia.modestmining.integration.jei;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.recipe.AbstractForgeRecipe;
import com.baisylia.modestmining.recipe.ForgeFuelManager;
import com.baisylia.modestmining.recipe.ForgeShapedRecipe;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForgingRecipeCategory implements IRecipeCategory<AbstractForgeRecipe> {
    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "forging");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "textures/gui/forge_gui_jei.png");
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    private final IDrawable background;
    private final IDrawable icon;
    private final int regularCookTime = 400;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public ForgingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.FORGE.get()));
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(TEXTURE, 123, 0, 23, 18)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
        staticFlame = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(ModIds.JEI_ID, "textures/gui/gui_vanilla.png"), 82, 114, 14, 14);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
    }

    private static List<Ingredient> padIngredients(AbstractForgeRecipe recipe) {
        List<Ingredient> result = new ArrayList<>(Collections.nCopies(9, Ingredient.EMPTY));
        if (recipe instanceof ForgeShapedRecipe shapedRecipe) {
            int width = shapedRecipe.getWidth();
            int height = shapedRecipe.getHeight();
            NonNullList<Ingredient> ingredients = recipe.getIngredients();
            for (int y = 0; y < height && y < 3; y++) {
                for (int x = 0; x < width && x < 3; x++) {
                    int index = x + y * width;
                    if (index < ingredients.size()) {
                        result.set(x + y * 3, ingredients.get(index));
                    }
                }
            }
        } else {
            List<Ingredient> ingredients = recipe.getIngredients();
            for (int i = 0; i < ingredients.size() && i < 9; i++) {
                result.set(i, ingredients.get(i));
            }
        }
        return result;
    }

    @Override
    public void draw(AbstractForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (recipe.getFuel().isEmpty() && recipe.getFuelTier() <= 0) {
            animatedFlame.draw(guiGraphics, 66, 23);
        }
        IDrawableAnimated arrow = getArrow(recipe);
        arrow.draw(guiGraphics, 63, 4);
        drawCookTime(recipe, guiGraphics, 50);
    }

    protected void drawCookTime(AbstractForgeRecipe recipe, GuiGraphics guiGraphics, int y) {
        int cookTime = recipe.getCookTime();
        if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
            Minecraft minecraft = Minecraft.getInstance();
            Font fontRenderer = minecraft.font;
            int stringWidth = fontRenderer.width(timeString);
            guiGraphics.drawString(fontRenderer, timeString, getWidth() - stringWidth, y, 0xFF808080, false);
        }
    }

    protected IDrawableAnimated getArrow(AbstractForgeRecipe recipe) {
        int cookTime = recipe.getCookTime();
        if (cookTime <= 0) {
            cookTime = regularCookTime;
        }
        return this.cachedArrows.getUnchecked(cookTime);
    }

    @Override
    public RecipeType<AbstractForgeRecipe> getRecipeType() {
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
    public void setRecipe(IRecipeLayoutBuilder builder, AbstractForgeRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> gridIngredients = padIngredients(recipe);
        for (int i = 0; i < 9; i++) {
            Ingredient ingredient = gridIngredients.get(i);
            if (ingredient.isEmpty()) continue;
            int col = i % 3;
            int row = i / 3;
            builder.addSlot(RecipeIngredientRole.INPUT, 3 + col * 18, 5 + row * 18).addIngredients(ingredient);
        }
        recipe.getFuel().ifPresent(fuel -> builder.addSlot(RecipeIngredientRole.INPUT, 65, 23)
                .setStandardSlotBackground()
                .addIngredients(fuel));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 6).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
        if (recipe.getFuelTier() > 0) {
            List<ItemStack> fuels = ForgeFuelManager.getFuelsForTier(recipe.getFuelTier());
            if (!fuels.isEmpty()) {
                builder.addSlot(RecipeIngredientRole.CATALYST, 65, 23)
                        .setStandardSlotBackground()
                        .addItemStacks(fuels);
            }
        }
    }
}