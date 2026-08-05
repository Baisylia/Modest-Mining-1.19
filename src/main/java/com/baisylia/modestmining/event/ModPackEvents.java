package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.config.ModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class ModPackEvents {

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            registerPack(event, "copper_tripwire_hook_textures", "Copper Tripwire Hook Textures", ModConfig.TRIPWIRE_HOOK_USES_COPPER);
            registerPack(event, "copper_repeater_textures", "Copper Repeater Textures", ModConfig.REPEATER_USES_COPPER);
            registerPack(event, "copper_bow_textures", "Copper Bow Textures", ModConfig.BOW_USES_COPPER);
            registerPack(event, "aluminium_bucket_textures", "Aluminium Bucket Textures", ModConfig.BUCKET_USES_ALUMINIUM);
            registerPack(event, "aluminium_cauldron_textures", "Aluminium Cauldron Textures", ModConfig.CAULDRON_USES_ALUMINIUM);
            registerPack(event, "aluminium_fishing_rod_textures", "Aluminium Fishing Rod Textures", ModConfig.FISHING_ROD_USES_ALUMINIUM);
            registerPack(event, "aluminium_forge_textures", "Aluminium Forge Textures", ModConfig.FORGE_USES_ALUMINIUM);
            registerPack(event, "bronze_crossbow_textures", "Bronze Crossbow Textures", ModConfig.CROSSBOW_USES_BRONZE);
            registerPack(event, "bronze_piston_textures", "Bronze Piston Textures", ModConfig.PISTON_USES_BRONZE);
            registerPack(event, "bronze_shield_textures", "Bronze Shield Textures", ModConfig.SHIELD_USES_BRONZE);
            registerPack(event, "bronze_smoker_textures", "Bronze Smoker Textures", ModConfig.SMOKER_USES_BRONZE);
            registerPack(event, "electrum_dispenser_textures", "Electrum Dispenser Textures", ModConfig.DISPENSER_USES_ELECTRUM);
            registerPack(event, "electrum_jukebox_textures", "Electrum Jukebox Textures", ModConfig.JUKEBOX_USES_ELECTRUM);
            registerPack(event, "electrum_lamp_textures", "Electrum Lamp Textures", ModConfig.LAMP_USES_ELECTRUM);
            registerPack(event, "electrum_powered_rail_textures", "Electrum Powered Rail Textures", ModConfig.POWERED_RAIL_USES_ELECTRUM);
            registerPack(event, "gold_brewing_stand_textures", "Gold Brewing Stand Textures", ModConfig.BREWING_STAND_USES_GOLD);
            registerPack(event, "gold_comparator_textures", "Gold Comparator Textures", ModConfig.COMPARATOR_USES_GOLD);
            registerPack(event, "gold_detector_rail_textures", "Gold Detector Rail Textures", ModConfig.DETECTOR_RAIL_USES_GOLD);
            registerPack(event, "lead_hopper_textures", "Lead Hopper Textures", ModConfig.HOPPER_USES_LEAD);
            registerPack(event, "lead_minecart_textures", "Lead Minecart Textures", ModConfig.MINECART_USES_LEAD);
            registerPack(event, "modestmining_materials", "Modest Mining Materials Textures", () -> true);
            registerPack(event, "rose_gold_daylight_detector_textures", "Rose Gold Daylight Detector Textures", ModConfig.DAYLIGHT_DETECTOR_USES_ROSE_GOLD);
            registerPack(event, "rose_gold_observer_textures", "Rose Gold Observer Textures", ModConfig.OBSERVER_USES_ROSE_GOLD);
            registerPack(event, "silver_activator_rail_textures", "Silver Activator Rail Textures", ModConfig.ACTIVATOR_RAIL_USES_SILVER);
            registerPack(event, "silver_dropper_textures", "Silver Dropper Textures", ModConfig.DROPPER_USES_SILVER);
            registerPack(event, "silver_note_block_textures", "Silver Note Block Textures", ModConfig.NOTE_BLOCK_RAIL_USES_SILVER);
            registerPack(event, "steel_anvil_textures", "Steel Anvil Textures", ModConfig.ANVIL_USES_STEEL);
            registerPack(event, "steel_blast_furnace_textures", "Steel Blast Furnace Textures", ModConfig.BLAST_FURNACE_USES_STEEL);
            registerPack(event, "steel_flint_and_steel_textures", "Steel Flint and Steel Textures", ModConfig.FLINT_AND_STEEL_USES_STEEL);
            registerPack(event, "steel_stonecutter_textures", "Steel Stonecutter Textures", ModConfig.STONECUTTER_USES_STEEL);
        }
    }

    private static void registerPack(AddPackFindersEvent event, String packId, String title, Supplier<Boolean> enabledSupplier) {
        if (enabledSupplier.get()) {
            event.addPackFinders(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "resourcepacks/" + packId), event.getPackType(), Component.literal(title), PackSource.BUILT_IN, true, Pack.Position.TOP);
        }
    }
}
