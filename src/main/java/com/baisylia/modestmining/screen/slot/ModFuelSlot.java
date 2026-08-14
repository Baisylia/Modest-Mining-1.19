package com.baisylia.modestmining.screen.slot;

import com.baisylia.modestmining.recipe.ForgeFuelManager;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import static net.minecraft.world.inventory.FurnaceFuelSlot.isBucket;

public class ModFuelSlot extends SlotItemHandler {
    public ModFuelSlot(IItemHandler itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ForgeFuelManager.isFuel(stack) || isBucket(stack);
    }
}