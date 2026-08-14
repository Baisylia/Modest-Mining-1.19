package com.baisylia.modestmining.entity.custom;

import com.baisylia.modestmining.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
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
        super(ModEntityTypes.THROWN_JAVELIN.get(), shooter, level, stack, null);
        this.javelinStack = stack.copy();
        this.entityData.set(DATA_JAVELIN_STACK, stack.copy());
        this.entityData.set(DATA_FOIL, stack.hasFoil());
    }

    @Override
    public ItemStack getDefaultPickupItem() {
        return this.entityData.get(DATA_JAVELIN_STACK);
    }

    public ItemStack getPickupItem() {
        return this.getDefaultPickupItem();
    }

    public boolean isFoil() {
        return this.entityData.get(DATA_FOIL);
    }

    public boolean isChanneling() {
        ItemStack stack = this.getPickupItem();
        if (stack.isEmpty()) {
            return false;
        }
        Holder<Enchantment> channeling = this.level().registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(Enchantments.CHANNELING);
        return stack.getEnchantments().getLevel(channeling) > 0;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_JAVELIN_STACK, ItemStack.EMPTY);
        builder.define(DATA_LOYALTY, (byte) 0);
        builder.define(DATA_FOIL, false);
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
                if (!this.level().isClientSide && this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * (double) loyaltyLevel, this.getZ());
                if (this.level().isClientSide) {
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
        Entity owner = this.getOwner();
        DamageSource damagesource = this.damageSources().trident(this, owner == null ? this : owner);
        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingentity1) {
                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float soundVolume = 1.0F;
        if (this.level().isThundering() && this.isChanneling()) {
            BlockPos blockpos = entity.blockPosition();
            if (this.level().canSeeSky(blockpos)) {
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level());
                if (lightningbolt != null) {
                    lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
                    lightningbolt.setCause(owner instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                    this.level().addFreshEntity(lightningbolt);
                    this.playSound(SoundEvents.TRIDENT_THUNDER.value(), 5.0F, 1.0F);
                    return;
                }
            }
        }

        this.playSound(soundevent, soundVolume, 1.0F);
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
        if (!this.javelinStack.isEmpty()) {
            tag.put("Javelin", this.javelinStack.save(this.registryAccess()));
        }
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Javelin")) {
            this.javelinStack = ItemStack.parseOptional(this.registryAccess(), tag.getCompound("Javelin"));
            this.entityData.set(DATA_JAVELIN_STACK, this.javelinStack.copy());
            this.entityData.set(DATA_FOIL, this.javelinStack.hasFoil());
        }
        this.dealtDamage = tag.getBoolean("DealtDamage");
    }
}