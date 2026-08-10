package com.baisylia.modestmining.integration.farmersdelight;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;

public class FarmersDelightCompat {
    private static final String FD_MODID = "farmersdelight";

    public static final ResourceKey<CreativeModeTab> TAB_KEY =
            ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(FD_MODID, FD_MODID));

    public static boolean isLoaded() {
        return ModList.get().isLoaded(FD_MODID);
    }

    public static void register(IEventBus eventBus) {
        if (!isLoaded()) {
            return;
        }

        try {
            FarmersDelightItems.register(eventBus);
            ModestMining.LOGGER.info("Farmer's Delight Integration: registered knives.");
        } catch (Throwable t) {
            ModestMining.LOGGER.error("Failed to register Farmer's Delight knives", t);
        }
    }
}
