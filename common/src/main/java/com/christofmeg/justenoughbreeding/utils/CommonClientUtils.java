package com.christofmeg.justenoughbreeding.utils;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
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

    private static final int WIDGET_SIZE = 36;
/*
    public static void renderEntity(PoseStack stack, double mouseX, LivingEntity currentLivingEntity, int entityPosX, int entityPosY) {
        // Set the desired position of the entity on the screen
        int ENTITY_RENDER_DISTANCE = 15728880;

        float yaw = (float) (60 - mouseX); // Calculate the yaw based on the mouse position

        stack.pushPose(); // Push the current pose onto the stack
        stack.translate((float) entityPosX, (float) entityPosY, 50f); // Translate the entity's position

        // Calculate the scaling factor based on the bounding box's largest dimension
        AABB boundingBox = currentLivingEntity.getBoundingBox();
        double largestDimension = Math.max(boundingBox.getXsize(), Math.max(boundingBox.getYsize(), boundingBox.getZsize()));

        float desiredWidth = 30.0F;
        float desiredHeight = 40.0F;

        // Calculate the scaling factors for width and height
        float scaleX = desiredWidth / (float) largestDimension;
        float scaleY = desiredHeight / (float) largestDimension;

        // Use the smaller of the two scaling factors to ensure the entity fits within the area
        float scalingFactor = Math.min(scaleX, scaleY);

        if (currentLivingEntity instanceof Frog) {
            scalingFactor = 50;
        }

        if (currentLivingEntity instanceof Axolotl || currentLivingEntity instanceof Cat ||
                currentLivingEntity instanceof Pig || currentLivingEntity instanceof Wolf) {
            scalingFactor = 25;
        }

        if (currentLivingEntity instanceof Ocelot || currentLivingEntity instanceof Fox
                || currentLivingEntity instanceof Turtle) {
            scalingFactor = 20;
        }

        if (currentLivingEntity instanceof Hoglin || currentLivingEntity instanceof Horse
                || currentLivingEntity instanceof Panda) {
            scalingFactor = 15;
        }

        if (currentLivingEntity instanceof Sniffer) {
            scalingFactor = 10;
        }

        stack.scale(scalingFactor, scalingFactor, scalingFactor); // Scale the entity to fit within the desired area
        stack.mulPose(Axis.ZP.rotationDegrees(180.0F)); // Rotate the entity to face a certain direction

        float yawRadians = -(yaw / 40.F) * 20.0F; // Calculate the yaw angle in radians for the entity's rotation

        // Apply the calculated yaw angle to the entity's rotation properties
        currentLivingEntity.yBodyRot = yawRadians;
        currentLivingEntity.setYRot(yawRadians);
        currentLivingEntity.yHeadRot = yawRadians;
        currentLivingEntity.yHeadRotO = yawRadians;

        Minecraft instance = Minecraft.getInstance();
        EntityRenderDispatcher entityRenderDispatcher = instance.getEntityRenderDispatcher(); // Get the entity rendering dispatcher
        entityRenderDispatcher.overrideCameraOrientation(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F)); // Override the camera orientation for rendering
        entityRenderDispatcher.setRenderShadow(false); // Disable rendering shadows for the entity

        // Get the buffer source for rendering
        final MultiBufferSource.BufferSource bufferSource = instance.renderBuffers().bufferSource();

        // Render the currentLivingEntity using the entityRenderDispatcher
        entityRenderDispatcher.render(currentLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, stack, bufferSource, ENTITY_RENDER_DISTANCE);

        bufferSource.endBatch(); // End the rendering batch
        entityRenderDispatcher.setRenderShadow(true); // Re-enable rendering shadows

        stack.popPose(); // Pop the pose from the stack to revert transformations
    }
*/
    public static void renderEntity(GuiGraphics guiGraphics, int mouseX, LivingEntity currentLivingEntity, Rect bounds, int fullWidth) {
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

        guiGraphics.pose().pushPose();
        /*
        guiGraphics.blit(
                null,
                bounds.x(),
                bounds.y(),
                bounds.width(),
                bounds.height(),
                0,
                36,
                36,
                36,
                256,
                256
        );*/

    //    guiGraphics.enableScissor(screenX + bounds.x() + 1, screenY + bounds.y() + 1, screenX + bounds.right() - 1, screenY + bounds.bottom() - 1);

        EntityDimensions dimensions = currentLivingEntity.getType().getDimensions();

        /*
        InventoryScreen.renderEntityInInventoryFollowsMouse(
                guiGraphics,
                bounds.x() + bounds.width() / 2,
                bounds.y() + WIDGET_SIZE - 5,
                (int) (Math.min(20 / dimensions.height, 20 / dimensions.width)),
                -mouseX + ((float) fullWidth / 2),
                mouseX,
                currentLivingEntity
        );
         */

        renderEntityInInventoryFollowsMouse(guiGraphics, 0, 0, 0, 0, (int) (Math.min(20 / dimensions.height, 20 / dimensions.width)), mouseX, currentLivingEntity, bounds);

        guiGraphics.pose().popPose();
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int size, float mouseX, LivingEntity entity, Rect bounds) {
        int x = bounds.x();
        int y = bounds.y();
        int renderLeft   = left + x;
        int renderTop    = top + y + 19;
        int renderRight  = right + x;
        int renderBottom = bottom + y + 19;
        int centerY = (renderTop + renderBottom) / 2;
        float entityScale = entity.getScale();
        float renderScale = size / entityScale;
        float yOffset = entity.getBbHeight() / 2.0F;
     //   guiGraphics.fill(1, 11, 60, 90, -65536);
    //    guiGraphics.fill(1 + 105, 11, 60 + 105, 90, -35536);

    //    guiGraphics.enableScissor(0, 0, guiGraphics.guiWidth(), guiGraphics.guiHeight());


/*
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();

        Screen screen = Minecraft.getInstance().screen;

        int screenWidth = screen.width;
        int screenHeight = screen.height;

        Window window = Minecraft.getInstance().getWindow();
        int windowWidth = window.getWidth();
        int windowHeight = window.getHeight();
        double windowGUIScale = window.getGuiScale();
        int getGuiScaledWidth = window.getGuiScaledWidth();
        int getGuiScaledHeight = window.getGuiScaledHeight();
        int getScreenWidth = window.getScreenWidth();
        int getScreenHeight = window.getScreenHeight();

        System.out.println("--------------------------------------------------");
        System.out.println("guiWidth: " + guiWidth);
        System.out.println("screenWidth: " + screenWidth);
        System.out.println("getGuiScaledWidth: " + getGuiScaledWidth);

        System.out.println("guiHeight: " + guiHeight);
        System.out.println("screenHeight: " + screenHeight);
        System.out.println("getGuiScaledHeight: " + getGuiScaledHeight);

        System.out.println("windowWidth: " + windowWidth);
        System.out.println("getScreenWidth: " + getScreenWidth);

        System.out.println("windowHeight: " + windowHeight);
        System.out.println("getScreenHeight: " + getScreenHeight);

        System.out.println("windowGUIScale: " + windowGUIScale);
        System.out.println("--------------------------------------------------");
        */

        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        Screen screen = minecraft.screen;
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

        guiGraphics.enableScissor(screenX + bounds.x() + 1, screenY + bounds.y() + 1, screenX + bounds.right() - 1, screenY + bounds.bottom() - 1);

        guiGraphics.fill(-guiGraphics.guiWidth(), -guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight(), -15536);
        guiGraphics.disableScissor();





     //   guiGraphics.fill(-111, 11, 60, 90, -65536);
    //    InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, renderLeft, renderTop, renderRight, renderBottom, mouseX, entity);

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
