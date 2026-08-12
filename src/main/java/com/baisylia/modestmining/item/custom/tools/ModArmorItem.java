package com.baisylia.modestmining.item.custom.tools;

import com.baisylia.modestmining.item.ModArmourMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ModArmorItem extends ArmorItem {
    public ModArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties settings) {
        super(material, type, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (this.getMaterial().equals(ModArmourMaterials.VALKYRIUM)) {
            if (this.getType() == ArmorItem.Type.BOOTS || this.getType() == ArmorItem.Type.LEGGINGS) {
                tooltipComponents.add(Component.translatable("tooltip.modestmining.valkyrium_speed").withStyle(ChatFormatting.BLUE));
            }
        } else if (this.getMaterial().equals(ModArmourMaterials.PRISMARITE)) {
            if (this.getType() == ArmorItem.Type.BOOTS || this.getType() == ArmorItem.Type.LEGGINGS) {
                tooltipComponents.add(Component.translatable("tooltip.modestmining.prismarite_swim_speed").withStyle(ChatFormatting.BLUE));
            } else if (this.getType() == ArmorItem.Type.CHESTPLATE || this.getType() == ArmorItem.Type.HELMET) {
                tooltipComponents.add(Component.translatable("tooltip.modestmining.prismarite_magic_resistance").withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
