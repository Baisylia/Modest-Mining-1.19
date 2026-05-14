package com.baisylia.modestmining.integration.emi;

import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.IShapedRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MillingEmiRecipe implements EmiRecipe {

    protected final ResourceLocation id;
    protected final List<EmiIngredient> input;
    protected final EmiStack output;
    protected final int cookTime;
    protected final boolean shapeless;

    public MillingEmiRecipe(AbstractMillstoneRecipe recipe) {
        this.id = recipe.getId();
        this.input = padIngredients(recipe);
        this.output = EmiStack.of(recipe.getResultItem());
        this.cookTime = recipe.getCookTime();
        this.shapeless = !(recipe instanceof IShapedRecipe);
    }

    private static List<EmiIngredient> padIngredients(AbstractMillstoneRecipe recipe) {
        return recipe.getIngredients().stream().map(EmiIngredient::of).toList();
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
        return this.input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(this.output);
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
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 60, 18);
        if (this.shapeless) widgets.addTexture(EmiTexture.SHAPELESS, 97, 0);

        int offset = 0;
        if (!this.shapeless) {
            if (canFit(1, 3)) {
                offset -= 1;
            }
            if (canFit(3, 1)) {
                offset -= 3;
            }
        }

        for (int i = 0; i < 9; i++) {
            int x = i % 3 * 18;
            int y = i / 3 * 18;

            int index = i + offset;
            if (index >= 0 && index < this.input.size()) {
                widgets.addSlot(this.input.get(index), x, y);
            } else {
                widgets.addSlot(EmiStack.of(ItemStack.EMPTY), x, y);
            }
        }

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 60, 18);
        Component timeString = Component.translatable("emi.cooking.time", this.cookTime / 20);
        widgets.addFillingArrow(60, 18, this.cookTime * 100).tooltipText(Collections.singletonList(timeString));

        widgets.addTexture(EmiTexture.EMPTY_FLAME, 64, 39);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 64, 39, 6000, false, true, true);

        widgets.addSlot(this.output, 92, 14).large(true).recipeContext(this);
    }

    public boolean canFit(int width, int height) {
        if (this.input.size() > 9) return false;

        for (int i = 0; i < this.input.size(); i++) {
            int x = i % 3;
            int y = i / 3;
            if (!this.input.get(i).isEmpty() && (x >= width || y >= height)) return false;
        }
        return true;
    }

}
