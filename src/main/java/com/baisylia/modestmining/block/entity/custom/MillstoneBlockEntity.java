package com.baisylia.modestmining.block.entity.custom;

import com.baisylia.modestmining.block.custom.MillstoneBlock;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.recipe.AbstractMillstoneRecipe;
import com.baisylia.modestmining.recipe.MillstoneRecipe;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.MillstoneMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MillstoneBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer, StackedContentsCompatible {

    protected final ContainerData data;
    private final RecipeManager.CachedCheck<ForgeBlockEntity.SingleRecipeInputContainer, AbstractMillstoneRecipe> quickCheck =
            RecipeManager.createCheck(ModRecipes.MILLING_TYPE.get());
    private final IItemHandlerModifiable[] sidedHandlers = new IItemHandlerModifiable[]{
            new SidedInvWrapper(this, Direction.DOWN),
            new SidedInvWrapper(this, Direction.UP),
            new SidedInvWrapper(this, Direction.NORTH),
            new SidedInvWrapper(this, Direction.SOUTH),
            new SidedInvWrapper(this, Direction.WEST),
            new SidedInvWrapper(this, Direction.EAST)
    };
    private int progress = 0;
    private int maxProgress = 72;
    private AbstractMillstoneRecipe currentRecipe = null;
    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (slot == 0) {
                resetProgress();
            }
        }
    };

    public MillstoneBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.MILLSTONE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
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
        ForgeBlockEntity.SingleRecipeInputContainer input = new ForgeBlockEntity.SingleRecipeInputContainer(entity.itemHandler);
        Optional<RecipeHolder<AbstractMillstoneRecipe>> recipeMatch = entity.quickCheck.getRecipeFor(input, entity.level);

        if (recipeMatch.isPresent()) {
            entity.currentRecipe = recipeMatch.get().value();
            entity.maxProgress = recipeMatch.get().value().getCookTime();
            return canInsertAllOutputs(entity, recipeMatch.get().value());
        }

        return false;
    }

    private static boolean canInsertAllOutputs(MillstoneBlockEntity entity, AbstractMillstoneRecipe recipe) {
        if (!(recipe instanceof MillstoneRecipe millstoneRecipe)) {
            return canInsertStack(entity, recipe.getResultItem(entity.level.registryAccess()));
        }
        for (ItemStack result : millstoneRecipe.results) {
            if (!result.isEmpty() && !canInsertStack(entity, result)) return false;
        }
        return true;
    }

    private static boolean canInsertStack(MillstoneBlockEntity entity, ItemStack stack) {
        for (int slot = 1; slot <= 9; slot++) {
            if (entity.itemHandler.insertItem(slot, stack, true).isEmpty()) return true;
        }
        return false;
    }

    private static void craftItem(MillstoneBlockEntity entity) {
        MillstoneRecipe recipe = (MillstoneRecipe) entity.currentRecipe;
        if (recipe == null) return;

        entity.itemHandler.extractItem(0, 1, false);

        for (int i = 0; i < recipe.results.size(); i++) {
            ItemStack result = recipe.results.get(i);
            float chance = recipe.chances.get(i);

            if (entity.level.random.nextFloat() <= chance) {
                ItemStack output = result.copy();
                for (int slot = 1; slot <= 9; slot++) {
                    ItemStack existing = entity.itemHandler.getStackInSlot(slot);
                    if (existing.isEmpty()) {
                        entity.itemHandler.setStackInSlot(slot, output);
                        break;
                    }
                    if (ItemStack.isSameItemSameComponents(existing, output)
                            && existing.getCount() + output.getCount() <= existing.getMaxStackSize()) {
                        existing.grow(output.getCount());
                        break;
                    }
                }
            }
        }

        entity.resetProgress();
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public IItemHandler getItemHandler(@Nullable Direction side) {
        if (side == null) {
            return this.itemHandler;
        }
        return sidedHandlers[side.get3DDataValue()];
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

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("millstone.progress", progress);
        tag.putInt("millstone.max_progress", maxProgress);
        super.saveAdditional(tag, registries);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        if (nbt.contains("inventory")) {
            itemHandler.deserializeNBT(registries, nbt.getCompound("inventory"));
        }
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

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
        this.currentRecipe = null;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.UP) {
            return new int[]{0};
        }
        return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot == 0;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return slot != 0;
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
        return player.distanceToSqr(this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 0.5D, this.worldPosition.getZ() + 0.5D) <= 64.0D;
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