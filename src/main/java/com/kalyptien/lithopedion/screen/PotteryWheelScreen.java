package com.kalyptien.lithopedion.screen;

import com.kalyptien.lithopedion.Lithopedion;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PotteryWheelScreen extends AbstractContainerScreen<PotteryWheelMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Lithopedion.MOD_ID, "textures/gui/pottery_wheel_gui.png");

    public PotteryWheelScreen(PotteryWheelMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.isClay()){
            int clayS = menu.getClayProgress();
            blit(pPoseStack, x + (150 - clayS), y + 17, 213 - clayS, 16, clayS, 17);
        }

        if(menu.isWater()){
            int waterS = menu.getWaterProgress();
            blit(pPoseStack, x + (150 - waterS), y + 53, 213 - waterS, 33, waterS, 17);
        }

        if(menu.isCrafting()) {
            blit(pPoseStack, x + 81, y + 35, 177, 0, menu.getScaledProgress(), 15);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
