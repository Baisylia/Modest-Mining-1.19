package com.baisylia.modestmining.attribute;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES
            = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ModestMining.MOD_ID);

    public static final RegistryObject<Attribute> MAGIC_RESISTANCE = ATTRIBUTES.register("magic_resistance",
            () -> new RangedAttribute("attribute.name.modestmining.magic_resistance", 0.0D, 0.0D, 1.0D).setSyncable(true));

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
