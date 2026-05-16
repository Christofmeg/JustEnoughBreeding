package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.List;

public class AllayDuplicationDisplay extends BasicDisplay {

    public AllayDuplicationRecipe recipe;

    public AllayDuplicationDisplay(AllayDuplicationRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.inputs()), EntryIngredients.ofIngredient(recipe.spawnEggs()), EntryIngredients.ofItemTag(ItemTags.CREEPER_DROP_MUSIC_DISCS), EntryIngredients.of(Items.JUKEBOX)),
                List.of(EntryIngredients.ofIngredient(recipe.spawnEggs()))
        );
        this.recipe = recipe;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AllayDuplicationCategoryREI.TYPE;
    }

}