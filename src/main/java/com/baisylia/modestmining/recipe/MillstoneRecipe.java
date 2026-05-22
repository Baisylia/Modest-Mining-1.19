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

    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;
    private final boolean isSimple;

    public MillstoneRecipe(ResourceLocation id, String group, MillingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime) {
        super(id, group, category, output, recipeItems, cookTime);
        this.output = output;
        this.recipeItems = recipeItems;
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
    public ItemStack assemble(Container p_44001_) {
        return output;
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
        public MillstoneRecipe fromJson(ResourceLocation resourceLocation, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            MillingBookCategory category = MillingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null));
            if (category == null) category = MillingBookCategory.MISC;
            NonNullList<Ingredient> inputs = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputs.isEmpty()) {
                throw new JsonParseException("No ingredients for milling recipe");
            } else if (inputs.size() > 9) {
                throw new JsonParseException("Too many ingredients for milling recipe. The maximum is 9");
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                int cookTimeIn = GsonHelper.getAsInt(json, "cooktime", 200);
                return new MillstoneRecipe(resourceLocation, group, category, itemstack, inputs,  cookTimeIn);
            }
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
            int i = buf.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < inputs.size(); ++j) {
                inputs.set(j, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            int cookTimeIn = buf.readVarInt();
            return new MillstoneRecipe(id, group, category, itemstack, inputs, cookTimeIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, MillstoneRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category);
            buf.writeVarInt(recipe.recipeItems.size());

            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.getResultItem());
            buf.writeVarInt(recipe.cookTime);

        }
    }
}