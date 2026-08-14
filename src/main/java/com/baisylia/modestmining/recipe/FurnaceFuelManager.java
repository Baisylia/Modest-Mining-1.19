package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ModestMining.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FurnaceFuelManager extends SimpleJsonResourceReloadListener {
    public static final FurnaceFuelManager INSTANCE = new FurnaceFuelManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final List<FurnaceFuelEntry> REGISTERED_FUELS = new ArrayList<>();
    private static final Map<Item, Integer> ITEM_CACHE = new ConcurrentHashMap<>();

    public FurnaceFuelManager() {
        super(GSON, "furnace_fuels");
    }

    private static void parseSingleEntry(JsonObject json) {
        Ingredient ingredient;
        if (json.has("ingredient")) {
            ingredient = Ingredient.fromJson(json.get("ingredient"));
        } else if (json.has("item")) {
            ResourceLocation itemId = new ResourceLocation(GsonHelper.getAsString(json, "item"));
            Item item = Registry.ITEM.get(itemId);
            if (item == Items.AIR) {
                return;
            }
            ingredient = Ingredient.of(item);
        } else if (json.has("tag")) {
            ResourceLocation tagId = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
            TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, tagId);
            ingredient = Ingredient.of(tagKey);
        } else {
            return;
        }

        int burnTime = GsonHelper.getAsInt(json, "burn_time", 0);
        if (burnTime > 0) {
            REGISTERED_FUELS.add(new FurnaceFuelEntry(ingredient, burnTime));
        }
    }

    public static int getBurnTime(ItemStack stack) {
        if (stack.isEmpty()) return -1;

        Item item = stack.getItem();
        if (ITEM_CACHE.containsKey(item)) {
            return ITEM_CACHE.get(item);
        }

        int result = -1;
        for (FurnaceFuelEntry entry : REGISTERED_FUELS) {
            if (entry.ingredient.test(stack)) {
                result = entry.burnTime;
                break;
            }
        }

        ITEM_CACHE.put(item, result);
        return result;
    }

    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        int burnTime = getBurnTime(event.getItemStack());
        if (burnTime > 0) {
            event.setBurnTime(burnTime);
        }
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        REGISTERED_FUELS.clear();
        ITEM_CACHE.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement json = entry.getValue();

            try {
                if (json.isJsonObject()) {
                    JsonObject jsonObj = json.getAsJsonObject();
                    if (jsonObj.has("values")) {
                        JsonElement valuesElem = jsonObj.get("values");
                        if (valuesElem.isJsonArray()) {
                            for (JsonElement elem : valuesElem.getAsJsonArray()) {
                                parseSingleEntry(elem.getAsJsonObject());
                            }
                        } else if (valuesElem.isJsonObject()) {
                            for (Map.Entry<String, JsonElement> valEntry : valuesElem.getAsJsonObject().entrySet()) {
                                JsonObject valObj = valEntry.getValue().getAsJsonObject();
                                if (!valObj.has("item") && !valObj.has("ingredient") && !valObj.has("tag")) {
                                    valObj.addProperty("item", valEntry.getKey());
                                }
                                parseSingleEntry(valObj);
                            }
                        }
                    } else if (jsonObj.has("fuels") && jsonObj.get("fuels").isJsonArray()) {
                        for (JsonElement elem : jsonObj.getAsJsonArray("fuels")) {
                            parseSingleEntry(elem.getAsJsonObject());
                        }
                    } else {
                        parseSingleEntry(jsonObj);
                    }
                } else if (json.isJsonArray()) {
                    for (JsonElement elem : json.getAsJsonArray()) {
                        if (elem.isJsonObject()) {
                            parseSingleEntry(elem.getAsJsonObject());
                        }
                    }
                }
            } catch (Exception e) {
                ModestMining.LOGGER.error("Error parsing furnace fuel definition from {}", id, e);
            }
        }

        ModestMining.LOGGER.info("Loaded {} furnace fuel definitions", REGISTERED_FUELS.size());
    }

    public record FurnaceFuelEntry(Ingredient ingredient, int burnTime) {
    }
}
