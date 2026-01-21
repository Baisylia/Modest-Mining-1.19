package com.baisylia.modestmining.integration.emi;

import com.google.common.collect.Lists;
import com.baisylia.modestmining.screen.ForgeMenu;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import net.minecraft.world.inventory.Slot;

import java.util.List;

public class ForgingEmiRecipeHandler implements StandardRecipeHandler<ForgeMenu> {

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        return recipe instanceof ForgingEmiRecipe;
    }

    @Override
    public List<Slot> getInputSources(ForgeMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 11; i < 47; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }

    @Override
    public List<Slot> getCraftingSlots(ForgeMenu handler) {
        List<Slot> list = Lists.newArrayList();
        for (int i = 0; i < 9; i++) {
            list.add(handler.getSlot(i));
        }
        return list;
    }
}
