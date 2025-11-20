package com.baisylia.modestmining.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

import java.util.Map;

public class PrismariteSpeedEffect extends MobEffect {
    public PrismariteSpeedEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
        this.addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "caad9384-d4b6-43eb-88e6-a35826628f7c", 0.0F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return 0.1 * (double) (amplifier + 1);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMapIn, int amplifier) {
        if (!entity.level.isClientSide) {
            int duration = entity.getEffect(ModEffects.PRISMARITE_SPEED.get()).getDuration();
            int totalDuration = entity.getPersistentData().contains("SpeedDuration") ? entity.getPersistentData().getInt("SpeedDuration") : 0;
            float amount = duration >= totalDuration / 3 ? (amplifier + 2) * 0.5F : -(amplifier + 2) * 1.25F;
            for (Map.Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
                AttributeInstance iattributeinstance = attributeMapIn.getInstance(entry.getKey());
                if (iattributeinstance != null) {
                    AttributeModifier attributemodifier = entry.getValue();
                    iattributeinstance.removeModifier(attributemodifier);
                    iattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + amplifier, amount * this.getAttributeModifierValue(amplifier, attributemodifier), attributemodifier.getOperation()));
                }
            }
        }
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        this.addAttributeModifiers(pLivingEntity, pLivingEntity.getAttributes(), pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}