package com.baisylia.modestmining.client;

import com.baisylia.modestmining.config.ModConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
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
                .option(createBoolOption("flint_replaces_wood", false, ModConfig.FLINT_REPLACES_WOOD::get, ModConfig.FLINT_REPLACES_WOOD::set))
                .option(createBoolOption("bronze_replaces_stone", false, ModConfig.BRONZE_REPLACES_STONE::get, ModConfig.BRONZE_REPLACES_STONE::set))
                .option(createBoolOption("steel_replaces_iron", false, ModConfig.STEEL_REPLACES_IRON::get, ModConfig.STEEL_REPLACES_IRON::set))
                .option(createBoolOption("repeater_uses_copper", false, ModConfig.REPEATER_USES_COPPER::get, ModConfig.REPEATER_USES_COPPER::set))
                .option(createBoolOption("bow_uses_copper", false, ModConfig.BOW_USES_COPPER::get, ModConfig.BOW_USES_COPPER::set))
                .option(createBoolOption("tripwire_hook_uses_copper", false, ModConfig.TRIPWIRE_HOOK_USES_COPPER::get, ModConfig.TRIPWIRE_HOOK_USES_COPPER::set))
                .option(createBoolOption("forge_uses_aluminium", false, ModConfig.FORGE_USES_ALUMINIUM::get, ModConfig.FORGE_USES_ALUMINIUM::set))
                .option(createBoolOption("bucket_uses_aluminium", false, ModConfig.BUCKET_USES_ALUMINIUM::get, ModConfig.BUCKET_USES_ALUMINIUM::set))
                .option(createBoolOption("fishing_rod_uses_aluminium", false, ModConfig.FISHING_ROD_USES_ALUMINIUM::get, ModConfig.FISHING_ROD_USES_ALUMINIUM::set))
                .option(createBoolOption("cauldron_uses_aluminium", false, ModConfig.CAULDRON_USES_ALUMINIUM::get, ModConfig.CAULDRON_USES_ALUMINIUM::set))
                .option(createBoolOption("hopper_uses_lead", false, ModConfig.HOPPER_USES_LEAD::get, ModConfig.HOPPER_USES_LEAD::set))
                .option(createBoolOption("minecart_uses_lead", false, ModConfig.MINECART_USES_LEAD::get, ModConfig.MINECART_USES_LEAD::set))
                .option(createBoolOption("comparator_uses_gold", false, ModConfig.COMPARATOR_USES_GOLD::get, ModConfig.COMPARATOR_USES_GOLD::set))
                .option(createBoolOption("detector_rail_uses_gold", false, ModConfig.DETECTOR_RAIL_USES_GOLD::get, ModConfig.DETECTOR_RAIL_USES_GOLD::set))
                .option(createBoolOption("spyglass_uses_gold", false, ModConfig.SPYGLASS_USES_GOLD::get, ModConfig.SPYGLASS_USES_GOLD::set))
                .option(createBoolOption("brewing_stand_uses_gold", false, ModConfig.BREWING_STAND_USES_GOLD::get, ModConfig.BREWING_STAND_USES_GOLD::set))
                .option(createBoolOption("activator_rail_uses_silver", false, ModConfig.ACTIVATOR_RAIL_USES_SILVER::get, ModConfig.ACTIVATOR_RAIL_USES_SILVER::set))
                .option(createBoolOption("note_block_uses_silver", false, ModConfig.NOTE_BLOCK_RAIL_USES_SILVER::get, ModConfig.NOTE_BLOCK_RAIL_USES_SILVER::set))
                .option(createBoolOption("dropper_uses_silver", false, ModConfig.DROPPER_USES_SILVER::get, ModConfig.DROPPER_USES_SILVER::set))
                .option(createBoolOption("piston_uses_bronze", false, ModConfig.PISTON_USES_BRONZE::get, ModConfig.PISTON_USES_BRONZE::set))
                .option(createBoolOption("smoker_uses_bronze", false, ModConfig.SMOKER_USES_BRONZE::get, ModConfig.SMOKER_USES_BRONZE::set))
                .option(createBoolOption("crossbow_uses_bronze", false, ModConfig.CROSSBOW_USES_BRONZE::get, ModConfig.CROSSBOW_USES_BRONZE::set))
                .option(createBoolOption("shield_uses_bronze", false, ModConfig.SHIELD_USES_BRONZE::get, ModConfig.SHIELD_USES_BRONZE::set))
                .option(createBoolOption("anvil_uses_steel", false, ModConfig.ANVIL_USES_STEEL::get, ModConfig.ANVIL_USES_STEEL::set))
                .option(createBoolOption("stonecutter_uses_steel", false, ModConfig.STONECUTTER_USES_STEEL::get, ModConfig.STONECUTTER_USES_STEEL::set))
                .option(createBoolOption("blast_furnace_uses_steel", false, ModConfig.BLAST_FURNACE_USES_STEEL::get, ModConfig.BLAST_FURNACE_USES_STEEL::set))
                .option(createBoolOption("flint_and_steel_uses_steel", false, ModConfig.FLINT_AND_STEEL_USES_STEEL::get, ModConfig.FLINT_AND_STEEL_USES_STEEL::set))
                .option(createBoolOption("daylight_detector_uses_rose_gold", false, ModConfig.DAYLIGHT_DETECTOR_USES_ROSE_GOLD::get, ModConfig.DAYLIGHT_DETECTOR_USES_ROSE_GOLD::set))
                .option(createBoolOption("observer_uses_rose_gold", false, ModConfig.OBSERVER_USES_ROSE_GOLD::get, ModConfig.OBSERVER_USES_ROSE_GOLD::set))
                .option(createBoolOption("powered_rail_uses_electrum", false, ModConfig.POWERED_RAIL_USES_ELECTRUM::get, ModConfig.POWERED_RAIL_USES_ELECTRUM::set))
                .option(createBoolOption("dispenser_uses_electrum", false, ModConfig.DISPENSER_USES_ELECTRUM::get, ModConfig.DISPENSER_USES_ELECTRUM::set))
                .option(createBoolOption("lamp_uses_electrum", false, ModConfig.LAMP_USES_ELECTRUM::get, ModConfig.LAMP_USES_ELECTRUM::set))
                .option(createBoolOption("jukebox_uses_electrum", false, ModConfig.JUKEBOX_USES_ELECTRUM::get, ModConfig.JUKEBOX_USES_ELECTRUM::set));

        ConfigCategory.Builder oreGen = ConfigCategory.createBuilder()
                .name(Component.translatable("config.modestmining.category.ore_generation"))
                .option(createBoolOption("generate_aluminium_ore", true, ModConfig.GENERATE_ALUMINIUM_ORE::get, ModConfig.GENERATE_ALUMINIUM_ORE::set))
                .option(createBoolOption("generate_lead_ore", true, ModConfig.GENERATE_LEAD_ORE::get, ModConfig.GENERATE_LEAD_ORE::set))
                .option(createBoolOption("generate_nether_lead_ore", true, ModConfig.GENERATE_NETHER_LEAD_ORE::get, ModConfig.GENERATE_NETHER_LEAD_ORE::set))
                .option(createBoolOption("generate_silver_ore", true, ModConfig.GENERATE_SILVER_ORE::get, ModConfig.GENERATE_SILVER_ORE::set))
                .option(createBoolOption("generate_clams", true, ModConfig.GENERATE_CLAMS::get, ModConfig.GENERATE_CLAMS::set));

        return builder.category(replacements.build()).category(oreGen.build()).build().generateScreen(parent);
    }

    private static Option<Boolean> createBoolOption(String name, boolean defaultValue, Supplier<Boolean> getter, Consumer<Boolean> setter) {
        return Option.<Boolean>createBuilder()
                .name(Component.translatable("config.modestmining.option." + name))
                .description(OptionDescription.of(Component.translatable("config.modestmining.option." + name + ".tooltip")))
                .binding(defaultValue, getter, setter)
                .controller(TickBoxControllerBuilder::create)
                .build();
    }
}
