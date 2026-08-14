package com.baisylia.modestmining.mixin.client;

import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @WrapOperation(method = {"aiStep", "canStartSprinting"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean modestmining$isUsingItemForMovement(LocalPlayer player, Operation<Boolean> original) {
        if (original.call(player)) {
            Item item = player.getUseItem().getItem();
            if (item instanceof JavelinItem) {
                return ModConfig.SPEC.isLoaded() && !ModConfig.REMOVE_JAVELIN_SLOWDOWN.get();
            } else if (item instanceof TridentItem) {
                return !ModConfig.SPEC.isLoaded() || !ModConfig.ENHANCED_TRIDENTS.get();
            }
            return true;
        }
        return false;
    }
}
