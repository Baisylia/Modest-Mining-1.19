package com.ncpbails.modestmining.integration.emi;

import com.ncpbails.modestmining.recipe.ForgeRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

public class ForgingEmiRecipe extends AbstractForgingRecipe {

    public ForgingEmiRecipe(ForgeRecipe recipe) {
        super(recipe.getId(), getIngredients(recipe), recipe.getResultItem(), recipe.getCookTime());
    }

    private static ArrayList<EmiIngredient> getIngredients(ForgeRecipe recipe) {
        ArrayList<EmiIngredient> i = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            i.add(EmiIngredient.of(ingredient));
        }
        return i;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIModestMiningPlugin.SHAPELESS_FORGING;
    }

    @Override
    public void addWidgets(WidgetHolder builder) {
        builder.addTexture(AbstractForgingRecipe.TEXTURE, 0, 0, 116, 60, 0, 0);

        builder.addSlot(getInputs().get(0), 2, 4);
        if (getInputs().size() > 1) {
            builder.addSlot(getInputs().get(1), 20, 4);
            if (getInputs().size() > 2) {
                builder.addSlot(getInputs().get(2), 38, 4);
                if (getInputs().size() > 3) {
                    builder.addSlot(getInputs().get(3), 2, 22);
                    if (getInputs().size() > 4) {
                        builder.addSlot(getInputs().get(4), 20, 22);
                        if (getInputs().size() > 5) {
                            builder.addSlot(getInputs().get(5), 38, 22);
                            if (getInputs().size() > 6) {
                                builder.addSlot(getInputs().get(6), 2, 40);
                                if (getInputs().size() > 7) {
                                    builder.addSlot(getInputs().get(7), 20, 40);
                                    if (getInputs().size() > 8) {
                                        builder.addSlot(getInputs().get(8), 38, 40);
                                    }}}}}}}}

        drawCookTime(cookTime, builder, 50, getDisplayWidth());
        builder.addAnimatedTexture(EmiTexture.FULL_FLAME, 66, 23, 6000, false, true, true);
        builder.addSlot(result, 96, 6).recipeContext(this).drawBack(false);
    }
}
