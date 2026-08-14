package com.baisylia.modestmining.block.entity.custom;

import com.baisylia.modestmining.block.custom.ForgeBlock;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.recipe.AbstractForgeRecipe;
import com.baisylia.modestmining.recipe.ForgeFuelManager;
import com.baisylia.modestmining.recipe.ModRecipes;
import com.baisylia.modestmining.screen.ForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.baisylia.modestmining.block.custom.ForgeBlock.LIT;

public class ForgeBlockEntity extends BlockEntity implements MenuProvider, WorldlyContainer, StackedContentsCompatible {

    private static final int[] INGREDIENT_SLOTS = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    protected final ContainerData data;
    private final RecipeManager.CachedCheck<SingleRecipeInputContainer, AbstractForgeRecipe> quickCheck =
            RecipeManager.createCheck(ModRecipes.FORGING_TYPE.get());
    private int progress = 0;
    private int maxProgress = 72;
    private int litTime = 0;
    private int fuelAmount = 0;
    private int currentFuelTier = 0;
    private ItemStack activeFuel = ItemStack.EMPTY;
    private AbstractForgeRecipe currentRecipe = null;
    private final ItemStackHandler itemHandler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (slot < 9) {
                resetProgress();
            }
        }
    };

    public ForgeBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.FORGE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ForgeBlockEntity.this.progress;
                    case 1 -> ForgeBlockEntity.this.maxProgress;
                    case 2 -> ForgeBlockEntity.this.litTime;
                    case 3 -> ForgeBlockEntity.this.fuelAmount;
                    case 4 -> ForgeBlockEntity.this.currentFuelTier;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        ForgeBlockEntity.this.progress = value;
                        break;
                    case 1:
                        ForgeBlockEntity.this.maxProgress = value;
                        break;
                    case 2:
                        ForgeBlockEntity.this.litTime = value;
                        break;
                    case 3:
                        ForgeBlockEntity.this.fuelAmount = value;
                        break;
                    case 4:
                        ForgeBlockEntity.this.currentFuelTier = value;
                        break;
                }
            }

            public int getCount() {
                return 5;
            }
        };
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ForgeBlockEntity pBlockEntity) {
        if (isFueled(pBlockEntity, pPos, pLevel)) {
            pBlockEntity.litTime--;
            if (pBlockEntity.litTime <= 0) {
                pBlockEntity.activeFuel = ItemStack.EMPTY;
                pBlockEntity.currentFuelTier = 0;
            }
        } else {
            pBlockEntity.litTime = 0;
        }

        if (hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            pBlockEntity.setChanged(pLevel, pPos, pState, true);
            if (pBlockEntity.progress == pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(ForgeBlockEntity entity) {
        Level level = entity.level;
        BlockPos pos = entity.getBlockPos();

        SingleRecipeInputContainer input = new SingleRecipeInputContainer(entity.itemHandler);
        Optional<RecipeHolder<AbstractForgeRecipe>> recipeMatch = entity.quickCheck.getRecipeFor(input, level);

        if (recipeMatch.isPresent()) {
            AbstractForgeRecipe recipe = recipeMatch.get().value();
            ItemStack result = recipe.getResultItem(level.registryAccess());
            if (canInsertAmountIntoOutputSlot(entity.itemHandler, result)) {
                entity.currentRecipe = recipe;
                return startCraftIfFueled(entity, pos, level, recipe);
            }
        }

        return false;
    }

    private static boolean canInsertAmountIntoOutputSlot(ItemStackHandler handler, ItemStack output) {
        ItemStack currentOutput = handler.getStackInSlot(10);
        if (currentOutput.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameComponents(currentOutput, output)) {
            return false;
        }
        return currentOutput.getCount() + output.getCount() <= currentOutput.getMaxStackSize();
    }

    static boolean startCraftIfFueled(ForgeBlockEntity entity, BlockPos pos, Level level, AbstractForgeRecipe recipe) {
        if (!isFueled(entity, pos, level) || !recipe.fuelMatches(entity.activeFuel) || entity.currentFuelTier < recipe.getFuelTier()) {
            if (!entity.burnFuel(recipe))
                return false;
        }
        entity.maxProgress = recipe.getCookTime();
        return true;
    }

    static boolean isFueled(ForgeBlockEntity entity, BlockPos pos, Level level) {
        if (level.isClientSide) return false;
        if (entity.litTime > 0) {
            entity.setChanged(level, pos, entity.getBlockState(), true);
            return true;
        } else {
            entity.setChanged(level, pos, entity.getBlockState(), false);
            return false;
        }
    }

    private static void craftItem(ForgeBlockEntity entity) {
        var currentRecipe = entity.currentRecipe;
        if (currentRecipe != null) {
            for (int i = 0; i < 9; ++i) {
                ItemStack slotStack = entity.itemHandler.getStackInSlot(i);
                if (slotStack.hasCraftingRemainingItem()) {
                    Direction direction = entity.getBlockState().getValue(ForgeBlock.FACING).getCounterClockWise();
                    double x = (double) entity.worldPosition.getX() + 0.5 + (double) direction.getStepX() * 0.25;
                    double y = (double) entity.worldPosition.getY() + 0.7;
                    double z = (double) entity.worldPosition.getZ() + 0.5 + (double) direction.getStepZ() * 0.25;
                    spawnItemEntity(entity.level, entity.itemHandler.getStackInSlot(i).getCraftingRemainingItem(), x, y, z, (float) direction.getStepX() * 0.08F, 0.25, (float) direction.getStepZ() * 0.08F);
                }
            }

            for (int i = 0; i < 9; ++i) {
                entity.itemHandler.extractItem(i, 1, false);
            }

            ItemStack resultStack = currentRecipe.getResultItem(entity.level.registryAccess());
            ItemStack currentOutput = entity.itemHandler.getStackInSlot(10);
            if (currentOutput.isEmpty()) {
                entity.itemHandler.setStackInSlot(10, resultStack.copy());
            } else {
                currentOutput.grow(resultStack.getCount());
            }

            entity.resetProgress();
        }
    }

    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(xMotion, yMotion, zMotion);
        level.addFreshEntity(entity);
    }

    public static boolean isForgeFuel(Level level, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (stack.getBurnTime(RecipeType.BLASTING) > 0 || AbstractFurnaceBlockEntity.isFuel(stack) || ForgeFuelManager.isFuel(stack)) {
            return true;
        }
        if (level != null) {
            RecipeManager recipeManager = level.getRecipeManager();
            for (RecipeHolder<AbstractForgeRecipe> holder : recipeManager.getAllRecipesFor(ModRecipes.FORGING_TYPE.get())) {
                Optional<Ingredient> fuelOpt = holder.value().getFuel();
                if (fuelOpt.isPresent() && fuelOpt.get().test(stack)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.modestmining.forge");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new ForgeMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("forge.progress", progress);
        tag.putInt("forge.lit_time", litTime);
        tag.putInt("forge.max_progress", maxProgress);
        tag.putInt("forge.fuel_amount", fuelAmount);
        tag.putInt("forge.fuel_tier", currentFuelTier);
        tag.put("forge.active_fuel", activeFuel.saveOptional(registries));
        super.saveAdditional(tag, registries);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        if (nbt.contains("inventory")) {
            itemHandler.deserializeNBT(registries, nbt.getCompound("inventory"));
        }
        progress = nbt.getInt("forge.progress");
        litTime = nbt.getInt("forge.lit_time");
        maxProgress = nbt.getInt("forge.max_progress");
        fuelAmount = nbt.getInt("forge.fuel_amount");
        currentFuelTier = nbt.getInt("forge.fuel_tier");
        activeFuel = ItemStack.parseOptional(registries, nbt.getCompound("forge.active_fuel"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void setChanged(Level pLevel, BlockPos pPos, BlockState pState, boolean b) {
        pLevel.setBlock(pPos, pState.setValue(LIT, b), 3);
        super.setChanged();
    }

    private boolean burnFuel(AbstractForgeRecipe recipe) {
        if (!this.level.isClientSide) {
            var fuel = this.itemHandler.getStackInSlot(9).copy();
            if (recipe.fuelMatches(fuel)) {
                ForgeFuelManager.FuelInfo info = ForgeFuelManager.getFuelInfo(fuel);
                int tier = info != null ? info.tier() : 0;
                if (tier < recipe.getFuelTier()) {
                    return false;
                }

                int burnTime = info != null && info.burnTime() > 0 ? info.burnTime() : 0;
                if (burnTime <= 0) {
                    burnTime = fuel.getBurnTime(RecipeType.BLASTING);
                }
                if (burnTime <= 0) {
                    burnTime = fuel.getBurnTime(RecipeType.SMELTING);
                }
                if (burnTime <= 0) {
                    burnTime = 200;
                }
                this.fuelAmount = burnTime;
                this.litTime = burnTime;
                this.currentFuelTier = tier;
                this.activeFuel = fuel.copyWithCount(1);
                if (fuel.getCount() > 1) {
                    fuel.setCount(fuel.getCount() - 1);
                    this.itemHandler.setStackInSlot(9, fuel);
                } else {
                    this.itemHandler.setStackInSlot(9, fuel.getCraftingRemainingItem());
                }
                return true;
            }
        }
        return false;
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
        } else {
            return new int[]{direction == Direction.DOWN ? 10 : 9};
        }
    }

    public boolean canPlaceItem(int slot, ItemStack itemStack) {
        if (slot == 10) {
            return false;
        } else if (slot == 9) {
            return isForgeFuel(this.level, itemStack);
        } else {
            return true;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.itemHandler.getSlots(); ++i) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return this.itemHandler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return this.itemHandler.extractItem(slot, 1, false);
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        this.itemHandler.setStackInSlot(slot, itemStack);
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public void fillStackedContents(StackedContents pHelper) {
        for (int i = 0; i < this.getContainerSize(); i++) {
            pHelper.accountStack(this.getItem(i));
        }
    }

    public static class SingleRecipeInputContainer implements RecipeInput {
        private final ItemStackHandler handler;

        public SingleRecipeInputContainer(ItemStackHandler handler) {
            this.handler = handler;
        }

        @Override
        public ItemStack getItem(int index) {
            return handler.getStackInSlot(index);
        }

        @Override
        public int size() {
            return handler.getSlots();
        }
    }
}