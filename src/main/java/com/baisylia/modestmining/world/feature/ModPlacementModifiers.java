package com.baisylia.modestmining.world.feature;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, ModestMining.MOD_ID);

    public static final RegistryObject<PlacementModifierType<ConfigPlacementFilter>> CONFIG_FILTER =
            PLACEMENT_MODIFIERS.register("config_filter", () -> () -> ConfigPlacementFilter.CODEC);

    public static void register(IEventBus eventBus) {
        PLACEMENT_MODIFIERS.register(eventBus);
    }
}
