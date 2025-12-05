package com.baisylia.modestmining.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.baisylia.modestmining.ModestMining;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class ForgeScreen extends AbstractContainerScreen<ForgeMenu> implements RecipeUpdateListener {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(ModestMining.MOD_ID, "textures/gui/forge_gui.png");

	private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");

	public final RecipeBookComponent recipeBookComponent = new ForgingRecipeBookComponent();
	private boolean widthTooNarrow;

    public ForgeScreen(ForgeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

	@Override
	public void init() {
		super.init();
		this.widthTooNarrow = this.width < 379;
		this.titleLabelX = 28;
		this.recipeBookComponent.init(this.width, this.height, this.minecraft, this.widthTooNarrow, this.menu);
		this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
		this.addRenderableWidget(new ImageButton(this.leftPos + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
			this.recipeBookComponent.toggleVisibility();
			this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
			((ImageButton)button).setPosition(this.leftPos + 5, this.height / 2 - 49);
		}));
		this.addWidget(this.recipeBookComponent);
		this.setInitialFocus(this.recipeBookComponent);
	}

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
		int x = this.leftPos;
		int y = this.topPos;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.isCrafting()) {
            blit(pPoseStack, x + 89, y + 17, 176, 14,  menu.getScaledProgress(), 17);
        }
        if(menu.isFueled()) {
            float currentHeight = menu.getLitTime();
            int offset = (int) (15- currentHeight);
            blit(pPoseStack, x + 94, y + 36+offset, 176, offset, 14, (int) currentHeight);
        }
    }

	@Override
	protected void containerTick() {
		super.containerTick();
		this.recipeBookComponent.tick();
	}

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
		this.renderBackground(pPoseStack);

		if (this.recipeBookComponent.isVisible() && this.widthTooNarrow) {
			this.renderBg(pPoseStack, delta, mouseX, mouseY);
			this.recipeBookComponent.render(pPoseStack, mouseX, mouseY, delta);
		} else {
			this.recipeBookComponent.render(pPoseStack, mouseX, mouseY, delta);
			super.render(pPoseStack, mouseX, mouseY, delta);
			this.recipeBookComponent.renderGhostRecipe(pPoseStack, this.leftPos, this.topPos, true, delta);
		}

		this.recipeBookComponent.renderTooltip(pPoseStack, this.leftPos, this.topPos, mouseX, mouseY);
		renderTooltip(pPoseStack, mouseX, mouseY);
    }

	@Override
	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		super.renderLabels(poseStack, mouseX, mouseY);
		this.font.draw(poseStack, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), 4210752);
	}

	@Override
	protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
		return (!this.widthTooNarrow || !this.recipeBookComponent.isVisible()) && super.isHovering(x, y, width, height, mouseX, mouseY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int buttonId) {
		if (this.recipeBookComponent.mouseClicked(mouseX, mouseY, buttonId)) {
			this.setFocused(this.recipeBookComponent);
			return true;
		}
		return this.widthTooNarrow && this.recipeBookComponent.isVisible() || super.mouseClicked(mouseX, mouseY, buttonId);
	}

	@Override
	protected boolean hasClickedOutside(double mouseX, double mouseY, int x, int y, int buttonIdx) {
		boolean flag = mouseX < (double) x || mouseY < (double) y || mouseX >= (double) (x + this.imageWidth) || mouseY >= (double) (y + this.imageHeight);
		return flag && this.recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, buttonIdx);
	}

	@Override
	protected void slotClicked(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
		super.slotClicked(pSlot, pSlotId, pMouseButton, pType);
		this.recipeBookComponent.slotClicked(pSlot);
	}

	@Override
	public void recipesUpdated() {
		this.recipeBookComponent.recipesUpdated();
	}

	@Override
	public RecipeBookComponent getRecipeBookComponent() {
		return this.recipeBookComponent;
	}

}