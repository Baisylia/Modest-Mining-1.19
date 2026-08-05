package com.baisylia.modestmining.integration;

import com.baisylia.modestmining.ModestMining;
import net.neoforged.fml.ModList;

public class ReliableRemoverCompat {
    private static final String RREMOVER_MODID = "reliable_remover";

    public static void applyBlacklist() {
        if (ModList.get().isLoaded(RREMOVER_MODID)) {
            try {
                ReliableRemoverCompatImpl.apply();
            } catch (Throwable t) {
                ModestMining.LOGGER.error("Failed to apply Reliable Remover integration", t);
            }
        }
    }
}
