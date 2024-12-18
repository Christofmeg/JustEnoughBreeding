package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;

import java.util.LinkedList;
import java.util.List;

public class BreedingCategoryREI implements DisplayCategory<BreedingDisplay> {

    public static final CategoryIdentifier<BreedingDisplay> TYPE =
            CategoryIdentifier.of(CommonConstants.MOD_ID, "breeding");

    @Override
    public CategoryIdentifier<? extends BreedingDisplay> getCategoryIdentifier() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("translation.justenoughbreeding.breeding");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.WHEAT);
    }

    final int inputSlotFrameX = 74;
    final int outputSlotFrameX = 135;
    final int eggSlotX = 154;
    final int eggSlotY = 6;
    final int arrowX = 98;
    final int mobSlotX = 5;
    final int mobSlotY = 15;

    @Override
    public List<Widget> setupDisplay(BreedingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlotBase(new Rectangle(bounds.x + mobSlotX, bounds.y + mobSlotY, 61, 81)));
        widgets.add(Widgets.createSlot(new Point(bounds.x + eggSlotX, bounds.y + eggSlotY)).entries(List.of(EntryStacks.of(display.breedingRecipe.spawnEgg))));

        boolean hasExtraInput = !display.getExtraInputEntries().get(0).get(0).isEmpty();
        boolean hasOutput = !display.getOutputEntries().get(0).isEmpty();
        if (hasExtraInput && hasOutput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 5, bounds.getCenterY() + 10 + 2)).entries(display.getInputEntries().get(0)));
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 5, bounds.getCenterY() - 9 + 2)).entries(display.getExtraInputEntries().get(0)));
        } else if (hasExtraInput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 31 + 7, bounds.getCenterY() + 10 + 2)).entries(display.getInputEntries().get(0)));
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 31 + 7, bounds.getCenterY() - 9 + 2)).entries(display.getExtraInputEntries().get(0)));
        } else if (!hasOutput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 31 + 7, bounds.getCenterY() + 3)).entries(display.getInputEntries().get(0)));
        } else {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 5, bounds.getCenterY() + 3)).entries(display.getInputEntries().get(0)));
        }
        if (hasOutput) {
            widgets.add(Widgets.createArrow(new Point(bounds.x + arrowX + 4, bounds.getCenterY() + 2)));
            widgets.add(Widgets.createResultSlotBackground(new Point(bounds.x + outputSlotFrameX + 4, bounds.getCenterY() + 3)));
            widgets.add(Widgets.createSlot(new Point(bounds.x + outputSlotFrameX + 4, bounds.getCenterY() + 3)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        }

        BreedingRecipe recipe = display.breedingRecipe;
        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Component entityName = Component.translatable(entityType.getDescriptionId());
            String entityNameString = entityName.getString(); // Convert Component to String
            if (recipe.needsToBeTamed != null) {
                Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                entityNameString += " (" + tamed.getString() + ")";
            } else if (recipe.animalTrusting != null) {
                Component trusting = Component.translatable("translation.justenoughbreeding.trusting");
                entityNameString += " (" + trusting.getString() + ")";
            }

            int stringWidth = Minecraft.getInstance().font.width(entityNameString); // Measure the width of the string in pixels
            int availableWidth = 154; // Initial available width in pixels
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / entityNameString.length();
                int maxCharacters = (int) (availableWidth / pixelWidthPerCharacter);
                entityNameString = entityNameString.substring(0, maxCharacters);
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                widgets.add(Widgets.createLabel(new Point(bounds.x + 5, bounds.y + 5),
                        abbreviatedEntityName).noShadow().leftAligned().color(0xFF404040, 0xFFBBBBBB));
            }
            widgets.add(Widgets.withTranslate(Widgets.createDrawableWidget((stack, mouseX, mouseY, v) -> {
                        LivingEntity currentLivingEntity = recipe.doRendering();
                        if (currentLivingEntity != null) {
                            Utils.renderEntity(stack.pose(), mouseX, currentLivingEntity);
                        }
                    }
            ), bounds.x + mobSlotX, bounds.y + mobSlotY - 10, 0));
        }

        return widgets;
    }

    @Override
    public int getDisplayWidth(BreedingDisplay breedingDisplay) {
        return 176;
    }

    @Override
    public int getDisplayHeight() {
        return 101;
    }

}