package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Ingredient> createCombinedResultIngredients(String mobIngredients, int minCount, int maxCount) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> resultIngredients = new ArrayList<>();
        List<Holder<Item>> combinedItemStacks = new ArrayList<>();

        for (int count = minCount; count <= maxCount; count++) {
            for (String ingredientId : ingredientIds) {
                // Validate ResourceLocation and the fetched Item
                ResourceLocation resourceLocation = ResourceLocation.tryParse(ingredientId.trim());
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(resourceLocation);
                if (resourceLocation == null || ingredientItem == Items.AIR) {
                    continue;
                }
                combinedItemStacks.add(new ItemStack(ingredientItem, count).getItemHolder());
            }
        }

        // Ensure combinedItemStacks is not empty before creating an Ingredient
        if (!combinedItemStacks.isEmpty()) {
            resultIngredients.add(Ingredient.of(HolderSet.direct(combinedItemStacks)));
        }

        return resultIngredients;
    }

    public static BreedingRecipe createBreedingRecipe(EntityType<?> entityType, Ingredient combinedIngredient, Item spawnEggItem, Boolean needsToBeTamed, @Nullable List<Ingredient> resultItemStacks, Boolean animalTrusting, @Nullable Ingredient combinedExtraIngredient) {
        if (resultItemStacks == null || resultItemStacks.isEmpty()) {
            return new BreedingRecipe(
                    entityType,
                    combinedIngredient,
                    new ItemStack(spawnEggItem),
                    needsToBeTamed,
                    null,
                    combinedExtraIngredient,
                    animalTrusting
            );
        }

        List<Holder<Item>> list = new ArrayList<>();
        for (Ingredient resultItemStack : resultItemStacks) {
            if (resultItemStack == null || resultItemStack.items().toList().isEmpty()) {
                continue;
            }
            list.addAll(resultItemStack.items().toList());
        }

        Ingredient mergedResultItemStacks = Ingredient.of(HolderSet.direct(list));
        return new BreedingRecipe(
                entityType,
                combinedIngredient,
                new ItemStack(spawnEggItem),
                needsToBeTamed,
                mergedResultItemStacks,
                combinedExtraIngredient,
                animalTrusting
        );
    }

    public static Ingredient createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(CommonUtils.createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(ResourceLocation.parse(ingredientId.trim()));
                combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem).getItem()));
            }
        }

        List<Holder<Item>> list = new ArrayList<>();
        for (Ingredient resultItemStack : combinedIngredients) {
            list.addAll(resultItemStack.items().toList());
        }

        return Ingredient.of(HolderSet.direct(list));
    }

}
