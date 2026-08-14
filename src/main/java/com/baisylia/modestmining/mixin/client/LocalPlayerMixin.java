package com.baisylia.modestmining.mixin.client;

import com.baisylia.modestmining.config.ModConfig;
import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean modestmining$isUsingItemAiStep(LocalPlayer player) {
        if (player.isUsingItem()) {
            Item item = player.getUseItem().getItem();
            if (item instanceof JavelinItem) {
                if (!ModConfig.SPEC.isLoaded() || ModConfig.REMOVE_JAVELIN_SLOWDOWN.get()) {
                    return false;
                }
            } else if (item instanceof TridentItem) {
                if (ModConfig.SPEC.isLoaded() && ModConfig.ENHANCED_TRIDENTS.get()) {
                    return false;
                }
            }
        }
        return player.isUsingItem();
    }
}
