package com.baisylia.modestmining.integration;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.config.ModConfig;
import com.evandev.reliable_remover.config.RuleManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReliableRemoverCompatImpl {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Map<String, String> WOOD_TO_FLINT = Map.of(
            "minecraft:wooden_sword", "modestmining:flint_blade",
            "minecraft:wooden_axe", "modestmining:flint_hatchet",
            "minecraft:wooden_pickaxe", "modestmining:flint_mattock",
            "minecraft:wooden_shovel", "modestmining:flint_spade",
            "minecraft:wooden_hoe", "modestmining:flint_hoe"
    );

    private static final Map<String, String> STONE_TO_BRONZE = Map.of(
            "minecraft:stone_sword", "modestmining:bronze_sword",
            "minecraft:stone_axe", "modestmining:bronze_axe",
            "minecraft:stone_pickaxe", "modestmining:bronze_pickaxe",
            "minecraft:stone_shovel", "modestmining:bronze_shovel",
            "minecraft:stone_hoe", "modestmining:bronze_hoe",
            "modestmining:stone_hammer", "modestmining:bronze_hammer",
            "modestmining:stone_javelin", "modestmining:bronze_javelin"
    );

    private static final Map<String, String> IRON_TO_STEEL = Map.ofEntries(
            Map.entry("minecraft:iron_sword", "modestmining:steel_sword"),
            Map.entry("minecraft:iron_axe", "modestmining:steel_axe"),
            Map.entry("minecraft:iron_pickaxe", "modestmining:steel_pickaxe"),
            Map.entry("minecraft:iron_shovel", "modestmining:steel_shovel"),
            Map.entry("minecraft:iron_hoe", "modestmining:steel_hoe"),
            Map.entry("minecraft:iron_helmet", "modestmining:steel_helmet"),
            Map.entry("minecraft:iron_chestplate", "modestmining:steel_chestplate"),
            Map.entry("minecraft:iron_leggings", "modestmining:steel_leggings"),
            Map.entry("minecraft:iron_boots", "modestmining:steel_boots"),
            Map.entry("modestmining:iron_hammer", "modestmining:steel_hammer"),
            Map.entry("modestmining:iron_javelin", "modestmining:steel_javelin"),
            Map.entry("farmersdelight:iron_knife", "modestmining:steel_knife")
    );

    public static void apply() {
        com.evandev.reliable_remover.config.ModConfig removerConfig = com.evandev.reliable_remover.config.ModConfig.get();
        if (removerConfig == null) {
            ModestMining.LOGGER.warn("Reliable Remover configuration was null. Skipping integration.");
            return;
        }

        boolean blacklistModified = false;
        if (removerConfig.blacklistedItems != null) {
            blacklistModified |= removerConfig.blacklistedItems.removeAll(WOOD_TO_FLINT.keySet());
            blacklistModified |= removerConfig.blacklistedItems.removeAll(STONE_TO_BRONZE.keySet());
            blacklistModified |= removerConfig.blacklistedItems.removeAll(IRON_TO_STEEL.keySet());
            if (blacklistModified) {
                com.evandev.reliable_remover.config.ModConfig.save();
                ModestMining.LOGGER.info("Reliable Remover Integration: cleaned up legacy blacklisted items.");
            }
        }

        Map<String, String> activeReplacements = new LinkedHashMap<>();
        if (ModConfig.FLINT_REPLACES_WOOD.get()) {
            activeReplacements.putAll(WOOD_TO_FLINT);
            ModestMining.LOGGER.info("Reliable Remover Integration: enabled wood -> flint replacement rules.");
        }
        if (ModConfig.BRONZE_REPLACES_STONE.get()) {
            activeReplacements.putAll(STONE_TO_BRONZE);
            ModestMining.LOGGER.info("Reliable Remover Integration: enabled stone -> bronze replacement rules.");
        }
        if (ModConfig.STEEL_REPLACES_IRON.get()) {
            activeReplacements.putAll(IRON_TO_STEEL);
            ModestMining.LOGGER.info("Reliable Remover Integration: enabled iron -> steel replacement rules.");
        }

        Path configDir = FMLPaths.CONFIGDIR.get().resolve("reliable_remover");
        Path rulesFilePath = configDir.resolve("modestmining.json");

        try {
            if (!activeReplacements.isEmpty()) {
                Files.createDirectories(configDir);
                JsonArray rulesArray = new JsonArray();
                for (Map.Entry<String, String> entry : activeReplacements.entrySet()) {
                    JsonObject ruleObj = new JsonObject();
                    ruleObj.addProperty("action", "remove");
                    JsonArray itemsArray = new JsonArray();
                    itemsArray.add(entry.getKey());
                    ruleObj.add("items", itemsArray);
                    ruleObj.addProperty("replace_with", entry.getValue());
                    rulesArray.add(ruleObj);
                }
                Files.writeString(rulesFilePath, GSON.toJson(rulesArray));
            } else {
                Files.deleteIfExists(rulesFilePath);
            }
            RuleManager.load();
            ModestMining.LOGGER.info("Reliable Remover Integration: successfully synced replacement rules.");
        } catch (Exception e) {
            ModestMining.LOGGER.error("Reliable Remover Integration: failed to write replacement rules", e);
        }
    }
}
