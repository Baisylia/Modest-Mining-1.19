package com.baisylia.modestmining.entity.renderer;

import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;

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
                Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F)
        );

        poseStack.mulPose(
                Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - 60.0F)
        );

        poseStack.translate(-0.5D, 0.0D, 0.0D);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                entity.getPickupItem(),
                ItemDisplayContext.NONE,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                entity.level(),
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