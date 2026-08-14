package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import com.baisylia.modestmining.block.ModBlocks;
import com.baisylia.modestmining.item.ModItems;
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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ForgeFuelManager extends SimpleJsonResourceReloadListener {
    public static final ForgeFuelManager INSTANCE = new ForgeFuelManager();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final List<FuelEntry> REGISTERED_FUELS = new ArrayList<>();
    private static final Map<Item, FuelInfo> ITEM_CACHE = new ConcurrentHashMap<>();
    private static final Map<Integer, List<ItemStack>> TIER_FUEL_CACHE = new ConcurrentHashMap<>();

    public ForgeFuelManager() {
        super(GSON, "forge_fuels");
        registerDefaults();
    }

    private static void registerDefaults() {
        REGISTERED_FUELS.clear();
        ITEM_CACHE.clear();
        TIER_FUEL_CACHE.clear();

        REGISTERED_FUELS.add(new FuelEntry(Ingredient.of(ModItems.COKE.get()), 1, 6400));
        REGISTERED_FUELS.add(new FuelEntry(Ingredient.of(ModItems.COKE_CHUNK.get()), 1, 800));
        REGISTERED_FUELS.add(new FuelEntry(Ingredient.of(ModBlocks.COKE_BLOCK.get()), 1, 57600));
        REGISTERED_FUELS.add(new FuelEntry(Ingredient.of(Items.BLAZE_POWDER), 1, 1200));
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

        int tier = GsonHelper.getAsInt(json, "tier", 0);
        int burnTime = GsonHelper.getAsInt(json, "burn_time", 0);

        REGISTERED_FUELS.add(new FuelEntry(ingredient, tier, burnTime));
    }

    @Nullable
    public static FuelInfo getFuelInfo(ItemStack stack) {
        if (stack.isEmpty()) return null;

        Item item = stack.getItem();
        if (ITEM_CACHE.containsKey(item)) {
            FuelInfo cached = ITEM_CACHE.get(item);
            if (cached != null) return cached;
        }

        FuelInfo bestMatch = null;

        for (FuelEntry entry : REGISTERED_FUELS) {
            if (entry.ingredient.test(stack)) {
                int burnTime = entry.burnTime > 0 ? entry.burnTime : ForgeHooks.getBurnTime(stack, RecipeType.BLASTING);
                if (bestMatch == null || entry.tier > bestMatch.tier) {
                    bestMatch = new FuelInfo(entry.tier, burnTime);
                }
            }
        }

        if (bestMatch == null) {
            int furnaceBurnTime = ForgeHooks.getBurnTime(stack, RecipeType.BLASTING);
            if (furnaceBurnTime > 0) {
                bestMatch = new FuelInfo(0, furnaceBurnTime);
            }
        }

        if (bestMatch != null) {
            ITEM_CACHE.put(item, bestMatch);
        }
        return bestMatch;
    }

    public static boolean isFuel(ItemStack stack) {
        return getFuelInfo(stack) != null;
    }

    public static int getFuelTier(ItemStack stack) {
        FuelInfo info = getFuelInfo(stack);
        return info != null ? info.tier : -1;
    }

    public static int getBurnTime(ItemStack stack) {
        FuelInfo info = getFuelInfo(stack);
        return info != null ? info.burnTime : 0;
    }

    public static List<ItemStack> getFuelsForTier(int minTier) {
        if (TIER_FUEL_CACHE.containsKey(minTier)) {
            return TIER_FUEL_CACHE.get(minTier);
        }

        Set<Item> seenItems = new LinkedHashSet<>();
        List<ItemStack> result = new ArrayList<>();

        for (FuelEntry entry : REGISTERED_FUELS) {
            for (ItemStack stack : entry.ingredient.getItems()) {
                FuelInfo info = getFuelInfo(stack);
                if (info != null && info.tier >= minTier) {
                    if (seenItems.add(stack.getItem())) {
                        result.add(stack.copy());
                    }
                }
            }
        }

        List<ItemStack> unmodifiable = Collections.unmodifiableList(result);
        TIER_FUEL_CACHE.put(minTier, unmodifiable);
        return unmodifiable;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        REGISTERED_FUELS.clear();
        ITEM_CACHE.clear();
        TIER_FUEL_CACHE.clear();

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
                ModestMining.LOGGER.error("Error parsing forge fuel definition from {}", id, e);
            }
        }

        if (REGISTERED_FUELS.isEmpty()) {
            registerDefaults();
        }

        ModestMining.LOGGER.info("Loaded {} forge fuel definitions", REGISTERED_FUELS.size());
    }

    public record FuelInfo(int tier, int burnTime) {
    }

    public record FuelEntry(Ingredient ingredient, int tier, int burnTime) {
    }
}
