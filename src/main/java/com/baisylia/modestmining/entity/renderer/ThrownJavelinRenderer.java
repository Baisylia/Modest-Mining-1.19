package com.baisylia.modestmining.entity.renderer;

import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

public class ThrownJavelinRenderer extends EntityRenderer<ThrownJavelinEntity> {

    public ThrownJavelinRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(
            ThrownJavelinEntity entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight
    ) {

        poseStack.pushPose();

        poseStack.mulPose(
                Vector3f.YP.rotationDegrees(entity.getYRot() - 90.0F)
        );

        poseStack.mulPose(
                Vector3f.ZP.rotationDegrees(entity.getXRot() + 135.0F)
        );

        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity.getPickupItem(),
                ItemTransforms.TransformType.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.getId()
        );

        poseStack.popPose();

        super.render(
                entity,
                entityYaw,
                partialTicks,
                poseStack,
                buffer,
                packedLight
        );
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownJavelinEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}