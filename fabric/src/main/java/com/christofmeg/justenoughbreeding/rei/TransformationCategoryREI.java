package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.config.ModConfigManager;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

import java.util.*;

public class TransformationCategoryREI extends AbstractRecipeCategoryREI<TransformationDisplay> {

    public static final CategoryIdentifier<TransformationDisplay> TYPE = CategoryIdentifier.of(CommonConstants.MOD_ID, "transformation");

    public TransformationCategoryREI() {
        super(TYPE, Component.translatable("translation.justenoughbreeding.transformation"), EntryStacks.of(Items.GOLDEN_CARROT), 176, 101);
    }
    /*
        @Override
        public List<Widget> setupDisplay(TransformationDisplay display, Rectangle bounds) {
            List<Widget> widgets = new LinkedList<>();
            widgets.add(Widgets.createRecipeBase(bounds));

            List<EntryStack<?>> inputSpawnEggs = new ArrayList<>();
            for (ItemStack stack : display.recipe.inputSpawnEggs().getItems()) {
                inputSpawnEggs.add(EntryStacks.of(stack));
            }
            widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 79)).entries(inputSpawnEggs));

            List<EntryStack<?>> outputSpawnEggs = new ArrayList<>();
            for (ItemStack stack : display.recipe.outputSpawnEggs().getItems()) {
                outputSpawnEggs.add(EntryStacks.of(stack));
            }
            widgets.add(Widgets.createSlot(new Point(bounds.x + 90, bounds.y + 79)).entries(outputSpawnEggs));

            boolean hasExtraInput = !display.getExtraInputEntries().isEmpty() &&
                    !display.getExtraInputEntries().getFirst().isEmpty() &&
                    !display.getExtraInputEntries().getFirst().getFirst().isEmpty();

            if (hasExtraInput) {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 27)).entries(display.getInputEntries().getFirst()));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 90, bounds.y + 27)).entries(display.getExtraInputEntries().getFirst()));
            } else {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 80, bounds.y + 27)).entries(display.getInputEntries().getFirst()));
            }

            widgets.add(Widgets.createArrow(new Point(bounds.x + 76, bounds.getCenterY() + 3)));

            TransformationRecipe recipe = display.recipe;
            REIUtils.drawMobSlot(widgets, bounds,0, 10);
            REIUtils.drawMobNameAndEntity(widgets, bounds, JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.tryParse(recipe.inputEntity())), recipe, 99, 0, true, getDisplayWidth(display));
            REIUtils.drawMobSlot(widgets, bounds,105, 10);
            REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.outputEntityType(), recipe, 61, 105, false, getDisplayWidth(display));

            REIUtils.addButton(bounds, JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.tryParse(recipe.inputEntity())), widgets);
            REIUtils.addButton(bounds, recipe.outputEntityType(), widgets, 105);

            return widgets;
        }
    */
    @Override
    public List<Widget> setupDisplay(TransformationDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        TransformationRecipe recipe = display.recipe;
        Collection<EntryStack<?>> inputSpawnEggs = new ArrayList<>();
        Arrays.stream(recipe.inputSpawnEggs().getItems()).forEach(stack -> inputSpawnEggs.add(EntryStacks.of(stack)));

        Collection<EntryStack<?>> outputSpawnEggs = new ArrayList<>();
        Arrays.stream(recipe.outputSpawnEggs().getItems()).forEach(stack -> outputSpawnEggs.add(EntryStacks.of(stack)));
        if (!recipe.outputs().isEmpty()) {
            Arrays.stream(recipe.outputs().getItems()).forEach(stack -> outputSpawnEggs.add(EntryStacks.of(stack)));
        }
        widgets.add(Widgets.createSlot(new Point(bounds.x + 90, bounds.y + 79)).entries(outputSpawnEggs));

        boolean hasExtraInput = !display.getExtraInputEntries().isEmpty() &&
                !display.getExtraInputEntries().getFirst().isEmpty() &&
                !display.getExtraInputEntries().getFirst().getFirst().isEmpty();

        if (recipe.inputEntityType() != null) {
            widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 79)).entries(inputSpawnEggs));
            if (hasExtraInput) {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 70, bounds.y + 27)).entries(display.getInputEntries().getFirst()));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 90, bounds.y + 27)).entries(display.getExtraInputEntries().getFirst()));
            } else {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 80, bounds.y + 27)).entries(display.getInputEntries().getFirst()));
            }
            widgets.add(Widgets.createArrow(new Point(bounds.x + 76, bounds.getCenterY() + 3)));
            REIUtils.drawMobSlot(widgets, bounds,0, 10);
            REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.inputEntityType(), recipe, 99, 0, true, getDisplayWidth(display));
            if (ModConfigManager.areConfigButtonsEnabled()) {
                REIUtils.addButton(bounds, recipe.inputEntityType(), widgets);
            }
        } else {
            if (hasExtraInput) {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 9, bounds.y + 48)).entries(inputSpawnEggs));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 48)).entries(display.getInputEntries().getFirst()));
                widgets.add(Widgets.createSlot(new Point(bounds.x + 49, bounds.y + 48)).entries(display.getExtraInputEntries().getFirst()));
            } else {
                widgets.add(Widgets.createSlot(new Point(bounds.x + 29, bounds.y + 48)).entries(inputSpawnEggs));
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