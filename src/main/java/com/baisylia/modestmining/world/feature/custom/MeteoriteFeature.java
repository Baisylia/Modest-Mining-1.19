package com.baisylia.modestmining.world.feature.custom;

import com.baisylia.modestmining.block.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MeteoriteFeature extends Feature<NoneFeatureConfiguration> {

    public MeteoriteFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        BlockPos surfacePos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin);
        int cx = surfacePos.getX();
        int cy = surfacePos.getY();
        int cz = surfacePos.getZ();

        if (cy <= level.getMinBuildHeight() + 10 || cy >= level.getMaxBuildHeight() - 10) {
            return false;
        }

        BlockState groundBelow = level.getBlockState(surfacePos.below());
        if (groundBelow.isAir() || groundBelow.is(Blocks.BEDROCK) || groundBelow.is(Blocks.WATER)) {
            return false;
        }

        if (level.getFluidState(surfacePos).isSource() || level.getFluidState(surfacePos.below()).isSource()) {
            return false;
        }

        Holder<Biome> biomeHolder = level.getBiome(surfacePos);
        boolean isBadlands = biomeHolder.is(BiomeTags.IS_BADLANDS)
                || groundBelow.is(Blocks.RED_SAND)
                || groundBelow.is(Blocks.RED_SANDSTONE)
                || groundBelow.is(Blocks.TERRACOTTA);
        boolean isDesert = biomeHolder.is(Biomes.DESERT)
                || groundBelow.is(Blocks.SAND)
                || groundBelow.is(Blocks.SANDSTONE);

        double centerX = cx - 0.5;
        double centerZ = cz - 0.5;

        double radius = 5.5 + random.nextDouble() * 0.5;
        int depth = 3;

        int minX = (int) Math.floor(centerX - radius - 1);
        int maxX = (int) Math.ceil(centerX + radius + 1);
        int minZ = (int) Math.floor(centerZ - radius - 1);
        int maxZ = (int) Math.ceil(centerZ + radius + 1);

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                double dx = x - centerX;
                double dz = z - centerZ;
                double distSq = dx * dx + dz * dz;

                if (distSq <= radius * radius) {
                    double normalizedDist = distSq / (radius * radius);
                    int craterY = (int) Math.round(cy - depth + normalizedDist * depth);

                    for (int y = craterY; y <= cy + 4; y++) {
                        BlockPos p = new BlockPos(x, y, z);
                        level.setBlock(p, Blocks.AIR.defaultBlockState(), 2);
                    }

                    BlockPos floorPos = new BlockPos(x, craterY - 1, z);
                    BlockState floorState = level.getBlockState(floorPos);
                    if (!floorState.isAir() && !floorState.is(ModBlocks.METEORITE.get())) {
                        BlockState scorchedState = getScorchedBlock(random, isDesert, isBadlands);
                        level.setBlock(floorPos, scorchedState, 2);
                    }
                }
            }
        }

        int baseY = cy - depth - 1;
        for (int dx = 0; dx <= 1; dx++) {
            for (int dy = 0; dy <= 1; dy++) {
                for (int dz = 0; dz <= 1; dz++) {
                    BlockPos meteorPos = new BlockPos(cx - 1 + dx, baseY + dy, cz - 1 + dz);
                    level.setBlock(meteorPos, ModBlocks.METEORITE.get().defaultBlockState(), 2);
                }
            }
        }

        return true;
    }

    private BlockState getScorchedBlock(RandomSource random, boolean isDesert, boolean isBadlands) {
        float chance = random.nextFloat();
        if (isBadlands) {
            if (chance < 0.60f) {
                return Blocks.RED_SAND.defaultBlockState();
            } else if (chance < 0.85f) {
                return Blocks.RED_SANDSTONE.defaultBlockState();
            } else {
                return Blocks.GRAVEL.defaultBlockState();
            }
        } else if (isDesert) {
            if (chance < 0.60f) {
                return Blocks.SAND.defaultBlockState();
            } else if (chance < 0.85f) {
                return Blocks.SANDSTONE.defaultBlockState();
            } else {
                return Blocks.GRAVEL.defaultBlockState();
            }
        } else {
            if (chance < 0.60f) {
                return Blocks.COARSE_DIRT.defaultBlockState();
            } else if (chance < 0.85f) {
                return Blocks.DIRT.defaultBlockState();
            } else {
                return Blocks.GRAVEL.defaultBlockState();
            }
        }
    }
}
