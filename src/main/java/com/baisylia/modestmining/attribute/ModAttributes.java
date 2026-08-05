package com.baisylia.modestmining.attribute;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES
            = DeferredRegister.create(Registries.ATTRIBUTE, ModestMining.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> MAGIC_RESISTANCE = ATTRIBUTES.register("magic_resistance",
            () -> new RangedAttribute("attribute.name.modestmining.magic_resistance", 0.0D, 0.0D, 1.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
