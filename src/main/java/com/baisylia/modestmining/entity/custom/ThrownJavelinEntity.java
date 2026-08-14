package com.baisylia.modestmining.entity.custom;

import com.baisylia.modestmining.entity.ModEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ThrownJavelinEntity extends AbstractArrow {

    private static final EntityDataAccessor<ItemStack> DATA_JAVELIN_STACK =
            SynchedEntityData.defineId(ThrownJavelinEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Byte> DATA_LOYALTY =
            SynchedEntityData.defineId(ThrownJavelinEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> DATA_FOIL =
            SynchedEntityData.defineId(ThrownJavelinEntity.class, EntityDataSerializers.BOOLEAN);
    public int clientSideReturnTridentTickCount;
    private ItemStack javelinStack = ItemStack.EMPTY;
    private boolean dealtDamage;

    public ThrownJavelinEntity(EntityType<? extends AbstractArrow> type, Level level) {
        super(type, level);
    }

    public ThrownJavelinEntity(Level level, LivingEntity shooter, ItemStack stack) {
        super(ModEntityTypes.THROWN_JAVELIN.get(), shooter, level);
        this.javelinStack = stack.copy();
        this.entityData.set(DATA_JAVELIN_STACK, stack.copy());
        this.entityData.set(DATA_LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
        this.entityData.set(DATA_FOIL, stack.hasFoil());
    }

    @Override
    public ItemStack getPickupItem() {
        return this.entityData.get(DATA_JAVELIN_STACK);
    }

    public boolean isFoil() {
        return this.entityData.get(DATA_FOIL);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_JAVELIN_STACK, ItemStack.EMPTY);
        this.entityData.define(DATA_LOYALTY, (byte) 0);
        this.entityData.define(DATA_FOIL, false);
    }

    @Override
    public void tick() {
        if (this.inGroundTime > 4) {
            this.dealtDamage = true;
        }

        Entity owner = this.getOwner();
        int loyaltyLevel = this.entityData.get(DATA_LOYALTY);
        if (loyaltyLevel > 0 && (this.dealtDamage || this.isNoPhysics()) && owner != null) {
            if (!this.isAcceptableReturnOwner()) {
                if (!this.level.isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double) loyaltyLevel, this.getZ());
                if (this.level.isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * (double) loyaltyLevel;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnTridentTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnTridentTickCount;
            }
        }

        super.tick();
    }

    private boolean isAcceptableReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    @Override
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = (float) this.getBaseDamage();
        if (this.isCritArrow()) {
            f *= 1.5F;
        }
        if (entity instanceof LivingEntity livingentity) {
            f += EnchantmentHelper.getDamageBonus(this.getPickupItem(), livingentity.getMobType());
        }

        Entity owner = this.getOwner();
        DamageSource damagesource = DamageSource.trident(this, owner == null ? this : owner);
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingentity1) {
                if (owner instanceof LivingEntity livingOwner) {
                    EnchantmentHelper.doPostHurtEffects(livingentity1, livingOwner);
                    EnchantmentHelper.doPostDamageEffects(livingOwner, livingentity1);
                }

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        this.playSound(soundevent, 1.0F, 1.0F);
    }

    @Override
    protected boolean tryPickup(Player pPlayer) {
        return super.tryPickup(pPlayer) || (this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem()));
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }

    @Override
    public void playerTouch(Player player) {
        if (this.ownedBy(player) || this.getOwner() == null) {
            super.playerTouch(player);
        }
    }

    @Override
    public void tickDespawn() {
        int loyaltyLevel = this.entityData.get(DATA_LOYALTY);
        if (this.pickup != AbstractArrow.Pickup.ALLOWED || loyaltyLevel <= 0) {
            super.tickDespawn();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Javelin", this.javelinStack.save(new CompoundTag()));
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Javelin")) {
            this.javelinStack = ItemStack.of(tag.getCompound("Javelin"));
            this.entityData.set(DATA_JAVELIN_STACK, this.javelinStack.copy());
            this.entityData.set(DATA_LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.javelinStack));
            this.entityData.set(DATA_FOIL, this.javelinStack.hasFoil());
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
    }
}