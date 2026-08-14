package com.baisylia.modestmining.entity.ai;

import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class JavelinAttackGoal extends Goal {

    private final Mob mob;
    private final double speedModifier;
    private final int attackIntervalMin;
    private final int attackIntervalMax;
    private final float attackRadius;
    private final float attackRadiusSqr;
    @Nullable
    private LivingEntity target;
    private int attackTime = -1;
    private int seeTime;

    public JavelinAttackGoal(Mob mob, double speedModifier, int attackInterval, float attackRadius) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.attackIntervalMin = attackInterval;
        this.attackIntervalMax = attackInterval;
        this.attackRadius = attackRadius;
        this.attackRadiusSqr = attackRadius * attackRadius;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    private boolean isHoldingJavelin() {
        return this.mob.getMainHandItem().getItem() instanceof JavelinItem;
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null && livingentity.isAlive() && this.isHoldingJavelin()) {
            this.target = livingentity;
            return true;
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.isHoldingJavelin() && (this.canUse() || (this.target != null && this.target.isAlive() && !this.mob.getNavigation().isDone()));
    }

    @Override
    public void start() {
        this.mob.setAggressive(true);
        this.mob.startUsingItem(InteractionHand.MAIN_HAND);
    }

    @Override
    public void stop() {
        this.mob.stopUsingItem();
        this.mob.setAggressive(false);
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            return;
        }

        double distanceSqr = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean canSee = this.mob.getSensing().hasLineOfSight(this.target);
        if (canSee) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(distanceSqr > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        if (--this.attackTime == 0) {
            if (!canSee) {
                return;
            }

            float distanceFactor = (float) Math.sqrt(distanceSqr) / this.attackRadius;
            float clampedFactor = Mth.clamp(distanceFactor, 0.1F, 1.0F);
            this.throwJavelin(this.target, clampedFactor);
            this.attackTime = Mth.floor(distanceFactor * (float) (this.attackIntervalMax - this.attackIntervalMin) + (float) this.attackIntervalMin);
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(distanceSqr) / (double) this.attackRadius, (double) this.attackIntervalMin, (double) this.attackIntervalMax));
        }
    }

    private void throwJavelin(LivingEntity target, float distanceFactor) {
        ItemStack heldStack = this.mob.getMainHandItem();
        if (!(heldStack.getItem() instanceof JavelinItem javelinItem)) {
            return;
        }

        ThrownJavelinEntity javelin = new ThrownJavelinEntity(this.mob.level(), this.mob, heldStack.copy());
        javelin.setBaseDamage(javelinItem.getThrowDamage());
        double dx = target.getX() - this.mob.getX();
        double dy = target.getY(0.3333333333333333D) - javelin.getY();
        double dz = target.getZ() - this.mob.getZ();
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        javelin.shoot(dx, dy + horizontalDistance * (double) 0.2F, dz, 1.6F, (float) (14 - this.mob.level().getDifficulty().getId() * 4));
        this.mob.playSound(SoundEvents.TRIDENT_THROW.value(), 1.0F, 1.0F / (this.mob.getRandom().nextFloat() * 0.4F + 0.8F));
        this.mob.level().addFreshEntity(javelin);
    }
}
