package com.baisylia.modestmining.mixin;

import com.baisylia.modestmining.config.ModConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TridentItem.class)
public class TridentItemMixin {

    @ModifyArg(
        method = "releaseUsing",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
        )
    )
    private Entity modestmining$applyTridentCrit(Entity entity) {
        if (entity instanceof ThrownTrident thrownTrident) {
            if (!ModConfig.SPEC.isLoaded() || ModConfig.ENHANCED_TRIDENTS.get()) {
                if (thrownTrident.getOwner() instanceof Player player) {
                    if ((player.fallDistance > 0.0F && !player.isOnGround()) || player.isSprinting()) {
                        thrownTrident.setCritArrow(true);
                    }
                }
            }
        }
        return entity;
    }
}
