package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BreedingDisplay extends BasicDisplay {

    protected List<EntryIngredient> extraInputs;
    public BreedingRecipe recipe;

    public BreedingDisplay(BreedingRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.inputs()), EntryIngredients.ofIngredient(recipe.spawnEggs())),
                List.of(EntryIngredients.ofIngredient(recipe.outputs()), EntryIngredients.ofIngredient(recipe.spawnEggs()))
        );
        this.recipe = recipe;
        if (!recipe.extraInputs().isEmpty()) {
            extraInputs = List.of(EntryIngredients.ofIngredient(recipe.extraInputs()));
        } else {
            extraInputs = List.of(EntryIngredient.of(EntryStacks.of(ItemStack.EMPTY)));
        }
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BreedingCategoryREI.TYPE;
    }

    public List<EntryIngredient> getExtraInputEntries() {
        return extraInputs;
    }

}