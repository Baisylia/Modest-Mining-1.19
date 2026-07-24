package com.baisylia.modestmining.config;

import com.baisylia.modestmining.ModestMining;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue FLINT_REPLACES_WOOD;
    public static final ForgeConfigSpec.BooleanValue BRONZE_REPLACES_STONE;
    public static final ForgeConfigSpec.BooleanValue STEEL_REPLACES_IRON;
    public static final ForgeConfigSpec.BooleanValue FORGE_USES_ALUMINIUM;

    public static final ForgeConfigSpec.BooleanValue GENERATE_ALUMINIUM_ORE;
    public static final ForgeConfigSpec.BooleanValue GENERATE_LEAD_ORE;
    public static final ForgeConfigSpec.BooleanValue GENERATE_NETHER_LEAD_ORE;
    public static final ForgeConfigSpec.BooleanValue GENERATE_SILVER_ORE;
    public static final ForgeConfigSpec.BooleanValue GENERATE_CLAMS;

    private static final Map<String, Supplier<Boolean>> CONDITION_MAP = new HashMap<>();

    static {
        BUILDER.comment("Modest Mining Configuration");

        BUILDER.push("ore_generation");
        GENERATE_ALUMINIUM_ORE = BUILDER.comment("Generate Aluminium Ore in the Overworld.").define("generate_aluminium_ore", true);
        GENERATE_LEAD_ORE = BUILDER.comment("Generate Lead Ore in the Overworld.").define("generate_lead_ore", true);
        GENERATE_NETHER_LEAD_ORE = BUILDER.comment("Generate Lead Ore in the Nether.").define("generate_nether_lead_ore", true);
        GENERATE_SILVER_ORE = BUILDER.comment("Generate Silver Ore in the Overworld.").define("generate_silver_ore", true);
        GENERATE_CLAMS = BUILDER.comment("Generate Clams on Cold Ocean Floors.").define("generate_clams", true);
        BUILDER.pop();

        BUILDER.push("replacements");

        FLINT_REPLACES_WOOD = BUILDER.comment("Vanilla wooden tools replaced by flint.").define("flint_replaces_wood", false);
        BRONZE_REPLACES_STONE = BUILDER.comment("Vanilla stone tools replaced by bronze.").define("bronze_replaces_stone", false);
        STEEL_REPLACES_IRON = BUILDER.comment("Vanilla iron tools/armour replaced by steel.").define("steel_replaces_iron", false);

        FORGE_USES_ALUMINIUM = BUILDER.comment("Forge uses aluminium instead of iron.").define("forge_uses_aluminium", false);

        BUILDER.pop();

        SPEC = BUILDER.build();

        registerCondition("forge_uses_aluminium", FORGE_USES_ALUMINIUM);
        registerCondition("generate_aluminium_ore", GENERATE_ALUMINIUM_ORE);
        registerCondition("generate_lead_ore", GENERATE_LEAD_ORE);
        registerCondition("generate_nether_lead_ore", GENERATE_NETHER_LEAD_ORE);
        registerCondition("generate_silver_ore", GENERATE_SILVER_ORE);
        registerCondition("generate_clams", GENERATE_CLAMS);
    }

    private static void registerCondition(String featureName, ForgeConfigSpec.BooleanValue configValue) {
        CONDITION_MAP.put(featureName, configValue);
        CONDITION_MAP.put("not_" + featureName, () -> !configValue.get());
    }

    public static boolean evaluateCondition(String featureName) {
        return CONDITION_MAP.getOrDefault(featureName, () -> {
            ModestMining.LOGGER.warn("Unknown config feature in recipe condition: '{}'", featureName);
            return false;
        }).get();
    }

    public static boolean isFeatureEnabled(String featureName, boolean defaultValue) {
        try {
            if (SPEC.isLoaded()) {
                return evaluateCondition(featureName);
            }
        } catch (Exception ignored) {
        }

        Path configPath = FMLPaths.CONFIGDIR.get().resolve("modestmining-common.toml");
        if (Files.exists(configPath)) {
            try {
                for (String line : Files.readAllLines(configPath)) {
                    line = line.trim();
                    if (line.startsWith(featureName + " =") || line.startsWith(featureName + "=")) {
                        return line.contains("true");
                    }
                }
            } catch (IOException e) {
                ModestMining.LOGGER.error("Failed to read early config for: {}", featureName, e);
            }
        }
        return defaultValue;
    }
}