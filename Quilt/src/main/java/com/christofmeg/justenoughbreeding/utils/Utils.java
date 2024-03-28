package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.jei.BreedingCategory;
import com.christofmeg.justenoughbreeding.jei.BreedingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static void registerMobBreedingRecipes(IRecipeRegistration registration) {
        List<String> sortedMobNames = new ArrayList<>(CommonConstants.breedingIngredients.keySet());
        Collections.sort(sortedMobNames);

        for (String mobName : sortedMobNames) {
            if (mobName != null) {
                if (CommonConstants.breedingIngredients != null) {
                    String mobIngredients = CommonConstants.breedingIngredients.get(mobName);
                    String mobResultItem = CommonConstants.breedingEggResult.get(mobName) != null ? CommonConstants.breedingEggResult.get(mobName) : "";
                    if (CommonConstants.sharedGetSpawnEggFromEntity != null) {
                        if (CommonConstants.sharedGetSpawnEggFromEntity.get(mobName) != null) {
                            String mobSpawnEgg = CommonConstants.sharedGetSpawnEggFromEntity.get(mobName);
                            int mobMinResultCount = CommonConstants.breedingEggResultMinAmount.get(mobName) != null ? CommonConstants.breedingEggResultMinAmount.get(mobName) : 1;
                            int mobMaxResultCount = CommonConstants.breedingEggResultMaxAmount.get(mobName) != null ? CommonConstants.breedingEggResultMaxAmount.get(mobName) : 1;

                            if (mobIngredients != null && mobSpawnEgg != null) {
                                Ingredient combinedIngredient = createCombinedIngredient(mobIngredients);
                                List<Ingredient> combinedResultIngredient = createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
                                Item spawnEggItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEgg.trim()));

                                if (spawnEggItem instanceof SpawnEggItem spawnEgg) {
                                    EntityType<?> entityType = spawnEgg.getType(null);
                                    Boolean needsToBeTamed = CommonConstants.breedingNeedsToBeTamed.get(mobName);
                                    Boolean animalTrusting = CommonConstants.breedingNeedsToBeTrusting.get(mobName);

                                    Ingredient combinedExtraIngredient = null;
                                    if (CommonConstants.breedingExtraIngredients != null) {
                                        if (CommonConstants.breedingExtraIngredients.get(mobName) != null) {
                                            String mobExtraIngredients = CommonConstants.breedingExtraIngredients.get(mobName);
                                            if (mobExtraIngredients != null) {
                                                combinedExtraIngredient = createCombinedIngredient(mobExtraIngredients);
                                            }
                                        }
                                    }

                                    BreedingRecipe breedingRecipe = createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
                                    registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(breedingRecipe));
                                }
                            }
                        }
                    }
                    if (CommonConstants.breedingGetSpawnEggFromItem != null && CommonConstants.breedingGetMobFromString != null) {
                        if (CommonConstants.breedingGetSpawnEggFromItem.get(mobName) != null && CommonConstants.breedingGetMobFromString.get(mobName) != null) {
                            String mobSpawnEggItem = CommonConstants.breedingGetSpawnEggFromItem.get(mobName);
                            String mobEntityName = CommonConstants.breedingGetMobFromString.get(mobName);
                            int mobMinResultCount = CommonConstants.breedingEggResultMinAmount.get(mobName) != null ? CommonConstants.breedingEggResultMinAmount.get(mobName) : 1;
                            int mobMaxResultCount = CommonConstants.breedingEggResultMaxAmount.get(mobName) != null ? CommonConstants.breedingEggResultMaxAmount.get(mobName) : 1;

                            if (mobIngredients != null && mobSpawnEggItem != null && mobEntityName != null) {
                                Ingredient combinedIngredient = createCombinedIngredient(mobIngredients);
                                List<Ingredient> combinedResultIngredient = createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
                                Item spawnEggItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEggItem.trim()));
                                EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(mobEntityName.trim()));
                                Boolean needsToBeTamed = CommonConstants.breedingNeedsToBeTamed.get(mobName);
                                Boolean animalTrusting = CommonConstants.breedingNeedsToBeTrusting.get(mobName);

                                Ingredient combinedExtraIngredient = null;
                                if (CommonConstants.breedingExtraIngredients != null) {
                                    if (CommonConstants.breedingExtraIngredients.get(mobName) != null) {
                                        String mobExtraIngredients = CommonConstants.breedingExtraIngredients.get(mobName);
                                        if (mobExtraIngredients != null) {
                                            combinedExtraIngredient = createCombinedIngredient(mobExtraIngredients);
                                        }
                                    }
                                }

                                BreedingRecipe breedingRecipe = createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
                                registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(breedingRecipe));
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<Ingredient> createCombinedResultIngredients(String mobIngredients, int minCount, int maxCount) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> resultIngredients = new ArrayList<>();

        List<ItemStack> combinedItemStacks = new ArrayList<>();

        for (int count = minCount; count <= maxCount; count++) {
            for (String ingredientId : ingredientIds) {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedItemStacks.add(new ItemStack(ingredientItem, count));
            }
        }

        resultIngredients.add(Ingredient.of(combinedItemStacks.toArray(new ItemStack[0])));
        return resultIngredients;
    }

    private static BreedingRecipe createBreedingRecipe(EntityType<?> entityType, Ingredient combinedIngredient, Item spawnEggItem, Boolean needsToBeTamed, List<Ingredient> resultItemStacks, Boolean animalTrusting, @Nullable Ingredient combinedExtraIngredient) {
        List<ItemStack> mergedResultItemStacks = new ArrayList<>();

        for (Ingredient resultItemStack : resultItemStacks) {
            ItemStack[] stacks = resultItemStack.getItems();
            mergedResultItemStacks.addAll(Arrays.asList(stacks));
        }

        return new BreedingRecipe(
                entityType,
                combinedIngredient,
                new ItemStack(spawnEggItem),
                needsToBeTamed,
                Ingredient.of(mergedResultItemStacks.toArray(new ItemStack[0])),
                combinedExtraIngredient,
                animalTrusting
        );
    }

    private static Ingredient createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(CommonUtils.createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem)));
            }
        }

        return Ingredient.of(Arrays.stream(combinedIngredients.toArray(Ingredient[]::new))
                .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
                .distinct()
                .toArray(ItemStack[]::new));
    }

}
