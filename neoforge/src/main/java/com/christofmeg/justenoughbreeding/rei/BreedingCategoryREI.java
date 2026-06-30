package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BreedingCategoryREI extends AbstractRecipeCategoryREI<BreedingDisplay> {

    public static final CategoryIdentifier<BreedingDisplay> TYPE = CategoryIdentifier.of("justenoughbreeding", "breeding");

    public BreedingCategoryREI() {
        super(TYPE, Component.translatable("translation.justenoughbreeding.breeding"), EntryStacks.of(Items.WHEAT), 176, 101);
    }

    @Override
    public List<Widget> setupDisplay(BreedingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        List<EntryStack<?>> entryStackList = new ArrayList<>();
        for (ItemStack stack : display.recipe.spawnEggs().getItems()) {
            entryStackList.add(EntryStacks.of(stack));
        }
        widgets.add(Widgets.createSlot(new Point(bounds.x + 154, bounds.y + 6)).entries(entryStackList));

        boolean hasExtraInput = !display.getExtraInputEntries().isEmpty() &&
                !display.getExtraInputEntries().getFirst().isEmpty() &&
                !display.getExtraInputEntries().getFirst().getFirst().isEmpty();

        boolean hasOutput = !display.getOutputEntries().getFirst().isEmpty();
        if (hasExtraInput && hasOutput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 79, bounds.getCenterY() - 11)).entries(display.getInputEntries().getFirst()));
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 79, bounds.getCenterY() + 12)).entries(display.getExtraInputEntries().getFirst()));
        } else if (hasExtraInput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 112, bounds.getCenterY() - 11)).entries(display.getInputEntries().getFirst()));
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 112, bounds.getCenterY() + 12)).entries(display.getExtraInputEntries().getFirst()));
        } else if (!hasOutput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 112, bounds.getCenterY() + 3)).entries(display.getInputEntries().getFirst()));
        } else {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 79, bounds.getCenterY() + 3)).entries(display.getInputEntries().getFirst()));
        }
        if (hasOutput) {
            widgets.add(Widgets.createArrow(new Point(bounds.x + 102, bounds.getCenterY() + 2)));
            widgets.add(Widgets.createResultSlotBackground(new Point(bounds.x + 139, bounds.getCenterY() + 3)));
            widgets.add(Widgets.createSlot(new Point(bounds.x + 139, bounds.getCenterY() + 3)).entries(display.getOutputEntries().getFirst()).disableBackground().markOutput());
        }

        BreedingRecipe recipe = display.recipe;
        REIUtils.drawMobSlot(widgets, bounds,0, 10);
        REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.entityType(), recipe, getDisplayWidth(display));

        REIUtils.addButton(bounds, recipe.entityType(), widgets);

        return widgets;
    }

}