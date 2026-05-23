package com.baisylia.modestmining.entity;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.entity.custom.ClamEntity;
import com.baisylia.modestmining.entity.custom.ThrownJavelinEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModestMining.MOD_ID);

    public static final RegistryObject<EntityType<ClamEntity>> CLAM =
            ENTITY_TYPES.register("clam",
                    () -> EntityType.Builder.of(ClamEntity::new, MobCategory.WATER_AMBIENT)
                            .sized(1.2f, 0.4f)
                            .build(new ResourceLocation(ModestMining.MOD_ID, "clam").toString()));

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
