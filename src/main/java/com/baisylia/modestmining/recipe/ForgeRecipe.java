package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.block.entity.custom.ForgeBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ForgeRecipe extends AbstractForgeRecipe {

    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;

    public ForgeRecipe(String group, ForgingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, Optional<Ingredient> fuel, int cookTime) {
        super(group, category, output, recipeItems, fuel, cookTime);
        this.output = output;
        this.recipeItems = recipeItems;
        this.cookTime = cookTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean matches(ForgeBlockEntity.SingleRecipeInputContainer input, Level level) {
        int ingredientCount = 0;
        for (int j = 0; j < 9; ++j) {
            ItemStack stack = input.getItem(j);
            if (!stack.isEmpty()) {
                ingredientCount++;
            }
        }
        if (ingredientCount != this.recipeItems.size()) {
            return false;
        }

        for (Ingredient ingredient : recipeItems) {
            boolean matched = false;
            for (int j = 0; j < 9; ++j) {
                if (ingredient.test(input.getItem(j))) {
                    matched = true;
                    break;
                }
            }
            if (!matched) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(ForgeBlockEntity.SingleRecipeInputContainer input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FORGING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        private static final MapCodec<ForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(ForgeRecipe::getGroup),
                ForgingBookCategory.CODEC.optionalFieldOf("category", ForgingBookCategory.MISC).forGetter(ForgeRecipe::getCategory),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.recipeItems),
                Ingredient.CODEC_NONEMPTY.optionalFieldOf("fuel").forGetter(ForgeRecipe::getFuel),
                Codec.INT.optionalFieldOf("cooktime", 200).forGetter(ForgeRecipe::getCookTime)
        ).apply(instance, (group, category, result, ingredients, fuel, cookTime) ->
                new ForgeRecipe(group, category, result, NonNullList.copyOf(ingredients), fuel, cookTime)));

        private static final StreamCodec<RegistryFriendlyByteBuf, ForgeRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    buf.writeUtf(recipe.group);
                    buf.writeEnum(recipe.category);
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output);
                    buf.writeVarInt(recipe.recipeItems.size());
                    for (Ingredient ing : recipe.recipeItems) {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
                    }
                    FUEL_STREAM_CODEC.encode(buf, recipe.getFuel());
                    buf.writeVarInt(recipe.cookTime);
                },
                buf -> {
                    String group = buf.readUtf();
                    ForgingBookCategory category = buf.readEnum(ForgingBookCategory.class);
                    ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                    int size = buf.readVarInt();
                    NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
                    for (int i = 0; i < size; i++) {
                        ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                    }
                    Optional<Ingredient> fuel = FUEL_STREAM_CODEC.decode(buf);
                    int cookTime = buf.readVarInt();
                    return new ForgeRecipe(group, category, output, ingredients, fuel, cookTime);
                }
        );

        @Override
        public MapCodec<ForgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ForgeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}