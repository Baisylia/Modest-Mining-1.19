package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.attribute.ModAttributes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class ModCombatEvents {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (!event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            return;
        }

        LivingEntity entity = event.getEntity();
        AttributeInstance magicResistance = entity.getAttribute(ModAttributes.MAGIC_RESISTANCE);
        if (magicResistance == null) {
            return;
        }

        float resistance = (float) magicResistance.getValue();
        event.setNewDamage(event.getOriginalDamage() * (1.0F - resistance));
    }
}
