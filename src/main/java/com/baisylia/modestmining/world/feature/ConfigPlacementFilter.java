package com.baisylia.modestmining.world.feature;

import com.baisylia.modestmining.config.ModConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class ConfigPlacementFilter extends PlacementFilter {
    public static final MapCodec<ConfigPlacementFilter> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("config_key").forGetter(filter -> filter.configKey)
            ).apply(instance, ConfigPlacementFilter::new)
    );

    private final String configKey;

    public ConfigPlacementFilter(String configKey) {
        this.configKey = configKey;
    }

    public static ConfigPlacementFilter of(String configKey) {
        return new ConfigPlacementFilter(configKey);
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return ModConfig.isFeatureEnabled(this.configKey, true);
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementModifiers.CONFIG_FILTER.get();
    }
}
