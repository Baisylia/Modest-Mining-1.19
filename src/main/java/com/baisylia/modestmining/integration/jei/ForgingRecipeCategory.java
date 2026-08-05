package com.baisylia.modestmining.integration.jei;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.recipe.AbstractForgeRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

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

    @Override
    public void draw(AbstractForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (recipe.getFuel().isEmpty()) {
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
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        recipe.getFuel().ifPresent(fuel -> builder.addSlot(RecipeIngredientRole.CATALYST, 65, 23)
                .setStandardSlotBackground()
                .addIngredients(fuel));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 6).addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
}