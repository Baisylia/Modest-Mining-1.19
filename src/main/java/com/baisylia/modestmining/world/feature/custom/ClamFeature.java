package com.baisylia.modestmining.world.feature.custom;

import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.block.custom.ClamBlock;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ClamFeature extends Feature<NoneFeatureConfiguration> {
    public ClamFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        if (!level.getFluidState(pos).is(FluidTags.WATER)) {
            pos = level.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, pos);
        }

        if (!level.getFluidState(pos).is(FluidTags.WATER)) {
            return false;
        }

        Direction facing = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        BlockPos posTL = pos;

        for (ClamBlock.ClamPart part : ClamBlock.ClamPart.values()) {
            BlockPos p = ClamBlock.getPosForPart(posTL, part, facing);

            BlockState stateAt = level.getBlockState(p);
            if (!stateAt.is(Blocks.WATER)) {
                return false;
            }

            BlockState stateAbove = level.getBlockState(p.above());
            if (stateAbove.is(Blocks.KELP) || stateAbove.is(Blocks.KELP_PLANT)) {
                return false;
            }

            BlockState blockBelow = level.getBlockState(p.below());
            if (!blockBelow.isFaceSturdy(level, p.below(), Direction.UP)) {
                return false;
            }
        }

        for (ClamBlock.ClamPart part : ClamBlock.ClamPart.values()) {
            BlockPos targetPos = ClamBlock.getPosForPart(posTL, part, facing);
            BlockState clamState = ModBlocks.CLAM.get().defaultBlockState()
                    .setValue(ClamBlock.FACING, facing)
                    .setValue(ClamBlock.PART, part)
                    .setValue(ClamBlock.WATERLOGGED, true);
            level.setBlock(targetPos, clamState, 2);
        }

        return true;
    }
}
