package com.baisylia.modestmining.integration.everycompat;

import com.baisylia.modestmining.ModestMining;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;

public class EveryCompatIntegration {
    public static void register() {
        EveryCompatAPI.registerModule(new MMEveryCompatModule(ModestMining.MOD_ID));
    }
}
