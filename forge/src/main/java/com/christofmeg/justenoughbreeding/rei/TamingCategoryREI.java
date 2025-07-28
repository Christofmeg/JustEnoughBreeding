package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
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

public class TamingCategoryREI extends AbstractRecipeCategoryREI<TamingDisplay> {

    public static final CategoryIdentifier<TamingDisplay> TYPE = CategoryIdentifier.of("justenoughbreeding", "taming");

    public TamingCategoryREI() {
        super(TYPE, Component.translatable("translation.justenoughbreeding.taming"), EntryStacks.of(Items.BONE), 176, 101);
    }

    @Override
    public List<Widget> setupDisplay(TamingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        List<EntryStack<?>> entryStackList = new ArrayList<>();
        for (ItemStack stack : display.recipe.spawnEgg.getItems()) {
            entryStackList.add(EntryStacks.of(stack));
        }
        widgets.add(Widgets.createSlot(new Point(bounds.x + 154, bounds.y + 6)).entries(entryStackList));

        boolean hasExtraInput = !display.getExtraInputEntries().isEmpty() &&
                !display.getExtraInputEntries().get(0).isEmpty() &&
                !display.getExtraInputEntries().get(0).get(0).isEmpty();

        if (hasExtraInput) {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 112, bounds.getCenterY() + 12)).entries(display.getInputEntries().get(0)));
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 112, bounds.getCenterY() - 11)).entries(display.getExtraInputEntries().get(0)));
        } else {
            widgets.add(Widgets.createSlot(new Point(bounds.getX() + 112, bounds.getCenterY() + 3)).entries(display.getInputEntries().get(0)));
        }

        TamingRecipe recipe = display.recipe;
        REIUtils.drawMobSlot(widgets, bounds,0, 10);
        REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.entityType, recipe);

        return widgets;
    }

}