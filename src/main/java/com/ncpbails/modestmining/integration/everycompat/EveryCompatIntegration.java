package com.ncpbails.modestmining.integration.everycompat;

import com.ncpbails.modestmining.ModestMining;
import net.mehvahdjukaar.every_compat.api.EveryCompatAPI;

public class EveryCompatIntegration {
    public static void register() {
        EveryCompatAPI.registerModule(new MMEveryCompatModule(ModestMining.MOD_ID));
    }
}
