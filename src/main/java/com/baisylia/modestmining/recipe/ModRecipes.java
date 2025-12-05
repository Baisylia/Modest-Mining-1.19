package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ModestMining.MOD_ID);

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModestMining.MOD_ID);


    public static final RegistryObject<RecipeType<AbstractForgeRecipe>> FORGING_TYPE =
            TYPES.register("forging", () -> new RecipeType<>() {});

    public static final RegistryObject<RecipeSerializer<ForgeRecipe>> FORGING_SERIALIZER =
            SERIALIZERS.register("forging", () -> ForgeRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<ForgeShapedRecipe>> FORGING_SHAPED_SERIALIZER =
            SERIALIZERS.register("forging_shaped", () -> ForgeShapedRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        TYPES.register(eventBus);
        SERIALIZERS.register(eventBus);
    }
}
