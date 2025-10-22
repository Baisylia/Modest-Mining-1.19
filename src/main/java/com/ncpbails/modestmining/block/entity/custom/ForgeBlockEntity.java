package com.ncpbails.modestmining.block.entity.custom;

import com.ncpbails.modestmining.block.custom.ForgeBlock;
import com.ncpbails.modestmining.block.entity.ModBlockEntities;
import com.ncpbails.modestmining.recipe.AbstractForgeRecipe;
import com.ncpbails.modestmining.recipe.ForgeRecipe;
import com.ncpbails.modestmining.recipe.ForgeShapedRecipe;
import com.ncpbails.modestmining.screen.ForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.ncpbails.modestmining.block.custom.ForgeBlock.LIT;

public class ForgeBlockEntity extends BlockEntity implements MenuProvider {

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int litTime = 0;
    private int fuelAmount = 0;
    static int countOutput = 1;
    private ContainerOpenersCounter openersCounter;
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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public ForgeBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.FORGE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> ForgeBlockEntity.this.progress;
                    case 1 -> ForgeBlockEntity.this.maxProgress;
                    case 2 -> ForgeBlockEntity.this.litTime;
                    case 3 -> ForgeBlockEntity.this.fuelAmount;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: ForgeBlockEntity.this.progress = value; break;
                    case 1: ForgeBlockEntity.this.maxProgress = value; break;
                    case 2: ForgeBlockEntity.this.litTime = value; break;
                }
            }

            public int getCount() {
                return 4;
            }
        };
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

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("forge.progress", progress);
        tag.putInt("forge.lit_time", litTime);
        tag.putInt("forge.max_progress", maxProgress);
        tag.putInt("forge.fuel_amount", fuelAmount);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("forge.progress");
        litTime = nbt.getInt("forge.lit_time");
        maxProgress = nbt.getInt("forge.max_progress");
        fuelAmount = nbt.getInt("forge.fuel_amount");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ForgeBlockEntity pBlockEntity) {


        if (isFueled(pBlockEntity, pPos, pLevel)) {
            pBlockEntity.litTime--;
        } else {
            pBlockEntity.litTime = 0;
        }

        if (hasRecipe(pBlockEntity)) {
            pBlockEntity.progress++;
            pBlockEntity.setChanged(pLevel, pPos, pState, true);
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                craftItem(pBlockEntity);
            }
        } else {
            pBlockEntity.resetProgress();
        }
    }

    private void setChanged(Level pLevel, BlockPos pPos, BlockState pState, boolean b) {
        pLevel.setBlock(pPos, pState.setValue(LIT, b), 3);
        super.setChanged();
    }

    private static boolean hasRecipe(ForgeBlockEntity entity) {
        Level level = entity.level;
        BlockPos pos = entity.getBlockPos();

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        if (!inventory.getItem(10).isEmpty() && !inventory.getItem(10).isStackable()) {
            return false;
        }

        if (entity.currentRecipe != null) {
            return startCraftIfFueled(entity, pos, level, entity.currentRecipe.getCookTime());
        }

        // Check for ForgeShapedRecipe
        Optional<ForgeShapedRecipe> shapedMatch = level.getRecipeManager()
                .getRecipeFor(ForgeShapedRecipe.Type.INSTANCE, inventory, level);

        // Check for ForgeRecipe
        Optional<ForgeRecipe> recipeMatch = level.getRecipeManager()
                .getRecipeFor(ForgeRecipe.Type.INSTANCE, inventory, level);

        if (shapedMatch.isPresent()) {
            entity.currentRecipe = shapedMatch.get();
            return startCraftIfFueled(entity, pos, level, shapedMatch.get().getCookTime());
        } else if (recipeMatch.isPresent()) {
            entity.currentRecipe = recipeMatch.get();
            return startCraftIfFueled(entity, pos, level, recipeMatch.get().getCookTime());
        } else {
            entity.currentRecipe = null;
        }

        return false;
    }

    static boolean startCraftIfFueled(ForgeBlockEntity entity, BlockPos pos, Level level, int progress) {
        if (!isFueled(entity, pos, level)) {
            if (!entity.burnFuel())
                return false;
        }
        entity.maxProgress = progress;
        return true;
    }

    static boolean isFueled(ForgeBlockEntity entity, BlockPos pos, Level level) {
        if (level.isClientSide) return false;
        if (entity.litTime > 0) {
            entity.setChanged(level, pos, entity.getBlockState(), true);
            return true;
        }
        else {
            entity.setChanged(level, pos, entity.getBlockState(), false);
            return false;
        }
    }

    private boolean burnFuel() {
        if (!this.level.isClientSide) {
            var fuel = this.itemHandler.getStackInSlot(9).copy();
            if (AbstractFurnaceBlockEntity.isFuel(fuel) && this.litTime == 0) {
                this.fuelAmount = ForgeHooks.getBurnTime(fuel, RecipeType.BLASTING)+2;
                this.litTime = ForgeHooks.getBurnTime(fuel, RecipeType.BLASTING)+2;
                if (fuel.getCount() > 1) {
                    fuel.setCount(fuel.getCount()-1);
                    this.itemHandler.setStackInSlot(9, fuel);
                } else {
                    this.itemHandler.setStackInSlot(9, fuel.getCraftingRemainingItem());
                }
                return true;
            }
        }
        return false;
    }

    private static void craftItem(ForgeBlockEntity entity) {

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        var currentRecipe = entity.currentRecipe;
        if (currentRecipe != null) {
            for(int i = 0; i < 9; ++i) {
                ItemStack slotStack = entity.itemHandler.getStackInSlot(i);
                if (slotStack.hasCraftingRemainingItem()) {
                    Direction direction = entity.getBlockState().getValue(ForgeBlock.FACING).getCounterClockWise();
                    double x = (double)entity.worldPosition.getX() + 0.5 + (double)direction.getStepX() * 0.25;
                    double y = (double)entity.worldPosition.getY() + 0.7;
                    double z = (double)entity.worldPosition.getZ() + 0.5 + (double)direction.getStepZ() * 0.25;
                    spawnItemEntity(entity.level, entity.itemHandler.getStackInSlot(i).getCraftingRemainingItem(), x, y, z, (float)direction.getStepX() * 0.08F, 0.25, (float)direction.getStepZ() * 0.08F);
                }
            }

            for (int i = 0; i < 9; ++i) {
                entity.itemHandler.extractItem(i, 1, false);
            }
            inventory.getItem(10).is(currentRecipe.getResultItem().getItem());

            entity.itemHandler.setStackInSlot(10, new ItemStack(currentRecipe.getResultItem().getItem(),
                    entity.itemHandler.getStackInSlot(10).getCount() + entity.getTheCount(currentRecipe.getResultItem())));

            entity.resetProgress();

        }
    }
    public static void spawnItemEntity(Level level, ItemStack stack, double x, double y, double z, double xMotion, double yMotion, double zMotion) {
        ItemEntity entity = new ItemEntity(level, x, y, z, stack);
        entity.setDeltaMovement(xMotion, yMotion, zMotion);
        level.addFreshEntity(entity);
    }

    private int getTheCount (ItemStack itemIn) {
        return itemIn.getCount();
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
        this.currentRecipe = null;
    }
}