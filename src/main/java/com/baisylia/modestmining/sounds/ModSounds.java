package com.baisylia.modestmining.sounds;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, ModestMining.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> FORGE_CRACKLE = SOUND_EVENTS.register("oven_crackle",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "block.forge.crackle")));

    public static final DeferredHolder<SoundEvent, SoundEvent> JAVELIN_THROW = SOUND_EVENTS.register("javelin_throw",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.javelin.throw")));

    public static final DeferredHolder<SoundEvent, SoundEvent> JAVELIN_THROW_CRUDE = SOUND_EVENTS.register("javelin_throw_crude",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.javelin.throw_crude")));

    public static final DeferredHolder<SoundEvent, SoundEvent> JAVELIN_HIT_GROUND = SOUND_EVENTS.register("javelin_hit_ground",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.javelin.hit_ground")));

    public static final DeferredHolder<SoundEvent, SoundEvent> JAVELIN_HIT_GROUND_CRUDE = SOUND_EVENTS.register("javelin_hit_ground_crude",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.javelin.hit_ground_crude")));

    public static final DeferredHolder<SoundEvent, SoundEvent> JAVELIN_HIT = SOUND_EVENTS.register("javelin_hit",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.javelin.hit")));

    public static final DeferredHolder<SoundEvent, SoundEvent> JAVELIN_RETURN = SOUND_EVENTS.register("javelin_return",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.javelin.return")));

    public static final DeferredHolder<SoundEvent, SoundEvent> CRITICAL_PIERCE = SOUND_EVENTS.register("critical_pierce",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "item.critical_pierce")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}