package com.ncpbails.modestmining.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Set;

public class ForgeShapedRecipe extends AbstractForgeRecipe {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;
    public static void setCraftingSize(int width, int height) {
        if (MAX_WIDTH < width) MAX_WIDTH = width;
        if (MAX_HEIGHT < height) MAX_HEIGHT = height;
    }

    final int width;
    final int height;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;
    private final boolean isSimple;

    public ForgeShapedRecipe(int width, int height, ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime) {
        super(id, output, recipeItems, cookTime);
        this.width = width;
        this.height = height;
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
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        ItemStack outputSlot = pContainer.getItem(10);
        if (!outputSlot.isEmpty() && !ItemStack.isSame(this.getResultItem(), outputSlot)) {
            return false;
        }

        if (!outputSlot.isEmpty() && outputSlot.getCount() >= outputSlot.getMaxStackSize()) {
            return false;
        }

        boolean[][] slotUsed = new boolean[3][3]; // Track which slots are used

        // Iterate over the crafting grid
        for (int offsetX = 0; offsetX <= 3 - this.getWidth(); ++offsetX) {
            for (int offsetY = 0; offsetY <= 3 - this.getHeight(); ++offsetY) {
                if (checkIngredients(pContainer, offsetX, offsetY, slotUsed)) {
                    if (areOtherSlotsEmpty(pContainer, offsetX, offsetY)) {
                        return true; // Match found, return true
                    }
                }
            }
        }

        return false; // No match found
    }

    private boolean areOtherSlotsEmpty(SimpleContainer pContainer, int offsetX, int offsetY) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i < offsetX || i >= offsetX + this.getWidth() || j < offsetY || j >= offsetY + this.getHeight()) {
                    ItemStack itemStack = pContainer.getItem(i + j * 3); // Use a fixed grid size of 3x3
                    if (!itemStack.isEmpty()) {
                        return false; // Slot is not empty
                    }
                }
            }
        }
        return true; // All other slots are empty
    }

    private boolean checkIngredients(SimpleContainer pContainer, int offsetX, int offsetY, boolean[][] slotUsed) {
        // Iterate over the recipe's dimensions
        for (int i = 0; i < this.getWidth(); ++i) {
            for (int j = 0; j < this.getHeight(); ++j) {
                int gridX = i + offsetX;
                int gridY = j + offsetY;

                // Check if the current position is within the crafting grid
                if (gridX >= 3 || gridY >= 3) {
                    continue;
                }

                // Check if the slot is already used by another recipe
                if (slotUsed[gridX][gridY]) {
                    return false;
                }

                Ingredient recipeIngredient = this.recipeItems.get(i + j * this.getWidth());
                ItemStack gridStack = pContainer.getItem(gridX + gridY * 3); // Use a fixed grid size of 3x3

                // Check if the ingredient matches the item in the crafting grid
                if (!recipeIngredient.test(gridStack)) {
                    return false;
                }

                // Mark the slot as used
                slotUsed[gridX][gridY] = true;
            }
        }

        return true; // All ingredients matched
    }

    @Override
    public ItemStack assemble(SimpleContainer p_44001_) {
        return output;
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
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ForgeShapedRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "forging_shaped";
    }

    static NonNullList<Ingredient> dissolvePattern(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(p_44205_ * p_44206_, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(p_44204_.keySet());
        set.remove(" ");

        for(int i = 0; i < p_44203_.length; ++i) {
            for(int j = 0; j < p_44203_[i].length(); ++j) {
                String s = p_44203_[i].substring(j, j + 1);
                Ingredient ingredient = p_44204_.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + p_44205_ * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... p_44187_) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < p_44187_.length; ++i1) {
            String s = p_44187_[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (p_44187_.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[p_44187_.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = p_44187_[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream().filter((p_151277_) -> {
            return !p_151277_.isEmpty();
        }).anyMatch((p_151273_) -> {
            return net.minecraftforge.common.ForgeHooks.hasNoElements(p_151273_);
        });
    }

    private static int firstNonSpace(String p_44185_) {
        int i;
        for(i = 0; i < p_44185_.length() && p_44185_.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String p_44201_) {
        int i;
        for(i = p_44201_.length() - 1; i >= 0 && p_44201_.charAt(i) == ' '; --i) {
        }

        return i;
    }

    static String[] patternFromJson(JsonArray p_44197_) {
        String[] astring = new String[p_44197_.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = GsonHelper.convertToString(p_44197_.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    static Map<String, Ingredient> keyFromJson(JsonObject p_44211_) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : p_44211_.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack itemStackFromJson(JsonObject p_151275_) {
        return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_151275_, true, true);
    }

    public static Item itemFromJson(JsonObject p_151279_) {
        String s = GsonHelper.getAsString(p_151279_, "item");
        Item item = Registry.ITEM.getOptional(new ResourceLocation(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Invalid item: " + s);
        } else {
            return item;
        }
    }

    public static class Serializer implements RecipeSerializer<ForgeShapedRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        private static final ResourceLocation NAME = new ResourceLocation("modestmining", "forging_shaped");
        public ForgeShapedRecipe fromJson(ResourceLocation id, JsonObject json) {
            Map<String, Ingredient> map = ForgeShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] astring = ForgeShapedRecipe.shrink(ForgeShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int width = astring[0].length();
            int height = astring.length;
            NonNullList<Ingredient> nonnulllist = ForgeShapedRecipe.dissolvePattern(astring, map, width, height);
            ItemStack itemstack = ForgeShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            int cookTimeIn = GsonHelper.getAsInt(json, "cooktime", 200);
            return new ForgeShapedRecipe(width, height, id, itemstack, nonnulllist, cookTimeIn);
        }

        @Override
        public ForgeShapedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int width = buf.readVarInt();
            int height = buf.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            int cookTimeIn = buf.readVarInt();
            return new ForgeShapedRecipe(width, height, id, itemstack, nonnulllist, cookTimeIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ForgeShapedRecipe recipe) {
            buf.writeVarInt(recipe.width);
            buf.writeVarInt(recipe.height);
            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.getResultItem());
            buf.writeVarInt(recipe.cookTime);
        }
    }
}