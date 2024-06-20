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
import net.minecraft.client.gui.Font;
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

    final int inputSlotFrameX = 68 - 19 + 25;
    final int inputSlot1FrameY = 51 + 12;
    final int inputSlot2FrameY = 32 + 12;
    final int outputSlotFrameX = 94 + 16 + 25;
    final int outputSlotFrameY = 38 + 15;
    final int eggSlotX = 133 - 4 + 25;
    final int eggSlotY = 6;
    final int arrowX = 73 + 25;
    final int arrowY = 52;
    final int mobSlotX = 5;
    final int mobSlotY = 15;

    @Override
    public List<Widget> setupDisplay(BreedingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlotBase(new Rectangle(bounds.x + mobSlotX, bounds.y + mobSlotY, 61, 81)));
        widgets.add(Widgets.createSlot(new Point(bounds.x + eggSlotX, bounds.y + eggSlotY)).entries(List.of(EntryStacks.of(display.breedingRecipe.spawnEgg))));
        widgets.add(Widgets.createSlot(new Point(bounds.x + inputSlotFrameX, bounds.y + inputSlot1FrameY)).entries(display.getInputEntries().getFirst()));
        widgets.add(Widgets.createSlot(new Point(bounds.x + inputSlotFrameX, bounds.y + inputSlot2FrameY)).entries(display.getExtraInputEntries().getFirst()));
        widgets.add(Widgets.createArrow(new Point(bounds.x + arrowX, bounds.y + arrowY)));
        widgets.add(Widgets.createResultSlotBackground(new Point(bounds.x + outputSlotFrameX, bounds.y + outputSlotFrameY)));
        widgets.add(Widgets.createSlot(new Point(bounds.x + outputSlotFrameX, bounds.y + outputSlotFrameY)).entries(display.getOutputEntries().getFirst()).disableBackground().markOutput());

        BreedingRecipe recipe = display.breedingRecipe;
        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Minecraft instance = Minecraft.getInstance();
            Font font = instance.font;
            Component entityName = Component.translatable(entityType.getDescriptionId());

            String entityNameString = entityName.getString(); // Convert Component to String
            if (recipe.needsToBeTamed != null) {
                Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                entityNameString += " (" + tamed.getString() + ")";
            } else if (recipe.animalTrusting != null) {
                Component trusting = Component.translatable("translation.justenoughbreeding.trusting");
                entityNameString += " (" + trusting.getString() + ")";
            }

            int stringWidth = font.width(entityNameString); // Measure the width of the string in pixels

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
        return 151 + 25;
    }

    @Override
    public int getDisplayHeight() {
        return 91 + 10;
    }

}