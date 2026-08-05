package com.baisylia.modestmining.recipe;

import com.baisylia.modestmining.block.entity.custom.ForgeBlockEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ForgeShapedRecipe extends AbstractForgeRecipe {

    private final ShapedRecipePattern pattern;
    private final ItemStack output;
    private final int cookTime;

    public ForgeShapedRecipe(String group, ForgingBookCategory category, ShapedRecipePattern pattern, ItemStack output, Optional<Ingredient> fuel, int cookTime) {
        super(group, category, output, pattern.ingredients(), fuel, cookTime);
        this.pattern = pattern;
        this.output = output;
        this.cookTime = cookTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean matches(ForgeBlockEntity.SingleRecipeInputContainer input, Level level) {
        for (int xOffset = 0; xOffset <= 3 - this.getWidth(); ++xOffset) {
            for (int yOffset = 0; yOffset <= 3 - this.getHeight(); ++yOffset) {
                if (this.matches(input, xOffset, yOffset, true)) return true;
                if (this.matches(input, xOffset, yOffset, false)) return true;
            }
        }
        return false;
    }

    private boolean matches(ForgeBlockEntity.SingleRecipeInputContainer input, int xOffset, int yOffset, boolean mirrored) {
        int width = this.getWidth();
        int height = this.getHeight();
        for (int xn = 0; xn < 3; ++xn) {
            for (int yn = 0; yn < 3; ++yn) {
                int x = xn - xOffset;
                int y = yn - yOffset;
                Ingredient ingredient = Ingredient.EMPTY;
                if (x >= 0 && y >= 0 && x < width && y < height) {
                    if (mirrored) ingredient = this.getIngredients().get(width - x - 1 + y * width);
                    else ingredient = this.getIngredients().get(x + y * width);
                }
                if (!ingredient.test(input.getItem(xn + yn * 3))) return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(ForgeBlockEntity.SingleRecipeInputContainer input, HolderLookup.Provider registries) {
        return output.copy();
    }

    public ShapedRecipePattern getPattern() {
        return this.pattern;
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FORGING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<ForgeShapedRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        private static final MapCodec<ForgeShapedRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(ForgeShapedRecipe::getGroup),
                ForgingBookCategory.CODEC.optionalFieldOf("category", ForgingBookCategory.MISC).forGetter(ForgeShapedRecipe::getCategory),
                ShapedRecipePattern.MAP_CODEC.forGetter(ForgeShapedRecipe::getPattern),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
                Ingredient.CODEC_NONEMPTY.optionalFieldOf("fuel").forGetter(ForgeShapedRecipe::getFuel),
                Codec.INT.optionalFieldOf("cooktime", 200).forGetter(ForgeShapedRecipe::getCookTime)
        ).apply(instance, ForgeShapedRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, ForgeShapedRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, recipe) -> {
                    buf.writeUtf(recipe.group);
                    buf.writeEnum(recipe.category);
                    ShapedRecipePattern.STREAM_CODEC.encode(buf, recipe.pattern);
                    ItemStack.STREAM_CODEC.encode(buf, recipe.output);
                    FUEL_STREAM_CODEC.encode(buf, recipe.getFuel());
                    buf.writeVarInt(recipe.cookTime);
                },
                buf -> {
                    String group = buf.readUtf();
                    ForgingBookCategory category = buf.readEnum(ForgingBookCategory.class);
                    ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(buf);
                    ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                    Optional<Ingredient> fuel = FUEL_STREAM_CODEC.decode(buf);
                    int cookTime = buf.readVarInt();
                    return new ForgeShapedRecipe(group, category, pattern, output, fuel, cookTime);
                }
        );

        @Override
        public MapCodec<ForgeShapedRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ForgeShapedRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
