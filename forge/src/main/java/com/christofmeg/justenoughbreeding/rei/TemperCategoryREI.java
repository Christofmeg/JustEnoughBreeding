package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TemperCategoryREI implements DisplayCategory<TemperDisplay> {

    public static final CategoryIdentifier<TemperDisplay> TYPE =
            CategoryIdentifier.of(CommonConstants.MOD_ID, "temper");

    @Override
    public CategoryIdentifier<? extends TemperDisplay> getCategoryIdentifier() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("translation.justenoughbreeding.temper");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.GOLDEN_APPLE);
    }

    final int inputSlotFrameX = 68 - 19 + 25;
    final int eggSlotX = 133 - 4 + 25;
    final int eggSlotY = 6;
    final int mobSlotX = 5;
    final int mobSlotY = 15;

    @Override
    public List<Widget> setupDisplay(TemperDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createSlotBase(new Rectangle(bounds.x + mobSlotX, bounds.y + mobSlotY, 61, 81)));
        List<EntryStack<?>> entryStackList = new ArrayList<>();
        for (ItemStack stack : display.temperRecipe.spawnEgg.getItems()) {
            entryStackList.add(EntryStacks.of(stack));
        }
        widgets.add(Widgets.createSlot(new Point(bounds.x + eggSlotX, bounds.y + eggSlotY)).entries(entryStackList));

        boolean hasExtraInput = !display.getExtraInputEntries().isEmpty() &&
                !display.getExtraInputEntries().get(0).isEmpty() &&
                !display.getExtraInputEntries().get(0).get(0).isEmpty();

        if (hasExtraInput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 31 + 7, bounds.getCenterY() + 10 + 2)).entries(display.getInputEntries().get(0)));
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 31 + 7, bounds.getCenterY() - 9 + 2)).entries(display.getExtraInputEntries().get(0)));
        } else {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + inputSlotFrameX + 31 + 7, bounds.getCenterY() + 3)).entries(display.getInputEntries().get(0)));
        }

        TemperRecipe recipe = display.temperRecipe;
        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Component entityName = Component.translatable(entityType.getDescriptionId());
            String entityNameString = entityName.getString(); // Convert Component to String

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
                    LivingEntity currentLivingEntity = recipe.doRendering(entityType);
                    if (currentLivingEntity != null) {
                        CommonUtils.renderEntity(stack.pose(), mouseX, currentLivingEntity);
                    }
                }
            ), bounds.x + mobSlotX, bounds.y + mobSlotY - 10, 0));
        }

        return widgets;
    }

    @Override
    public int getDisplayWidth(TemperDisplay display) {
        return 151 + 25;
    }

    @Override
    public int getDisplayHeight() {
        return 91 + 10;
    }

}