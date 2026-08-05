package com.baisylia.modestmining.screen;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ForgingRecipeBookComponent extends RecipeBookComponent {

    public static final Ingredient FUELS =
            Ingredient.of(AbstractFurnaceBlockEntity.getFuel().keySet().stream().map(ItemStack::new));
    private static final WidgetSprites FILTER_BUTTON_SPRITES = new WidgetSprites(
            ResourceLocation.withDefaultNamespace("recipe_book/filter_button"),
            ResourceLocation.withDefaultNamespace("recipe_book/filter_button_highlighted")
    );

    @Override
    protected @NotNull Component getRecipeFilterName() {
        return Component.translatable("gui.recipe_book.toggle_recipes.forgeable");
    }

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(FILTER_BUTTON_SPRITES);
    }

    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipe, @NotNull List<Slot> slots) {
        ItemStack result = recipe.value().getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(recipe);
        Slot resultSlot = slots.get(10);
        this.ghostRecipe.addIngredient(Ingredient.of(result), resultSlot.x, resultSlot.y);

        Slot fuelSlot = slots.get(9);
        if (fuelSlot.getItem().isEmpty()) this.ghostRecipe.addIngredient(FUELS, fuelSlot.x, fuelSlot.y);

        this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.value().getIngredients().iterator(), 0);
    }

}
