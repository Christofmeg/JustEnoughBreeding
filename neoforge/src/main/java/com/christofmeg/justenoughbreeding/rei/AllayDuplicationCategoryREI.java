package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AllayDuplicationCategoryREI extends AbstractRecipeCategoryREI<AllayDuplicationDisplay> {

    public static final CategoryIdentifier<AllayDuplicationDisplay> TYPE = CategoryIdentifier.of("justenoughbreeding", "allay_duplication");

    public AllayDuplicationCategoryREI() {
        super(TYPE, Component.translatable("translation.justenoughbreeding.allay_duplication"), EntryStacks.of(Items.AMETHYST_SHARD), 176, 101);
    }

    @Override
    public List<Widget> setupDisplay(AllayDuplicationDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        List<EntryStack<?>> spawnEggs = new ArrayList<>();
        for (ItemStack stack : display.recipe.spawnEgg.getItems()) {
            spawnEggs.add(EntryStacks.of(stack));
        }
        widgets.add(Widgets.createSlot(new Point(bounds.x + 154, bounds.y + 6)).entries(spawnEggs));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 112, bounds.y + 37)).entries(display.getInputEntries().getFirst()));

        List<EntryIngredient> musicDiscs = new ArrayList<>();
        musicDiscs.add(EntryIngredients.ofIngredient(Ingredient.of(ItemTags.CREEPER_DROP_MUSIC_DISCS)));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 102, bounds.y + 57)).entries(musicDiscs.getFirst()));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 122, bounds.y + 57)).entries(List.of(EntryStacks.of(Items.JUKEBOX))));

        AllayDuplicationRecipe recipe = display.recipe;
        REIUtils.drawMobSlot(widgets, bounds,0, 10);
        REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.entityType, recipe, getDisplayWidth(display));

        REIUtils.addButton(bounds, recipe.entityType, widgets);

        return widgets;
    }

}