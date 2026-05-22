package com.baisylia.modestmining.block.entity.custom;

import com.baisylia.modestmining.block.custom.MillstoneBlock;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.MillstoneMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

public class MillstoneBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer, StackedContentsCompatible {

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 72;

    private static final int[] INGREDIENT_SLOTS =
            new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

    private AbstractMillstoneRecipe currentRecipe = null;

    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();

            if (slot < 9) {
                resetProgress();
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final RecipeManager.CachedCheck<Container, AbstractMillstoneRecipe>
            quickCheck = RecipeManager.createCheck(ModRecipes.MILLING_TYPE.get());

    public MillstoneBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.MILLSTONE_BLOCK_ENTITY.get(),
                pWorldPosition, pBlockState);

        this.data = new ContainerData() {

            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.modestmining.millstone");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MillstoneMenu(id, inventory, this, this.data);
    }

    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (side == null && cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        if (!this.remove && side != null
                && cap == ForgeCapabilities.ITEM_HANDLER) {

            if (side == Direction.UP) {
                return handlers[0].cast();
            }
            else if (side == Direction.DOWN) {
                return handlers[1].cast();
            }
            else {
                return handlers[2].cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("millstone.progress", progress);
        tag.putInt("millstone.max_progress", maxProgress);

        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        itemHandler.deserializeNBT(nbt.getCompound("inventory"));

        progress = nbt.getInt("millstone.progress");
        maxProgress = nbt.getInt("millstone.max_progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MillstoneBlockEntity entity) {
        if (level.isClientSide()) return;

        if (!state.getValue(MillstoneBlock.LIT)) {
            entity.resetProgress();
            return;
        }

        if (hasRecipe(entity)) {

            entity.progress++;

            setChanged(level, pos, state);

            if (entity.progress >= entity.maxProgress) {
                craftItem(entity);
            }

        } else {
            entity.resetProgress();
        }
    }

    public static void setChanged(Level level, BlockPos pos, BlockState state) {
        level.sendBlockUpdated(pos, state, state, 3);
    }

    private static boolean hasRecipe(MillstoneBlockEntity entity) {

        Level level = entity.level;

        SimpleContainer inventory =
                new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i,
                    entity.itemHandler.getStackInSlot(i));
        }

        Optional<AbstractMillstoneRecipe> recipeMatch =
                entity.quickCheck.getRecipeFor(inventory, level);

        if (recipeMatch.isPresent()) {

            ItemStack result =
                    recipeMatch.get().getResultItem();

            if (canInsertAmountIntoOutputSlot(inventory, result)) {

                entity.currentRecipe = recipeMatch.get();
                entity.maxProgress =
                        recipeMatch.get().getCookTime();

                return true;
            }
        }

        return false;
    }

    private static boolean canInsertAmountIntoOutputSlot(
            SimpleContainer inventory,
            ItemStack output) {

        ItemStack currentOutput = inventory.getItem(9);

        if (currentOutput.isEmpty()) {
            return true;
        }

        if (!currentOutput.is(output.getItem())) {
            return false;
        }

        return currentOutput.getCount() + output.getCount()
                <= currentOutput.getMaxStackSize();
    }

    private static void craftItem(MillstoneBlockEntity entity) {

        AbstractMillstoneRecipe currentRecipe =
                entity.currentRecipe;

        if (currentRecipe == null) return;

        for (int i = 0; i < 9; ++i) {

            ItemStack slotStack =
                    entity.itemHandler.getStackInSlot(i);

            if (slotStack.hasCraftingRemainingItem()) {

                Direction direction =
                        entity.getBlockState()
                                .getValue(MillstoneBlock.FACING)
                                .getCounterClockWise();

                double x =
                        entity.worldPosition.getX()
                                + 0.5
                                + direction.getStepX() * 0.25;

                double y =
                        entity.worldPosition.getY() + 0.7;

                double z =
                        entity.worldPosition.getZ()
                                + 0.5
                                + direction.getStepZ() * 0.25;

                spawnItemEntity(
                        entity.level,
                        slotStack.getCraftingRemainingItem(),
                        x, y, z,
                        direction.getStepX() * 0.08F,
                        0.25,
                        direction.getStepZ() * 0.08F
                );
            }
        }

        for (int i = 0; i < 9; ++i) {
            entity.itemHandler.extractItem(i, 1, false);
        }

        ItemStack output =
                entity.itemHandler.getStackInSlot(9);

        output.grow(currentRecipe.getResultItem().getCount());

        if (output.isEmpty()) {

            entity.itemHandler.setStackInSlot(
                    9,
                    currentRecipe.getResultItem().copy()
            );
        }

        entity.resetProgress();
    }

    public static void spawnItemEntity(Level level,
                                       ItemStack stack,
                                       double x,
                                       double y,
                                       double z,
                                       double xMotion,
                                       double yMotion,
                                       double zMotion) {

        ItemEntity entity =
                new ItemEntity(level, x, y, z, stack);

        entity.setDeltaMovement(
                xMotion,
                yMotion,
                zMotion
        );

        level.addFreshEntity(entity);
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
        this.currentRecipe = null;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {

        if (direction == Direction.UP) {
            return INGREDIENT_SLOTS;
        }

        return new int[]{9};
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot != 9;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot,
                                           ItemStack stack,
                                           @Nullable Direction direction) {

        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot,
                                          ItemStack stack,
                                          Direction direction) {

        return true;
    }

    @Override
    public int getContainerSize() {
        return itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {

        for (int i = 0; i < itemHandler.getSlots(); ++i) {

            if (!itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return itemHandler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return itemHandler.extractItem(slot, 1, false);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        itemHandler.setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {

        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }

        return player.distanceToSqr(
                this.worldPosition.getX() + 0.5D,
                this.worldPosition.getY() + 0.5D,
                this.worldPosition.getZ() + 0.5D
        ) <= 64.0D;
    }

    @Override
    public void clearContent() {

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void fillStackedContents(StackedContents helper) {

        for (int i = 0; i < this.getContainerSize(); i++) {
            helper.accountStack(this.getItem(i));
        }
    }
}