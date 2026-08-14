package com.baisylia.modestmining.mixin;

import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ThrownTrident.class)
public class ThrownTridentMixin {

    @ModifyVariable(method = "onHitEntity", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float modestmining$applyTridentCritDamage(float f) {
        if (!ModConfig.SPEC.isLoaded() || ModConfig.ENHANCED_TRIDENTS.get()) {
            ThrownTrident self = (ThrownTrident) (Object) this;
            if (self.isCritArrow()) {
                return f * 1.5F;
            }
        }
        return f;
    }

    @ModifyArg(
            method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/ThrownTrident;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"
            ),
            index = 0
    )
    private SoundEvent modestmining$criticalTridentSound(SoundEvent original) {
        ThrownTrident self = (ThrownTrident) (Object) this;
        if (self.isCritArrow() && (!ModConfig.SPEC.isLoaded() || ModConfig.ENHANCED_TRIDENTS.get())) {
            return ModSounds.CRITICAL_PIERCE.value();
        }
        return original;
    }
}
