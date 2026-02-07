package com.christofmeg.justenoughbreeding.utils;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.io.IOException;
import java.io.InputStream;

public class CommonClientUtils {

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int left, int top, int right, int bottom, float mouseX, LivingEntity entity, Rect bounds) {
        guiGraphics.pose().pushPose();
            int x = bounds.x();
            int y = bounds.y();
            int renderLeft = left + x + 31;
            int renderBottom = bottom + y + 79;

            Minecraft minecraft = Minecraft.getInstance();
            Window window = minecraft.getWindow();
            PoseStack poseStack = guiGraphics.pose();

            // Get the model-view matrix (combined) from the PoseStack
            Matrix4f modelViewMatrix = new Matrix4f(poseStack.last().pose());
            // Get the projection matrix
            Matrix4f projectionMatrix = new Matrix4f(RenderSystem.getProjectionMatrix());
            // Combine model-view and projection
            Matrix4f mvpMatrix = projectionMatrix.mul(modelViewMatrix);
            // Define the 3D coordinates of the top-left and bottom-right corners of your element
            // Since it's a 2D element in GUI, Z can be 0.
            Vector4f topLeftWorld = new Vector4f(0, 0, 0, 1);
            // Project to clip space
            Vector4f topLeftClip = mvpMatrix.transform(topLeftWorld);
            // Perspective divide
            Vector4f topLeftNDC = new Vector4f(topLeftClip.x / topLeftClip.w, topLeftClip.y / topLeftClip.w, 0, 1);

            // Convert to screen coordinates (pixels)
            int screenX = Math.round((topLeftNDC.x + 1) / 2f * window.getGuiScaledWidth());
            int screenY = Math.round((1 - topLeftNDC.y) / 2f * window.getGuiScaledHeight());

            EntityDimensions dimensions = entity.getType().getDimensions();
            int scale = (int) (Math.min(50 / dimensions.height, 50 / dimensions.width));

            float yaw = 60 - mouseX;
            float yawRadians = -(yaw / 40.F) * 20.0F;

            guiGraphics.enableScissor(screenX + bounds.x() + 1, screenY + bounds.y() + 1, screenX + bounds.right() - 1, screenY + bounds.bottom() - 1);
        //        guiGraphics.fill(-guiGraphics.guiWidth(), -guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight(), -15536);
                InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, renderLeft, renderBottom, scale, -yawRadians, 0, entity);
            guiGraphics.disableScissor();
        guiGraphics.pose().popPose();
    }

    public static int getPixelColor(ResourceLocation texture, int px, int py) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        try {
            Resource resource = resourceManager.getResource(texture).orElse(null);
            if (resource == null) return -7631989; // fallback

            try (InputStream is = resource.open()) {
                NativeImage image = NativeImage.read(is);

                // flip Y (Minecraft textures are usually top-left origin)
                int flippedY = image.getHeight() - 1 - py;

                // ABGR → ARGB conversion
                int abgr = image.getPixelRGBA(px, flippedY);
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

}
