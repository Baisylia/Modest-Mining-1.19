package com.baisylia.modestmining.integration;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.config.ModConfig;
import com.evandev.reliable_remover.config.RuleManager;

import java.util.List;

public class ReliableRemoverCompatImpl {
    private static final List<String> WOOD_TOOLS = List.of(
            "minecraft:wooden_sword",
            "minecraft:wooden_axe",
            "minecraft:wooden_pickaxe",
            "minecraft:wooden_shovel",
            "minecraft:wooden_hoe"
    );

    private static final List<String> STONE_TOOLS = List.of(
            "minecraft:stone_sword",
            "minecraft:stone_axe",
            "minecraft:stone_pickaxe",
            "minecraft:stone_shovel",
            "minecraft:stone_hoe"
    );

    private static final List<String> IRON_TOOLS = List.of(
            "minecraft:iron_sword",
            "minecraft:iron_axe",
            "minecraft:iron_pickaxe",
            "minecraft:iron_shovel",
            "minecraft:iron_hoe",
            "minecraft:iron_helmet",
            "minecraft:iron_chestplate",
            "minecraft:iron_leggings",
            "minecraft:iron_boots"
    );

    public static void apply() {
        com.evandev.reliable_remover.config.ModConfig removerConfig = com.evandev.reliable_remover.config.ModConfig.get();
        if (removerConfig == null || removerConfig.blacklistedItems == null) {
            ModestMining.LOGGER.warn("Reliable Remover configuration was null. Skipping integration.");
            return;
        }

        List<String> blacklist = removerConfig.blacklistedItems;
        boolean modified = false;

        if (ModConfig.FLINT_REPLACES_WOOD.get()) {
            modified |= addIfAbsent(blacklist, WOOD_TOOLS);
            if (modified) ModestMining.LOGGER.info("Reliable Remover Integration: blacklisted vanilla wooden tools.");
        }
        if (ModConfig.BRONZE_REPLACES_STONE.get()) {
            boolean changed = addIfAbsent(blacklist, STONE_TOOLS);
            if (changed) ModestMining.LOGGER.info("Reliable Remover Integration: blacklisted vanilla stone tools.");
            modified |= changed;
        }
        if (ModConfig.STEEL_REPLACES_IRON.get()) {
            boolean changed = addIfAbsent(blacklist, IRON_TOOLS);
            if (changed) ModestMining.LOGGER.info("Reliable Remover Integration: blacklisted vanilla iron tools and armour.");
            modified |= changed;
        }

        if (modified) {
            com.evandev.reliable_remover.config.ModConfig.save();
            RuleManager.load();
            ModestMining.LOGGER.info("Reliable Remover Integration: successfully reloaded rules.");
        }
    }

    private static boolean addIfAbsent(List<String> list, List<String> toAdd) {
        boolean changed = false;
        for (String id : toAdd) {
            if (!list.contains(id)) {
                list.add(id);
                changed = true;
            }
        }
        return changed;
    }
}
