package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EMIUtils {

    public static void registerRecipes(EmiRegistry registration) {
        List<AllayDuplicationRecipe> allayDuplicationRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE));
        allayDuplicationRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (AllayDuplicationRecipe recipe : allayDuplicationRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        AllayDuplicationCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "allay_duplication" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        List<BreedingRecipe> breedingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE));
        breedingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (BreedingRecipe recipe : breedingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        BreedingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "breeding" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        List<TamingRecipe> tamingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE));
        tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (TamingRecipe recipe : tamingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        TamingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "taming" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        List<TemperRecipe> temperRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE));
        tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (TemperRecipe recipe : temperRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        TemperCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "temper" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        List<TransformationRecipe> transformationRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE));
        transformationRecipes.sort(Comparator.comparing(r -> r.outputEntityType == null ? r.fileName: r.outputEntityType.toShortString()));
        for (TransformationRecipe recipe : transformationRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.inputEntityType != null && recipe.outputEntityType != null) {
                registration.addRecipe(
                        TransformationCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "transformation" + "/" + recipe.jsonModID + "/" + recipe.modFolder + "/" + recipe.fileName))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        List<TrustingRecipe> trustingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE));
        trustingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (TrustingRecipe recipe : trustingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        TrustingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "trusting" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .recipe(recipe)
                                .build()
                );
            }
        }
    }

    public static void drawMobSlot(int mobSlotX, int mobSlotY, WidgetHolder widgets) {
        EmiTexture TOP = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 25, 1);
        EmiTexture CORNER = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 0, 1, 1);
        EmiTexture LEFT = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 1, 25);
        EmiTexture RIGHT = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 1, 1, 25);
        EmiTexture BOTTOM = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 25, 25, 1);
        EmiTexture BACKGROUND = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 1, 24, 24);

        widgets.addTexture(TOP, mobSlotX + 1, mobSlotY + 1);
        widgets.addTexture(TOP, mobSlotX + 26, mobSlotY + 1);
        widgets.addTexture(TOP, mobSlotX + 36, mobSlotY + 1);
        widgets.addTexture(CORNER, mobSlotX + 61, mobSlotY + 1);

        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 2);
        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 27);
        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 52);
        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 56);

        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 2);
        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 27);
        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 52);
        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 56);

        widgets.addTexture(BACKGROUND, mobSlotX + 2, mobSlotY + 2);
        widgets.addTexture(BACKGROUND, mobSlotX + 26, mobSlotY + 2);
        widgets.addTexture(BACKGROUND, mobSlotX + 37, mobSlotY + 2);
        widgets.addTexture(BACKGROUND, mobSlotX + 2, mobSlotY + 26);
        widgets.addTexture(BACKGROUND, mobSlotX + 26, mobSlotY + 26);
        widgets.addTexture(BACKGROUND, mobSlotX + 37, mobSlotY + 26);
        widgets.addTexture(BACKGROUND, mobSlotX + 2, mobSlotY + 50);
        widgets.addTexture(BACKGROUND, mobSlotX + 26, mobSlotY + 50);
        widgets.addTexture(BACKGROUND, mobSlotX + 37, mobSlotY + 50);
        widgets.addTexture(BACKGROUND, mobSlotX + 2, mobSlotY + 57);
        widgets.addTexture(BACKGROUND, mobSlotX + 26, mobSlotY + 57);
        widgets.addTexture(BACKGROUND, mobSlotX + 37, mobSlotY + 57);

        widgets.addTexture(CORNER, mobSlotX + 1, mobSlotY + 81);
        widgets.addTexture(BOTTOM, mobSlotX + 2, mobSlotY + 81);
        widgets.addTexture(BOTTOM, mobSlotX + 27, mobSlotY + 81);
        widgets.addTexture(BOTTOM, mobSlotX + 37, mobSlotY + 81);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, WidgetHolder widgets, FabricRecipe recipe) {
        drawMobNameAndEntity(entityType, widgets, recipe, 148, 0);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, WidgetHolder widgets, FabricRecipe recipe, int availableWidth, int extraX) {
        drawMobNameAndEntity(entityType, widgets, recipe, availableWidth, extraX, true, null);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, WidgetHolder widgets, FabricRecipe recipe, int availableWidth, int extraX, boolean input, DyeColor color) {
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
                widgets.addText(EmiPort.ordered(abbreviatedEntityName), extraX + 1, 1, -1, true);
            }

            widgets.add(new Widget() {
                @Override
                public Bounds getBounds() {
                    return new Bounds(100, 0, 60, 80);
                }

                @Override
                public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float delta) {
                    LivingEntity currentLivingEntity = recipe instanceof TransformationRecipe ? ((TransformationRecipe) recipe).doRendering(entityType, input, color) : recipe.doRendering(entityType);

                    if (currentLivingEntity != null) {
                        CommonUtils.renderEntity(stack.pose(), mouseX, currentLivingEntity, 31 + extraX, 89);
                    }
                }
            });

        }
    }

}
