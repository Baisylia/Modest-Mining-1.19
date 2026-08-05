package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.attribute.ModAttributes;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class ModArmorAttributeEvents {
    private static final ResourceLocation SWIM_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "swim_speed");
    private static final ResourceLocation MAGIC_RESISTANCE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "magic_resistance");
    private static final ResourceLocation MOVEMENT_SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "movement_speed");

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) {
            return;
        }

        boolean prismariteBoots = player.getInventory().getArmor(0).getItem() == ModItems.PRISMARITE_BOOTS.get();
        boolean prismariteLeggings = player.getInventory().getArmor(1).getItem() == ModItems.PRISMARITE_LEGGINGS.get();
        boolean prismariteChestplate = player.getInventory().getArmor(2).getItem() == ModItems.PRISMARITE_CHESTPLATE.get();
        boolean prismariteHelmet = player.getInventory().getArmor(3).getItem() == ModItems.PRISMARITE_HELMET.get();

        applyScaledModifier(player, NeoForgeMod.SWIM_SPEED, SWIM_SPEED_MODIFIER_ID,
                prismariteBoots, prismariteLeggings, 0.6D, 1.0D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        applyScaledModifier(player, ModAttributes.MAGIC_RESISTANCE, MAGIC_RESISTANCE_MODIFIER_ID,
                prismariteChestplate, prismariteHelmet, 0.25D, 0.5D, AttributeModifier.Operation.ADD_VALUE);

        boolean valkyriumBoots = player.getInventory().getArmor(0).getItem() == ModItems.VALKYRIUM_BOOTS.get();
        boolean valkyriumLeggings = player.getInventory().getArmor(1).getItem() == ModItems.VALKYRIUM_LEGGINGS.get();

        applyScaledModifier(player, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER_ID,
                valkyriumBoots, valkyriumLeggings, 0.3D, 0.5D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    private static void applyScaledModifier(Player player, Holder<Attribute> attribute, ResourceLocation modifierId,
                                            boolean onePieceWorn, boolean otherPieceWorn,
                                            double singlePieceAmount, double fullSetAmount,
                                            AttributeModifier.Operation operation) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance == null) {
            return;
        }

        double amount;
        if (onePieceWorn && otherPieceWorn) {
            amount = fullSetAmount;
        } else if (onePieceWorn || otherPieceWorn) {
            amount = singlePieceAmount;
        } else {
            amount = 0.0D;
        }

        AttributeModifier existing = instance.getModifier(modifierId);
        if (existing != null && existing.amount() == amount) {
            return;
        }

        instance.removeModifier(modifierId);
        if (amount != 0.0D) {
            instance.addPermanentModifier(new AttributeModifier(modifierId, amount, operation));
        }
    }
}
