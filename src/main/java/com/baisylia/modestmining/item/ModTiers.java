package com.baisylia.modestmining.item;

import com.baisylia.modestmining.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {

    public static final ForgeTier FLINT = new ForgeTier(0, 45, 1.0f, 0.0f, 2,
            ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.of(Items.FLINT));

    public static final ForgeTier COPPER = new ForgeTier(1, 600, 4.0f, 1.0f, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));

    public static final ForgeTier COPPER_TOOL = new ForgeTier(1, 300, 2.0f, 1.0f, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));

    /*public static final ForgeTier STEEL = new ForgeTier(2, 1200, 7.0f, 2.5f, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    public static final ForgeTier ROSEGOLD = new ForgeTier(0, 1200, 12.0f, 0.0f, 22,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ModItems.ROSEGOLD_INGOT.get()));*/

    public static final ForgeTier PRISMARITE = new ForgeTier(4, 2031, 9.0f, 4.0f, 13,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.PRISMARITE_INGOT.get()));


    /*public static final ForgeTier MOUNTAIN_FLINT = new ForgeTier(0, 45, 0.25f, 1.5f, 2,
            ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.of(Items.FLINT));

    public static final ForgeTier MOUNTAIN_WOOD = new ForgeTier(0, 59, 0.5F, 1.5f, 15,
            ModTags.Blocks.NEEDS_FLINT_TOOL, () -> Ingredient.of(ItemTags.PLANKS));

    public static final ForgeTier MOUNTAIN_STONE = new ForgeTier(1, 131, 1.0F, 2.5f, 5,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ItemTags.STONE_TOOL_MATERIALS));

    public static final ForgeTier MOUNTAIN_IRON = new ForgeTier(2, 250, 2.0F, 3.5f, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.IRON_INGOT));

    public static final ForgeTier MOUNTAIN_STEEL = new ForgeTier(2, 1200, 3.0f, 4.0f, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    public static final ForgeTier MOUNTAIN_GOLD = new ForgeTier(0, 32, 5.5F, 1.5f, 22,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.GOLD_INGOT));

    public static final ForgeTier MOUNTAIN_ROSEGOLD = new ForgeTier(0, 1200, 6.0f, 1.5f, 22,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ModItems.ROSEGOLD_INGOT.get()));

    public static final ForgeTier MOUNTAIN_DIAMOND = new ForgeTier(3, 1561, 4.0F, 4.5f, 10,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.DIAMOND));

    public static final ForgeTier MOUNTAIN_NETHERITE = new ForgeTier(4, 2031, 5.0F, 5.5f, 15,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_INGOT));

    public static final ForgeTier MOUNTAIN_PRISMARITE = new ForgeTier(4, 2031, 5.0f, 5.5f, 13,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.PRISMARITE_INGOT.get()));*/

}