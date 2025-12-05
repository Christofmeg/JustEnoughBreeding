package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.client.ClientUtils;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonClientUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class REIUtils {

    public static void registerRecipes(DisplayRegistry registration) {
        List<AllayDuplicationRecipe> allayDuplicationRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE.get()));
        allayDuplicationRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
        for (AllayDuplicationRecipe recipe : allayDuplicationRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                registration.add(new AllayDuplicationDisplay(recipe));
            }
        }

        List<BreedingRecipe> breedingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get()));
        breedingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
        for (BreedingRecipe recipe : breedingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                registration.add(new BreedingDisplay(recipe));
            }
        }

        List<TamingRecipe> tamingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE.get()));
        tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
        for (TamingRecipe recipe : tamingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                registration.add(new TamingDisplay(recipe));
            }
        }

        List<TemperRecipe> temperRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get()));
        temperRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
        for (TemperRecipe recipe : temperRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                registration.add(new TemperDisplay(recipe));
            }
        }

        List<TransformationRecipe> transformationRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get()));
        transformationRecipes.sort(Comparator.comparing(r -> r.outputEntityType == null ? r.fileName : r.outputEntityType.toShortString()));
        for (TransformationRecipe recipe : transformationRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.inputEntityType != null && recipe.outputEntityType != null && !recipe.inputStack.isEmpty()) {
                registration.add(new TransformationDisplay(recipe));
            }
        }

        List<TrustingRecipe> trustingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get()));
        trustingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
        for (TrustingRecipe recipe : trustingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                registration.add(new TrustingDisplay(recipe));
            }
        }
    }

    public static void drawMobSlot(List<Widget> widgets, Rectangle bounds, int mobSlotX, int mobSlotY) {
        widgets.add(Widgets.createSlotBase(new Rectangle(bounds.x + 5 + mobSlotX, bounds.y + 5 + mobSlotY, 61, 81)));
    }

    public static void drawMobNameAndEntity(List<Widget> widgets, Rectangle bounds, EntityType<?> entityType, BaseRecipe recipe) {
        drawMobNameAndEntity(widgets, bounds, entityType, recipe, 148, 0);
    }

    public static void drawMobNameAndEntity(List<Widget> widgets, Rectangle bounds, EntityType<?> entityType, BaseRecipe recipe, int availableWidth, int extraX) {
        drawMobNameAndEntity(widgets, bounds, entityType, recipe, availableWidth, extraX, true, null);
    }

    public static void drawMobNameAndEntity(List<Widget> widgets, Rectangle bounds, EntityType<?> entityType, BaseRecipe recipe, int availableWidth, int extraX, boolean input, DyeColor color) {
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
                widgets.add(Widgets.createLabel(new Point(bounds.x + extraX + 5, bounds.y + 5), abbreviatedEntityName).leftAligned().noShadow().color(0xFF404040, 0xFFBBBBBB));
            }

            LivingEntity currentLivingEntity = recipe instanceof TransformationRecipe ? ClientUtils.doRendering(entityType, input, color) : ClientUtils.doRendering(entityType);
            widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((graphics, mouseX, mouseY, v) -> {
                        if (currentLivingEntity != null) {
                            if (input) {
                                if (recipe instanceof TransformationRecipe transformationRecipe) {
                                    if (transformationRecipe.inputEntityNbt != null) {
                                        currentLivingEntity.load(transformationRecipe.inputEntityNbt);
                                    }
                                }
                            } else {
                                if (recipe instanceof AllayDuplicationRecipe allayDuplicationRecipe) {
                                    if (allayDuplicationRecipe.outputEntityNbt != null) {
                                        currentLivingEntity.load(allayDuplicationRecipe.outputEntityNbt);
                                    }
                                }
                                if (recipe instanceof BreedingRecipe breedingRecipe) {
                                    if (breedingRecipe.outputEntityNbt != null) {
                                        currentLivingEntity.load(breedingRecipe.outputEntityNbt);
                                    }
                                }
                                if (recipe instanceof TamingRecipe tamingRecipe) {
                                    if (tamingRecipe.outputEntityNbt != null) {
                                        currentLivingEntity.load(tamingRecipe.outputEntityNbt);
                                    }
                                }
                                if (recipe instanceof TemperRecipe temperRecipe) {
                                    if (temperRecipe.outputEntityNbt != null) {
                                        currentLivingEntity.load(temperRecipe.outputEntityNbt);
                                    }
                                }
                                if (recipe instanceof TransformationRecipe transformationRecipe) {
                                    if (transformationRecipe.outputEntityNbt != null) {
                                        currentLivingEntity.load(transformationRecipe.outputEntityNbt);
                                    }
                                }
                                if (recipe instanceof TrustingRecipe trustingRecipe) {
                                    if (trustingRecipe.outputEntityNbt != null) {
                                        currentLivingEntity.load(trustingRecipe.outputEntityNbt);
                                    }
                                }
                            }
                            CommonClientUtils.renderEntity(graphics.pose(), mouseX, currentLivingEntity, 31 + extraX, 89);
                        }
                    }
            ), bounds.x + 5, bounds.y + 5, 0));
        }
    }
}
