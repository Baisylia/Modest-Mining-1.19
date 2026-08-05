package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.block.entity.custom.ForgeBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MillstoneRecipe extends AbstractMillstoneRecipe {
    public final NonNullList<ItemStack> results;
    public final NonNullList<Float> chances;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;

    public MillstoneRecipe(String group, MillingBookCategory category, NonNullList<Ingredient> recipeItems,
                           NonNullList<ItemStack> results, NonNullList<Float> chances, int cookTime) {
        super(group, category, results.isEmpty() ? ItemStack.EMPTY : results.get(0), recipeItems, cookTime);
        this.recipeItems = recipeItems;
        this.results = results;
        this.chances = chances;
        this.cookTime = cookTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean matches(ForgeBlockEntity.SingleRecipeInputContainer input, Level level) {
        ItemStack inputStack = input.getItem(0);
        if (inputStack.isEmpty()) {
            return false;
        }
        return this.recipeItems.get(0).test(inputStack);
    }

    @Override
    public ItemStack assemble(ForgeBlockEntity.SingleRecipeInputContainer input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MILLING_TYPE.get();
    }

    private List<ChancedResult> packResults() {
        List<ChancedResult> packed = new ArrayList<>(this.results.size());
        for (int i = 0; i < this.results.size(); i++) {
            packed.add(new ChancedResult(this.results.get(i), i < this.chances.size() ? this.chances.get(i) : 1.0F));
        }
        return packed;
    }

    private record ChancedResult(ItemStack stack, float chance) {
        static final Codec<ChancedResult> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("id").forGetter(result -> result.stack.getItemHolder()),
                ExtraCodecs.intRange(1, 99).optionalFieldOf("count", 1).forGetter(result -> result.stack.getCount()),
                DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(result -> result.stack.getComponentsPatch()),
                Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(ChancedResult::chance)
        ).apply(instance, (item, count, components, chance) -> new ChancedResult(new ItemStack(item, count, components), chance)));
    }

    public static class Serializer implements RecipeSerializer<MillstoneRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        private static final MapCodec<MillstoneRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(MillstoneRecipe::getGroup),
                MillingBookCategory.CODEC.optionalFieldOf("category", MillingBookCategory.MISC).forGetter(MillstoneRecipe::getCategory),
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.recipeItems),
                ChancedResult.CODEC.listOf().fieldOf("results").forGetter(MillstoneRecipe::packResults),
                Codec.INT.optionalFieldOf("cooktime", 200).forGetter(MillstoneRecipe::getCookTime)
        ).apply(instance, (group, category, ingredients, results, cookTime) -> {
            NonNullList<ItemStack> stacks = NonNullList.create();
            NonNullList<Float> chances = NonNullList.create();
            for (ChancedResult result : results) {
                stacks.add(result.stack());
                chances.add(result.chance());
            }
            return new MillstoneRecipe(group, category, NonNullList.copyOf(ingredients), stacks, chances, cookTime);
        }));

        private static final StreamCodec<RegistryFriendlyByteBuf, MillstoneRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    buf.writeUtf(recipe.group);
                    buf.writeEnum(recipe.category);
                    buf.writeVarInt(recipe.recipeItems.size());
                    for (Ingredient ing : recipe.recipeItems) {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
                    }
                    buf.writeVarInt(recipe.results.size());
                    for (int i = 0; i < recipe.results.size(); i++) {
                        ItemStack.STREAM_CODEC.encode(buf, recipe.results.get(i));
                        buf.writeFloat(recipe.chances.get(i));
                    }
                    buf.writeVarInt(recipe.cookTime);
                },
                buf -> {
                    String group = buf.readUtf();
                    MillingBookCategory category = buf.readEnum(MillingBookCategory.class);
                    int inSize = buf.readVarInt();
                    NonNullList<Ingredient> ingredients = NonNullList.withSize(inSize, Ingredient.EMPTY);
                    for (int i = 0; i < inSize; i++) {
                        ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                    }
                    int outSize = buf.readVarInt();
                    NonNullList<ItemStack> results = NonNullList.withSize(outSize, ItemStack.EMPTY);
                    NonNullList<Float> chances = NonNullList.withSize(outSize, 0f);
                    for (int i = 0; i < outSize; i++) {
                        results.set(i, ItemStack.STREAM_CODEC.decode(buf));
                        chances.set(i, buf.readFloat());
                    }
                    int cookTime = buf.readVarInt();
                    return new MillstoneRecipe(group, category, ingredients, results, chances, cookTime);
                }
        );

        @Override
        public MapCodec<MillstoneRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MillstoneRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}