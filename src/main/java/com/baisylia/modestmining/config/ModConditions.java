package com.baisylia.modestmining.config;

import com.baisylia.modestmining.ModestMining;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, ModestMining.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ConfigEnabledCondition>> CONFIG_ENABLED =
            CONDITION_CODECS.register("config_enabled", () -> ConfigEnabledCondition.CODEC);

    public static void register(IEventBus eventBus) {
        CONDITION_CODECS.register(eventBus);
    }

    public record ConfigEnabledCondition(String feature) implements ICondition {
        public static final MapCodec<ConfigEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(builder ->
                builder.group(
                        Codec.STRING.fieldOf("feature").forGetter(ConfigEnabledCondition::feature)
                ).apply(builder, ConfigEnabledCondition::new)
        );

        @Override
        public boolean test(IContext context) {
            return ModConfig.evaluateCondition(feature);
        }

        @Override
        public MapCodec<? extends ICondition> codec() {
            return CODEC;
        }
    }
}