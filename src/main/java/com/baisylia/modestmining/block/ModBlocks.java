package com.baisylia.modestmining.block;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.custom.ClamBlock;
import com.baisylia.modestmining.block.custom.ForgeBlock;
import com.baisylia.modestmining.block.custom.MillstoneBlock;
import com.baisylia.modestmining.block.entity.custom.ShellBlock;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, ModestMining.MOD_ID);

    public static final DeferredHolder<Block, Block> COKE_BLOCK = registerBlock("coke_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK)), true, 64000);

    public static final DeferredHolder<Block, Block> ALUMINIUM_BLOCK = registerBlock("aluminium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> RAW_ALUMINIUM_BLOCK = registerBlock("raw_aluminium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> ALUMINIUM_ORE = registerBlock("aluminium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE)), false, 0);
    public static final DeferredHolder<Block, Block> DEEPSLATE_ALUMINIUM_ORE = registerBlock("deepslate_aluminium_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE)), false, 0);
    public static final DeferredHolder<Block, Block> LEAD_BLOCK = registerBlock("lead_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> RAW_LEAD_BLOCK = registerBlock("raw_lead_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> LEAD_ORE = registerBlock("lead_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE)), false, 0);
    public static final DeferredHolder<Block, Block> DEEPSLATE_LEAD_ORE = registerBlock("deepslate_lead_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_COPPER_ORE)), false, 0);
    public static final DeferredHolder<Block, Block> NETHER_LEAD_ORE = registerBlock("nether_lead_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE)), false, 0);
    public static final DeferredHolder<Block, Block> SILVER_BLOCK = registerBlock("silver_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> SILVER_ORE = registerBlock("silver_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_ORE)), false, 0);
    public static final DeferredHolder<Block, Block> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_GOLD_ORE)), false, 0);

    public static final DeferredHolder<Block, Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> ROSEGOLD_BLOCK = registerBlock("rosegold_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> BRONZE_BLOCK = registerBlock("bronze_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> ELECTRUM_BLOCK = registerBlock("electrum_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)), false, 0);

    public static final DeferredHolder<Block, Block> PRISMARITE_BLOCK = registerBlock("prismarite_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> VALKYRIUM_BLOCK = registerBlock("valkyrium_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)), false, 0);
    public static final DeferredHolder<Block, Block> METEORITE = registerBlock("meteorite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ANCIENT_DEBRIS)), false, 0);

    public static final DeferredHolder<Block, Block> FORGE = registerBlock("forge",
            () -> new ForgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).lightLevel((blockState) -> {
                        if (blockState.getValue(ForgeBlock.LIT)) {
                            return 15;
                        }
                        return 0;
                    })
                    .strength(5.0f, 6.0f).requiresCorrectToolForDrops()), false, 0);

    public static final DeferredHolder<Block, Block> MILLSTONE = registerBlock("millstone",
            () -> new MillstoneBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY)
                    .strength(5.0f, 6.0f).requiresCorrectToolForDrops()), false, 0);

    public static final DeferredHolder<Block, Block> COMPACT_AMETHYST_BLOCK = registerBlock("compact_amethyst_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)), false, 0);

    public static final DeferredHolder<Block, Block> DIAMOND_SHARD_BLOCK = registerBlock("diamond_shard_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.METAL)), false, 0);

    public static final DeferredHolder<Block, Block> SHELL = registerBlock("shell",
            () -> new ShellBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.5f, 0.5f)), false, 0);

    public static final DeferredHolder<Block, Block> CLAM = registerBlock("clam",
            () -> new ClamBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(0.8f, 0.8f).sound(SoundType.CORAL_BLOCK).noOcclusion()), false, 0);

    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name, Supplier<T> block, Boolean isFuel, Integer fuelAmount) {
        DeferredHolder<Block, T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, isFuel, fuelAmount);
        return toReturn;
    }

    private static <T extends Block> DeferredHolder<Item, Item> registerBlockItem(String name, DeferredHolder<Block, T> block, Boolean isFuel, Integer fuelAmount) {
        if (!isFuel) {
            return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        } else {
            return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
                @Override
                public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                    return fuelAmount;
                }
            });
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}