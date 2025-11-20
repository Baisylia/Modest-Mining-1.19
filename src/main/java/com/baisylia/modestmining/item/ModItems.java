package com.baisylia.modestmining.item;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.item.custom.tools.BrushItem;
import com.baisylia.modestmining.item.custom.tools.ChiselItem;
import com.baisylia.modestmining.item.custom.tools.ModArmorItem;
import com.baisylia.modestmining.item.custom.weapons.GlaiveItem;
import com.baisylia.modestmining.item.custom.weapons.HammerItem;
import com.baisylia.modestmining.item.custom.weapons.KatanaItem;
import com.baisylia.modestmining.item.custom.weapons.MaceItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModestMining.MOD_ID);

    //Materials
    public static final RegistryObject<Item> PLANK = ITEMS.register("plank", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
        @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 50;}});

    //public static final RegistryObject<Item> ROCKS = ITEMS.register("rocks", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> FIBER = ITEMS.register("fiber", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
    //    @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 50;}});

    //public static final RegistryObject<Item> CHAINMAIL = ITEMS.register("chainmail", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> COAL_POWDER = ITEMS.register("coal_powder", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
    //    @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 2400;}});

    public static final RegistryObject<Item> COKE = ITEMS.register("coke", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
                @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 6400;}});

    public static final RegistryObject<Item> COAL_CHUNK = ITEMS.register("coal_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
        @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 200;}});

    public static final RegistryObject<Item> CHARCOAL_CHUNK = ITEMS.register("charcoal_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
        @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 200;}});

    public static final RegistryObject<Item> COKE_CHUNK = ITEMS.register("coke_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)){
        @Override public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {return 800;}});

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> EXPOSED_COPPER_INGOT = ITEMS.register("exposed_copper_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> WEATHERED_COPPER_INGOT = ITEMS.register("weathered_copper_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> OXIDIZED_COPPER_INGOT = ITEMS.register("oxidized_copper_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    //public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    //public static final RegistryObject<Item> ROSEGOLD_INGOT = ITEMS.register("rosegold_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> ROSEGOLD_NUGGET = ITEMS.register("rosegold_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> PRISMARITE_INGOT = ITEMS.register("prismarite_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> PRISMARITE_NUGGET = ITEMS.register("prismarite_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> PRISMARITE_SCRAP = ITEMS.register("prismarite_scrap", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COPPER_SCREW = ITEMS.register("copper_screw", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> SHELL = ITEMS.register("shell", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> PEARL = ITEMS.register("pearl", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> FLESH = ITEMS.register("flesh", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> ADOBE_BRICK = ITEMS.register("adobe_brick", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> IRON_PLATING = ITEMS.register("iron_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> GOLD_PLATING = ITEMS.register("gold_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> STEEL_PLATING = ITEMS.register("steel_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> ROSEGOLD_PLATING = ITEMS.register("rosegold_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> NETHERITE_PLATING = ITEMS.register("netherite_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> PRISMARITE_PLATING = ITEMS.register("prismarite_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> DIAMOND_PLATING = ITEMS.register("diamond_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> IRON_PIECE = ITEMS.register("iron_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> GOLD_PIECE = ITEMS.register("gold_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> STEEL_PIECE = ITEMS.register("steel_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> ROSEGOLD_PIECE = ITEMS.register("rosegold_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> NETHERITE_PIECE = ITEMS.register("netherite_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> PRISMARITE_PIECE = ITEMS.register("prismarite_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> DIAMOND_PIECE = ITEMS.register("diamond_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> DEBRIS_DUST = ITEMS.register("debris_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> REMAININGS_DUST = ITEMS.register("remaining_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> CLAM_SPAWN_EGG = ITEMS.register("clam_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.CLAM, 0xdeac87, 0x9e5543,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //public static final RegistryObject<Item> ENCHANTED_ROSEGOLD_APPLE = ITEMS.register("enchanted_rosegold_apple",
    //        () -> new EnchantedGoldenAppleItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).rarity(Rarity.EPIC).food(ModFoods.ENCHANTED_ROSEGOLD_APPLE)));

    //public static final RegistryObject<Item> ROSEGOLD_APPLE = ITEMS.register("rosegold_apple",
    //        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).rarity(Rarity.RARE).food(ModFoods.ROSEGOLD_APPLE)));

    //public static final RegistryObject<Item> ROSEGOLD_CARROT = ITEMS.register("rosegold_carrot",
    //        () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(ModFoods.ROSEGOLD_CARROT)));

    //TOOLS                                                                                                                            attack      speed
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", () -> new BrushItem(0f,0f, ModTiers.COPPER,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(450)));

    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(0f,0f, ModTiers.COPPER,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(450)));

    /*public static final RegistryObject<Item> FLINT_BLADE = ITEMS.register("flint_blade", () -> new SwordItem(ModTiers.FLINT, 1, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> FLINT_HATCHET = ITEMS.register("flint_hatchet", () -> new HatchetItem(ModTiers.FLINT, 3, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> FLINT_MATTOCK = ITEMS.register("flint_mattock", () -> new PickaxeItem(ModTiers.FLINT, 0, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> FLINT_SPADE = ITEMS.register("flint_spade", () -> new ShovelItem(ModTiers.FLINT, 0, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> FLINT_HOE = ITEMS.register("flint_hoe", () -> new HoeItem(ModTiers.FLINT, -1, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));*/

    /*public static final RegistryObject<Item> FIBER_CAP = ITEMS.register("fiber_cap", () -> new ArmorItem(ModArmourMaterials.FIBER, EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> FIBER_TUNIC = ITEMS.register("fiber_tunic", () -> new ArmorItem(ModArmourMaterials.FIBER, EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> FIBER_PANTS = ITEMS.register("fiber_pants", () -> new ArmorItem(ModArmourMaterials.FIBER, EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> FIBER_BOOTS = ITEMS.register("fiber_boots", () -> new ArmorItem(ModArmourMaterials.FIBER, EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));*/


    //public static final RegistryObject<Item> COPPER_HATCHET = ITEMS.register("copper_hatchet", () -> new HatchetItem(ModTiers.COPPER_TOOL, 4, -3,
    //        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    /*public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(ModTiers.STEEL, 3, -2.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(ModTiers.STEEL, 5.5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(ModTiers.STEEL, 1, -2.8f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(ModTiers.STEEL, 1.5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(ModTiers.STEEL, -2, 0f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(ModArmourMaterials.STEEL, EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(ModArmourMaterials.STEEL, EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(ModArmourMaterials.STEEL, EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(ModArmourMaterials.STEEL, EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));*/


    /*public static final RegistryObject<Item> ROSEGOLD_SWORD = ITEMS.register("rosegold_sword", () -> new SwordItem(ModTiers.ROSEGOLD, 3, -2.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> ROSEGOLD_AXE = ITEMS.register("rosegold_axe", () -> new AxeItem(ModTiers.ROSEGOLD, 7f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> ROSEGOLD_PICKAXE = ITEMS.register("rosegold_pickaxe", () -> new PickaxeItem(ModTiers.ROSEGOLD, 1, -2.8f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> ROSEGOLD_SHOVEL = ITEMS.register("rosegold_shovel", () -> new ShovelItem(ModTiers.ROSEGOLD, 1.5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> ROSEGOLD_HOE = ITEMS.register("rosegold_hoe", () -> new HoeItem(ModTiers.ROSEGOLD, 0, -2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> ROSEGOLD_HELMET = ITEMS.register("rosegold_helmet", () -> new ArmorItem(ModArmourMaterials.ROSEGOLD, EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> ROSEGOLD_CHESTPLATE = ITEMS.register("rosegold_chestplate", () -> new ArmorItem(ModArmourMaterials.ROSEGOLD, EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> ROSEGOLD_LEGGINGS = ITEMS.register("rosegold_leggings", () -> new ArmorItem(ModArmourMaterials.ROSEGOLD, EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> ROSEGOLD_BOOTS = ITEMS.register("rosegold_boots", () -> new ArmorItem(ModArmourMaterials.ROSEGOLD, EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));*/


    public static final RegistryObject<Item> PRISMARITE_SWORD = ITEMS.register("prismarite_sword", () -> new SwordItem(ModTiers.PRISMARITE, 3, -2.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_AXE = ITEMS.register("prismarite_axe", () -> new AxeItem(ModTiers.PRISMARITE, 5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PRISMARITE_PICKAXE = ITEMS.register("prismarite_pickaxe", () -> new PickaxeItem(ModTiers.PRISMARITE, 1, -2.8f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PRISMARITE_SHOVEL = ITEMS.register("prismarite_shovel", () -> new ShovelItem(ModTiers.PRISMARITE, 1.5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PRISMARITE_HOE = ITEMS.register("prismarite_hoe", () -> new HoeItem(ModTiers.PRISMARITE, -4, 0f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PRISMARITE_HELMET = ITEMS.register("prismarite_helmet", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_CHESTPLATE = ITEMS.register("prismarite_chestplate", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_LEGGINGS = ITEMS.register("prismarite_leggings", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_BOOTS = ITEMS.register("prismarite_boots", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    //public static final RegistryObject<Item> FLINT_MALLET = ITEMS.register("flint_mallet", () -> new HammerItem(ModTiers.FLINT, 2, -3.5f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer", () -> new HammerItem(Tiers.WOOD, 7, -3.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new HammerItem(Tiers.STONE, 7, -3.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(Tiers.IRON, 7, -3.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    //public static final RegistryObject<Item> STEEL_HAMMER = ITEMS.register("steel_hammer", () -> new HammerItem(ModTiers.STEEL, 7, -3.3f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer", () -> new HammerItem(Tiers.GOLD, 7, -3.3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    //public static final RegistryObject<Item> ROSEGOLD_HAMMER = ITEMS.register("rosegold_hammer", () -> new HammerItem(ModTiers.STEEL, 6, -3.3f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(Tiers.DIAMOND, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(Tiers.NETHERITE, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> PRISMARITE_HAMMER = ITEMS.register("prismarite_hammer", () -> new HammerItem(ModTiers.PRISMARITE, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));


    //public static final RegistryObject<Item> FLINT_POLEAXE = ITEMS.register("flint_poleaxe", () -> new GlaiveItem(ModTiers.FLINT, 1, -3.6f, 3.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> WOODEN_GLAIVE = ITEMS.register("wooden_glaive", () -> new GlaiveItem(Tiers.WOOD, 3, -3.5f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STONE_GLAIVE = ITEMS.register("stone_glaive", () -> new GlaiveItem(Tiers.STONE, 3, -3.5f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> GOLDEN_GLAIVE = ITEMS.register("golden_glaive", () -> new GlaiveItem(Tiers.GOLD, 3, -3.5f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //public static final RegistryObject<Item> ROSEGOLD_GLAIVE = ITEMS.register("rosegold_glaive", () -> new GlaiveItem(ModTiers.ROSEGOLD, 3, -3.4f, 3.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> IRON_GLAIVE = ITEMS.register("iron_glaive", () -> new GlaiveItem(Tiers.IRON, 3, -3.4f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //public static final RegistryObject<Item> STEEL_GLAIVE = ITEMS.register("steel_glaive", () -> new GlaiveItem(ModTiers.STEEL, 3, -3.4f, 3.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> DIAMOND_GLAIVE = ITEMS.register("diamond_glaive", () -> new GlaiveItem(Tiers.DIAMOND, 3, -3.3f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> NETHERITE_GLAIVE = ITEMS.register("netherite_glaive", () -> new GlaiveItem(Tiers.NETHERITE, 3, -3.3f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_GLAIVE = ITEMS.register("prismarite_glaive", () -> new GlaiveItem(ModTiers.PRISMARITE, 3, -3.3f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    //public static final RegistryObject<Item> FLINT_TANTO = ITEMS.register("flint_tanto", () -> new KatanaItem(ModTiers.FLINT, 0, -3.3f, 3.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> WOODEN_KATANA = ITEMS.register("wooden_katana", () -> new KatanaItem(Tiers.WOOD, 2, -2.9f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STONE_KATANA = ITEMS.register("stone_katana", () -> new KatanaItem(Tiers.STONE, 2, -2.9f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> GOLDEN_KATANA = ITEMS.register("golden_katana", () -> new KatanaItem(Tiers.GOLD, 2, -2.9f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //public static final RegistryObject<Item> ROSEGOLD_KATANA = ITEMS.register("rosegold_katana", () -> new KatanaItem(ModTiers.ROSEGOLD, 2, -2.7f, 3.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> IRON_KATANA = ITEMS.register("iron_katana", () -> new KatanaItem(Tiers.IRON, 2, -2.8f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //public static final RegistryObject<Item> STEEL_KATANA = ITEMS.register("steel_katana", () -> new KatanaItem(ModTiers.STEEL, 2, -2.7f, 3.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> DIAMOND_KATANA = ITEMS.register("diamond_katana", () -> new KatanaItem(Tiers.DIAMOND, 2, -2.7f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> NETHERITE_KATANA = ITEMS.register("netherite_katana", () -> new KatanaItem(Tiers.NETHERITE, 2, -2.7f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_KATANA = ITEMS.register("prismarite_katana", () -> new KatanaItem(ModTiers.PRISMARITE, 2, -2.7f, 3.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));



    //public static final RegistryObject<Item> FLINT_BLUDGEON = ITEMS.register("flint_bludgeon", () -> new MaceItem(ModTiers.FLINT, 0, -3.5f, 2.0f, 2.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> WOODEN_MACE = ITEMS.register("wooden_mace", () -> new MaceItem(Tiers.WOOD, 1, -3.4f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> STONE_MACE = ITEMS.register("stone_mace", () -> new MaceItem(Tiers.STONE, 1, -3.4f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> GOLDEN_MACE = ITEMS.register("golden_mace", () -> new MaceItem(Tiers.GOLD, 1, -3.4f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //public static final RegistryObject<Item> ROSEGOLD_MACE = ITEMS.register("rosegold_mace", () -> new MaceItem(ModTiers.ROSEGOLD, 1, -3.3f, 2.0f, 2.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> IRON_MACE = ITEMS.register("iron_mace", () -> new MaceItem(Tiers.IRON, 1, -3.3f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //public static final RegistryObject<Item> STEEL_MACE = ITEMS.register("steel_mace", () -> new MaceItem(ModTiers.STEEL, 1, -3.3f, 2.0f, 2.0f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> DIAMOND_MACE = ITEMS.register("diamond_mace", () -> new MaceItem(Tiers.DIAMOND, 1, -3.2f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> NETHERITE_MACE = ITEMS.register("netherite_mace", () -> new MaceItem(Tiers.NETHERITE, 1, -3.2f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> PRISMARITE_MACE = ITEMS.register("prismarite_mace", () -> new MaceItem(ModTiers.PRISMARITE, 1, -3.2f, 2.0f, 2.0f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
