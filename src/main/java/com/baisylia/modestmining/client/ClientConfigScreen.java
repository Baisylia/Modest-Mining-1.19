package com.baisylia.modestmining.client;

import com.baisylia.modestmining.config.ModConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ClientConfigScreen {
    public static Screen create(Screen parent) {
        YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.modestmining.title"))
                .save(ModConfig.SPEC::save);

        ConfigCategory.Builder replacements = ConfigCategory.createBuilder()
                .name(Component.translatable("config.modestmining.category.replacements"))
                .option(createBoolOption("flint_replaces_wood", false, ModConfig.FLINT_REPLACES_WOOD, ModConfig.FLINT_REPLACES_WOOD::set))
                .option(createBoolOption("bronze_replaces_stone", false, ModConfig.BRONZE_REPLACES_STONE, ModConfig.BRONZE_REPLACES_STONE::set))
                .option(createBoolOption("steel_replaces_iron", false, ModConfig.STEEL_REPLACES_IRON, ModConfig.STEEL_REPLACES_IRON::set))
                .option(createBoolOption("repeater_uses_copper", false, ModConfig.REPEATER_USES_COPPER, ModConfig.REPEATER_USES_COPPER::set))
                .option(createBoolOption("bow_uses_copper", false, ModConfig.BOW_USES_COPPER, ModConfig.BOW_USES_COPPER::set))
                .option(createBoolOption("tripwire_hook_uses_copper", false, ModConfig.TRIPWIRE_HOOK_USES_COPPER, ModConfig.TRIPWIRE_HOOK_USES_COPPER::set))
                .option(createBoolOption("forge_uses_aluminium", false, ModConfig.FORGE_USES_ALUMINIUM, ModConfig.FORGE_USES_ALUMINIUM::set))
                .option(createBoolOption("bucket_uses_aluminium", false, ModConfig.BUCKET_USES_ALUMINIUM, ModConfig.BUCKET_USES_ALUMINIUM::set))
                .option(createBoolOption("fishing_rod_uses_aluminium", false, ModConfig.FISHING_ROD_USES_ALUMINIUM, ModConfig.FISHING_ROD_USES_ALUMINIUM::set))
                .option(createBoolOption("cauldron_uses_aluminium", false, ModConfig.CAULDRON_USES_ALUMINIUM, ModConfig.CAULDRON_USES_ALUMINIUM::set))
                .option(createBoolOption("hopper_uses_lead", false, ModConfig.HOPPER_USES_LEAD, ModConfig.HOPPER_USES_LEAD::set))
                .option(createBoolOption("minecart_uses_lead", false, ModConfig.MINECART_USES_LEAD, ModConfig.MINECART_USES_LEAD::set))
                .option(createBoolOption("comparator_uses_gold", false, ModConfig.COMPARATOR_USES_GOLD, ModConfig.COMPARATOR_USES_GOLD::set))
                .option(createBoolOption("detector_rail_uses_gold", false, ModConfig.DETECTOR_RAIL_USES_GOLD, ModConfig.DETECTOR_RAIL_USES_GOLD::set))
                .option(createBoolOption("spyglass_uses_gold", false, ModConfig.SPYGLASS_USES_GOLD, ModConfig.SPYGLASS_USES_GOLD::set))
                .option(createBoolOption("brewing_stand_uses_gold", false, ModConfig.BREWING_STAND_USES_GOLD, ModConfig.BREWING_STAND_USES_GOLD::set))
                .option(createBoolOption("activator_rail_uses_silver", false, ModConfig.ACTIVATOR_RAIL_USES_SILVER, ModConfig.ACTIVATOR_RAIL_USES_SILVER::set))
                .option(createBoolOption("note_block_uses_silver", false, ModConfig.NOTE_BLOCK_RAIL_USES_SILVER, ModConfig.NOTE_BLOCK_RAIL_USES_SILVER::set))
                .option(createBoolOption("dropper_uses_silver", false, ModConfig.DROPPER_USES_SILVER, ModConfig.DROPPER_USES_SILVER::set))
                .option(createBoolOption("piston_uses_bronze", false, ModConfig.PISTON_USES_BRONZE, ModConfig.PISTON_USES_BRONZE::set))
                .option(createBoolOption("smoker_uses_bronze", false, ModConfig.SMOKER_USES_BRONZE, ModConfig.SMOKER_USES_BRONZE::set))
                .option(createBoolOption("crossbow_uses_bronze", false, ModConfig.CROSSBOW_USES_BRONZE, ModConfig.CROSSBOW_USES_BRONZE::set))
                .option(createBoolOption("shield_uses_bronze", false, ModConfig.SHIELD_USES_BRONZE, ModConfig.SHIELD_USES_BRONZE::set))
                .option(createBoolOption("anvil_uses_steel", false, ModConfig.ANVIL_USES_STEEL, ModConfig.ANVIL_USES_STEEL::set))
                .option(createBoolOption("stonecutter_uses_steel", false, ModConfig.STONECUTTER_USES_STEEL, ModConfig.STONECUTTER_USES_STEEL::set))
                .option(createBoolOption("blast_furnace_uses_steel", false, ModConfig.BLAST_FURNACE_USES_STEEL, ModConfig.BLAST_FURNACE_USES_STEEL::set))
                .option(createBoolOption("flint_and_steel_uses_steel", false, ModConfig.FLINT_AND_STEEL_USES_STEEL, ModConfig.FLINT_AND_STEEL_USES_STEEL::set))
                .option(createBoolOption("daylight_detector_uses_rose_gold", false, ModConfig.DAYLIGHT_DETECTOR_USES_ROSE_GOLD, ModConfig.DAYLIGHT_DETECTOR_USES_ROSE_GOLD::set))
                .option(createBoolOption("observer_uses_rose_gold", false, ModConfig.OBSERVER_USES_ROSE_GOLD, ModConfig.OBSERVER_USES_ROSE_GOLD::set))
                .option(createBoolOption("powered_rail_uses_electrum", false, ModConfig.POWERED_RAIL_USES_ELECTRUM, ModConfig.POWERED_RAIL_USES_ELECTRUM::set))
                .option(createBoolOption("dispenser_uses_electrum", false, ModConfig.DISPENSER_USES_ELECTRUM, ModConfig.DISPENSER_USES_ELECTRUM::set))
                .option(createBoolOption("lamp_uses_electrum", false, ModConfig.LAMP_USES_ELECTRUM, ModConfig.LAMP_USES_ELECTRUM::set))
                .option(createBoolOption("jukebox_uses_electrum", false, ModConfig.JUKEBOX_USES_ELECTRUM, ModConfig.JUKEBOX_USES_ELECTRUM::set));

        ConfigCategory.Builder oreGen = ConfigCategory.createBuilder()
                .name(Component.translatable("config.modestmining.category.ore_generation"))
                .option(createBoolOption("generate_aluminium_ore", true, ModConfig.GENERATE_ALUMINIUM_ORE, ModConfig.GENERATE_ALUMINIUM_ORE::set))
                .option(createBoolOption("generate_lead_ore", true, ModConfig.GENERATE_LEAD_ORE, ModConfig.GENERATE_LEAD_ORE::set))
                .option(createBoolOption("generate_nether_lead_ore", true, ModConfig.GENERATE_NETHER_LEAD_ORE, ModConfig.GENERATE_NETHER_LEAD_ORE::set))
                .option(createBoolOption("generate_silver_ore", true, ModConfig.GENERATE_SILVER_ORE, ModConfig.GENERATE_SILVER_ORE::set))
                .option(createBoolOption("generate_clams", true, ModConfig.GENERATE_CLAMS, ModConfig.GENERATE_CLAMS::set))
                .option(createBoolOption("generate_meteorite", true, ModConfig.GENERATE_METEORITE, ModConfig.GENERATE_METEORITE::set));

        ConfigCategory.Builder loot = ConfigCategory.createBuilder()
                .name(Component.translatable("config.modestmining.category.loot"))
                .option(createBoolOption("generate_copper_screw_loot", true, ModConfig.GENERATE_COPPER_SCREW_LOOT, ModConfig.GENERATE_COPPER_SCREW_LOOT::set))
                .option(createDoubleOption("copper_screw_loot_chance", 0.07, 0.0, 1.0, 0.01, ModConfig.COPPER_SCREW_LOOT_CHANCE, ModConfig.COPPER_SCREW_LOOT_CHANCE::set));

        ConfigCategory.Builder weapons = ConfigCategory.createBuilder()
                .name(Component.translatable("config.modestmining.category.weapons"))
                .option(createBoolOption("drowned_spawn_with_javelins", true, ModConfig.DROWNED_SPAWN_WITH_JAVELINS, ModConfig.DROWNED_SPAWN_WITH_JAVELINS::set))
                .option(createBoolOption("zombies_spawn_with_javelins", true, ModConfig.ZOMBIES_SPAWN_WITH_JAVELINS, ModConfig.ZOMBIES_SPAWN_WITH_JAVELINS::set))
                .option(createBoolOption("zombies_throw_javelins", true, ModConfig.ZOMBIES_THROW_JAVELINS, ModConfig.ZOMBIES_THROW_JAVELINS::set))
                .option(createBoolOption("zombies_throw_tridents", true, ModConfig.ZOMBIES_THROW_TRIDENTS, ModConfig.ZOMBIES_THROW_TRIDENTS::set))
                .option(createBoolOption("remove_javelin_slowdown", true, ModConfig.REMOVE_JAVELIN_SLOWDOWN, ModConfig.REMOVE_JAVELIN_SLOWDOWN::set))
                .option(createBoolOption("enhanced_tridents", false, ModConfig.ENHANCED_TRIDENTS, ModConfig.ENHANCED_TRIDENTS::set))
                .option(createDoubleOption("javelin_ranged_damage_multiplier", 1.0, 0.0, 10.0, 0.1, ModConfig.JAVELIN_RANGED_DAMAGE_MULTIPLIER, ModConfig.JAVELIN_RANGED_DAMAGE_MULTIPLIER::set));

        return builder.category(replacements.build()).category(oreGen.build()).category(loot.build()).category(weapons.build()).build().generateScreen(parent);
    }

    private static Option<Boolean> createBoolOption(String name, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable("config.modestmining.option." + name))
                .description(OptionDescription.of(Component.translatable("config.modestmining.option." + name + ".tooltip")))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }

    private static Option<Double> createDoubleOption(String name, double defaultValue, double min, double max, double step, Supplier<Double> getter, Consumer<Double> setter) {
        return Option.<Double>createBuilder()
                .name(Component.translatable("config.modestmining.option." + name))
                .description(OptionDescription.of(Component.translatable("config.modestmining.option." + name + ".tooltip")))
                .binding(defaultValue, getter, setter)
                .controller(opt -> DoubleSliderControllerBuilder.create(opt).range(min, max).step(step))
                .build();
    }
}
