package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.attribute.ModAttributes;
import com.baisylia.modestmining.item.ModItems;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ModestMining.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModArmorAttributeEvents {
    private static final UUID SWIM_SPEED_MODIFIER_ID = UUID.fromString("caad9384-d4b6-43eb-88e6-a35826628f7c");
    private static final UUID MAGIC_RESISTANCE_MODIFIER_ID = UUID.fromString("d4689749-14ee-4445-b496-9d39f1c23f33");
    private static final UUID MOVEMENT_SPEED_MODIFIER_ID = UUID.fromString("7a3aa1e4-4b40-4d6a-9b8f-2a1a1e2c8b2d");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level.isClientSide()) {
            return;
        }

        Player player = event.player;

        boolean prismariteBoots = player.getInventory().getArmor(0).getItem() == ModItems.PRISMARITE_BOOTS.get();
        boolean prismariteLeggings = player.getInventory().getArmor(1).getItem() == ModItems.PRISMARITE_LEGGINGS.get();
        boolean prismariteChestplate = player.getInventory().getArmor(2).getItem() == ModItems.PRISMARITE_CHESTPLATE.get();
        boolean prismariteHelmet = player.getInventory().getArmor(3).getItem() == ModItems.PRISMARITE_HELMET.get();

        applyScaledModifier(player, ForgeMod.SWIM_SPEED.get(), SWIM_SPEED_MODIFIER_ID,
                prismariteBoots, prismariteLeggings, 0.6D, 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        applyScaledModifier(player, ModAttributes.MAGIC_RESISTANCE.get(), MAGIC_RESISTANCE_MODIFIER_ID,
                prismariteChestplate, prismariteHelmet, 0.25D, 0.5D, AttributeModifier.Operation.ADDITION);

        boolean valkyriumBoots = player.getInventory().getArmor(0).getItem() == ModItems.VALKYRIUM_BOOTS.get();
        boolean valkyriumLeggings = player.getInventory().getArmor(1).getItem() == ModItems.VALKYRIUM_LEGGINGS.get();

        applyScaledModifier(player, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED_MODIFIER_ID,
                valkyriumBoots, valkyriumLeggings, 0.3D, 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private static void applyScaledModifier(Player player, Attribute attribute, UUID modifierId,
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
        if (existing != null && existing.getAmount() == amount) {
            return;
        }

        instance.removeModifier(modifierId);
        if (amount != 0.0D) {
            instance.addPermanentModifier(new AttributeModifier(modifierId, "Armor modifier", amount, operation));
        }
    }
}
