package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.attribute.ModAttributes;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.block.renderer.MillstoneRenderer;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.entity.renderer.ThrownJavelinRenderer;
import com.baisylia.modestmining.integration.ReliableRecipesCompat;
import com.baisylia.modestmining.integration.ReliableRemoverCompat;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

@EventBusSubscriber(modid = ModestMining.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.MILLSTONE_BLOCK_ENTITY.get(),
                MillstoneRenderer::new
        );

        event.registerEntityRenderer(
                ModEntityTypes.THROWN_JAVELIN.get(),
                ThrownJavelinRenderer::new
        );
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ReliableRemoverCompat.applyBlacklist();
            ReliableRecipesCompat.apply();
        });
    }

    @SubscribeEvent
    public static void entityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.MAGIC_RESISTANCE);
    }
}
