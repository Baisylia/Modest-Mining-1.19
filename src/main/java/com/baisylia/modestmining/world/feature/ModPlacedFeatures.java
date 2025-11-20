package com.baisylia.modestmining.world.feature;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ModestMining.MOD_ID);

    //public static final RegistryObject<PlacedFeature> OCEANIC_REMAINS_PLACED = PLACED_FEATURES.register("oceanic_remains_placed",
    //        () -> new PlacedFeature(ModConfiguredFeatures.OCEANIC_REMAINS.getHolder().get(),
    //                commonOrePlacement(7, // VeinsPerChunk
    //                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)))));

    public static final RegistryObject<PlacedFeature> SHELL_PLACED = PLACED_FEATURES.register("shell_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.SHELL.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(25),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    //public static final RegistryObject<PlacedFeature> ROCKS_PLACED = PLACED_FEATURES.register("rocks_placed",
    //        () -> new PlacedFeature(ModConfiguredFeatures.ROCKS.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(25),
    //                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));


    public static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    public static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}
