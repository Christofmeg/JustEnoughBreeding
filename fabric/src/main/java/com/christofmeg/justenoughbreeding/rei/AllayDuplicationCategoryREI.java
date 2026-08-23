package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.config.ModConfigManager;
import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

@SuppressWarnings("deprecation")
public class AllayDuplicationCategoryREI extends AbstractRecipeCategoryREI<AllayDuplicationDisplay> {

    public static final CategoryIdentifier<AllayDuplicationDisplay> TYPE = CategoryIdentifier.of("justenoughbreeding", "allay_duplication");

    public AllayDuplicationCategoryREI() {
        super(TYPE, Component.translatable("translation.justenoughbreeding.allay_duplication"), EntryStacks.of(Items.AMETHYST_SHARD), 176, 101);
    }

    @Override
    public List<Widget> setupDisplay(AllayDuplicationDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        Collection<EntryStack<?>> spawnEggs = new ArrayList<>();
        display.recipe.spawnEggs().items().forEach(stack -> spawnEggs.add(EntryStacks.ofItemHolder(stack)));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 154, bounds.y + 6)).entries(spawnEggs));

        Collection<EntryStack<?>> inputEntries = new ArrayList<>();
        display.getInputEntries().forEach(inputEntries::addAll);
        widgets.add(Widgets.createSlot(new Point(bounds.x + 112, bounds.y + 37)).entries(inputEntries));

        Collection<EntryStack<ItemStack>> musicDiscs = new ArrayList<>();
        BuiltInRegistries.ITEM.get(ItemTags.CREEPER_DROP_MUSIC_DISCS).ifPresent(stack -> stack.forEach(holder -> musicDiscs.add(EntryStacks.of(holder.value()))));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 102, bounds.y + 57)).entries(musicDiscs));
        widgets.add(Widgets.createSlot(new Point(bounds.x + 122, bounds.y + 57)).entries(List.of(EntryStacks.of(Items.JUKEBOX))));

        AllayDuplicationRecipe recipe = display.recipe;
        REIUtils.drawMobSlot(widgets, bounds,0, 10);
        REIUtils.drawMobNameAndEntity(widgets, bounds, recipe.entityType(), recipe, getDisplayWidth(display));

        if (ModConfigManager.areConfigButtonsEnabled()) {
            REIUtils.addButton(bounds, recipe.entityType(), widgets);
        }

        return widgets;
    }

}