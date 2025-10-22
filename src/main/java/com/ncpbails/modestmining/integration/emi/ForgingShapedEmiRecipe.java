package com.ncpbails.modestmining.integration.emi;

import com.google.common.collect.Lists;
import com.ncpbails.modestmining.recipe.ForgeShapedRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.List;

public class ForgingShapedEmiRecipe extends AbstractForgingRecipe {

    private final int height;
    private final int width;

    public ForgingShapedEmiRecipe(ForgeShapedRecipe recipe) {
        super(recipe.getId(), padIngredients(recipe), recipe.getResultItem(), recipe.getCookTime());
        this.height = recipe.getHeight();
        this.width = recipe.getWidth();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIModestMiningPlugin.SHAPED_FORGING;
    }

    @Override
    public void addWidgets(WidgetHolder builder) {
        builder.addTexture(AbstractForgingRecipe.TEXTURE, 0, 0, 116, 60, 0, 0);
        int startX = 2;
        int startY = 4;
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                builder.addSlot(getInputs().get(index), startX + x * 18, startY + y * 18).drawBack(false);
                index++;
            }
        }

        drawCookTime(cookTime, builder, 50, getDisplayWidth());
        builder.addAnimatedTexture(EmiTexture.FULL_FLAME, 66, 23, 6000, false, true, true);
        builder.addSlot(result, 97, 6).recipeContext(this).drawBack(false);
    }

    private static List<EmiIngredient> padIngredients(ForgeShapedRecipe recipe) {
        List<EmiIngredient> list = Lists.newArrayList();
        int i = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (x >= recipe.getWidth() || y >= recipe.getHeight() || i >= recipe.getIngredients().size()) {
                    list.add(EmiStack.EMPTY);
                } else {
                    list.add(EmiIngredient.of(recipe.getIngredients().get(i++)));
                }
            }
        }
        return list;
    }
}
