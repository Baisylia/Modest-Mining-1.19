package com.baisylia.modestmining.mixin.client;

import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow
    protected abstract void applyItemArmTransform(PoseStack pMatrixStack, HumanoidArm pHand, float pEquippedProg);

    @Shadow
    public abstract void renderItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemTransforms.TransformType pTransformType, boolean pLeftHand, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight);

    @Inject(
            method = "renderArmWithItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void modestmining$renderJavelinSpinAttack(
            AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch,
            InteractionHand pHand, float pSwingProgress, ItemStack pStack,
            float pEquippedProgress, PoseStack pMatrixStack,
            MultiBufferSource pBuffer, int pCombinedLight,
            CallbackInfo ci
    ) {
        if (pPlayer.isAutoSpinAttack() && pStack.getItem() instanceof JavelinItem) {
            if (!pPlayer.isScoping()) {
                boolean isMainHand = pHand == InteractionHand.MAIN_HAND;
                HumanoidArm humanoidarm = isMainHand ? pPlayer.getMainArm() : pPlayer.getMainArm().getOpposite();
                boolean isRightArm = humanoidarm == HumanoidArm.RIGHT;
                int side = isRightArm ? 1 : -1;

                pMatrixStack.pushPose();
                this.applyItemArmTransform(pMatrixStack, humanoidarm, pEquippedProgress);

                pMatrixStack.translate(0.0F, 0.20F, -0.30F);
                pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(-140.0F));
                pMatrixStack.mulPose(Vector3f.YP.rotationDegrees((float) side * -80.0F));
                pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees((float) side * -90.0F));

                this.renderItem(
                        pPlayer,
                        pStack,
                        isRightArm ? ItemTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND,
                        !isRightArm,
                        pMatrixStack,
                        pBuffer,
                        pCombinedLight
                );
                pMatrixStack.popPose();
            }
            ci.cancel();
        }
    }
}
