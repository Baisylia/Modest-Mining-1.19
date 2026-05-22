package com.baisylia.modestmining.screen;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MillingRecipeBookComponent extends RecipeBookComponent {

	protected static final ResourceLocation RECIPE_BOOK_BUTTON_TEXTURE =
			new ResourceLocation(ModestMining.MOD_ID, "textures/gui/millstone_gui.png");

	@Override
	protected @NotNull Component getRecipeFilterName() {
		return Component.translatable("gui.recipe_book.toggle_recipes.millable");
	}

	@Override
	protected void initFilterButtonTextures() {
		this.filterButton.initTextureValues(176, 31, 28, 18, RECIPE_BOOK_BUTTON_TEXTURE);
	}

	@Override
	public void setupGhostRecipe(Recipe<?> recipe, @NotNull List<Slot> slots) {
		ItemStack result = recipe.getResultItem();
		this.ghostRecipe.setRecipe(recipe);
		Slot resultSlot = slots.get(10);
		this.ghostRecipe.addIngredient(Ingredient.of(result), resultSlot.x, resultSlot.y);

		this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.getIngredients().iterator(), 0);
	}

}
