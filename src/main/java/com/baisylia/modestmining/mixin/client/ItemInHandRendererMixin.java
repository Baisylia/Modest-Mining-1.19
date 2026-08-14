package com.baisylia.modestmining.mixin.client;

import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow
    protected abstract void applyItemArmTransform(PoseStack poseStack, HumanoidArm hand, float equippedProg);

    @Shadow
    public abstract void renderItem(LivingEntity entity, ItemStack itemStack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int seed);

    @Inject(
            method = "renderArmWithItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void modestmining$renderJavelinSpinAttack(
            AbstractClientPlayer player, float partialTicks, float pitch,
            InteractionHand hand, float swingProgress, ItemStack stack,
            float equippedProgress, PoseStack poseStack,
            MultiBufferSource buffer, int combinedLight,
            CallbackInfo ci
    ) {
        if (player.isAutoSpinAttack() && stack.getItem() instanceof JavelinItem) {
            if (!player.isScoping()) {
                boolean isMainHand = hand == InteractionHand.MAIN_HAND;
                HumanoidArm humanoidarm = isMainHand ? player.getMainArm() : player.getMainArm().getOpposite();
                boolean isRightArm = humanoidarm == HumanoidArm.RIGHT;
                int side = isRightArm ? 1 : -1;

                poseStack.pushPose();
                this.applyItemArmTransform(poseStack, humanoidarm, equippedProgress);

                poseStack.translate(0.0F, 0.20F, -0.30F);
                poseStack.mulPose(Axis.XP.rotationDegrees(-140.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees((float) side * -80.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees((float) side * -90.0F));

                this.renderItem(
                        player,
                        stack,
                        isRightArm ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                        !isRightArm,
                        poseStack,
                        buffer,
                        combinedLight
                );
                poseStack.popPose();
            }
            ci.cancel();
        }
    }
}
