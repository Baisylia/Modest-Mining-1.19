package com.baisylia.modestmining.integration.emi;

import com.baisylia.modestmining.screen.MillstoneMenu;
import com.google.common.collect.Lists;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class MillingEmiRecipeHandler implements StandardRecipeHandler<MillstoneMenu> {

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe instanceof MillingEmiRecipe;
    }

    @Override
    public List<Slot> getInputSources(MillstoneMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 10; i < 46; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(MillstoneMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 0; i < 9; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }
}
