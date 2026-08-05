package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class ModCreativeTabEvents {

    @SubscribeEvent
    public static void buildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            add(event,
                    ModBlocks.COKE_BLOCK,
                    ModBlocks.ALUMINIUM_BLOCK,
                    ModBlocks.LEAD_BLOCK,
                    ModBlocks.SILVER_BLOCK,
                    ModBlocks.BRONZE_BLOCK,
                    ModBlocks.STEEL_BLOCK,
                    ModBlocks.ROSEGOLD_BLOCK,
                    ModBlocks.ELECTRUM_BLOCK,
                    ModBlocks.PRISMARITE_BLOCK,
                    ModBlocks.VALKYRIUM_BLOCK,
                    ModBlocks.COMPACT_AMETHYST_BLOCK,
                    ModBlocks.DIAMOND_SHARD_BLOCK);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            add(event,
                    ModBlocks.ALUMINIUM_ORE,
                    ModBlocks.DEEPSLATE_ALUMINIUM_ORE,
                    ModBlocks.LEAD_ORE,
                    ModBlocks.DEEPSLATE_LEAD_ORE,
                    ModBlocks.NETHER_LEAD_ORE,
                    ModBlocks.SILVER_ORE,
                    ModBlocks.DEEPSLATE_SILVER_ORE,
                    ModBlocks.RAW_ALUMINIUM_BLOCK,
                    ModBlocks.RAW_LEAD_BLOCK,
                    ModBlocks.RAW_SILVER_BLOCK,
                    ModBlocks.CLAM,
                    ModBlocks.SHELL);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            add(event,
                    ModBlocks.FORGE,
                    ModBlocks.MILLSTONE);
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            add(event,
                    ModItems.COKE,
                    ModItems.COAL_CHUNK,
                    ModItems.CHARCOAL_CHUNK,
                    ModItems.COKE_CHUNK,
                    ModItems.RAW_ALUMINIUM,
                    ModItems.RAW_LEAD,
                    ModItems.RAW_SILVER,
                    ModItems.COPPER_NUGGET,
                    ModItems.ALUMINIUM_NUGGET,
                    ModItems.LEAD_NUGGET,
                    ModItems.SILVER_NUGGET,
                    ModItems.BRONZE_NUGGET,
                    ModItems.STEEL_NUGGET,
                    ModItems.ROSEGOLD_NUGGET,
                    ModItems.ELECTRUM_NUGGET,
                    ModItems.PRISMARITE_NUGGET,
                    ModItems.VALKYRIUM_NUGGET,
                    ModItems.ALUMINIUM_INGOT,
                    ModItems.LEAD_INGOT,
                    ModItems.SILVER_INGOT,
                    ModItems.BRONZE_INGOT,
                    ModItems.STEEL_INGOT,
                    ModItems.ROSEGOLD_INGOT,
                    ModItems.ELECTRUM_INGOT,
                    ModItems.PRISMARITE_INGOT,
                    ModItems.VALKYRIUM_INGOT,
                    ModItems.COPPER_DUST,
                    ModItems.IRON_DUST,
                    ModItems.GOLD_DUST,
                    ModItems.ALUMINIUM_DUST,
                    ModItems.LEAD_DUST,
                    ModItems.SILVER_DUST,
                    ModItems.DEBRIS_DUST,
                    ModItems.DIAMOND_SHARD,
                    ModItems.DIAMOND_PIECE,
                    ModItems.DIAMOND_PLATING,
                    ModItems.AMETHYST,
                    ModItems.PEARL,
                    ModItems.FLESH,
                    ModItems.COPPER_SCREW);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            add(event,
                    ModItems.CHISEL,
                    ModItems.FLINT_SPADE,
                    ModItems.FLINT_MATTOCK,
                    ModItems.FLINT_HATCHET,
                    ModItems.FLINT_HOE,
                    ModItems.BRONZE_SHOVEL,
                    ModItems.BRONZE_PICKAXE,
                    ModItems.BRONZE_AXE,
                    ModItems.BRONZE_HOE,
                    ModItems.STEEL_SHOVEL,
                    ModItems.STEEL_PICKAXE,
                    ModItems.STEEL_AXE,
                    ModItems.STEEL_HOE,
                    ModItems.PRISMARITE_SHOVEL,
                    ModItems.PRISMARITE_PICKAXE,
                    ModItems.PRISMARITE_AXE,
                    ModItems.PRISMARITE_HOE,
                    ModItems.VALKYRIUM_SHOVEL,
                    ModItems.VALKYRIUM_PICKAXE,
                    ModItems.VALKYRIUM_AXE,
                    ModItems.VALKYRIUM_HOE);
            addHammers(event);
        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            add(event,
                    ModItems.FLINT_BLADE,
                    ModItems.BRONZE_SWORD,
                    ModItems.STEEL_SWORD,
                    ModItems.PRISMARITE_SWORD,
                    ModItems.VALKYRIUM_SWORD,
                    ModItems.FLINT_HATCHET,
                    ModItems.BRONZE_AXE,
                    ModItems.STEEL_AXE,
                    ModItems.PRISMARITE_AXE,
                    ModItems.VALKYRIUM_AXE);
            addHammers(event);
            add(event,
                    ModItems.WOODEN_JAVELIN,
                    ModItems.STONE_JAVELIN,
                    ModItems.IRON_JAVELIN,
                    ModItems.GOLDEN_JAVELIN,
                    ModItems.DIAMOND_JAVELIN,
                    ModItems.NETHERITE_JAVELIN,
                    ModItems.PRISMARITE_JAVELIN,
                    ModItems.VALKYRIUM_JAVELIN,
                    ModItems.BRONZE_HELMET,
                    ModItems.BRONZE_CHESTPLATE,
                    ModItems.BRONZE_LEGGINGS,
                    ModItems.BRONZE_BOOTS,
                    ModItems.STEEL_HELMET,
                    ModItems.STEEL_CHESTPLATE,
                    ModItems.STEEL_LEGGINGS,
                    ModItems.STEEL_BOOTS,
                    ModItems.PRISMARITE_HELMET,
                    ModItems.PRISMARITE_CHESTPLATE,
                    ModItems.PRISMARITE_LEGGINGS,
                    ModItems.PRISMARITE_BOOTS,
                    ModItems.VALKYRIUM_HELMET,
                    ModItems.VALKYRIUM_CHESTPLATE,
                    ModItems.VALKYRIUM_LEGGINGS,
                    ModItems.VALKYRIUM_BOOTS);
        }
    }

    private static void addHammers(BuildCreativeModeTabContentsEvent event) {
        add(event,
                ModItems.WOODEN_HAMMER,
                ModItems.STONE_HAMMER,
                ModItems.IRON_HAMMER,
                ModItems.GOLDEN_HAMMER,
                ModItems.DIAMOND_HAMMER,
                ModItems.NETHERITE_HAMMER,
                ModItems.PRISMARITE_HAMMER,
                ModItems.VALKYRIUM_HAMMER);
    }

    @SafeVarargs
    private static void add(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike>... entries) {
        for (Supplier<? extends ItemLike> entry : entries) {
            event.accept(entry.get());
        }
    }
}
