package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.client.ClientUtils;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonClientUtils;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

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
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                    registration.addRecipes(AllayDuplicationCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<BreedingRecipe> breedingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE));
            breedingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (BreedingRecipe recipe : breedingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                    registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TamingRecipe> tamingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE));
            tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (TamingRecipe recipe : tamingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                    registration.addRecipes(TamingCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TemperRecipe> temperRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE));
            temperRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (TemperRecipe recipe : temperRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                    registration.addRecipes(TemperCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TransformationRecipe> transformationRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE));
            transformationRecipes.sort(Comparator.comparing(r -> r.outputEntityType == null ? r.fileName : r.outputEntityType.toShortString()));
            for (TransformationRecipe recipe : transformationRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.inputEntityType != null && recipe.outputEntityType != null && !recipe.inputStack.isEmpty()) {
                    registration.addRecipes(TransformationCategory.TYPE, Collections.singletonList(recipe));
                }
            }

            List<TrustingRecipe> trustingRecipes = new ArrayList<>(clientLevel.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE));
            trustingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID == null ? "" : r.jsonAnimalID));
            for (TrustingRecipe recipe : trustingRecipes) {
                if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null && !recipe.inputStack.isEmpty()) {
                    registration.addRecipes(TrustingCategory.TYPE, Collections.singletonList(recipe));
                }
            }
        }
    }

    public static void drawMobSlot(int mobSlotX, int mobSlotY, IDrawableStatic bigSlot, GuiGraphics graphics) {
        // Left
        draw(bigSlot, graphics, mobSlotX, mobSlotY, bigSlot.getHeight(), 0, 0, 1, 25);
        draw(bigSlot, graphics, mobSlotX, mobSlotY + 25, bigSlot.getHeight(), 0, 0, 1, 25);
        draw(bigSlot, graphics, mobSlotX, mobSlotY + 50, bigSlot.getHeight(), 0, 0, 1, 25);
        draw(bigSlot, graphics, mobSlotX, mobSlotY + 55, bigSlot.getHeight(), 0, 20, 1, 6);

        // Right
        draw(bigSlot, graphics, mobSlotX + 35, mobSlotY, bigSlot.getHeight(), 25, 0, 1, 25);
        draw(bigSlot, graphics, mobSlotX + 35, mobSlotY + 24, bigSlot.getHeight(), 25, 1, 1, 25);
        draw(bigSlot, graphics, mobSlotX + 35, mobSlotY + 49, bigSlot.getHeight(), 25, 1, 1, 25);
        draw(bigSlot, graphics, mobSlotX + 35, mobSlotY + 55, bigSlot.getHeight(), 25, 19, 1, 6);

        // Top
        draw(bigSlot, graphics, mobSlotX, mobSlotY, bigSlot.getHeight(), 1, 0, 24, 1);
        draw(bigSlot, graphics, mobSlotX + 24, mobSlotY, bigSlot.getHeight(), 1, 0, 24, 1);
        draw(bigSlot, graphics, mobSlotX + 35, mobSlotY, bigSlot.getHeight(), 14, 0, 11, 1);

        // Bottom
        draw(bigSlot, graphics, mobSlotX, mobSlotY + 55, bigSlot.getHeight(), 1, 25, 24, 1);
        draw(bigSlot, graphics, mobSlotX + 24, mobSlotY + 55, bigSlot.getHeight(), 1, 25, 24, 1);
        draw(bigSlot, graphics, mobSlotX + 35, mobSlotY + 55, bigSlot.getHeight(), 14, 25, 11, 1);

        int color = CommonClientUtils.getPixelColor(new ResourceLocation("jei", "textures/jei/atlas/gui/output_slot.png"), 13 ,13);
        int startX = mobSlotX + 1;
        int startY = mobSlotY + 1;
        int width  = 59;
        int height = 79;
        CommonClientUtils.fillSolidColor(graphics, startX, startY, width, height, color);
    }

    private static void draw(IDrawableStatic slot, GuiGraphics graphics, int mobSlotX, int mobSlotY, int textureSize, int removeFromLeft, int removeFromTop, int selectionX, int selectionY) {
        int removeFromBottom = textureSize - (removeFromTop + selectionY);
        int removeFromRight = textureSize - (removeFromLeft + selectionX);
        slot.draw(graphics, mobSlotX, mobSlotY, removeFromTop, removeFromBottom, removeFromLeft, removeFromRight);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics graphics, double mouseX, BaseRecipe recipe, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(entityType, graphics, mouseX, recipe, 148, 0, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics graphics, double mouseX, BaseRecipe recipe, int availableWidth, int extraX, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(entityType, graphics, mouseX, recipe, availableWidth, extraX, true, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics graphics, double mouseX, BaseRecipe recipe, int availableWidth, int extraX, boolean input, int CATEGORY_WIDTH) {
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
                graphics.drawString(font, abbreviatedEntityName, extraX, 0, DyeColor.BLACK.getTextColor(), false);
            }

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
                Utils.renderEntityInInventoryFollowsMouse(graphics, 0, 0, (float) mouseX, livingEntity, CommonUtils.getRect(input, CATEGORY_WIDTH), entityType);
            }
        }
    }

    public static void addButton(IRecipeExtrasBuilder builder, EntityType<?> entityType) {
        addButton(builder, entityType, 0);
    }

    public static void addButton(IRecipeExtrasBuilder builder, EntityType<?> entityType, int xOffset) {
        JeiToggleButtonWidget button = new JeiToggleButtonWidget(48 + xOffset, 13, 10, 10);
        builder.addWidget(button);

        builder.addInputHandler(new IJeiInputHandler() {
            @Override
            public @NotNull ScreenRectangle getArea() {
                return new ScreenRectangle(48 + xOffset, 13, 10, 10);
            }
            @Override
            public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {
                if (input.isSimulate()) return false;
                if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_LEFT && input.getKey().getValue() == InputConstants.RELEASE) {
                    button.toggle();
                    return true;
                }
                return false;
            }
        });

        for (int i = 0; i < 4; i++) {
            int x = 3 + (i * 11);
            int y = 13;

            Component component = switch (i) {
                case 0 -> Component.translatable("option.justenoughbreeding.increase_scale");
                case 1 -> Component.translatable("option.justenoughbreeding.decrease_scale");
                case 2 -> Component.translatable("option.justenoughbreeding.move_left");
                default -> Component.translatable("option.justenoughbreeding.move_right");
            };

            JeiChildButtonWidget child = new JeiChildButtonWidget(x + xOffset, y, 10, 10, button, component);
            builder.addWidget(child);

            int finalI = i;
            builder.addInputHandler(new IJeiInputHandler() {
                @Override
                public @NotNull ScreenRectangle getArea() {
                    return new ScreenRectangle(x + xOffset, y, 10, 10);
                }

                @Override
                public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {
                    if (button.isToggled()) return false;
                    if (input.isSimulate()) return false;
                    ResourceLocation entity = JustEnoughBreeding.getKeyLoaderRegistries(entityType);
                    if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
                        switch (finalI) {
                            case 0 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() + 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                            case 1 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() - 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                            case 2 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() - 1, MobOffsetManager.get(entity).y());
                            default -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() + 1, MobOffsetManager.get(entity).y());
                        }
                        return true;
                    } else if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_RIGHT) {
                        switch (finalI) {
                            case 0, 1 -> MobOffsetManager.updateOffset(entity, 0, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                            default -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), 0, MobOffsetManager.get(entity).y());
                        }
                    }
                    return false;
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int x = 3 + 45;
            int y = 13 + 12 + (i * 11);

            Component component = i == 0 ? Component.translatable("option.justenoughbreeding.move_up") : Component.translatable("option.justenoughbreeding.move_down");
            JeiChildButtonWidget child = new JeiChildButtonWidget(x + xOffset, y, 10, 10, button, component);
            builder.addWidget(child);

            int finalI = i;
            builder.addInputHandler(new IJeiInputHandler() {
                @Override
                public @NotNull ScreenRectangle getArea() {
                    return new ScreenRectangle(x + xOffset, y, 10, 10);
                }

                @Override
                public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {
                    if (button.isToggled()) return false;
                    if (input.isSimulate()) return false;
                    ResourceLocation entity = JustEnoughBreeding.getKeyLoaderRegistries(entityType);
                    if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
                        if (finalI == 0) {
                            MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() - 1);
                        } else {
                            MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() + 1);
                        }
                        return true;
                    } else if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_RIGHT) {
                        MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), 0);
                    }
                    return false;
                }
            });
        }
    }

}