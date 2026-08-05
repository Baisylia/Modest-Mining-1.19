package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, ModestMining.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ModestMining.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AbstractForgeRecipe>> FORGING_TYPE =
            TYPES.register("forging", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "forging";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ForgeRecipe>> FORGING_SERIALIZER =
            SERIALIZERS.register("forging", () -> ForgeRecipe.Serializer.INSTANCE);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ForgeShapedRecipe>> FORGING_SHAPED_SERIALIZER =
            SERIALIZERS.register("forging_shaped", () -> ForgeShapedRecipe.Serializer.INSTANCE);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AbstractMillstoneRecipe>> MILLING_TYPE =
            TYPES.register("milling", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "milling";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MillstoneRecipe>> MILLING_SERIALIZER =
            SERIALIZERS.register("milling", () -> MillstoneRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }
}
