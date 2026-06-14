package com.baisylia.modestmining.entity.custom;

import com.baisylia.modestmining.entity.ModEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownJavelinEntity extends AbstractArrow {

    private static final EntityDataAccessor<ItemStack> DATA_JAVELIN_STACK =
            SynchedEntityData.defineId(ThrownJavelinEntity.class, EntityDataSerializers.ITEM_STACK);

    private ItemStack javelinStack = ItemStack.EMPTY;

    public ThrownJavelinEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public ThrownJavelinEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(ModEntityTypes.THROWN_JAVELIN.get(), shooter, level);
        this.javelinStack = stack.copy();
        this.entityData.set(DATA_JAVELIN_STACK, stack.copy());
    }

    @Override
    public ItemStack getPickupItem() {
        return this.entityData.get(DATA_JAVELIN_STACK);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_JAVELIN_STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Javelin", this.javelinStack.save(new CompoundTag()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Javelin")) {
            this.javelinStack = ItemStack.of(tag.getCompound("Javelin"));
            this.entityData.set(DATA_JAVELIN_STACK, this.javelinStack.copy());
        }
    }
}