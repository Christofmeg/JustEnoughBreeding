package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class JEIUtils {

    public static void registerRecipes(IRecipeRegistration registration) {
        ClientLevel clientLevel = Minecraft.getInstance().level;
        if (clientLevel != null) {
            List<AllayDuplicationRecipe> allayDuplicationRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE));
            allayDuplicationRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
            for (AllayDuplicationRecipe recipe : allayDuplicationRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(AllayDuplicationCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<BreedingRecipe> breedingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE));
            breedingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
            for (BreedingRecipe recipe : breedingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TamingRecipe> tamingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE));
            tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
            for (TamingRecipe recipe : tamingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(TamingCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TemperRecipe> temperRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE));
            temperRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
            for (TemperRecipe recipe : temperRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(TemperCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TransformationRecipe> transformationRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE));
            transformationRecipes.sort(Comparator.comparing(r -> r.outputEntityType == null ? r.fileName: r.outputEntityType.toShortString()));
            for (TransformationRecipe recipe : transformationRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.inputEntityType != null && recipe.outputEntityType != null) {
                    registration.addRecipes(TransformationCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TrustingRecipe> trustingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE));
            trustingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
            for (TrustingRecipe recipe : trustingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                    registration.addRecipes(TrustingCategory.TYPE, Collections.singletonList(recipe));
                }
            }
        }
    }

    public static void drawMobSlot(int mobSlotX, int mobSlotY, IDrawableStatic bigSlot, GuiGraphics stack) {
        bigSlot.draw(stack, mobSlotX, mobSlotY, 0, 1, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY, 0, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY, 0, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY, 0, 1, 22, 0);

        bigSlot.draw(stack, mobSlotX, mobSlotY + 24, 1, 1, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY + 24, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY + 24, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY + 24, 1, 1, 22, 0);

        bigSlot.draw(stack, mobSlotX, mobSlotY + 48, 1, 1, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY + 48, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY + 48, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY + 48, 1, 1, 22, 0);

        bigSlot.draw(stack, mobSlotX, mobSlotY + 55, 18, 0, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY + 55, 18, 0, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY + 55, 18, 0, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY + 55, 18, 0, 22, 0);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics stack, double mouseX, FabricRecipe recipe) {
        drawMobNameAndEntity(entityType, stack, mouseX, recipe, 148, 0);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics stack, double mouseX, FabricRecipe recipe, int availableWidth, int extraX) {
        drawMobNameAndEntity(entityType, stack, mouseX, recipe, availableWidth, extraX, true, null);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics stack, double mouseX, FabricRecipe recipe, int availableWidth, int extraX, boolean input, DyeColor color) {
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
                } else if (breedingRecipe.jsonModID.equals("tfc")) {
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

            int stringWidth = font.width(entityNameString); // Measure the width of the string in pixels
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / entityNameString.length();
                int maxCharacters = (int) (availableWidth / pixelWidthPerCharacter);
                entityNameString = entityNameString.substring(0, maxCharacters);
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                stack.drawString(font, abbreviatedEntityName, extraX, 0, DyeColor.BLACK.getTextColor(), false);
            }

            LivingEntity currentLivingEntity = recipe instanceof TransformationRecipe ? ((TransformationRecipe) recipe).doRendering(entityType, input, color) : recipe.doRendering(entityType);
            if (currentLivingEntity != null) {
                CommonUtils.renderEntity(stack.pose(), mouseX, currentLivingEntity, 31 + extraX, 89);
            }
        }
    }

}
