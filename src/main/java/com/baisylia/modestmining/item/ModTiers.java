package com.baisylia.modestmining.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {

    public static final Tier FLINT = new SimpleTier(BlockTags.INCORRECT_FOR_WOODEN_TOOL, 45, 1.0f, 0.0f, 2,
            () -> Ingredient.of(Items.FLINT));

    public static final Tier COPPER = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 600, 4.0f, 1.0f, 10,
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final Tier COPPER_TOOL = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 300, 2.0f, 1.0f, 10,
            () -> Ingredient.of(Items.COPPER_INGOT));

    public static final Tier BRONZE = new SimpleTier(BlockTags.INCORRECT_FOR_STONE_TOOL, 400, 5.0f, 1.5f, 10,
            () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));

    public static final Tier STEEL = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1200, 7.0f, 2.5f, 14,
            () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    public static final Tier PRISMARITE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 9.0f, 4.0f, 13,
            () -> Ingredient.of(ModItems.PRISMARITE_INGOT.get()));

    public static final Tier VALKYRIUM = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, 10.0f, 4.0f, 11,
            () -> Ingredient.of(ModItems.VALKYRIUM_INGOT.get()));
}