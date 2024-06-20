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
    public BreedingRecipe breedingRecipe;

    public BreedingDisplay(BreedingRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.breedingCatalyst)),
                List.of(EntryIngredients.ofIngredient(recipe.resultItemStack))
        );
        breedingRecipe = recipe;
        if (recipe.extraInputStack != null) {
            extraInputs = List.of(EntryIngredients.ofIngredient(recipe.extraInputStack));
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