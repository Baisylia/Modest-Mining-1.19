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

    private static final Map<String, Supplier<Boolean>> CONDITION_MAP = new HashMap<>();

    static {
        BUILDER.comment("Modest Mining Configuration");
        BUILDER.push("replacements");

        FLINT_REPLACES_WOOD = BUILDER.comment("Vanilla wooden tools replaced by flint.").define("flint_replaces_wood", false);
        BRONZE_REPLACES_STONE = BUILDER.comment("Vanilla stone tools replaced by bronze.").define("bronze_replaces_stone", false);
        STEEL_REPLACES_IRON = BUILDER.comment("Vanilla iron tools/armour replaced by steel.").define("steel_replaces_iron", false);

        FORGE_USES_ALUMINIUM = BUILDER.comment("Forge uses aluminium instead of iron.").define("forge_uses_aluminium", false);

        BUILDER.pop();

        SPEC = BUILDER.build();

        registerCondition("forge_uses_aluminium", FORGE_USES_ALUMINIUM);
    }

    /**
     * Registers a condition string to a boolean supplier.
     * Automatically creates a "not_" prefixed inverse condition.
     */
    private static void registerCondition(String featureName, ForgeConfigSpec.BooleanValue configValue) {
        CONDITION_MAP.put(featureName, configValue);
        CONDITION_MAP.put("not_" + featureName, () -> !configValue.get());
    }

    /**
     * Evaluates a condition string from a recipe JSON.
     */
    public static boolean evaluateCondition(String featureName) {
        return CONDITION_MAP.getOrDefault(featureName, () -> {
            ModestMining.LOGGER.warn("Unknown config feature in recipe condition: '{}'", featureName);
            return false;
        }).get();
    }

    /**
     * Safely reads a config value during early startup events
     * before Forge has loaded the configuration system.
     */
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