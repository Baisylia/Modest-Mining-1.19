package com.baisylia.modestmining.screen;

import com.baisylia.modestmining.ModestMining;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MillstoneScreen extends AbstractContainerScreen<MillstoneMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ModestMining.MOD_ID, "textures/gui/millstone_gui.png");

    public MillstoneScreen(MillstoneMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void init() {
        super.init();
        this.titleLabelX = 28;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = this.leftPos;
        int y = this.topPos;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 61, y + 36, 176, 14, menu.getScaledProgress(), 17);
        }
        if (menu.isFueled()) {
            guiGraphics.blit(TEXTURE, x + 61, y + 55, 201, 14, 24, 17);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        this.renderTooltip(guiGraphics, mouseX, mouseY);

        if (isHovering(61, 55, 24, 17, mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            if (menu.isFueled()) {
                tooltip.add(Component.translatable("tooltip.modestmining.millstone.powered"));
            } else {
                tooltip.add(Component.translatable("tooltip.modestmining.millstone.not_powered"));
            }
            guiGraphics.renderComponentTooltip(this.font, tooltip, mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), 4210752, false);
    }
}
