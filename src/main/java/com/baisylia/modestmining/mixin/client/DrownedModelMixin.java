package com.baisylia.modestmining.mixin.client;

import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedModel.class)
public class DrownedModelMixin<T extends Zombie> {

    @Inject(method = "prepareMobModel(Lnet/minecraft/world/entity/monster/Zombie;FFF)V", at = @At("TAIL"))
    private void modestmining$injectJavelinPose(T entity, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci) {
        @SuppressWarnings("unchecked")
        DrownedModel<T> model = (DrownedModel<T>) (Object) this;

        ItemStack itemstack = entity.getItemInHand(InteractionHand.MAIN_HAND);

        if (itemstack.getItem() instanceof JavelinItem && entity.isAggressive()) {
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                model.rightArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
            } else {
                model.leftArmPose = HumanoidModel.ArmPose.THROW_SPEAR;
            }
        }
    }
}