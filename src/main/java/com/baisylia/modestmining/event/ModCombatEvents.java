package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.attribute.ModAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModestMining.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCombatEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (!event.getSource().isMagic()) {
            return;
        }

        LivingEntity entity = event.getEntity();
        AttributeInstance magicResistance = entity.getAttribute(ModAttributes.MAGIC_RESISTANCE.get());
        if (magicResistance == null) {
            return;
        }

        float resistance = (float) magicResistance.getValue();
        event.setAmount(event.getAmount() * (1.0F - resistance));
    }
}
