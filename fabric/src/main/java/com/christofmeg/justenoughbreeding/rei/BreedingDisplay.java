package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreedingDisplay extends BasicDisplay {

    protected List<EntryIngredient> extraInputs;
    public BreedingRecipe breedingRecipe;

    public BreedingDisplay(BreedingRecipe recipe) {
        super(List.of(EntryIngredients.ofIngredient(recipe.breedingCatalyst), EntryIngredients.ofIngredient(Ingredient.of(recipe.spawnEgg.getItem()))),
                List.of(recipe.resultItemStack == null ? EntryIngredient.of(EntryStacks.of(ItemStack.EMPTY)) : EntryIngredients.ofIngredient(recipe.resultItemStack), EntryIngredients.ofIngredient(Ingredient.of(recipe.spawnEgg.getItem())))
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

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return null;
    }

    public List<EntryIngredient> getExtraInputEntries() {
        return extraInputs;
    }

}