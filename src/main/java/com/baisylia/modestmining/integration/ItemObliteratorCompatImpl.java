package com.baisylia.modestmining.integration;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.config.ModConfig;
import elocindev.item_obliterator.forge.ItemObliterator;

import java.util.Arrays;
import java.util.List;

public class ItemObliteratorCompatImpl {
    private static final List<String> WOOD_TOOLS = Arrays.asList(
            "minecraft:wooden_sword",
            "minecraft:wooden_axe",
            "minecraft:wooden_pickaxe",
            "minecraft:wooden_shovel",
            "minecraft:wooden_hoe"
    );

    private static final List<String> STONE_TOOLS = Arrays.asList(
            "minecraft:stone_sword",
            "minecraft:stone_axe",
            "minecraft:stone_pickaxe",
            "minecraft:stone_shovel",
            "minecraft:stone_hoe"
    );

    private static final List<String> IRON_TOOLS = Arrays.asList(
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

    private static final List<String> MODESTMINING_WOOD_TOOLS = Arrays.asList(
            "modestmining:wooden_hammer",
            "modestmining:wooden_javelin"
    );

    private static final List<String> MODESTMINING_STONE_TOOLS = Arrays.asList(
            "modestmining:stone_hammer",
            "modestmining:stone_javelin"
    );

    private static final List<String> MODESTMINING_IRON_TOOLS = Arrays.asList(
            "modestmining:iron_hammer",
            "modestmining:iron_javelin"
    );

    private static final List<String> FARMERSDELIGHT_IRON_TOOLS = Arrays.asList(
            "farmersdelight:iron_knife"
    );

    public static void apply() {
        if (ItemObliterator.Config == null) {
            ModestMining.LOGGER.warn("Item Obliterator configuration was null. Skipping integration.");
            return;
        }

        List<String> blacklist = ItemObliterator.Config.blacklisted_items;
        if (blacklist == null) {
            ModestMining.LOGGER.warn("Item Obliterator blacklisted_items list was null. Skipping integration.");
            return;
        }

        boolean modified = false;

        if (ModConfig.FLINT_REPLACES_WOOD.get()) {
            boolean changed = addIfAbsent(blacklist, WOOD_TOOLS);
            changed |= addIfAbsent(blacklist, MODESTMINING_WOOD_TOOLS);
            if (changed) ModestMining.LOGGER.info("Item Obliterator Integration: blacklisted wooden tools.");
            modified |= changed;
        }
        if (ModConfig.BRONZE_REPLACES_STONE.get()) {
            boolean changed = addIfAbsent(blacklist, STONE_TOOLS);
            changed |= addIfAbsent(blacklist, MODESTMINING_STONE_TOOLS);
            if (changed) ModestMining.LOGGER.info("Item Obliterator Integration: blacklisted stone tools.");
            modified |= changed;
        }
        if (ModConfig.STEEL_REPLACES_IRON.get()) {
            boolean changed = addIfAbsent(blacklist, IRON_TOOLS);
            changed |= addIfAbsent(blacklist, MODESTMINING_IRON_TOOLS);
            changed |= addIfAbsent(blacklist, FARMERSDELIGHT_IRON_TOOLS);
            if (changed)
                ModestMining.LOGGER.info("Item Obliterator Integration: blacklisted iron tools and armour.");
            modified |= changed;
        }

        if (modified) {
            ItemObliterator.reloadConfigHashsets();
            ModestMining.LOGGER.info("Item Obliterator Integration: successfully reloaded config.");
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
