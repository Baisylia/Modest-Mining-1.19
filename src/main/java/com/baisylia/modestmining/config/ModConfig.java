package com.baisylia.modestmining.config;

import com.baisylia.modestmining.ModestMining;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModConfig {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue FLINT_REPLACES_WOOD;
    public static final ModConfigSpec.BooleanValue BRONZE_REPLACES_STONE;
    public static final ModConfigSpec.BooleanValue STEEL_REPLACES_IRON;

    public static final ModConfigSpec.BooleanValue REPEATER_USES_COPPER;
    public static final ModConfigSpec.BooleanValue BOW_USES_COPPER;
    public static final ModConfigSpec.BooleanValue TRIPWIRE_HOOK_USES_COPPER;
    public static final ModConfigSpec.BooleanValue FORGE_USES_ALUMINIUM;
    public static final ModConfigSpec.BooleanValue BUCKET_USES_ALUMINIUM;
    public static final ModConfigSpec.BooleanValue FISHING_ROD_USES_ALUMINIUM;
    public static final ModConfigSpec.BooleanValue CAULDRON_USES_ALUMINIUM;
    public static final ModConfigSpec.BooleanValue HOPPER_USES_LEAD;
    public static final ModConfigSpec.BooleanValue MINECART_USES_LEAD;
    public static final ModConfigSpec.BooleanValue COMPARATOR_USES_GOLD;
    public static final ModConfigSpec.BooleanValue DETECTOR_RAIL_USES_GOLD;
    public static final ModConfigSpec.BooleanValue SPYGLASS_USES_GOLD;
    public static final ModConfigSpec.BooleanValue BREWING_STAND_USES_GOLD;
    public static final ModConfigSpec.BooleanValue ACTIVATOR_RAIL_USES_SILVER;
    public static final ModConfigSpec.BooleanValue NOTE_BLOCK_RAIL_USES_SILVER;
    public static final ModConfigSpec.BooleanValue DROPPER_USES_SILVER;
    public static final ModConfigSpec.BooleanValue PISTON_USES_BRONZE;
    public static final ModConfigSpec.BooleanValue SMOKER_USES_BRONZE;
    public static final ModConfigSpec.BooleanValue CROSSBOW_USES_BRONZE;
    public static final ModConfigSpec.BooleanValue SHIELD_USES_BRONZE;
    public static final ModConfigSpec.BooleanValue ANVIL_USES_STEEL;
    public static final ModConfigSpec.BooleanValue STONECUTTER_USES_STEEL;
    public static final ModConfigSpec.BooleanValue BLAST_FURNACE_USES_STEEL;
    public static final ModConfigSpec.BooleanValue FLINT_AND_STEEL_USES_STEEL;
    public static final ModConfigSpec.BooleanValue DAYLIGHT_DETECTOR_USES_ROSE_GOLD;
    public static final ModConfigSpec.BooleanValue OBSERVER_USES_ROSE_GOLD;
    public static final ModConfigSpec.BooleanValue POWERED_RAIL_USES_ELECTRUM;
    public static final ModConfigSpec.BooleanValue DISPENSER_USES_ELECTRUM;
    public static final ModConfigSpec.BooleanValue LAMP_USES_ELECTRUM;
    public static final ModConfigSpec.BooleanValue JUKEBOX_USES_ELECTRUM;

    public static final ModConfigSpec.BooleanValue GENERATE_ALUMINIUM_ORE;
    public static final ModConfigSpec.BooleanValue GENERATE_LEAD_ORE;
    public static final ModConfigSpec.BooleanValue GENERATE_NETHER_LEAD_ORE;
    public static final ModConfigSpec.BooleanValue GENERATE_SILVER_ORE;
    public static final ModConfigSpec.BooleanValue GENERATE_CLAMS;

    public static final ModConfigSpec.BooleanValue GENERATE_COPPER_SCREW_LOOT;

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

        BUILDER.push("loot");
        GENERATE_COPPER_SCREW_LOOT = BUILDER.comment("Add a chance for Copper Screws to be found in archaeology loot.").define("generate_copper_screw_loot", true);
        BUILDER.pop();

        BUILDER.push("replacements");

        FLINT_REPLACES_WOOD = BUILDER.comment("Vanilla wooden tools replaced by flint.").define("flint_replaces_wood", false);
        BRONZE_REPLACES_STONE = BUILDER.comment("Vanilla stone tools replaced by bronze.").define("bronze_replaces_stone", false);
        STEEL_REPLACES_IRON = BUILDER.comment("Vanilla iron tools/armour replaced by steel.").define("steel_replaces_iron", false);

        REPEATER_USES_COPPER = BUILDER.comment("Repeater uses copper instead of stone.").define("repeater_uses_copper", false);
        BOW_USES_COPPER = BUILDER.comment("Bow uses copper as well as sticks.").define("bow_uses_copper", false);
        TRIPWIRE_HOOK_USES_COPPER = BUILDER.comment("Tripwire Hook uses copper instead of stone.").define("tripwire_hook_uses_copper", false);
        FORGE_USES_ALUMINIUM = BUILDER.comment("Forge uses aluminium instead of iron.").define("forge_uses_aluminium", false);
        BUCKET_USES_ALUMINIUM = BUILDER.comment("All Buckets uses aluminium instead of iron.").define("bucket_uses_aluminium", false);
        FISHING_ROD_USES_ALUMINIUM = BUILDER.comment("Fishing Rod uses aluminium instead of iron.").define("fishing_rod_uses_aluminium", false);
        CAULDRON_USES_ALUMINIUM = BUILDER.comment("Cauldron uses aluminium instead of iron.").define("cauldron_uses_aluminium", false);
        HOPPER_USES_LEAD = BUILDER.comment("Hopper uses lead instead of iron.").define("hopper_uses_lead", false);
        MINECART_USES_LEAD = BUILDER.comment("Minecart uses lead instead of iron.").define("minecart_uses_lead", false);
        COMPARATOR_USES_GOLD = BUILDER.comment("Comparator uses gold instead of stone.").define("comparator_uses_gold", false);
        DETECTOR_RAIL_USES_GOLD = BUILDER.comment("Detector Rail uses gold instead of iron.").define("detector_rail_uses_gold", false);
        SPYGLASS_USES_GOLD = BUILDER.comment("Spyglass uses gold instead of copper.").define("spyglass_uses_gold", false);
        BREWING_STAND_USES_GOLD = BUILDER.comment("Brewing Stand uses gold instead of stone.").define("brewing_stand_uses_gold", false);
        ACTIVATOR_RAIL_USES_SILVER = BUILDER.comment("Activator Rail uses silver instead of iron.").define("activator_rail_uses_silver", false);
        NOTE_BLOCK_RAIL_USES_SILVER = BUILDER.comment("Note Block uses silver as well as wood.").define("note_block_uses_silver", false);
        DROPPER_USES_SILVER = BUILDER.comment("Dropper uses silver as well as stone.").define("dropper_uses_silver", false);
        PISTON_USES_BRONZE = BUILDER.comment("Both Pistons use bronze instead of iron.").define("piston_uses_bronze", false);
        SMOKER_USES_BRONZE = BUILDER.comment("Smoker uses bronze instead of wood.").define("smoker_uses_bronze", false);
        CROSSBOW_USES_BRONZE = BUILDER.comment("Crossbow uses bronze instead of iron ingot.").define("crossbow_uses_bronze", false);
        SHIELD_USES_BRONZE = BUILDER.comment("Shield uses bronze instead of iron.").define("shield_uses_bronze", false);
        ANVIL_USES_STEEL = BUILDER.comment("Anvil uses steel instead of iron.").define("anvil_uses_steel", false);
        STONECUTTER_USES_STEEL = BUILDER.comment("Stonecutter uses steel instead of iron.").define("stonecutter_uses_steel", false);
        BLAST_FURNACE_USES_STEEL = BUILDER.comment("Blast Furnace uses steel instead of iron.").define("blast_furnace_uses_steel", false);
        FLINT_AND_STEEL_USES_STEEL = BUILDER.comment("Flint and Steel uses steel instead of iron.").define("flint_and_steel_uses_steel", false);
        DAYLIGHT_DETECTOR_USES_ROSE_GOLD = BUILDER.comment("Daylight Detector uses rose gold instead of wood.").define("daylight_detector_uses_rose_gold", false);
        OBSERVER_USES_ROSE_GOLD = BUILDER.comment("Observer uses rose gold as well as stone.").define("observer_uses_rose_gold", false);
        POWERED_RAIL_USES_ELECTRUM = BUILDER.comment("Powered Rail uses steel instead of electrum.").define("powered_rail_uses_electrum", false);
        DISPENSER_USES_ELECTRUM = BUILDER.comment("Dispenser uses steel instead of electrum.").define("dispenser_uses_electrum", false);
        LAMP_USES_ELECTRUM = BUILDER.comment("Lamp uses steel instead of electrum.").define("lamp_uses_electrum", false);
        JUKEBOX_USES_ELECTRUM = BUILDER.comment("Jukebox uses steel instead of electrum.").define("jukebox_uses_electrum", false);

        BUILDER.pop();

        SPEC = BUILDER.build();

        registerCondition("flint_replaces_wood", FLINT_REPLACES_WOOD);
        registerCondition("bronze_replaces_stone", BRONZE_REPLACES_STONE);
        registerCondition("steel_replaces_iron", STEEL_REPLACES_IRON);

        registerCondition("repeater_uses_copper", REPEATER_USES_COPPER);
        registerCondition("bow_uses_copper", BOW_USES_COPPER);
        registerCondition("tripwire_hook_uses_copper", TRIPWIRE_HOOK_USES_COPPER);
        registerCondition("forge_uses_aluminium", FORGE_USES_ALUMINIUM);
        registerCondition("bucket_uses_aluminium", BUCKET_USES_ALUMINIUM);
        registerCondition("fishing_rod_uses_aluminium", FISHING_ROD_USES_ALUMINIUM);
        registerCondition("cauldron_uses_aluminium", CAULDRON_USES_ALUMINIUM);
        registerCondition("hopper_uses_lead", HOPPER_USES_LEAD);
        registerCondition("minecart_uses_lead", MINECART_USES_LEAD);
        registerCondition("comparator_uses_gold", COMPARATOR_USES_GOLD);
        registerCondition("detector_rail_uses_gold", DETECTOR_RAIL_USES_GOLD);
        registerCondition("spyglass_uses_gold", SPYGLASS_USES_GOLD);
        registerCondition("brewing_stand_uses_gold", BREWING_STAND_USES_GOLD);
        registerCondition("activator_rail_uses_silver", ACTIVATOR_RAIL_USES_SILVER);
        registerCondition("note_block_uses_silver", NOTE_BLOCK_RAIL_USES_SILVER);
        registerCondition("dropper_uses_silver", DROPPER_USES_SILVER);
        registerCondition("piston_uses_bronze", PISTON_USES_BRONZE);
        registerCondition("smoker_uses_bronze", SMOKER_USES_BRONZE);
        registerCondition("crossbow_uses_bronze", CROSSBOW_USES_BRONZE);
        registerCondition("shield_uses_bronze", SHIELD_USES_BRONZE);
        registerCondition("anvil_uses_steel", ANVIL_USES_STEEL);
        registerCondition("stonecutter_uses_steel", STONECUTTER_USES_STEEL);
        registerCondition("blast_furnace_uses_steel", BLAST_FURNACE_USES_STEEL);
        registerCondition("flint_and_steel_uses_steel", FLINT_AND_STEEL_USES_STEEL);
        registerCondition("daylight_detector_uses_rose_gold", DAYLIGHT_DETECTOR_USES_ROSE_GOLD);
        registerCondition("observer_uses_rose_gold", OBSERVER_USES_ROSE_GOLD);
        registerCondition("powered_rail_uses_electrum", POWERED_RAIL_USES_ELECTRUM);
        registerCondition("dispenser_uses_electrum", DISPENSER_USES_ELECTRUM);
        registerCondition("lamp_uses_electrum", LAMP_USES_ELECTRUM);
        registerCondition("jukebox_uses_electrum", JUKEBOX_USES_ELECTRUM);

        registerCondition("generate_aluminium_ore", GENERATE_ALUMINIUM_ORE);
        registerCondition("generate_lead_ore", GENERATE_LEAD_ORE);
        registerCondition("generate_nether_lead_ore", GENERATE_NETHER_LEAD_ORE);
        registerCondition("generate_silver_ore", GENERATE_SILVER_ORE);
        registerCondition("generate_clams", GENERATE_CLAMS);

        registerCondition("generate_copper_screw_loot", GENERATE_COPPER_SCREW_LOOT);
    }

    private static void registerCondition(String featureName, ModConfigSpec.BooleanValue configValue) {
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