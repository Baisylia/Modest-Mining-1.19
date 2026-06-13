package com.baisylia.modestmining.integration.emi;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import com.baisylia.modestmining.recipe.MillstoneRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MillingEmiRecipe implements EmiRecipe {

    private static final ResourceLocation MILLSTONE_GUI =
            new ResourceLocation(ModestMining.MOD_ID, "textures/gui/millstone_gui.png");
    private static final EmiTexture LIT_POWER =
            new EmiTexture(MILLSTONE_GUI, 201, 14, 24, 17, 24, 17, 256, 256);

    protected final ResourceLocation id;
    protected final EmiIngredient input;
    protected final List<EmiStack> outputs;
    protected final int cookTime;

    public MillingEmiRecipe(AbstractMillstoneRecipe recipe) {
        this.id = recipe.getId();
        this.input = EmiIngredient.of(recipe.getIngredients().get(0));
        this.cookTime = recipe.getCookTime();

        List<EmiStack> outs = new ArrayList<>();
        if (recipe instanceof MillstoneRecipe millstoneRecipe) {
            for (int i = 0; i < millstoneRecipe.results.size(); i++) {
                float chance = i < millstoneRecipe.chances.size() ? millstoneRecipe.chances.get(i) : 1.0f;
                EmiStack stack = EmiStack.of(millstoneRecipe.results.get(i));
                if (chance < 1.0f) {
                    stack = stack.setChance(chance);
                }
                outs.add(stack);
            }
        } else {
            outs.add(EmiStack.of(recipe.getResultItem()));
        }
        this.outputs = outs;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIModestMiningPlugin.MILLING;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(this.input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return this.outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 118;
    }

    @Override
    public int getDisplayHeight() {
        return 54;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        Component timeString = Component.translatable("emi.cooking.time", this.cookTime / 20);

        widgets.addTexture(LIT_POWER, 0, 0);

        widgets.addSlot(this.input, 0, 20);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 22, 20);
        widgets.addFillingArrow(22, 20, this.cookTime * 100).tooltipText(Collections.singletonList(timeString));

        for (int i = 0; i < 9; i++) {
            int x = 50 + (i % 3) * 18;
            int y = (i / 3) * 18;
            if (i < this.outputs.size()) {
                widgets.addSlot(this.outputs.get(i), x, y).recipeContext(this);
            } else {
                widgets.addSlot(EmiStack.EMPTY, x, y);
            }
        }
    }
}
