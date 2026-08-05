package com.baisylia.modestmining.item;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmourMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, ModestMining.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BRONZE = register("bronze",
            Map.of(ArmorItem.Type.BOOTS, 2, ArmorItem.Type.LEGGINGS, 4, ArmorItem.Type.CHESTPLATE, 5, ArmorItem.Type.HELMET, 2),
            10, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(ModItems.BRONZE_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> STEEL = register("steel",
            Map.of(ArmorItem.Type.BOOTS, 2, ArmorItem.Type.LEGGINGS, 5, ArmorItem.Type.CHESTPLATE, 7, ArmorItem.Type.HELMET, 2),
            9, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0F, 0.1F, () -> Ingredient.of(ModItems.STEEL_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PRISMARITE = register("prismarite",
            Map.of(ArmorItem.Type.BOOTS, 3, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.HELMET, 3),
            25, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(ModItems.PRISMARITE_INGOT.get()));

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> VALKYRIUM = register("valkyrium",
            Map.of(ArmorItem.Type.BOOTS, 3, ArmorItem.Type.LEGGINGS, 6, ArmorItem.Type.CHESTPLATE, 8, ArmorItem.Type.HELMET, 3),
            25, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(ModItems.VALKYRIUM_INGOT.get()));

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(
            String name,
            Map<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, name)));
        EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
        typeMap.putAll(defense);
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(typeMap, enchantmentValue, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
