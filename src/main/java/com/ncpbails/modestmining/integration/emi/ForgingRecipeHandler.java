package com.ncpbails.modestmining.integration.emi;

import com.google.common.collect.Lists;
import com.ncpbails.modestmining.screen.ForgeMenu;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class ForgingRecipeHandler implements StandardRecipeHandler<ForgeMenu> {

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe instanceof ForgingEmiRecipe || recipe instanceof ForgingShapedEmiRecipe;
    }

    @Override
    public List<Slot> getInputSources(ForgeMenu handler) {
        List<Slot> list = Lists.newArrayList();
        list.add(handler.getSlot(0));
        int invStart = 3;
        for (int i = invStart; i < invStart + 36; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(ForgeMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 1; i < 10; i++) {
            list.add(handler.getSlot(35+i));
        }
        return list;
    }
}
