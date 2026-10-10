package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.config.ModConfigManager;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.LinkedList;
import java.util.List;

public class TransformationCategoryREI extends AbstractRecipeCategoryREI<TransformationDisplay> {

    public static final CategoryIdentifier<TransformationDisplay> TYPE = CategoryIdentifier.of(CommonConstants.MOD_ID, "transformation");

    public TransformationCategoryREI() {
        super(TYPE, Component.translatable("translation.justenoughbreeding.transformation"), EntryStacks.of(Items.GOLDEN_CARROT), 176, 101);
    }

    @Override
    public List<Widget> setupDisplay(TransformationDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        TransformationRecipe recipe = display.recipe;

        EntityType<?> inputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.parse(recipe.inputEntity()));
        Ingredient inputSpawnEggs = recipe.inputSpawnEggs() == null ? (JustEnoughBreeding.getSpawnEggItem(inputEntityType).isPresent() ? Ingredient.of(JustEnoughBreeding.getSpawnEggItem(inputEntityType).get().value()) : null): recipe.inputSpawnEggs();

        Ingredient outputSpawnEggs = recipe.outputSpawnEggs() == null ? (JustEnoughBreeding.getSpawnEggItem(recipe.outputEntityType()).isPresent() ? Ingredient.of(JustEnoughBreeding.getSpawnEggItem(recipe.outputEntityType()).get().value()) : null): recipe.outputSpawnEggs();
        Slot outputSlot = Widgets.createSlot(new Point(bounds.x + 90, bounds.y + 79));
        if (recipe.outputs() != null && !recipe.outputs().isEmpty()) outputSlot.entries(EntryIngredients.ofIngredient(recipe.outputs()));
        if (outputSpawnEggs != null && !outputSpawnEggs.isEmpty()) outputSlot.entries(EntryIngredients.ofIngredient(outputSpawnEggs));
        widgets.add(outputSlot);

        boolean hasExtraInput = !display.getExtraInputEntries().isEmpty() &&
                !display.getExtraInputEntries().getFirst().isEmpty() &&
                !display.getExtraInputEntries().getFirst().getFirst().isEmpty();

        if (recipe.inputEntityType() != null) {
            if (inputSpawnEggs != null && !inputSpawnEggs.isEmpty()) widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 79)).entries(EntryIngredients.ofIngredient(inputSpawnEggs))); else widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 79)));
            if (hasExtraInput) {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 27)).entries(display.getInputEntries().getFirst()));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 90, bounds.y + 27)).entries(display.getExtraInputEntries().getFirst()));
            } else {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 80, bounds.y + 27)).entries(display.getInputEntries().getFirst()));
            }
            widgets.add(Widgets.createArrow(new Point(bounds.x + 76, bounds.getCenterY() + 3)));
            REIUtils.drawMobSlot(widgets, bounds,0, 10);
            REIUtils.drawMobNameAndEntity(widgets, bounds, JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.tryParse(recipe.inputEntity())), recipe, 99, 0, true, getDisplayWidth(display));
            if (ModConfigManager.areConfigButtonsEnabled()) {
                REIUtils.addButton(bounds, JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.tryParse(recipe.inputEntity())), widgets);
            }
        } else {
            if (hasExtraInput) {
                if (inputSpawnEggs != null && !inputSpawnEggs.isEmpty()) widgets.add(Widgets.createSlot(new Point(bounds.x + 9, bounds.y + 48)).entries(EntryIngredients.ofIngredient(inputSpawnEggs))); else  widgets.add(Widgets.createSlot(new Point(bounds.x + 9, bounds.y + 48)));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 48)).entries(display.getInputEntries().getFirst()));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 49, bounds.y + 48)).entries(display.getExtraInputEntries().getFirst()));
            } else {
                if (inputSpawnEggs != null && !inputSpawnEggs.isEmpty()) widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 48)).entries(EntryIngredients.ofIngredient(inputSpawnEggs))); else widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 48)));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 49, bounds.y + 48)).entries(display.getInputEntries().getFirst()));
            }
            widgets.add(Widgets.createArrow(new Point(bounds.x + 76, bounds.getCenterY() - 2)));
        }

        REIUtils.drawMobSlot(widgets, bounds,105, 10);
        REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.outputEntityType(), recipe, 61, 105, false, getDisplayWidth(display));
        if (ModConfigManager.areConfigButtonsEnabled()) {
            REIUtils.addButton(bounds, recipe.outputEntityType(), widgets, 105);
        }

        return widgets;
    }

}