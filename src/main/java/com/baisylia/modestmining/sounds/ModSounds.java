package com.baisylia.modestmining.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.baisylia.modestmining.ModestMining.MOD_ID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static final RegistryObject<SoundEvent> FORGE_CRACKLE = SOUND_EVENTS.register("oven_crackle",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "block.forge.crackle")));

    public static final RegistryObject<SoundEvent> JAVELIN_THROW = SOUND_EVENTS.register("javelin_throw",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.javelin.throw")));

    public static final RegistryObject<SoundEvent> JAVELIN_THROW_CRUDE = SOUND_EVENTS.register("javelin_throw_crude",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.javelin.throw_crude")));

    public static final RegistryObject<SoundEvent> JAVELIN_HIT_GROUND = SOUND_EVENTS.register("javelin_hit_ground",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.javelin.hit_ground")));

    public static final RegistryObject<SoundEvent> JAVELIN_HIT_GROUND_CRUDE = SOUND_EVENTS.register("javelin_hit_ground_crude",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.javelin.hit_ground_crude")));

    public static final RegistryObject<SoundEvent> JAVELIN_HIT = SOUND_EVENTS.register("javelin_hit",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.javelin.hit")));

    public static final RegistryObject<SoundEvent> JAVELIN_RETURN = SOUND_EVENTS.register("javelin_return",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.javelin.return")));

    public static final RegistryObject<SoundEvent> CRITICAL_PIERCE = SOUND_EVENTS.register("critical_pierce",
            () -> new SoundEvent(new ResourceLocation(MOD_ID, "item.critical_pierce")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}