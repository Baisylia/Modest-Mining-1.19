package com.baisylia.modestmining.integration;

import com.baisylia.modestmining.ModestMining;
import net.minecraftforge.fml.ModList;

public class ItemObliteratorCompat {
    private static final String IO_MODID = "item_obliterator";

    public static void applyBlacklist() {
        if (ModList.get().isLoaded(IO_MODID)) {
            try {
                ItemObliteratorCompatImpl.apply();
            } catch (Throwable t) {
                ModestMining.LOGGER.error("Failed to apply Item Obliterator integration", t);
            }
        }
    }
}
