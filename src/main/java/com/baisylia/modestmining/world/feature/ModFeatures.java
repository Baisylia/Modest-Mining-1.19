package com.baisylia.modestmining.world.feature;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.world.feature.custom.ClamFeature;
import com.baisylia.modestmining.world.feature.custom.MeteoriteFeature;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registry.FEATURE_REGISTRY, ModestMining.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CLAM_FEATURE = FEATURES.register("clam",
            () -> new ClamFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> METEORITE_FEATURE = FEATURES.register("meteorite",
            () -> new MeteoriteFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
