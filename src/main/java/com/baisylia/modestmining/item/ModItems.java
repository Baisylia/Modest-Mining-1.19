package com.baisylia.modestmining.item;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.item.custom.tools.BrushItem;
import com.baisylia.modestmining.item.custom.tools.ChiselItem;
import com.baisylia.modestmining.item.custom.tools.ModArmorItem;
import com.baisylia.modestmining.item.custom.weapons.HammerItem;
import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
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

    // Materials
    public static final RegistryObject<Item> COKE = ITEMS.register("coke", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 6400;
        }
    });

    public static final RegistryObject<Item> COAL_CHUNK = ITEMS.register("coal_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final RegistryObject<Item> CHARCOAL_CHUNK = ITEMS.register("charcoal_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final RegistryObject<Item> COKE_CHUNK = ITEMS.register("coke_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 800;
        }
    });
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ALUMINIUM_NUGGET = ITEMS.register("aluminium_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> RAW_ALUMINIUM = ITEMS.register("raw_aluminium", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ALUMINIUM_DUST = ITEMS.register("aluminium_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ROSEGOLD_INGOT = ITEMS.register("rosegold_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ROSEGOLD_NUGGET = ITEMS.register("rosegold_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ELECTRUM_NUGGET = ITEMS.register("electrum_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> PRISMARITE_INGOT = ITEMS.register("prismarite_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> PRISMARITE_NUGGET = ITEMS.register("prismarite_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> VALKYRIUM_INGOT = ITEMS.register("valkyrium_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> VALKYRIUM_NUGGET = ITEMS.register("valkyrium_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> COPPER_SCREW = ITEMS.register("copper_screw", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> PEARL = ITEMS.register("pearl", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> FLESH = ITEMS.register("flesh", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DIAMOND_PLATING = ITEMS.register("diamond_plating", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DIAMOND_PIECE = ITEMS.register("diamond_piece", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> DEBRIS_DUST = ITEMS.register("debris_dust", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> CLAM_SPAWN_EGG = ITEMS.register("clam_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.CLAM, 0xdeac87, 0x9e5543,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // TOOLS
    public static final RegistryObject<Item> BRUSH = ITEMS.register("brush", () -> new BrushItem(0f, 0f, ModTiers.COPPER,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(450)));

    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(0f, 0f, ModTiers.COPPER,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).durability(450)));

    // Flint
    public static final RegistryObject<Item> FLINT_BLADE = ITEMS.register("flint_blade", () -> new SwordItem(ModTiers.FLINT, 1, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> FLINT_HATCHET = ITEMS.register("flint_hatchet", () -> new AxeItem(ModTiers.FLINT, 3.0f, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> FLINT_MATTOCK = ITEMS.register("flint_mattock", () -> new PickaxeItem(ModTiers.FLINT, 0, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> FLINT_SPADE = ITEMS.register("flint_spade", () -> new ShovelItem(ModTiers.FLINT, 0, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> FLINT_HOE = ITEMS.register("flint_hoe", () -> new HoeItem(ModTiers.FLINT, -1, -3,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    // Bronze
    public static final RegistryObject<Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> new SwordItem(ModTiers.BRONZE, 3, -2.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(ModTiers.BRONZE, 6.0f, -3.1f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new PickaxeItem(ModTiers.BRONZE, 1, -2.8f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(ModTiers.BRONZE, 1.5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(ModTiers.BRONZE, -1, -2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(ModArmourMaterials.BRONZE, EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(ModArmourMaterials.BRONZE, EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(ModArmourMaterials.BRONZE, EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(ModArmourMaterials.BRONZE, EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    // Steel
    public static final RegistryObject<Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(ModTiers.STEEL, 3, -2.4f,
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
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    // Prismarite
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

    // Valkyrium
    public static final RegistryObject<Item> VALKYRIUM_SWORD = ITEMS.register("valkyrium_sword", () -> new SwordItem(ModTiers.VALKYRIUM, 3, -2.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> VALKYRIUM_AXE = ITEMS.register("valkyrium_axe", () -> new AxeItem(ModTiers.VALKYRIUM, 5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> VALKYRIUM_PICKAXE = ITEMS.register("valkyrium_pickaxe", () -> new PickaxeItem(ModTiers.VALKYRIUM, 1, -2.8f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> VALKYRIUM_SHOVEL = ITEMS.register("valkyrium_shovel", () -> new ShovelItem(ModTiers.VALKYRIUM, 1.5f, -3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> VALKYRIUM_HOE = ITEMS.register("valkyrium_hoe", () -> new HoeItem(ModTiers.VALKYRIUM, -4, 0f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> VALKYRIUM_HELMET = ITEMS.register("valkyrium_helmet", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, EquipmentSlot.HEAD,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> VALKYRIUM_CHESTPLATE = ITEMS.register("valkyrium_chestplate", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, EquipmentSlot.CHEST,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> VALKYRIUM_LEGGINGS = ITEMS.register("valkyrium_leggings", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, EquipmentSlot.LEGS,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> VALKYRIUM_BOOTS = ITEMS.register("valkyrium_boots", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, EquipmentSlot.FEET,
            new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    // Hammers
    public static final RegistryObject<Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer", () -> new HammerItem(Tiers.WOOD, 7, -3.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new HammerItem(Tiers.STONE, 7, -3.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(Tiers.IRON, 7, -3.4f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    // public static final RegistryObject<Item> STEEL_HAMMER = ITEMS.register("steel_hammer", () -> new HammerItem(ModTiers.STEEL, 7, -3.3f,
    //        new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer", () -> new HammerItem(Tiers.GOLD, 7, -3.3f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(Tiers.DIAMOND, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(Tiers.NETHERITE, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> PRISMARITE_HAMMER = ITEMS.register("prismarite_hammer", () -> new HammerItem(ModTiers.PRISMARITE, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> VALKYRIUM_HAMMER = ITEMS.register("valkyrium_hammer", () -> new HammerItem(ModTiers.VALKYRIUM, 7, -3.2f,
            new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    // Javelins
    public static final RegistryObject<Item> WOODEN_JAVELIN = ITEMS.register("wooden_javelin", () -> new JavelinItem(Tiers.WOOD, 2.0F, -2.9F, 3.0F,
            new Item.Properties().durability(Tiers.WOOD.getUses()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> STONE_JAVELIN = ITEMS.register("stone_javelin", () -> new JavelinItem(Tiers.STONE, 2.0F, -2.9F, 3.0F,
            new Item.Properties().durability(Tiers.STONE.getUses()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> GOLDEN_JAVELIN = ITEMS.register("golden_javelin", () -> new JavelinItem(Tiers.GOLD, 2.0F, -2.9F, 3.0F,
            new Item.Properties().durability(Tiers.GOLD.getUses()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> IRON_JAVELIN = ITEMS.register("iron_javelin", () -> new JavelinItem(Tiers.IRON, 2.0F, -2.8F, 3.0F,
            new Item.Properties().durability(Tiers.IRON.getUses()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> DIAMOND_JAVELIN = ITEMS.register("diamond_javelin", () -> new JavelinItem(Tiers.DIAMOND, 2.0F, -2.7F, 3.0F,
            new Item.Properties().durability(Tiers.DIAMOND.getUses()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> NETHERITE_JAVELIN = ITEMS.register("netherite_javelin", () -> new JavelinItem(Tiers.NETHERITE, 2.0F, -2.7F, 3.0F,
            new Item.Properties().durability(Tiers.NETHERITE.getUses()).fireResistant().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> PRISMARITE_JAVELIN = ITEMS.register("prismarite_javelin", () -> new JavelinItem(ModTiers.PRISMARITE, 2.0F, -2.7F, 3.0F,
            new Item.Properties().durability(ModTiers.PRISMARITE.getUses()).tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> VALKYRIUM_JAVELIN = ITEMS.register("valkyrium_javelin", () -> new JavelinItem(ModTiers.VALKYRIUM, 2.0F, -2.7F, 3.0F,
            new Item.Properties().durability(ModTiers.VALKYRIUM.getUses()).tab(CreativeModeTab.TAB_COMBAT)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
