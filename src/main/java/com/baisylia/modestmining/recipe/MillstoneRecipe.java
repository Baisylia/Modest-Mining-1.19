package com.baisylia.modestmining.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;

public class MillstoneRecipe extends AbstractMillstoneRecipe {
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;
    private final boolean isSimple;
    public final NonNullList<ItemStack> results;
    public final NonNullList<Float> chances;

    public MillstoneRecipe(ResourceLocation id, String group, MillingBookCategory category, NonNullList<Ingredient> recipeItems,
                           NonNullList<ItemStack> results, NonNullList<Float> chances, int cookTime) {
        super(id, group, category, results.isEmpty() ? ItemStack.EMPTY : results.get(0), recipeItems, cookTime);

        this.recipeItems = recipeItems;
        this.results = results;
        this.chances = chances;
        this.cookTime = cookTime;
        this.isSimple = recipeItems.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack input = pContainer.getItem(0);

        if (input.isEmpty()) {
            return false;
        }

        return this.recipeItems.get(0).test(input);
    }

    @Override
    public ItemStack assemble(Container container) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MILLING_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<MillstoneRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final ResourceLocation NAME = new ResourceLocation("modestmining", "milling");
        public MillstoneRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            MillingBookCategory category =
                    MillingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", "misc"));

            if (category == null) category = MillingBookCategory.MISC;

            NonNullList<Ingredient> inputs =
                    itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));

            if (inputs.isEmpty()) {
                throw new JsonParseException("No ingredients for milling recipe");
            }

            // Results
            JsonArray resultsJson = GsonHelper.getAsJsonArray(json, "results");

            NonNullList<ItemStack> results = NonNullList.create();
            NonNullList<Float> chances = NonNullList.create();

            for (int i = 0; i < resultsJson.size(); i++) {
                JsonObject obj = resultsJson.get(i).getAsJsonObject();

                ItemStack stack = ShapedRecipe.itemStackFromJson(obj);

                float chance = 1.0f;
                if (obj.has("chance")) {
                    chance = GsonHelper.getAsFloat(obj, "chance");
                }

                results.add(stack);
                chances.add(chance);
            }

            int cookTime = GsonHelper.getAsInt(json, "cooktime", 200);

            return new MillstoneRecipe(id, group, category, inputs, results, chances, cookTime);
        }


        private static NonNullList<Ingredient> itemsFromJson(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (true || !ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }
            return nonnulllist;
        }
        @Override
        public MillstoneRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String group = buf.readUtf();
            MillingBookCategory category = buf.readEnum(MillingBookCategory.class);

            int inputSize = buf.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(inputSize, Ingredient.EMPTY);

            for (int i = 0; i < inputSize; i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            int resultSize = buf.readVarInt();
            NonNullList<ItemStack> results = NonNullList.withSize(resultSize, ItemStack.EMPTY);
            NonNullList<Float> chances = NonNullList.withSize(resultSize, 0f);

            for (int i = 0; i < resultSize; i++) {
                results.set(i, buf.readItem());
                chances.set(i, buf.readFloat());
            }

            int cookTime = buf.readVarInt();

            return new MillstoneRecipe(id, group, category, inputs, results, chances, cookTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MillstoneRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category);

            buf.writeVarInt(recipe.recipeItems.size());
            for (Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeVarInt(recipe.results.size());
            for (int i = 0; i < recipe.results.size(); i++) {
                buf.writeItem(recipe.results.get(i));
                buf.writeFloat(recipe.chances.get(i));
            }

            buf.writeVarInt(recipe.cookTime);
        }
    }
}