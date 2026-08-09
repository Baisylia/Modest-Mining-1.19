package com.baisylia.modestmining.integration.farmersdelight;

import com.baisylia.modestmining.ModestMining;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class FarmersDelightCompat {
    public static final String FD_MODID = "farmersdelight";

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
