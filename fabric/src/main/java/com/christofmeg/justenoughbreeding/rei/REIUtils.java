package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.client.ClientUtils;
import com.christofmeg.justenoughbreeding.config.MobOffset;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.utils.CommonClientUtils;
import com.christofmeg.justenoughbreeding.utils.Rect;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Button;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class REIUtils {

    public static boolean showChildButtons = false;

    public static void registerRecipes(DisplayRegistry registration) {
        if (Minecraft.getInstance().getConnection() != null) {
            SynchronizedRecipes recipes = Minecraft.getInstance().getConnection().recipes().getSynchronizedRecipes();
            
            ArrayList<RecipeHolder<@NotNull AllayDuplicationRecipe>> allayDuplicationRecipes = new ArrayList<>(recipes.getAllOfType(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE));
            allayDuplicationRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
            for (RecipeHolder<@NotNull AllayDuplicationRecipe> recipeHold : allayDuplicationRecipes) {
                AllayDuplicationRecipe recipe = recipeHold.value();
                if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                    registration.add(new AllayDuplicationDisplay(recipe));
                }
            }

            ArrayList<RecipeHolder<@NotNull BreedingRecipe>> breedingRecipes = new ArrayList<>(recipes.getAllOfType(JustEnoughBreeding.BREEDING_PROVIDER_TYPE));
            breedingRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
            for (RecipeHolder<@NotNull BreedingRecipe> recipeHold : breedingRecipes) {
                BreedingRecipe recipe = recipeHold.value();
                if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                    registration.add(new BreedingDisplay(recipe));
                }
            }

            ArrayList<RecipeHolder<@NotNull TamingRecipe>> tamingRecipes = new ArrayList<>(recipes.getAllOfType(JustEnoughBreeding.TAMING_PROVIDER_TYPE));
            tamingRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
            for (RecipeHolder<@NotNull TamingRecipe> recipeHold : tamingRecipes) {
                TamingRecipe recipe = recipeHold.value();
                if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                    registration.add(new TamingDisplay(recipe));
                }
            }

            ArrayList<RecipeHolder<@NotNull TemperRecipe>> temperRecipes = new ArrayList<>(recipes.getAllOfType(JustEnoughBreeding.TEMPER_PROVIDER_TYPE));
            temperRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
            for (RecipeHolder<@NotNull TemperRecipe> recipeHold : temperRecipes) {
                TemperRecipe recipe = recipeHold.value();
                if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                    registration.add(new TemperDisplay(recipe));
                }
            }

            ArrayList<RecipeHolder<@NotNull TransformationRecipe>> transformationRecipes = new ArrayList<>(recipes.getAllOfType(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE));
            transformationRecipes.sort(Comparator.comparing(r -> r.value().outputEntityType() == null ? "" : r.value().outputEntityType().toShortString()));
            for (RecipeHolder<@NotNull TransformationRecipe> recipeHold : transformationRecipes) {
                TransformationRecipe recipe = recipeHold.value();
                if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.outputEntityType() != null && !recipe.inputs().isEmpty()) {
                    registration.add(new TransformationDisplay(recipe));
                }
            }

            ArrayList<RecipeHolder<@NotNull TrustingRecipe>>trustingRecipes = new ArrayList<>(recipes.getAllOfType(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE));
            trustingRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));
            for (RecipeHolder<@NotNull TrustingRecipe> recipeHold : trustingRecipes) {
                TrustingRecipe recipe = recipeHold.value();
                if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null && !recipe.inputs().isEmpty()) {
                    registration.add(new TrustingDisplay(recipe));
                }
            }
        }
    }

    public static void drawMobSlot(List<Widget> widgets, Rectangle bounds, int mobSlotX, int mobSlotY) {
        widgets.add(Widgets.createSlotBase(new Rectangle(bounds.x + 5 + mobSlotX, bounds.y + 5 + mobSlotY, 61, 81)));
    }

    public static void drawMobNameAndEntity(List<Widget> widgets, Rectangle bounds, EntityType<?> entityType, Recipe<?> recipe, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(widgets, bounds, entityType, recipe, 148, 0, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(List<Widget> widgets, Rectangle bounds, EntityType<?> entityType, Recipe<?> recipe, int availableWidth, int extraX, int CATEGORY_WIDTH) {
        drawMobNameAndEntity(widgets, bounds, entityType, recipe, availableWidth, extraX, true, CATEGORY_WIDTH);
    }

    public static void drawMobNameAndEntity(List<Widget> widgets, Rectangle bounds, EntityType<?> entityType, Recipe<?> recipe, int availableWidth, int extraX, boolean input, int CATEGORY_WIDTH) {
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
                widgets.add(Widgets.createLabel(new Point(bounds.x + extraX + 5, bounds.y + 5), abbreviatedEntityName).leftAligned().noShadow().color(0xFF404040, 0xFFBBBBBB));
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

            widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((graphics, mouseX, mouseY, v) -> {
                LivingEntity livingEntity = Utils.getLivingEntity(currentLivingEntity, input, recipe);
                if (livingEntity != null) {
                    final Rect rect = new Rect((CATEGORY_WIDTH - 59 - 10) / 2 + extraX, 10, 59, 59);
                    MobOffset offset = MobOffsetManager.get(JustEnoughBreeding.getKeyLoaderRegistries(currentLivingEntity.getType()));
                    CommonClientUtils.renderEntity(currentLivingEntity, rect, graphics, mouseX, offset);
                }
                    }
            ), bounds.x, bounds.y));
        }
    }

    public static List<Widget> addButton(Rectangle bounds, EntityType<?> entityType, List<Widget> widgets) {
        return addButton(bounds, entityType, widgets, 0);
    }

    public static List<Widget> addButton(Rectangle bounds, EntityType<?> entityType, List<Widget> widgets, int xOffset) {
        Rectangle buttonRect = bounds.clone();
        buttonRect.setSize(10, 10);
        buttonRect.move(bounds.getLocation().x + 53 + xOffset, bounds.getLocation().y + 18);
        Identifier entity = JustEnoughBreeding.getKeyLoaderRegistries(entityType);

        Rectangle button1 = buttonRect.clone();
        button1.move(buttonRect.getLocation().x - 45, buttonRect.getLocation().y);
        Button button_1 = Widgets.createButton(button1, Component.empty());
        button_1.tooltipLine(Component.translatable("option.justenoughbreeding.increase_scale"));
        button_1.onClick(button -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() + 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y()));

        Rectangle button2 = buttonRect.clone();
        button2.move(buttonRect.getLocation().x - 34, buttonRect.getLocation().y);
        Button button_2 = Widgets.createButton(button2, Component.empty());
        button_2.tooltipLine(Component.translatable("option.justenoughbreeding.decrease_scale"));
        button_2.onClick(button -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale() - 0.5f, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y()));

        Rectangle button3 = buttonRect.clone();
        button3.move(buttonRect.getLocation().x - 23, buttonRect.getLocation().y);
        Button button_3 = Widgets.createButton(button3, Component.empty());
        button_3.tooltipLine(Component.translatable("option.justenoughbreeding.move_left"));
        button_3.onClick(button -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() - 1, MobOffsetManager.get(entity).y()));

        Rectangle button4 = buttonRect.clone();
        button4.move(buttonRect.getLocation().x - 12, buttonRect.getLocation().y);
        Button button_4 = Widgets.createButton(button4, Component.empty());
        button_4.tooltipLine(Component.translatable("option.justenoughbreeding.move_right"));
        button_4.onClick(button -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x() + 1, MobOffsetManager.get(entity).y()));

        Rectangle button5 = buttonRect.clone();
        button5.move(buttonRect.getLocation().x, buttonRect.getLocation().y + 12);
        Button button_5 = Widgets.createButton(button5, Component.empty());
        button_5.tooltipLine(Component.translatable("option.justenoughbreeding.move_up"));
        button_5.onClick(button -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() - 1));

        Rectangle button6 = buttonRect.clone();
        button6.move(buttonRect.getLocation().x, buttonRect.getLocation().y + 23);
        Button button_6 = Widgets.createButton(button6, Component.empty());
        button_6.tooltipLine(Component.translatable("option.justenoughbreeding.move_down"));
        button_6.onClick(button -> MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y() + 1));

        widgets.add(new Widget() {
            @Override
            public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
                if (!REIUtils.showChildButtons) return;
                button_1.render(graphics, mouseX, mouseY, delta);
                button_2.render(graphics, mouseX, mouseY, delta);
                button_3.render(graphics, mouseX, mouseY, delta);
                button_4.render(graphics, mouseX, mouseY, delta);
                button_5.render(graphics, mouseX, mouseY, delta);
                button_6.render(graphics, mouseX, mouseY, delta);
            }

            @Override
            public boolean containsMouse(double mouseX, double mouseY) {
                if (!REIUtils.showChildButtons) return false;
                return button_1.containsMouse(mouseX, mouseY) ||
                        button_2.containsMouse(mouseX, mouseY) ||
                        button_3.containsMouse(mouseX, mouseY) ||
                        button_4.containsMouse(mouseX, mouseY) ||
                        button_5.containsMouse(mouseX, mouseY) ||
                        button_6.containsMouse(mouseX, mouseY);
            }

            @Override
            public @NotNull List<? extends GuiEventListener> children() {
                return List.of(button_1, button_2, button_3, button_4, button_5, button_6);
            }

            @Override
            public boolean mouseClicked(@NonNull MouseButtonEvent mouseButtonEvent, boolean bl) {
                double pMouseX = mouseButtonEvent.x();
                double pMouseY = mouseButtonEvent.y();
                double pButton = mouseButtonEvent.button();
                this.getChildAt(pMouseX, pMouseY).ifPresent(child -> {
                    if (pButton == InputConstants.MOUSE_BUTTON_RIGHT) {
                        if (child.equals(button_1) || child.equals(button_2)) {
                            MobOffsetManager.updateOffset(entity, 0, MobOffsetManager.get(entity).x(), MobOffsetManager.get(entity).y());
                        }
                        if (child.equals(button_3) || child.equals(button_4)) {
                            MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), 0, MobOffsetManager.get(entity).y());
                        }
                        if (child.equals(button_5) || child.equals(button_6)) {
                            MobOffsetManager.updateOffset(entity, MobOffsetManager.get(entity).scale(), MobOffsetManager.get(entity).x(), 0);
                        }
                    }
                });
                return super.mouseClicked(mouseButtonEvent, bl);
            }
        });

        Button toggleButton = Widgets.createButton(buttonRect, Component.empty());
        toggleButton.tooltipLine(REIUtils.showChildButtons ? Component.translatable("option.justenoughbreeding.hide_options") :
                Component.translatable("option.justenoughbreeding.show_options"));
        toggleButton.onClick(button -> {
            REIUtils.showChildButtons = !REIUtils.showChildButtons;
            toggleButton.tooltipLine(REIUtils.showChildButtons ? Component.translatable("option.justenoughbreeding.hide_options") :
                    Component.translatable("option.justenoughbreeding.show_options"));
        });
        widgets.add(toggleButton);

        return widgets;
    }

}
