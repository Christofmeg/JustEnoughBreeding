package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TrustingDisplay extends BasicDisplay {

    protected List<EntryIngredient> extraInputs;
    public TrustingRecipe recipe;

    public TrustingDisplay(TrustingRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.inputs()), EntryIngredients.ofIngredient(recipe.spawnEgg())),
                List.of(EntryIngredients.ofIngredient(recipe.spawnEgg()))
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
        return TrustingCategoryREI.TYPE;
    }

    public List<EntryIngredient> getExtraInputEntries() {
        return extraInputs;
    }

}