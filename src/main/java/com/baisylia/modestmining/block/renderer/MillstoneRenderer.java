package com.baisylia.modestmining.block.renderer;

import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.block.custom.MillstoneBlock;
import com.baisylia.modestmining.block.entity.custom.MillstoneBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;

public class MillstoneRenderer implements BlockEntityRenderer<MillstoneBlockEntity> {

    public MillstoneRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MillstoneBlockEntity millstone, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        if (millstone.getLevel() == null) return;
        BlockState state = millstone.getBlockState();
        if (!state.getValue(MillstoneBlock.LIT)) return;

        poseStack.pushPose();
        poseStack.translate(0.5D, 0.0D, 0.5D);

        float rotation = (millstone.getLevel().getGameTime() + partialTick) * 4f;

        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));
        poseStack.translate(-0.5D, 0.0D, -0.5D);

        BlockRenderDispatcher renderer = Minecraft.getInstance().getBlockRenderer();

        renderer.renderSingleBlock(
                state.setValue(MillstoneBlock.BASE, false)
                        .setValue(MillstoneBlock.FACING, state.getValue(MillstoneBlock.FACING)),
                poseStack,
                buffer,
                light,
                overlay
        );

        poseStack.popPose();
    }
}