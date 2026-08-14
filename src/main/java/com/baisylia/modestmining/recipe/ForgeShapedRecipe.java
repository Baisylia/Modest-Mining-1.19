package com.baisylia.modestmining.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.Map;
import java.util.Set;

public class ForgeShapedRecipe extends AbstractForgeRecipe implements IShapedRecipe<Container> {
    static final int MAX_WIDTH = 3;
    static final int MAX_HEIGHT = 3;

    final int width;
    final int height;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;

    public ForgeShapedRecipe(int width, int height, ResourceLocation id, String group, ForgingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime, int fuelTier) {
        super(id, group, category, output, recipeItems, cookTime, fuelTier);
        this.width = width;
        this.height = height;
        this.output = output;
        this.recipeItems = recipeItems;
        this.cookTime = cookTime;
    }

    static NonNullList<Ingredient> dissolvePattern(String[] pattern, Map<String, Ingredient> keys, int width, int height) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
        Set<String> unusedKeys = Sets.newHashSet(keys.keySet());
        unusedKeys.remove(" ");

        for (int row = 0; row < pattern.length; ++row) {
            for (int col = 0; col < pattern[row].length(); ++col) {
                String symbol = pattern[row].substring(col, col + 1);
                Ingredient ingredient = keys.get(symbol);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + symbol + "' but it's not defined in the key");
                }

                unusedKeys.remove(symbol);
                ingredients.set(col + width * row, ingredient);
            }
        }

        if (!unusedKeys.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + unusedKeys);
        }
        return ingredients;
    }

    @VisibleForTesting
    static String[] shrink(String... lines) {
        int minCol = Integer.MAX_VALUE;
        int maxCol = 0;
        int leadingEmptyRows = 0;
        int trailingEmptyRows = 0;

        for (int row = 0; row < lines.length; ++row) {
            String line = lines[row];
            minCol = Math.min(minCol, firstNonSpace(line));
            int lastNonSpaceCol = lastNonSpace(line);
            maxCol = Math.max(maxCol, lastNonSpaceCol);

            if (lastNonSpaceCol < 0) {
                if (leadingEmptyRows == row) {
                    ++leadingEmptyRows;
                }
                ++trailingEmptyRows;
            } else {
                trailingEmptyRows = 0;
            }
        }

        if (lines.length == trailingEmptyRows) {
            return new String[0];
        }

        String[] result = new String[lines.length - trailingEmptyRows - leadingEmptyRows];
        for (int row = 0; row < result.length; ++row) {
            result[row] = lines[row + leadingEmptyRows].substring(minCol, maxCol + 1);
        }
        return result;
    }

    private static int firstNonSpace(String line) {
        int i = 0;
        while (i < line.length() && line.charAt(i) == ' ') {
            i++;
        }
        return i;
    }

    private static int lastNonSpace(String line) {
        int i = line.length() - 1;
        while (i >= 0 && line.charAt(i) == ' ') {
            i--;
        }
        return i;
    }

    static String[] patternFromJson(JsonArray patternArray) {
        String[] pattern = new String[patternArray.size()];
        if (pattern.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        }
        if (pattern.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        }

        for (int i = 0; i < pattern.length; ++i) {
            String row = GsonHelper.convertToString(patternArray.get(i), "pattern[" + i + "]");
            if (row.length() > MAX_WIDTH) {
                throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
            }
            if (i > 0 && pattern[0].length() != row.length()) {
                throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }
            pattern[i] = row;
        }
        return pattern;
    }

    static Map<String, Ingredient> keyFromJson(JsonObject keyObject) {
        Map<String, Ingredient> keys = Maps.newHashMap();

        for (Map.Entry<String, JsonElement> entry : keyObject.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            keys.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        keys.put(" ", Ingredient.EMPTY);
        return keys;
    }

    public static ItemStack itemStackFromJson(JsonObject json) {
        return CraftingHelper.getItemStack(json, true, true);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean matches(Container container, Level level) {
        for (int startX = 0; startX <= 3 - this.width; ++startX) {
            for (int startY = 0; startY <= 3 - this.height; ++startY) {
                if (this.matches(container, startX, startY, true)) return true;
                if (this.matches(container, startX, startY, false)) return true;
            }
        }
        return false;
    }

    private boolean matches(Container container, int startX, int startY, boolean mirrored) {
        for (int xn = 0; xn < 3; ++xn) {
            for (int yn = 0; yn < 3; ++yn) {
                int x = xn - startX;
                int y = yn - startY;
                Ingredient ingredient = Ingredient.EMPTY;
                if (x >= 0 && y >= 0 && x < this.width && y < this.height) {
                    if (mirrored) ingredient = this.recipeItems.get(this.width - x - 1 + y * this.width);
                    else ingredient = this.recipeItems.get(x + y * this.width);
                }

                if (!ingredient.test(container.getItem(xn + yn * 3))) return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(Container container) {
        return this.output;
    }

    public int getWidth() {
        return this.width;
    }

    public int getRecipeWidth() {
        return getWidth();
    }

    public int getHeight() {
        return this.height;
    }

    public int getRecipeHeight() {
        return getHeight();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FORGING_TYPE.get();
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> ingredients = this.getIngredients();
        return ingredients.isEmpty() || ingredients.stream().filter(p -> !p.isEmpty()).anyMatch(ForgeHooks::hasNoElements);
    }

    public static class Serializer implements RecipeSerializer<ForgeShapedRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public ForgeShapedRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            ForgingBookCategory category = ForgingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null));
            if (category == null) category = ForgingBookCategory.MISC;

            Map<String, Ingredient> keys = ForgeShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] pattern = ForgeShapedRecipe.shrink(ForgeShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int width = pattern[0].length();
            int height = pattern.length;

            NonNullList<Ingredient> ingredients = ForgeShapedRecipe.dissolvePattern(pattern, keys, width, height);
            ItemStack result = ForgeShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            int cookTime = GsonHelper.getAsInt(json, "cooktime", 200);
            int fuelTier = ForgeRecipe.Serializer.parseFuelTier(json);

            return new ForgeShapedRecipe(width, height, id, group, category, result, ingredients, cookTime, fuelTier);
        }

        @Override
        public ForgeShapedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int width = buf.readVarInt();
            int height = buf.readVarInt();
            String group = buf.readUtf();
            ForgingBookCategory category = buf.readEnum(ForgingBookCategory.class);

            NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack result = buf.readItem();
            int cookTime = buf.readVarInt();
            int fuelTier = buf.readVarInt();

            return new ForgeShapedRecipe(width, height, id, group, category, result, ingredients, cookTime, fuelTier);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ForgeShapedRecipe recipe) {
            buf.writeVarInt(recipe.width);
            buf.writeVarInt(recipe.height);
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category);

            for (Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.getResultItem());
            buf.writeVarInt(recipe.cookTime);
            buf.writeVarInt(recipe.getFuelTier());
        }
    }
}