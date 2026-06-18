package com.baisylia.modestmining.config;

import com.baisylia.modestmining.ModestMining;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModConditions {
    public static final ResourceLocation CONFIG_ENABLED_ID = new ResourceLocation(ModestMining.MOD_ID, "config_enabled");

    public static void register(IEventBus eventBus) {
        eventBus.addListener(ModConditions::onCommonSetup);
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        CraftingHelper.register(new ConfigEnabledSerializer());
    }

    public static class ConfigEnabledCondition implements ICondition {
        private final String feature;

        public ConfigEnabledCondition(String feature) {
            this.feature = feature;
        }

        @Override
        public ResourceLocation getID() {
            return CONFIG_ENABLED_ID;
        }

        @Override
        public boolean test(IContext context) {
            return ModConfig.evaluateCondition(feature);
        }
    }

    public static class ConfigEnabledSerializer implements IConditionSerializer<ConfigEnabledCondition> {
        @Override
        public ResourceLocation getID() {
            return CONFIG_ENABLED_ID;
        }

        @Override
        public void write(JsonObject json, ConfigEnabledCondition value) {
            json.addProperty("feature", value.feature);
        }

        @Override
        public ConfigEnabledCondition read(JsonObject json) {
            String feature = json.get("feature").getAsString();
            return new ConfigEnabledCondition(feature);
        }
    }
}