package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.config.MobOffset;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class CommonUtils {

    public static String makeKey(EntityType<?> type, boolean input) {
        return type.toString() + ":" + input;
    }

    public static @NotNull Ingredient safe(Ingredient ing) {
        return ing == null ? Ingredient.EMPTY : ing;
    }

    public static @NotNull Ingredient safe(ItemLike itemLike) {
        return itemLike == null ? Ingredient.EMPTY : Ingredient.of(itemLike);
    }

    public static Rect getRect(boolean input, int CATEGORY_WIDTH) {
       return getRect(input, CATEGORY_WIDTH, 0, 0);
    }

    public static Rect getRect(boolean input, int CATEGORY_WIDTH, int extraX, int extraY) {
        Rect rect;
        final int WIDGET_SIZE = 61;
        if (input) {
            rect = new Rect(((CATEGORY_WIDTH - WIDGET_SIZE) / 2) - 52 - extraX, 10 + extraY, WIDGET_SIZE, WIDGET_SIZE + 20);
        } else {
            rect = new Rect(((CATEGORY_WIDTH - WIDGET_SIZE) / 2) + 53 - extraX, 10 + extraY, WIDGET_SIZE, WIDGET_SIZE + 20);
        }
        return rect;
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, float mouseX, LivingEntity entity, Rect bounds, MobOffset mobOffset) {
        guiGraphics.pose().pushPose();
        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        PoseStack poseStack = guiGraphics.pose();

        Matrix4f modelViewMatrix = new Matrix4f(poseStack.last().pose());
        Matrix4f projectionMatrix = new Matrix4f(RenderSystem.getProjectionMatrix());
        Matrix4f mvpMatrix = projectionMatrix.mul(modelViewMatrix);
        Vector4f topLeftWorld = new Vector4f(0, 0, 0, 1);
        Vector4f topLeftClip = mvpMatrix.transform(topLeftWorld);
        Vector4f topLeftNDC = new Vector4f(topLeftClip.x / topLeftClip.w, topLeftClip.y / topLeftClip.w, 0, 1);

        int screenX = Math.round((topLeftNDC.x + 1) / 2f * window.getGuiScaledWidth());
        int screenY = Math.round((1 - topLeftNDC.y) / 2f * window.getGuiScaledHeight());

        EntityDimensions dimensions = entity.getType().getDimensions();
        int scale = (int) (Math.min(50 / dimensions.height(), 50 / dimensions.width()));

        float yaw = 60 - mouseX;
        float yawRadians = -(yaw / 40.F) * 20.0F;

        guiGraphics.enableScissor(screenX + bounds.x() + 1, screenY + bounds.y() + 1, screenX + bounds.right() - 1, screenY + bounds.bottom() - 1);
        //            guiGraphics.fill(-guiGraphics.guiWidth(), -guiGraphics.guiHeight(<), guiGraphics.guiWidth(), guiGraphics.guiHeight(), -15536);
        renderEntityInInventoryFollowsMouse(guiGraphics, //left, top, right, bottom
                bounds.x(), bounds.y() + 15, (int) (bounds.right() + mobOffset.x()), (int) (bounds.bottom() + mobOffset.y()),
                scale + (int) mobOffset.scale(),
                0, -yawRadians, entity);
        guiGraphics.disableScissor();
        guiGraphics.pose().popPose();
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int scale, float yOffset, float mouseX, LivingEntity entity) {
        float f = (float)(x1 + x2) / 2.0F;
        float f2 = (float)Math.atan((f - mouseX) / 40.0F);
        renderEntityInInventoryFollowsAngle(guiGraphics, x1, y1, x2, y2, scale, yOffset, f2, entity);
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphics guiGraphics, int x1, int y1, int x2, int y2, int scale, float yOffset, float angleXComponent, LivingEntity livingEntity) {
        float f = (float)(x1 + x2) / 2.0F;
        float f1 = (float)(y1 + y2) / 2.0F;
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        float f4 = livingEntity.yBodyRot;
        float f5 = livingEntity.getYRot();
        float f7 = livingEntity.yHeadRotO;
        float f8 = livingEntity.yHeadRot;
        livingEntity.yBodyRot = 180.0F - angleXComponent * 20.0F;
        livingEntity.setYRot(180.0F - angleXComponent * 40.0F);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();
        float f9 = livingEntity.getScale();
        Vector3f vector3f = new Vector3f(0.0F, livingEntity.getBbHeight() / 2.0F + yOffset * f9, 0.0F);
        float f10 = (float)scale / f9;
        InventoryScreen.renderEntityInInventory(guiGraphics, f, f1, f10, vector3f, quaternionf, new Quaternionf(), livingEntity);
        livingEntity.yBodyRot = f4;
        livingEntity.setYRot(f5);
        livingEntity.yHeadRotO = f7;
        livingEntity.yHeadRot = f8;
    }

}
