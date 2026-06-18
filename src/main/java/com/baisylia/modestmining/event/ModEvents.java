package com.baisylia.modestmining.event;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.entity.ModBlockEntities;
import com.baisylia.modestmining.block.renderer.MillstoneRenderer;
import com.baisylia.modestmining.entity.ModEntityTypes;
import com.baisylia.modestmining.entity.custom.ClamEntity;
import com.baisylia.modestmining.entity.renderer.ThrownJavelinRenderer;
import com.baisylia.modestmining.integration.ItemObliteratorCompat;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModestMining.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.MILLSTONE_BLOCK_ENTITY.get(),
                MillstoneRenderer::new
        );

        EntityRenderers.register(
                ModEntityTypes.THROWN_JAVELIN.get(),
                ThrownJavelinRenderer::new
        );
    }

    @Mod.EventBusSubscriber(modid = ModestMining.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntityTypes.CLAM.get(), ClamEntity.setAttributes());
        }
    }

    @Mod.EventBusSubscriber(modid = ModestMining.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onServerStarting(ServerStartingEvent event) {
            ItemObliteratorCompat.applyBlacklist();
        }
    }
}
