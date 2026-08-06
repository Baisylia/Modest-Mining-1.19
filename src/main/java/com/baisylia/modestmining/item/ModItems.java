package com.baisylia.modestmining.item;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.item.custom.tools.ChiselItem;
import com.baisylia.modestmining.item.custom.tools.ModArmorItem;
import com.baisylia.modestmining.item.custom.weapons.HammerItem;
import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, ModestMining.MOD_ID);

    // Materials
    public static final DeferredHolder<Item, Item> COKE = ITEMS.register("coke", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 6400;
        }
    });

    public static final DeferredHolder<Item, Item> COAL_CHUNK = ITEMS.register("coal_chunk", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final DeferredHolder<Item, Item> CHARCOAL_CHUNK = ITEMS.register("charcoal_chunk", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 200;
        }
    });
    public static final DeferredHolder<Item, Item> COKE_CHUNK = ITEMS.register("coke_chunk", () -> new Item(new Item.Properties()) {
        @Override
        public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
            return 800;
        }
    });
    public static final DeferredHolder<Item, Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIAMOND_SHARD = ITEMS.register("diamond_shard", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ALUMINIUM_INGOT = ITEMS.register("aluminium_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ALUMINIUM_NUGGET = ITEMS.register("aluminium_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAW_ALUMINIUM = ITEMS.register("raw_aluminium", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ALUMINIUM_DUST = ITEMS.register("aluminium_dust", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEAD_INGOT = ITEMS.register("lead_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEAD_NUGGET = ITEMS.register("lead_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAW_LEAD = ITEMS.register("raw_lead", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEAD_DUST = ITEMS.register("lead_dust", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SILVER_DUST = ITEMS.register("silver_dust", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_INGOT = ITEMS.register("steel_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_NUGGET = ITEMS.register("steel_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ROSEGOLD_INGOT = ITEMS.register("rosegold_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ROSEGOLD_NUGGET = ITEMS.register("rosegold_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_INGOT = ITEMS.register("bronze_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_NUGGET = ITEMS.register("bronze_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ELECTRUM_NUGGET = ITEMS.register("electrum_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_INGOT = ITEMS.register("prismarite_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_NUGGET = ITEMS.register("prismarite_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_INGOT = ITEMS.register("valkyrium_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_NUGGET = ITEMS.register("valkyrium_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_SCREW = ITEMS.register("copper_screw", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> PEARL = ITEMS.register("pearl", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLESH = ITEMS.register("flesh", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> AMETHYST = ITEMS.register("amethyst", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_DUST = ITEMS.register("copper_dust", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> GOLD_DUST = ITEMS.register("gold_dust", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> IRON_DUST = ITEMS.register("iron_dust", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DEBRIS_DUST = ITEMS.register("debris_dust", () -> new Item(new Item.Properties()));

    // TOOLS
    public static final DeferredHolder<Item, Item> CHISEL = ITEMS.register("chisel", () -> new ChiselItem(0f, 0f, ModTiers.COPPER,
            new Item.Properties().durability(450)));

    // Flint
    public static final DeferredHolder<Item, Item> FLINT_BLADE = ITEMS.register("flint_blade", () -> new SwordItem(ModTiers.FLINT, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLINT_HATCHET = ITEMS.register("flint_hatchet", () -> new AxeItem(ModTiers.FLINT, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLINT_MATTOCK = ITEMS.register("flint_mattock", () -> new PickaxeItem(ModTiers.FLINT, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLINT_SPADE = ITEMS.register("flint_spade", () -> new ShovelItem(ModTiers.FLINT, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FLINT_HOE = ITEMS.register("flint_hoe", () -> new HoeItem(ModTiers.FLINT, new Item.Properties()));

    // Bronze
    public static final DeferredHolder<Item, Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> new SwordItem(ModTiers.BRONZE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(ModTiers.BRONZE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new PickaxeItem(ModTiers.BRONZE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(ModTiers.BRONZE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(ModTiers.BRONZE, new Item.Properties()));

    public static final DeferredHolder<Item, Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(ModArmourMaterials.BRONZE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(ModArmourMaterials.BRONZE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(ModArmourMaterials.BRONZE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(ModArmourMaterials.BRONZE, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Steel
    public static final DeferredHolder<Item, Item> STEEL_SWORD = ITEMS.register("steel_sword", () -> new SwordItem(ModTiers.STEEL, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_AXE = ITEMS.register("steel_axe", () -> new AxeItem(ModTiers.STEEL, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe", () -> new PickaxeItem(ModTiers.STEEL, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_SHOVEL = ITEMS.register("steel_shovel", () -> new ShovelItem(ModTiers.STEEL, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_HOE = ITEMS.register("steel_hoe", () -> new HoeItem(ModTiers.STEEL, new Item.Properties()));

    public static final DeferredHolder<Item, Item> STEEL_HELMET = ITEMS.register("steel_helmet", () -> new ArmorItem(ModArmourMaterials.STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_CHESTPLATE = ITEMS.register("steel_chestplate", () -> new ArmorItem(ModArmourMaterials.STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_LEGGINGS = ITEMS.register("steel_leggings", () -> new ArmorItem(ModArmourMaterials.STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STEEL_BOOTS = ITEMS.register("steel_boots", () -> new ArmorItem(ModArmourMaterials.STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Prismarite
    public static final DeferredHolder<Item, Item> PRISMARITE_SWORD = ITEMS.register("prismarite_sword", () -> new SwordItem(ModTiers.PRISMARITE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_AXE = ITEMS.register("prismarite_axe", () -> new AxeItem(ModTiers.PRISMARITE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_PICKAXE = ITEMS.register("prismarite_pickaxe", () -> new PickaxeItem(ModTiers.PRISMARITE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_SHOVEL = ITEMS.register("prismarite_shovel", () -> new ShovelItem(ModTiers.PRISMARITE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_HOE = ITEMS.register("prismarite_hoe", () -> new HoeItem(ModTiers.PRISMARITE, new Item.Properties()));

    public static final DeferredHolder<Item, Item> PRISMARITE_HELMET = ITEMS.register("prismarite_helmet", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_CHESTPLATE = ITEMS.register("prismarite_chestplate", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_LEGGINGS = ITEMS.register("prismarite_leggings", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_BOOTS = ITEMS.register("prismarite_boots", () -> new ModArmorItem(ModArmourMaterials.PRISMARITE, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Valkyrium
    public static final DeferredHolder<Item, Item> VALKYRIUM_SWORD = ITEMS.register("valkyrium_sword", () -> new SwordItem(ModTiers.VALKYRIUM, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_AXE = ITEMS.register("valkyrium_axe", () -> new AxeItem(ModTiers.VALKYRIUM, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_PICKAXE = ITEMS.register("valkyrium_pickaxe", () -> new PickaxeItem(ModTiers.VALKYRIUM, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_SHOVEL = ITEMS.register("valkyrium_shovel", () -> new ShovelItem(ModTiers.VALKYRIUM, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_HOE = ITEMS.register("valkyrium_hoe", () -> new HoeItem(ModTiers.VALKYRIUM, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_HELMET = ITEMS.register("valkyrium_helmet", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_CHESTPLATE = ITEMS.register("valkyrium_chestplate", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_LEGGINGS = ITEMS.register("valkyrium_leggings", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_BOOTS = ITEMS.register("valkyrium_boots", () -> new ModArmorItem(ModArmourMaterials.VALKYRIUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    // Hammers
    public static final DeferredHolder<Item, Item> WOODEN_HAMMER = ITEMS.register("wooden_hammer", () -> new HammerItem(Tiers.WOOD, 7, -3.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STONE_HAMMER = ITEMS.register("stone_hammer", () -> new HammerItem(Tiers.STONE, 7, -3.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> IRON_HAMMER = ITEMS.register("iron_hammer", () -> new HammerItem(Tiers.IRON, 7, -3.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GOLDEN_HAMMER = ITEMS.register("golden_hammer", () -> new HammerItem(Tiers.GOLD, 7, -3.3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer", () -> new HammerItem(Tiers.DIAMOND, 7, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer", () -> new HammerItem(Tiers.NETHERITE, 7, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PRISMARITE_HAMMER = ITEMS.register("prismarite_hammer", () -> new HammerItem(ModTiers.PRISMARITE, 7, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_HAMMER = ITEMS.register("valkyrium_hammer", () -> new HammerItem(ModTiers.VALKYRIUM, 7, -3.2f, new Item.Properties()));

    // Javelins
    public static final DeferredHolder<Item, Item> WOODEN_JAVELIN = ITEMS.register("wooden_javelin", () -> new JavelinItem(Tiers.WOOD, 2.0F, -2.9F, 3.0F, new Item.Properties()));
    public static final DeferredHolder<Item, Item> STONE_JAVELIN = ITEMS.register("stone_javelin", () -> new JavelinItem(Tiers.STONE, 2.0F, -2.9F, 3.0F, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GOLDEN_JAVELIN = ITEMS.register("golden_javelin", () -> new JavelinItem(Tiers.GOLD, 2.0F, -2.9F, 3.0F, new Item.Properties()));
    public static final DeferredHolder<Item, Item> IRON_JAVELIN = ITEMS.register("iron_javelin", () -> new JavelinItem(Tiers.IRON, 2.0F, -2.8F, 3.0F, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIAMOND_JAVELIN = ITEMS.register("diamond_javelin", () -> new JavelinItem(Tiers.DIAMOND, 2.0F, -2.7F, 3.0F, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERITE_JAVELIN = ITEMS.register("netherite_javelin", () -> new JavelinItem(Tiers.NETHERITE, 2.0F, -2.7F, 3.0F, new Item.Properties().fireResistant()));
    public static final DeferredHolder<Item, Item> PRISMARITE_JAVELIN = ITEMS.register("prismarite_javelin", () -> new JavelinItem(ModTiers.PRISMARITE, 2.0F, -2.7F, 3.0F, new Item.Properties()));
    public static final DeferredHolder<Item, Item> VALKYRIUM_JAVELIN = ITEMS.register("valkyrium_javelin", () -> new JavelinItem(ModTiers.VALKYRIUM, 2.0F, -2.7F, 3.0F, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
