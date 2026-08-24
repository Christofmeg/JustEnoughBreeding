package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.client.ClientUtils;
import com.christofmeg.justenoughbreeding.config.MobOffset;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonClientUtils;
import com.christofmeg.justenoughbreeding.utils.Rect;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class JEIUtils {

    public static void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().getConnection() != null) {
            SynchronizedRecipes recipes = Minecraft.getInstance().getConnection().recipes().getSynchronizedRecipes();
            registerCategoryRecipes(registration, recipes, JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE, AllayDuplicationCategory.TYPE, AllayDuplicationRecipe.class, AllayDuplicationRecipe::mod, AllayDuplicationRecipe::entityType, r -> !r.inputs().isEmpty());
            registerCategoryRecipes(registration, recipes, JustEnoughBreeding.BREEDING_PROVIDER_TYPE, BreedingCategory.TYPE, BreedingRecipe.class, BreedingRecipe::mod, BreedingRecipe::entityType, r -> !r.inputs().isEmpty());
            registerCategoryRecipes(registration, recipes, JustEnoughBreeding.TAMING_PROVIDER_TYPE, TamingCategory.TYPE, TamingRecipe.class, TamingRecipe::mod, TamingRecipe::entityType, r -> !r.inputs().isEmpty());
            registerCategoryRecipes(registration, recipes, JustEnoughBreeding.TEMPER_PROVIDER_TYPE, TemperCategory.TYPE, TemperRecipe.class, TemperRecipe::mod, TemperRecipe::entityType, r -> !r.inputs().isEmpty());
            registerCategoryRecipes(registration, recipes, JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE, TransformationCategory.TYPE, TransformationRecipe.class, TransformationRecipe::mod, TransformationRecipe::outputEntityType, r -> !r.inputs().isEmpty());
            registerCategoryRecipes(registration, recipes, JustEnoughBreeding.TRUSTING_PROVIDER_TYPE, TrustingCategory.TYPE, TrustingRecipe.class, TrustingRecipe::mod, TrustingRecipe::entityType, r -> !r.inputs().isEmpty());
        }
    }

    private static <T extends Recipe<?>> void registerCategoryRecipes(
            IRecipeRegistration registration,
            SynchronizedRecipes recipes,
            RecipeType<?> targetType,
            IRecipeType<@NotNull T> categoryType,
            Class<T> recipeClass,
            Function<T, String> modExtractor,
            Function<T, EntityType<?>> entityTypeExtractor,
            Predicate<T> validInputsCheck) {
        List<T> validRecipes = recipes.recipes().stream()
                .map(RecipeHolder::value)
                .filter(recipe -> recipe.getType() == targetType)
                .filter(recipeClass::isInstance)
                .map(recipeClass::cast)
                .filter(recipe -> {
                    EntityType<?> type = entityTypeExtractor.apply(recipe);
                    return type != null
                            && JustEnoughBreeding.isModLoaded(modExtractor.apply(recipe))
                            && validInputsCheck.test(recipe);
                })
                .sorted(Comparator.comparing(r -> {
                    EntityType<?> type = entityTypeExtractor.apply(r);
                    return type == null ? "" : type.toShortString();
                }))
                .toList();
        if (!validRecipes.isEmpty()) {
            registration.addRecipes(categoryType, validRecipes);
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

        int color = CommonClientUtils.getPixelColor(Identifier.fromNamespaceAndPath("jei", "textures/jei/atlas/gui/output_slot.png"), 13 ,13);
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

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics graphics, double mouseX, Recipe<?> recipe, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(entityType, graphics, mouseX, recipe, 148, 0, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics graphics, double mouseX, Recipe<?> recipe, int availableWidth, int extraX, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(entityType, graphics, mouseX, recipe, availableWidth, extraX, true, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(EntityType<?> entityType, GuiGraphics graphics, double mouseX, Recipe<?> recipe, int availableWidth, int extraX, boolean input, int CATEGORY_WIDTH) {
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
                if (transformationRecipe.tamed() != null && transformationRecipe.tamed()) {
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
                    currentLivingEntity = ClientUtils.doRendering(JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.tryParse(transformationRecipe.inputEntity())), transformationRecipe.inputEntityNbt(), true);
                } else {
                    currentLivingEntity = ClientUtils.doRendering(transformationRecipe.outputEntityType(), transformationRecipe.outputEntityNbt(), false);
                }
            } else {
                currentLivingEntity = ClientUtils.doRendering(entityType);
            }
            LivingEntity livingEntity = Utils.getLivingEntity(currentLivingEntity, input, recipe);
            if (livingEntity != null) {
                final Rect rect = new Rect((CATEGORY_WIDTH - 59 - 10) / 2 + extraX, 10, 59, 59);
                MobOffset offset = MobOffsetManager.get(JustEnoughBreeding.getKeyLoaderRegistries(currentLivingEntity.getType()));
                CommonClientUtils.renderEntity(currentLivingEntity, rect, graphics, (int) mouseX, offset);
            }
        }
    }

    public static void addButton(IRecipeExtrasBuilder builder, EntityType<?> entityType) {
        addButton(builder, entityType, 0);
    }

    public static void addButton(IRecipeExtrasBuilder builder, EntityType<?> entityType, int xOffset) {
        JEIToggleButtonWidget button = new JEIToggleButtonWidget(48 + xOffset, 13, 10, 10);
        builder.addWidget(button);

        builder.addInputHandler(new IJeiInputHandler() {
            @Override
            public @NotNull ScreenRectangle getArea() {
                return new ScreenRectangle(48 + xOffset, 13, 10, 10);
            }
            @Override
            public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {
                if (input.getKey().getValue() != InputConstants.MOUSE_BUTTON_LEFT) return false;
                if (input.isSimulate()) return true;
                button.toggle();
                return true;
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

            JEIChildButtonWidget child = new JEIChildButtonWidget(x + xOffset, y, 10, 10, button, component);
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
                    int mouseButton = input.getKey().getValue();
                    if (mouseButton != InputConstants.MOUSE_BUTTON_LEFT && mouseButton != InputConstants.MOUSE_BUTTON_RIGHT) return false;
                    if (input.isSimulate()) return true;
                    Identifier entity = JustEnoughBreeding.getKeyLoaderRegistries(entityType);
                    if (mouseButton == InputConstants.MOUSE_BUTTON_LEFT) {
                        switch (finalI) {
                            case 0 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() + 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                            case 1 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() - 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                            case 2 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() - 1, MobOffsetManager.get(entity).y());
                            case 3 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() + 1, MobOffsetManager.get(entity).y());
                        }
                        return true;
                    }
                    switch (finalI) {
                        case 0, 1 -> MobOffsetManager.updateOffset(entity, 0, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                        case 2, 3 -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), 0, MobOffsetManager.get(entity).y());
                    }
                    return true;
                }
            });
        }

        for (int i = 0; i < 2; i++) {
            int x = 3 + 45;
            int y = 13 + 12 + (i * 11);

            Component component = i == 0 ? Component.translatable("option.justenoughbreeding.move_up") : Component.translatable("option.justenoughbreeding.move_down");
            JEIChildButtonWidget child = new JEIChildButtonWidget(x + xOffset, y, 10, 10, button, component);
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
                    int mouseButton = input.getKey().getValue();
                    if (mouseButton != InputConstants.MOUSE_BUTTON_LEFT && mouseButton != InputConstants.MOUSE_BUTTON_RIGHT) return false;
                    if (input.isSimulate()) return true;
                    Identifier entity = JustEnoughBreeding.getKeyLoaderRegistries(entityType);
                    if (mouseButton == InputConstants.MOUSE_BUTTON_LEFT) {
                        if (finalI == 0) {
                            MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() - 1);
                        } else {
                            MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() + 1);
                        }
                        return true;
                    } else {
                        MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), 0);
                    }
                    return false;
                }
            });
        }
    }

}