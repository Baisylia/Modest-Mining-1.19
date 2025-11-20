package com.baisylia.modestmining.item.custom.tools;

import com.baisylia.modestmining.effect.ModEffects;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModArmorItem extends ArmorItem {
    public ModArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties settings) {
        super(material, slot, settings);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        if(!world.isClientSide()) {
            if(player.isInWater()) {
                //Boots and Leggings
                if (player.getInventory().getArmor(0).getItem() == ModItems.PRISMARITE_BOOTS.get()
                        || player.getInventory().getArmor(1).getItem() == ModItems.PRISMARITE_LEGGINGS.get()) {
                    //Both
                    if (player.getInventory().getArmor(0).getItem() == ModItems.PRISMARITE_BOOTS.get()
                            && player.getInventory().getArmor(1).getItem() == ModItems.PRISMARITE_LEGGINGS.get()) {
                        player.addEffect(new MobEffectInstance(ModEffects.PRISMARITE_SPEED.get(), 20, 3, false, false));
                    }
                    //One
                    else {
                        player.addEffect(new MobEffectInstance(ModEffects.PRISMARITE_SPEED.get(), 20, 2, false, false));
                    }
                }
                //Chestplate and Helmet
                if (player.getInventory().getArmor(2).getItem() == ModItems.PRISMARITE_CHESTPLATE.get()
                        || player.getInventory().getArmor(3).getItem() == ModItems.PRISMARITE_HELMET.get()) {
                    //Both
                    if (player.getInventory().getArmor(2).getItem() == ModItems.PRISMARITE_CHESTPLATE.get()
                            && player.getInventory().getArmor(3).getItem() == ModItems.PRISMARITE_HELMET.get()) {

                        player.addEffect(new MobEffectInstance(ModEffects.PRISMARITE_STRENGTH.get(), 20, 2, false, false));
                    }
                    //One
                    else {
                        player.addEffect(new MobEffectInstance(ModEffects.PRISMARITE_STRENGTH.get(), 20, 1, false, false));
                    }

                }
            }
        }
    }
}