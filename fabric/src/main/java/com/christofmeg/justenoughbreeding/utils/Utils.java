package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.config.MobOffset;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Utils {

    public static <T extends Recipe<?>> RecipeType<T> simple(final ResourceLocation name) {
        final String toString = name.toString();
        return new RecipeType<T>() {
            @Override
            public String toString() {
                return toString;
            }
        };
    }

    public static LivingEntity getLivingEntity(LivingEntity currentLivingEntity, boolean input, Recipe<?> recipe) {
        if (currentLivingEntity != null) {
            if (input) {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(transformationRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof AllayDuplicationRecipe allayDuplicationRecipe) {
                    if (allayDuplicationRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(allayDuplicationRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof BreedingRecipe breedingRecipe) {
                    if (breedingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(breedingRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof TamingRecipe tamingRecipe) {
                    if (tamingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(tamingRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof TemperRecipe temperRecipe) {
                    if (temperRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(temperRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof TrustingRecipe trustingRecipe) {
                    if (trustingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(trustingRecipe.inputEntityNbt());
                    }
                }
            } else {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.outputEntityNbt() != null) {
                        currentLivingEntity.load(transformationRecipe.outputEntityNbt());
                    }
                }
            }
        }
        return currentLivingEntity;
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, float mouseX, LivingEntity entity, Rect bounds, EntityType<?> entityType) {
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
        MobOffset mobOffset = MobOffsetManager.get(JustEnoughBreeding.getKeyLoaderRegistries(entityType));
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
