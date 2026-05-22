package com.baisylia.modestmining.screen;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.block.entity.custom.MillstoneBlockEntity;
import com.baisylia.modestmining.screen.slot.ModFuelSlot;
import com.baisylia.modestmining.screen.slot.ModResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class MillstoneMenu extends RecipeBookMenu<Container> {

    private final MillstoneBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public MillstoneMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public MillstoneMenu(int pContainerId, Inventory pPlayerInventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.MILLSTONE_MENU.get(), pContainerId);
        checkContainerSize(pPlayerInventory, 10);
        blockEntity = ((MillstoneBlockEntity) entity);
        this.level = pPlayerInventory.player.level;
        this.data = data;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            int index = 0;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    this.addSlot(new SlotItemHandler(handler, index++, 30 + y * 18, 17 + x * 18));
                }
            }
            this.addSlot(new ModResultSlot(handler, index, 124, 19));
        });

        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 9; ++y) {
                this.addSlot(new Slot(pPlayerInventory, y + x * 9 + 9, 8 + y * 18, 84 + x * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(pPlayerInventory, x, 8 + x * 18, 142));
        }

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public boolean isFueled() {
        return blockEntity.getBlockState().getValue(BlockStateProperties.LIT);
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);  // Max Progress
        int progressArrowSize = 26; // This is the height in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    public int getLitTime() {
        int litTime = this.data.get(2);
        int fuel = this.data.get(3);
        float percentage = (float) litTime / fuel;
        percentage = percentage * 17;
        return (int) percentage;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        if (index == 9) {

            if (!moveItemStackTo(sourceStack, 10, 46, true)) {
                return ItemStack.EMPTY;
            }

            sourceSlot.onQuickCraft(sourceStack, copy);
        }

        else if (index < 9) {

            if (!moveItemStackTo(sourceStack, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
        }

        else {

            if (!moveItemStackTo(sourceStack, 0, 9, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);

        return copy;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.MILLSTONE.get());
    }

    @Override
    public void fillCraftSlotsStackedContents(StackedContents helper) {
        this.blockEntity.fillStackedContents(helper);
    }

    @Override
    public void clearCraftingContent() {
        for (int i = 0; i < 9; ++i) this.getSlot(i).set(ItemStack.EMPTY);
        this.getSlot(9).set(ItemStack.EMPTY);
    }

    @Override
    public boolean recipeMatches(Recipe<? super Container> recipe) {
//        return false;
        return recipe.matches(this.blockEntity, this.level);
    }

    @Override
    public int getResultSlotIndex() {
        return 9;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public int getSize() {
        return 10;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return ModestMining.MILLING_RECIPE_BOOK_TYPE;
    }

    @Override
    public boolean shouldMoveToInventory(int index) {
        return index == 10 || index < (getGridWidth() * getGridHeight());
    }

}