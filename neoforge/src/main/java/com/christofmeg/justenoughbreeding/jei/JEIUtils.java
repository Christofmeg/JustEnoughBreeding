package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JEIUtils {

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
                                Ingredient combinedIngredient = Utils.createCombinedIngredient(mobIngredients);
                                List<Ingredient> combinedResultIngredient = Utils.createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
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
                                                combinedExtraIngredient = Utils.createCombinedIngredient(mobExtraIngredients);
                                            }
                                        }
                                    }

                                    BreedingRecipe breedingRecipe = Utils.createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
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
                                Ingredient combinedIngredient = Utils.createCombinedIngredient(mobIngredients);
                                List<Ingredient> combinedResultIngredient = Utils.createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
                                Item spawnEggItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEggItem.trim()));
                                EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(mobEntityName.trim()));
                                Boolean needsToBeTamed = CommonConstants.breedingNeedsToBeTamed.get(mobName);
                                Boolean animalTrusting = CommonConstants.breedingNeedsToBeTrusting.get(mobName);

                                Ingredient combinedExtraIngredient = null;
                                if (CommonConstants.breedingExtraIngredients != null) {
                                    if (CommonConstants.breedingExtraIngredients.get(mobName) != null) {
                                        String mobExtraIngredients = CommonConstants.breedingExtraIngredients.get(mobName);
                                        if (mobExtraIngredients != null) {
                                            combinedExtraIngredient = Utils.createCombinedIngredient(mobExtraIngredients);
                                        }
                                    }
                                }

                                BreedingRecipe breedingRecipe = Utils.createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
                                registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(breedingRecipe));
                            }
                        }
                    }
                }
            }
        }
    }

}
