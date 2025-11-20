package com.baisylia.modestmining.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties ENCHANTED_ROSEGOLD_APPLE = (new FoodProperties.Builder()).nutrition(6).saturationMod(1.6F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 8000, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 8000, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 800, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 3000, 3), 1.0F).build();
    public static final FoodProperties ROSEGOLD_APPLE = (new FoodProperties.Builder()).nutrition(6).saturationMod(1.6F)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 1), 1.0F).build();
    public static final FoodProperties ROSEGOLD_CARROT = (new FoodProperties.Builder()).nutrition(9).saturationMod(1.6F).build();
}