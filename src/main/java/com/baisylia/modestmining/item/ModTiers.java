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

    public static final ForgeTier BRONZE = new ForgeTier(1, 400, 5.0f, 1.5f, 10,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));

    public static final ForgeTier STEEL = new ForgeTier(2, 1200, 7.0f, 2.5f, 14,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    public static final ForgeTier PRISMARITE = new ForgeTier(4, 2031, 9.0f, 4.0f, 13,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.PRISMARITE_INGOT.get()));

    public static final ForgeTier VALKYRIUM = new ForgeTier(4, 2031, 10.0f, 4.0f, 11,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.VALKYRIUM_INGOT.get()));
}