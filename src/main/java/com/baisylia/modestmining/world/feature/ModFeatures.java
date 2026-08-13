package com.baisylia.modestmining.world.feature;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.world.feature.custom.ClamFeature;
import com.baisylia.modestmining.world.feature.custom.MeteoriteFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, ModestMining.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> CLAM_FEATURE = FEATURES.register("clam",
            () -> new ClamFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> METEORITE_FEATURE = FEATURES.register("meteorite",
            () -> new MeteoriteFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
