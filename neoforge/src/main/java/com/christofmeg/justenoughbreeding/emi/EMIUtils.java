package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.client.ClientUtils;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonClientUtils;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.ButtonWidget;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EMIUtils {

    public static void registerRecipes(EmiRegistry registration) {

        ArrayList<RecipeHolder<AllayDuplicationRecipe>> allayDuplicationRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE.get()));
        allayDuplicationRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
        for (RecipeHolder<AllayDuplicationRecipe> recipeHold : allayDuplicationRecipes) {
            AllayDuplicationRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                registration.addRecipe(
                        AllayDuplicationCategoryEMI.builder()
                                .id(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "/" + recipeHold.id().getPath()))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        ArrayList<RecipeHolder<BreedingRecipe>> breedingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get()));
        breedingRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
        for (RecipeHolder<BreedingRecipe> recipeHold : breedingRecipes) {
            BreedingRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                registration.addRecipe(
                        BreedingCategoryEMI.builder()
                                .id(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "/" + recipeHold.id().getPath()))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        ArrayList<RecipeHolder<TamingRecipe>> tamingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE.get()));
        tamingRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
        for (RecipeHolder<TamingRecipe> recipeHold : tamingRecipes) {
            TamingRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                registration.addRecipe(
                        TamingCategoryEMI.builder()
                                .id(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "/" + recipeHold.id().getPath()))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        ArrayList<RecipeHolder<TemperRecipe>> temperRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get()));
        temperRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
        for (RecipeHolder<TemperRecipe> recipeHold : temperRecipes) {
            TemperRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                registration.addRecipe(
                        TemperCategoryEMI.builder()
                                .id(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "/" + recipeHold.id().getPath()))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        ArrayList<RecipeHolder<TransformationRecipe>> transformationRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get()));
        transformationRecipes.sort(Comparator.comparing(r -> r.value().outputEntityType == null ? r.value().fileName : r.value().outputEntityType.toShortString()));
        for (RecipeHolder<TransformationRecipe> recipeHold : transformationRecipes) {
            TransformationRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.inputEntityType != null && recipe.outputEntityType != null && !recipe.inputStack.isEmpty()) {
                registration.addRecipe(
                        TransformationCategoryEMI.builder()
                                .id(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "/" + "transformation" + "/" + recipe.jsonModID + "/" + recipe.modFolder + "/" + recipe.fileName))
                                .recipe(recipe)
                                .build()
                );
            }
        }

        ArrayList<RecipeHolder<TrustingRecipe>>trustingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get()));
        trustingRecipes.sort(Comparator.comparing(r -> r.value().jsonAnimalID == null ? "" : r.value().jsonAnimalID));
        for (RecipeHolder<TrustingRecipe> recipeHold : trustingRecipes) {
            TrustingRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                registration.addRecipe(
                        TrustingCategoryEMI.builder()
                                .id(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "/" + "trusting" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .recipe(recipe)
                                .build()
                );
            }
        }
    }

    public static void drawMobSlot(int mobSlotX, int mobSlotY, WidgetHolder widgets) {
        EmiTexture TOP = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 0, 24, 1);
        EmiTexture CORNER = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 0, 1, 1);
        EmiTexture LEFT = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 1, 25);
        EmiTexture RIGHT = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 1, 1, 25);
        EmiTexture BOTTOM = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 25, 25, 1);

        widgets.addTexture(TOP, mobSlotX + 2, mobSlotY + 1);
        widgets.addTexture(TOP, mobSlotX + 26, mobSlotY + 1);
        widgets.addTexture(new EmiTexture(EmiRenderHelper.WIDGETS, 19, 0, 11, 1), mobSlotX + 50, mobSlotY + 1);
        widgets.addTexture(CORNER, mobSlotX + 61, mobSlotY + 1);

        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 2);
        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 27);
        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 52);
        widgets.addTexture(LEFT, mobSlotX + 1, mobSlotY + 56);

        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 2);
        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 27);
        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 52);
        widgets.addTexture(RIGHT, mobSlotX + 61, mobSlotY + 56);

        int color = CommonClientUtils.getPixelColor(ResourceLocation.fromNamespaceAndPath("emi", "textures/gui/widgets.png"), 30 ,12);
        if (color == 0) color = -7631989;
        int startX = mobSlotX + 1;
        int startY = mobSlotY - 4;
        int width  = 59;
        int height = 79;
        int finalColor = color;
        widgets.addDrawable(1, startY, width, height, (guiGraphics, mouseX, mouseY, delta) -> guiGraphics.fill(startX, startY, startX + width, startY + height, finalColor));

        widgets.addTexture(CORNER, mobSlotX + 1, mobSlotY + 81);
        widgets.addTexture(BOTTOM, mobSlotX + 2, mobSlotY + 81);
        widgets.addTexture(BOTTOM, mobSlotX + 27, mobSlotY + 81);
        widgets.addTexture(BOTTOM, mobSlotX + 37, mobSlotY + 81);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, WidgetHolder widgets, Recipe<?> recipe, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(entityType, widgets, recipe, 148, 0, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, WidgetHolder widgets, Recipe<?> recipe, int availableWidth, int extraX, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(entityType, widgets, recipe, availableWidth, extraX, true, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, WidgetHolder widgets, Recipe<?> recipe, int availableWidth, int extraX, boolean input, int CATEGORY_WIDTH) {
        if (entityType != null) {
            Font font = Minecraft.getInstance().font;
            Component entityName = Component.translatable(entityType.getDescriptionId());
            String entityNameString = entityName.getString();
            if (recipe instanceof BreedingRecipe breedingRecipe) {
                if (breedingRecipe.tamed() != null && breedingRecipe.tamed()) {
                    Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                    entityNameString += " (" + tamed.getString() + ")";
                } else if (breedingRecipe.trusting() != null && breedingRecipe.trusting()) {
                    Component trusting = Component.translatable("translation.justenoughbreeding.trusting");
                    entityNameString += " (" + trusting.getString() + ")";
                } else if ("tfc".equals(breedingRecipe.mod())) {
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
                widgets.addText(EmiPort.ordered(abbreviatedEntityName), extraX + 1, 1, -1, true);
            }

            widgets.add(new Widget() {
                @Override
                public Bounds getBounds() { return new Bounds(100, 0, 60, 80); }

                @Override
                public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
                    LivingEntity currentLivingEntity;
                    if (recipe instanceof TransformationRecipe transformationRecipe) {
                        if (input) {
                            currentLivingEntity = ClientUtils.doRendering(transformationRecipe.inputEntityType, transformationRecipe.inputEntityNbt, true);
                        } else {
                            currentLivingEntity = ClientUtils.doRendering(transformationRecipe.outputEntityType, transformationRecipe.outputEntityNbt, false);
                        }
                    } else {
                        currentLivingEntity = ClientUtils.doRendering(entityType);
                    }
                    LivingEntity livingEntity = Utils.getLivingEntity(currentLivingEntity, input, recipe);
                    if (livingEntity != null) {
                        Utils.renderEntityInInventoryFollowsMouse(graphics, (float) mouseX, livingEntity, CommonUtils.getRect(input, CATEGORY_WIDTH, 0, 1), entityType);
                    }
                }
            });
        }
    }

    public static void addButton(WidgetHolder widgets, EntityType<?> entityType) {
        addButton(widgets, entityType, 0);
    }

    public static void addButton(WidgetHolder widgets, EntityType<?> entityType, int xOffset) {
        ResourceLocation entity = JustEnoughBreeding.getKeyLoaderRegistries(entityType);
        widgets.add(new EMIToggleButtonWidget(4 + xOffset, 14, (mouseX1, mouseY1, button1) -> {
            if (InputConstants.MOUSE_BUTTON_LEFT == button1) {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() + 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
            } else {
                MobOffsetManager.updateOffset(entity, 0, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
            }
        }, Component.translatable("option.justenoughbreeding.increase_scale")));

        widgets.add(new EMIToggleButtonWidget(15 + xOffset, 14, (mouseX1, mouseY1, button1) -> {
            if (InputConstants.MOUSE_BUTTON_LEFT == button1) {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() - 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
            } else {
                MobOffsetManager.updateOffset(entity, 0, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
            }
        }, Component.translatable("option.justenoughbreeding.decrease_scale")));

        widgets.add(new EMIToggleButtonWidget(26 + xOffset, 14, (mouseX1, mouseY1, button1) -> {
            if (InputConstants.MOUSE_BUTTON_LEFT == button1) {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() - 1, MobOffsetManager.get(entity).y());
            } else {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), 0, MobOffsetManager.get(entity).y());
            }
        }, Component.translatable("option.justenoughbreeding.move_left")));

        widgets.add(new EMIToggleButtonWidget(37 + xOffset, 14, (mouseX1, mouseY1, button1) -> {
            if (InputConstants.MOUSE_BUTTON_LEFT == button1) {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() + 1, MobOffsetManager.get(entity).y());
            } else {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), 0, MobOffsetManager.get(entity).y());
            }
        }, Component.translatable("option.justenoughbreeding.move_right")));

        widgets.add(new EMIToggleButtonWidget(49 + xOffset, 25, (mouseX1, mouseY1, button1) -> {
            if (InputConstants.MOUSE_BUTTON_LEFT == button1) {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() - 1);
            } else {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), 0);
            }
        }, Component.translatable("option.justenoughbreeding.move_up")));

        widgets.add(new EMIToggleButtonWidget(49 + xOffset, 36, (mouseX1, mouseY1, button1) -> {
            if (InputConstants.MOUSE_BUTTON_LEFT == button1) {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() + 1);
            } else {
                MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), 0);
            }
        }, Component.translatable("option.justenoughbreeding.move_down")));

        widgets.add(new ButtonWidget(49 + xOffset, 14, 10, 10, 0, 0, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "textures/emi_config_button.png"), () -> true, (mouseX, mouseY, button) -> EMIToggleButtonWidget.showChildButtons = !EMIToggleButtonWidget.showChildButtons) {
            @Override
            public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
                return EMIToggleButtonWidget.showChildButtons ?
                        List.of(ClientTooltipComponent.create(EmiPort.ordered(Component.translatable("option.justenoughbreeding.hide_options")))) :
                        List.of(ClientTooltipComponent.create(EmiPort.ordered(Component.translatable("option.justenoughbreeding.show_options"))));
            }
        });
    }

}
