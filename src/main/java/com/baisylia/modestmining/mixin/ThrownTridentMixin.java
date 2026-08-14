package com.baisylia.modestmining.mixin;

import com.baisylia.modestmining.config.ModConfig;
import net.minecraft.world.entity.projectile.ThrownTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
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
}
