package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.integration.farmersdelight.FarmersDelightCompat;
import com.baisylia.modestmining.integration.farmersdelight.FarmersDelightItems;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
            insertAfter(event, Items.COAL_BLOCK,
                    ModBlocks.COKE_BLOCK);

            insertAfter(event, Items.IRON_BLOCK,
                    ModBlocks.STEEL_BLOCK);

            insertAfter(event, Items.EMERALD_BLOCK,
                    ModBlocks.ALUMINIUM_BLOCK,
                    ModBlocks.LEAD_BLOCK,
                    ModBlocks.SILVER_BLOCK,
                    ModBlocks.BRONZE_BLOCK,
                    ModBlocks.ROSE_GOLD_BLOCK,
                    ModBlocks.ELECTRUM_BLOCK);

            insertAfter(event, Items.DIAMOND_BLOCK,
                    ModBlocks.DIAMOND_SHARD_BLOCK);

            insertAfter(event, Items.NETHERITE_BLOCK,
                    ModBlocks.PRISMARITE_BLOCK,
                    ModBlocks.VALKYRIUM_BLOCK);

            insertAfter(event, Items.AMETHYST_BLOCK,
                    ModBlocks.COMPACT_AMETHYST_BLOCK);

        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            insertAfter(event, Items.DEEPSLATE_COPPER_ORE,
                    ModBlocks.ALUMINIUM_ORE,
                    ModBlocks.DEEPSLATE_ALUMINIUM_ORE,
                    ModBlocks.LEAD_ORE,
                    ModBlocks.DEEPSLATE_LEAD_ORE);

            insertAfter(event, Items.DEEPSLATE_GOLD_ORE,
                    ModBlocks.SILVER_ORE,
                    ModBlocks.DEEPSLATE_SILVER_ORE);

            insertAfter(event, Items.NETHER_QUARTZ_ORE,
                    ModBlocks.NETHER_LEAD_ORE);

            insertAfter(event, Items.ANCIENT_DEBRIS,
                    ModBlocks.METEORITE);

            insertAfter(event, Items.RAW_COPPER_BLOCK,
                    ModBlocks.RAW_ALUMINIUM_BLOCK,
                    ModBlocks.RAW_LEAD_BLOCK);

            insertAfter(event, Items.RAW_GOLD_BLOCK,
                    ModBlocks.RAW_SILVER_BLOCK);

            insertAfter(event, Items.SEA_PICKLE,
                    ModBlocks.CLAM,
                    ModBlocks.SHELL);

        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertAfter(event, Items.GRINDSTONE,
                    ModBlocks.MILLSTONE);

            insertAfter(event, Items.BLAST_FURNACE,
                    ModBlocks.FORGE);

        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            insertAfter(event, Items.CHARCOAL,
                    ModItems.COKE,
                    ModItems.COAL_CHUNK,
                    ModItems.CHARCOAL_CHUNK,
                    ModItems.COKE_CHUNK);

            insertAfter(event, Items.RAW_COPPER,
                    ModItems.RAW_ALUMINIUM,
                    ModItems.RAW_LEAD);

            insertAfter(event, Items.RAW_GOLD,
                    ModItems.RAW_SILVER);

            insertAfter(event, Items.DIAMOND,
                    ModItems.DIAMOND_SHARD);

            insertAfter(event, Items.AMETHYST_SHARD,
                    ModItems.AMETHYST);

            insertAfter(event, Items.GOLD_NUGGET,
                    ModItems.COPPER_NUGGET,
                    ModItems.ALUMINIUM_NUGGET,
                    ModItems.LEAD_NUGGET,
                    ModItems.SILVER_NUGGET,
                    ModItems.BRONZE_NUGGET,
                    ModItems.STEEL_NUGGET,
                    ModItems.ROSE_GOLD_NUGGET,
                    ModItems.ELECTRUM_NUGGET,
                    ModItems.PRISMARITE_NUGGET,
                    ModItems.VALKYRIUM_NUGGET);

            insertAfter(event, Items.GOLD_INGOT,
                    ModItems.ALUMINIUM_INGOT,
                    ModItems.LEAD_INGOT,
                    ModItems.SILVER_INGOT,
                    ModItems.BRONZE_INGOT,
                    ModItems.STEEL_INGOT,
                    ModItems.ROSE_GOLD_INGOT,
                    ModItems.ELECTRUM_INGOT,
                    ModItems.COPPER_SCREW);

            insertAfter(event, Items.NETHERITE_SCRAP,
                    ModItems.METEORIC_SCRAP);

            insertAfter(event, Items.NETHERITE_INGOT,
                    ModItems.PRISMARITE_INGOT,
                    ModItems.VALKYRIUM_INGOT);

            insertAfter(event, Items.LEATHER,
                    ModItems.FLESH);

            insertAfter(event, Items.HEART_OF_THE_SEA,
                    ModItems.PEARL);

            insertAfter(event, Items.GLOWSTONE_DUST,
                    ModItems.COPPER_DUST,
                    ModItems.IRON_DUST,
                    ModItems.GOLD_DUST,
                    ModItems.ALUMINIUM_DUST,
                    ModItems.LEAD_DUST,
                    ModItems.SILVER_DUST,
                    ModItems.DEBRIS_DUST,
                    ModItems.METEORIC_DUST);

        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            insertAfter(event, Items.STONE_HOE,
                    ModItems.FLINT_SPADE,
                    ModItems.FLINT_MATTOCK,
                    ModItems.FLINT_HATCHET,
                    ModItems.FLINT_HOE);

            insertAfter(event, Items.IRON_HOE,
                    ModItems.BRONZE_SHOVEL,
                    ModItems.BRONZE_PICKAXE,
                    ModItems.BRONZE_AXE,
                    ModItems.BRONZE_HOE,
                    ModItems.STEEL_SHOVEL,
                    ModItems.STEEL_PICKAXE,
                    ModItems.STEEL_AXE,
                    ModItems.STEEL_HOE);

            insertAfter(event, Items.NETHERITE_HOE,
                    ModItems.PRISMARITE_SHOVEL,
                    ModItems.PRISMARITE_PICKAXE,
                    ModItems.PRISMARITE_AXE,
                    ModItems.PRISMARITE_HOE,
                    ModItems.VALKYRIUM_SHOVEL,
                    ModItems.VALKYRIUM_PICKAXE,
                    ModItems.VALKYRIUM_AXE,
                    ModItems.VALKYRIUM_HOE,
                    ModItems.WOODEN_HAMMER,
                    ModItems.STONE_HAMMER,
                    ModItems.BRONZE_HAMMER,
                    ModItems.IRON_HAMMER,
                    ModItems.STEEL_HAMMER,
                    ModItems.GOLDEN_HAMMER,
                    ModItems.DIAMOND_HAMMER,
                    ModItems.NETHERITE_HAMMER,
                    ModItems.PRISMARITE_HAMMER,
                    ModItems.VALKYRIUM_HAMMER);

            insertAfter(event, Items.BRUSH,
                    ModItems.CHISEL);

        } else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            insertAfter(event, Items.STONE_SWORD,
                    ModItems.FLINT_BLADE);

            insertAfter(event, Items.IRON_SWORD,
                    ModItems.BRONZE_SWORD,
                    ModItems.STEEL_SWORD);

            insertAfter(event, Items.NETHERITE_SWORD,
                    ModItems.PRISMARITE_SWORD,
                    ModItems.VALKYRIUM_SWORD);

            insertAfter(event, Items.STONE_AXE,
                    ModItems.FLINT_HATCHET);

            insertAfter(event, Items.IRON_AXE,
                    ModItems.BRONZE_AXE,
                    ModItems.STEEL_AXE);

            insertAfter(event, Items.NETHERITE_AXE,
                    ModItems.PRISMARITE_AXE,
                    ModItems.VALKYRIUM_AXE);

            insertAfter(event, Items.TRIDENT,
                    ModItems.WOODEN_JAVELIN,
                    ModItems.STONE_JAVELIN,
                    ModItems.BRONZE_JAVELIN,
                    ModItems.IRON_JAVELIN,
                    ModItems.STEEL_JAVELIN,
                    ModItems.GOLDEN_JAVELIN,
                    ModItems.DIAMOND_JAVELIN,
                    ModItems.NETHERITE_JAVELIN,
                    ModItems.PRISMARITE_JAVELIN,
                    ModItems.VALKYRIUM_JAVELIN);

            insertAfter(event, Items.MACE,
                    ModItems.WOODEN_HAMMER,
                    ModItems.STONE_HAMMER,
                    ModItems.BRONZE_HAMMER,
                    ModItems.IRON_HAMMER,
                    ModItems.STEEL_HAMMER,
                    ModItems.GOLDEN_HAMMER,
                    ModItems.DIAMOND_HAMMER,
                    ModItems.NETHERITE_HAMMER,
                    ModItems.PRISMARITE_HAMMER,
                    ModItems.VALKYRIUM_HAMMER);

            insertAfter(event, Items.IRON_BOOTS,
                    ModItems.BRONZE_HELMET,
                    ModItems.BRONZE_CHESTPLATE,
                    ModItems.BRONZE_LEGGINGS,
                    ModItems.BRONZE_BOOTS,
                    ModItems.STEEL_HELMET,
                    ModItems.STEEL_CHESTPLATE,
                    ModItems.STEEL_LEGGINGS,
                    ModItems.STEEL_BOOTS);

            insertAfter(event, Items.NETHERITE_BOOTS,
                    ModItems.PRISMARITE_HELMET,
                    ModItems.PRISMARITE_CHESTPLATE,
                    ModItems.PRISMARITE_LEGGINGS,
                    ModItems.PRISMARITE_BOOTS,
                    ModItems.VALKYRIUM_HELMET,
                    ModItems.VALKYRIUM_CHESTPLATE,
                    ModItems.VALKYRIUM_LEGGINGS,
                    ModItems.VALKYRIUM_BOOTS);

        } else if (FarmersDelightCompat.isLoaded() && event.getTabKey() == FarmersDelightCompat.TAB_KEY) {
            add(event,
                    FarmersDelightItems.BRONZE_KNIFE,
                    FarmersDelightItems.STEEL_KNIFE,
                    FarmersDelightItems.PRISMARITE_KNIFE,
                    FarmersDelightItems.VALKYRIUM_KNIFE);
        }
    }

    @SafeVarargs
    private static void insertAfter(BuildCreativeModeTabContentsEvent event, ItemLike target, Supplier<? extends ItemLike>... newEntries) {
        CreativeModeTab.TabVisibility vis = CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
        ItemLike prev = target;
        for (Supplier<? extends ItemLike> entry : newEntries) {
            ItemLike item = entry.get();
            try {
                event.insertAfter(new ItemStack(prev), new ItemStack(item), vis);
                prev = item;
            } catch (IllegalArgumentException e) {
                event.accept(new ItemStack(item), vis);
                prev = item;
            }
        }
    }

    @SafeVarargs
    private static void add(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike>... entries) {
        for (Supplier<? extends ItemLike> entry : entries) {
            event.accept(entry.get());
        }
    }
}
