package com.baisylia.modestmining.entity;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModestMining.MOD_ID);

    public static final RegistryObject<EntityType<ThrownJavelinEntity>> THROWN_JAVELIN =
            ENTITY_TYPES.register("thrown_javelin",
                    () -> EntityType.Builder.<ThrownJavelinEntity>of(ThrownJavelinEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("thrown_javelin"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
