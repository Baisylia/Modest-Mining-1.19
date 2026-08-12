package com.baisylia.modestmining.item.custom.tools;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.attribute.ModAttributes;
import com.baisylia.modestmining.item.ModArmourMaterials;
import com.google.common.base.Suppliers;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.function.Supplier;

public class ModArmorItem extends ArmorItem {
    private final Supplier<ItemAttributeModifiers> customAttributeModifiers;

    public ModArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
        this.customAttributeModifiers = Suppliers.memoize(() -> {
            ItemAttributeModifiers baseModifiers = super.getDefaultAttributeModifiers();
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

            for (ItemAttributeModifiers.Entry entry : baseModifiers.modifiers()) {
                builder.add(entry.attribute(), entry.modifier(), entry.slot());
            }

            EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(type.getSlot());

            if (material.equals(ModArmourMaterials.VALKYRIUM)) {
                if (type == ArmorItem.Type.BOOTS) {
                    builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "valkyrium_boots_speed"),
                            0.25D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slotGroup);
                } else if (type == ArmorItem.Type.LEGGINGS) {
                    builder.add(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "valkyrium_leggings_speed"),
                            0.25D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slotGroup);
                }
            } else if (material.equals(ModArmourMaterials.PRISMARITE)) {
                if (type == ArmorItem.Type.BOOTS) {
                    builder.add(NeoForgeMod.SWIM_SPEED, new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "prismarite_boots_swim_speed"),
                            0.50D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slotGroup);
                } else if (type == ArmorItem.Type.LEGGINGS) {
                    builder.add(NeoForgeMod.SWIM_SPEED, new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "prismarite_leggings_swim_speed"),
                            0.50D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slotGroup);
                } else if (type == ArmorItem.Type.CHESTPLATE) {
                    builder.add(ModAttributes.MAGIC_RESISTANCE, new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "prismarite_chestplate_magic_resistance"),
                            0.25D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slotGroup);
                } else if (type == ArmorItem.Type.HELMET) {
                    builder.add(ModAttributes.MAGIC_RESISTANCE, new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "prismarite_helmet_magic_resistance"),
                            0.25D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slotGroup);
                }
            }

            return builder.build();
        });
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return this.customAttributeModifiers.get();
    }
}
