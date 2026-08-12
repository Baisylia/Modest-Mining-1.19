package com.baisylia.modestmining.screen.slot;

import com.baisylia.modestmining.block.entity.custom.ForgeBlockEntity;
import net.minecraft.world.inventory.FurnaceFuelSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ModFuelSlot extends SlotItemHandler {
    private final Level level;

    public ModFuelSlot(IItemHandler itemHandler, Level level, int index, int x, int y) {
        super(itemHandler, index, x, y);
        this.level = level;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ForgeBlockEntity.isForgeFuel(this.level, stack) || FurnaceFuelSlot.isBucket(stack);
    }
}