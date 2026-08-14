package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.item.ModTiers;
import com.baisylia.modestmining.item.custom.weapons.JavelinItem;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class ModAnvilEvents {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        if (left.getItem() instanceof JavelinItem javelin) {
            ItemStack right = event.getRight();
            if (!right.isEmpty()) {
                ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(right);
                for (Holder<Enchantment> enchantment : enchantments.keySet()) {
                    if (enchantment.is(Enchantments.IMPALING)) {
                        event.setCanceled(true);
                        return;
                    }
                    if (enchantment.is(Enchantments.RIPTIDE) && javelin.getTier() != ModTiers.PRISMARITE) {
                        event.setCanceled(true);
                        return;
                    }
                    if (enchantment.is(Enchantments.CHANNELING) && javelin.getTier() != ModTiers.VALKYRIUM) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
        }
    }
}
