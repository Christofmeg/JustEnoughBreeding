package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TamingDisplay extends BasicDisplay {

    protected List<EntryIngredient> extraInputs;
    public TamingRecipe tamingRecipe;

    public TamingDisplay(TamingRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.inputStack), EntryIngredients.ofIngredient(recipe.spawnEgg)),
                List.of(EntryIngredients.ofIngredient(recipe.spawnEgg))
        );
        tamingRecipe = recipe;
        if (recipe.extraInputStack != null) {
            extraInputs = List.of(EntryIngredients.ofIngredient(recipe.extraInputStack));
        } else {
            extraInputs = List.of(EntryIngredient.of(EntryStacks.of(ItemStack.EMPTY)));
        }
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return TamingCategoryREI.TYPE;
    }

    public List<EntryIngredient> getExtraInputEntries() {
        return extraInputs;
    }

}