package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TransformationDisplay extends BasicDisplay {

    protected List<EntryIngredient> extraInputs;
    public TransformationRecipe recipe;

    public TransformationDisplay(TransformationRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.inputs()), EntryIngredients.ofIngredient(recipe.inputSpawnEggs())),
                List.of(EntryIngredients.ofIngredient(recipe.outputSpawnEggs()))
        );
        this.recipe = recipe;
        if (recipe.extraInputs() != null) {
            extraInputs = List.of(EntryIngredients.ofIngredient(recipe.extraInputs()));
        } else {
            extraInputs = List.of(EntryIngredient.of(EntryStacks.of(ItemStack.EMPTY)));
        }
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return TransformationCategoryREI.TYPE;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return null;
    }

    public List<EntryIngredient> getExtraInputEntries() {
        return extraInputs;
    }

}