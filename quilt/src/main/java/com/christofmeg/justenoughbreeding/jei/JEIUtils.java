package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.client.ClientUtils;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonClientUtils;
import com.mojang.blaze3d.platform.NativeImage;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JEIUtils {

    public static void registerRecipes(IRecipeRegistration registration) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            List<AllayDuplicationRecipe> allayDuplicationRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE));
            allayDuplicationRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (AllayDuplicationRecipe recipe : allayDuplicationRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(AllayDuplicationCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<BreedingRecipe> breedingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE));
            breedingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (BreedingRecipe recipe : breedingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TamingRecipe> tamingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE));
            tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (TamingRecipe recipe : tamingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(TamingCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TemperRecipe> temperRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE));
            temperRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (TemperRecipe recipe : temperRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(TemperCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TransformationRecipe> transformationRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE));
            transformationRecipes.sort(Comparator.comparing(r -> r.outputEntityType == null ? r.fileName : r.outputEntityType.toShortString()));
            for (TransformationRecipe recipe : transformationRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.inputEntityType != null && recipe.outputEntityType != null) {
                    registration.addRecipes(TransformationCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TrustingRecipe> trustingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE));
            trustingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (TrustingRecipe recipe : trustingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(TrustingCategory.TYPE, Collections.singletonList(recipe));
                }
            }
        }
    }

    public static void drawMobSlot(int mobSlotX, int mobSlotY, IDrawableStatic bigSlot, GuiGraphics stack) {
        // Left
        draw(bigSlot, stack, mobSlotX, mobSlotY, bigSlot.getHeight(), 0, 0, 1, 25);
        draw(bigSlot, stack, mobSlotX, mobSlotY + 25, bigSlot.getHeight(), 0, 0, 1, 25);
        draw(bigSlot, stack, mobSlotX, mobSlotY + 50, bigSlot.getHeight(), 0, 0, 1, 25);
        draw(bigSlot, stack, mobSlotX, mobSlotY + 55, bigSlot.getHeight(), 0, 20, 1, 6);

        // Right
        draw(bigSlot, stack, mobSlotX + 35, mobSlotY, bigSlot.getHeight(), 25, 0, 1, 25);
        draw(bigSlot, stack, mobSlotX + 35, mobSlotY + 24, bigSlot.getHeight(), 25, 1, 1, 25);
        draw(bigSlot, stack, mobSlotX + 35, mobSlotY + 49, bigSlot.getHeight(), 25, 1, 1, 25);
        draw(bigSlot, stack, mobSlotX + 35, mobSlotY + 55, bigSlot.getHeight(), 25, 19, 1, 6);

        // Top
        draw(bigSlot, stack, mobSlotX, mobSlotY, bigSlot.getHeight(), 1, 0, 24, 1);
        draw(bigSlot, stack, mobSlotX + 24, mobSlotY, bigSlot.getHeight(), 1, 0, 24, 1);
        draw(bigSlot, stack, mobSlotX + 35, mobSlotY, bigSlot.getHeight(), 14, 0, 11, 1);

        // Bottom
        draw(bigSlot, stack, mobSlotX, mobSlotY + 55, bigSlot.getHeight(), 1, 25, 24, 1);
        draw(bigSlot, stack, mobSlotX + 24, mobSlotY + 55, bigSlot.getHeight(), 1, 25, 24, 1);
        draw(bigSlot, stack, mobSlotX + 35, mobSlotY + 55, bigSlot.getHeight(), 14, 25, 11, 1);

        int color = getPixelColor(new ResourceLocation("jei", "textures/jei/atlas/gui/output_slot.png"), 13 ,13);
        int startX = mobSlotX + 1;
        int startY = mobSlotY + 1;
        int width  = 59;
        int height = 79;
        fillSolidColor(stack, startX, startY, width, height, color);
    }

    private static void draw(IDrawableStatic slot, GuiGraphics stack, int mobSlotX, int mobSlotY, int textureSize, int removeFromLeft, int removeFromTop, int selectionX, int selectionY) {
        int removeFromBottom = textureSize - (removeFromTop + selectionY);
        int removeFromRight = textureSize - (removeFromLeft + selectionX);
        slot.draw(stack, mobSlotX, mobSlotY, removeFromTop, removeFromBottom, removeFromLeft, removeFromRight);
    }

    private static void fillSolidColor(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.fill(x, y, x + width, y + height, color);
    }

    private static int getPixelColor(ResourceLocation texture, int px, int py) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        try {
            Resource resource = resourceManager.getResource(texture).orElse(null);
            if (resource == null) return 0xFFFFFFFF; // fallback

            try (InputStream is = resource.open()) { // use resource.open(), not getInputStream
                NativeImage image = NativeImage.read(is);
                return image.getPixelRGBA(px, py);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0xFFFFFFFF; // fallback color
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics stack, double mouseX, BaseRecipe recipe) {
        drawMobNameAndEntity(entityType, stack, mouseX, recipe, 148, 0);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics stack, double mouseX, BaseRecipe recipe, int availableWidth, int extraX) {
        drawMobNameAndEntity(entityType, stack, mouseX, recipe, availableWidth, extraX, true, null);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics stack, double mouseX, BaseRecipe recipe, int availableWidth, int extraX, boolean input, DyeColor color) {
        if (entityType != null) {
            Font font = Minecraft.getInstance().font;
            Component entityName = Component.translatable(entityType.getDescriptionId());
            String entityNameString = entityName.getString();
            if (recipe instanceof BreedingRecipe breedingRecipe) {
                if (breedingRecipe.needsToBeTamed != null && breedingRecipe.needsToBeTamed) {
                    Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                    entityNameString += " (" + tamed.getString() + ")";
                } else if (breedingRecipe.animalTrusting != null && breedingRecipe.animalTrusting) {
                    Component trusting = Component.translatable("translation.justenoughbreeding.trusting");
                    entityNameString += " (" + trusting.getString() + ")";
                } else if ("tfc".equals(breedingRecipe.jsonModID)) {
                    Component familiarity = Component.translatable("tfc.jade.familiarity");
                    String tfc = familiarity.getString().replaceAll(":[^:]*$", "");
                    entityNameString += " (" + tfc + " > 30" + ")";
                }
            } else if (recipe instanceof TransformationRecipe transformationRecipe && input) {
                if (transformationRecipe.needsToBeTamed != null && transformationRecipe.needsToBeTamed) {
                    Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                    entityNameString += " (" + tamed.getString() + ")";
                }
            }

            int stringWidth = font.width(entityNameString);
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / Math.max(1, entityNameString.length());
                int maxCharacters = Math.max(0, (int) (availableWidth / pixelWidthPerCharacter));
                entityNameString = entityNameString.substring(0, Math.min(entityNameString.length(), maxCharacters));
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                stack.drawString(font, abbreviatedEntityName, extraX, 0, DyeColor.BLACK.getTextColor(), false);
            }

            LivingEntity currentLivingEntity = recipe instanceof TransformationRecipe ? ClientUtils.doRendering(entityType, input, color) : ClientUtils.doRendering(entityType);
            if (currentLivingEntity != null) {
                CommonClientUtils.renderEntity(stack.pose(), mouseX, currentLivingEntity, 31 + extraX, 89);
            }
        }
    }
}