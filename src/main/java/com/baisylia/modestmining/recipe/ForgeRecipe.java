package com.baisylia.modestmining.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;

public class ForgeRecipe extends AbstractForgeRecipe {

    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int cookTime;
    private final boolean isSimple;

    public ForgeRecipe(ResourceLocation id, String group, ForgingBookCategory category, ItemStack output, NonNullList<Ingredient> recipeItems, int cookTime, int fuelTier) {
        super(id, group, category, output, recipeItems, cookTime, fuelTier);
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
        // Check if output slot is already occupied with a different item
        ItemStack outputSlot = pContainer.getItem(10);
        if (!outputSlot.isEmpty() && !ItemStack.isSame(this.getResultItem(), outputSlot)) {
            return false;
        }

        // Check if output slot is full
        if (!outputSlot.isEmpty() && outputSlot.getCount() >= outputSlot.getMaxStackSize()) {
            return false;
        }
        StackedContents stackedcontents = new StackedContents();
        List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for (int j = 0; j < 9; ++j) {
            ItemStack itemstack = pContainer.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }
        return i == this.recipeItems.size()
                && (isSimple ? stackedcontents.canCraft(this, null) : RecipeMatcher.findMatches(inputs, this.recipeItems) != null);
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
        return ModRecipes.FORGING_TYPE.get();
    }


    public static class Serializer implements RecipeSerializer<ForgeRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static int parseFuelTier(JsonObject json) {
            if (json.has("fuel_tier")) {
                return GsonHelper.getAsInt(json, "fuel_tier", 0);
            }
            if (json.has("min_fuel_tier")) {
                return GsonHelper.getAsInt(json, "min_fuel_tier", 0);
            }
            if (json.has("fuel")) {
                JsonElement fuelElem = json.get("fuel");
                if (fuelElem.isJsonPrimitive()) {
                    if (fuelElem.getAsJsonPrimitive().isNumber()) {
                        return fuelElem.getAsInt();
                    } else {
                        String fuelStr = fuelElem.getAsString();
                        try {
                            return Integer.parseInt(fuelStr);
                        } catch (NumberFormatException ignored) {
                        }
                        ResourceLocation itemId = ResourceLocation.tryParse(fuelStr);
                        if (itemId != null) {
                            Item item = Registry.ITEM.get(itemId);
                            if (item != Items.AIR) {
                                int tier = ForgeFuelManager.getFuelTier(new ItemStack(item));
                                if (tier >= 0) return tier;
                            }
                        }
                        return 1;
                    }
                } else if (fuelElem.isJsonObject()) {
                    JsonObject fuelObj = fuelElem.getAsJsonObject();
                    if (fuelObj.has("tier")) {
                        return GsonHelper.getAsInt(fuelObj, "tier", 0);
                    }
                    if (fuelObj.has("item")) {
                        ResourceLocation itemId = ResourceLocation.tryParse(GsonHelper.getAsString(fuelObj, "item"));
                        if (itemId != null) {
                            Item item = Registry.ITEM.get(itemId);
                            if (item != Items.AIR) {
                                int tier = ForgeFuelManager.getFuelTier(new ItemStack(item));
                                if (tier >= 0) return tier;
                            }
                        }
                        return 1;
                    }
                    if (fuelObj.has("tag")) {
                        String tagStr = GsonHelper.getAsString(fuelObj, "tag");
                        if (tagStr.contains("tier_")) {
                            try {
                                String num = tagStr.substring(tagStr.indexOf("tier_") + 5);
                                return Integer.parseInt(num);
                            } catch (Exception ignored) {
                            }
                        }
                        return 1;
                    }
                    return 1;
                }
            }
            return 0;
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                nonnulllist.add(ingredient);
            }
            return nonnulllist;
        }

        public ForgeRecipe fromJson(ResourceLocation resourceLocation, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            ForgingBookCategory category = ForgingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null));
            if (category == null) category = ForgingBookCategory.MISC;
            NonNullList<Ingredient> inputs = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputs.isEmpty()) {
                throw new JsonParseException("No ingredients for forging recipe");
            } else if (inputs.size() > 9) {
                throw new JsonParseException("Too many ingredients for forging recipe. The maximum is 9");
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                int cookTimeIn = GsonHelper.getAsInt(json, "cooktime", 200);
                int fuelTier = parseFuelTier(json);
                return new ForgeRecipe(resourceLocation, group, category, itemstack, inputs, cookTimeIn, fuelTier);
            }
        }

        @Override
        public ForgeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String group = buf.readUtf();
            ForgingBookCategory category = buf.readEnum(ForgingBookCategory.class);
            int i = buf.readVarInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(i, Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromNetwork(buf));

            ItemStack itemstack = buf.readItem();
            int cookTimeIn = buf.readVarInt();
            int fuelTier = buf.readVarInt();
            return new ForgeRecipe(id, group, category, itemstack, inputs, cookTimeIn, fuelTier);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ForgeRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category);
            buf.writeVarInt(recipe.recipeItems.size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.getResultItem());
            buf.writeVarInt(recipe.cookTime);
            buf.writeVarInt(recipe.getFuelTier());
        }
    }
}