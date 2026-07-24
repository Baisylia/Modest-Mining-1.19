package com.baisylia.modestmining.world.feature;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, ModestMining.MOD_ID);

    public static final RegistryObject<PlacedFeature> SHELL_PLACED = PLACED_FEATURES.register("shell_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.SHELL.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(25),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> CLAM_PLACED = PLACED_FEATURES.register("clam_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.CLAM.getHolder().get(), List.of(
                    ConfigPlacementFilter.of("generate_clams"),
                    RarityFilter.onAverageOnceEvery(5),
                    InSquarePlacement.spread(),
                    PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                    BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> ALUMINIUM_ORE_PLACED = PLACED_FEATURES.register("aluminium_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ALUMINIUM_ORE.getHolder().get(),
                    commonOrePlacement(8,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112)),
                            "generate_aluminium_ore")));

    public static final RegistryObject<PlacedFeature> LEAD_ORE_PLACED = PLACED_FEATURES.register("lead_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.LEAD_ORE.getHolder().get(),
                    commonOrePlacement(6,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(48)),
                            "generate_lead_ore")));

    public static final RegistryObject<PlacedFeature> NETHER_LEAD_ORE_PLACED = PLACED_FEATURES.register("nether_lead_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.NETHER_LEAD_ORE.getHolder().get(),
                    commonOrePlacement(10,
                            HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.belowTop(10)),
                            "generate_nether_lead_ore")));

    public static final RegistryObject<PlacedFeature> SILVER_ORE_PLACED = PLACED_FEATURES.register("silver_ore_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.SILVER_ORE.getHolder().get(),
                    commonOrePlacement(3,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)),
                            "generate_silver_ore")));

    public static final RegistryObject<PlacedFeature> SILVER_ORE_EXTRA_PLACED = PLACED_FEATURES.register("silver_ore_extra_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.SILVER_ORE_EXTRA.getHolder().get(),
                    commonOrePlacement(20,
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(32), VerticalAnchor.absolute(192)),
                            "generate_silver_ore")));

    public static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    public static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier heightModifier, String configKey) {
        return List.of(ConfigPlacementFilter.of(configKey), countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int veinsPerChunk, PlacementModifier heightModifier) {
        return orePlacement(CountPlacement.of(veinsPerChunk), heightModifier);
    }

    public static List<PlacementModifier> commonOrePlacement(int veinsPerChunk, PlacementModifier heightModifier, String configKey) {
        return orePlacement(CountPlacement.of(veinsPerChunk), heightModifier, configKey);
    }

    public static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier heightModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), heightModifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int rarity, PlacementModifier heightModifier, String configKey) {
        return orePlacement(RarityFilter.onAverageOnceEvery(rarity), heightModifier, configKey);
    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }
}