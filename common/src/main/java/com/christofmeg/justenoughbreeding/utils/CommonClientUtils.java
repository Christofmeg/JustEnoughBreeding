package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.config.MobOffset;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;

import java.io.IOException;
import java.io.InputStream;

public class CommonClientUtils {

    public static int getPixelColor(Identifier texture, int px, int py) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        try {
            Resource resource = resourceManager.getResource(texture).orElse(null);
            if (resource == null) return -7631989; // fallback

            try (InputStream is = resource.open()) {
                NativeImage image = NativeImage.read(is);

                // flip Y (Minecraft textures are usually top-left origin)
                int flippedY = image.getHeight() - 1 - py;

                // ABGR → ARGB conversion
                int abgr = image.getPixel(px, flippedY);
                int a = (abgr >> 24) & 0xFF;
                int b = (abgr >> 16) & 0xFF;
                int g = (abgr >> 8)  & 0xFF;
                int r = (abgr)       & 0xFF;
                return (a << 24) | (r << 16) | (g << 8) | b;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -7631989; // fallback
    }

    public static void fillSolidColor(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.fill(x, y, x + width, y + height, color);
    }

    public static void renderEntity(Entity entity, Rect bounds, GuiGraphics guiGraphics, int mouseX, MobOffset offset) {
        if (entity instanceof LivingEntity livingEntity) {
            int left = bounds.x() - 47;
            int top = bounds.y() + 1;
            int right = bounds.right() - 47;
            int bottom = bounds.bottom() + 21;

            // Debug: visualize the cutout/scissor area.
        //    guiGraphics.fill(left, top, right, bottom, 0x80FF0000);

            guiGraphics.enableScissor(left, top, right, bottom);
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(- 52 + 5.5f + offset.x(), 54 + offset.y());
            EntityDimensions dimensions = entity.getType().getDimensions();
            float size = Math.min(20 / dimensions.height(), 20 / dimensions.width()) + offset.scale();
            renderEntityInInventoryFollowsMouse(
                    guiGraphics,
                    bounds.x(),
                    bounds.y() - 53,
                    bounds.right(),
                    bounds.bottom() - 33,
                    size,
                    mouseX,
                    livingEntity
            );
            guiGraphics.disableScissor();
            guiGraphics.pose().popMatrix();
        }
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int left, int top, int right, int bottom, float size, float mouseX, LivingEntity entity) {
        int x = (int) guiGraphics.pose().m20();
        int y = (int) guiGraphics.pose().m21();
        int renderLeft   = left + x;
        int renderTop    = top + y + 19;
        int renderRight  = right + x;
        int renderBottom = bottom + y + 19;
        int centerY = (renderTop + renderBottom) / 2;
        float entityScale = entity.getScale();
        float renderScale = size / entityScale;
        float yOffset = entity.getBbHeight() / 2.0F;
        float screenCenterX = (left + right) / 2.0F;
        float mouseDelta = mouseX - screenCenterX;
        float renderCenterX = (renderLeft + renderRight) / 2.0F;
        float adjustedMouseX = renderCenterX + mouseDelta;
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, renderLeft, renderTop, renderRight, renderBottom, (int) renderScale, yOffset, adjustedMouseX, centerY, entity);
    }

}
