package com.baisylia.modestmining.mixin.client;

import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieVillagerModel.class)
public class ZombieVillagerModelMixin<T extends Zombie> {

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/Zombie;FFFFF)V", at = @At("TAIL"))
    private void modestmining$setupZombieVillagerAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        @SuppressWarnings("unchecked")
        ZombieVillagerModel<T> model = (ZombieVillagerModel<T>) (Object) this;

        ItemStack mainHand = pEntity.getMainHandItem();
        boolean isThrowingWeapon = mainHand.getItem() instanceof JavelinItem || mainHand.is(Items.TRIDENT) || mainHand.getItem() instanceof TridentItem;
        if (isThrowingWeapon && (pEntity.isAggressive() || model.leftArmPose == HumanoidModel.ArmPose.THROW_SPEAR || model.rightArmPose == HumanoidModel.ArmPose.THROW_SPEAR)) {
            if (pEntity.getMainArm() == HumanoidArm.RIGHT) {
                model.rightArm.xRot = model.rightArm.xRot * 0.5F - (float) Math.PI;
                model.rightArm.yRot = 0.0F;
            } else {
                model.leftArm.xRot = model.leftArm.xRot * 0.5F - (float) Math.PI;
                model.leftArm.yRot = 0.0F;
            }
        }
    }
}
