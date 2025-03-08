package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EMIUtils {

    public static void registerRecipes(EmiRegistry registration) {

        List<BreedingRecipe> breedingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get()));
        breedingRecipes.sort(Comparator.comparing(r -> r.animalID));
        for (BreedingRecipe recipe : breedingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.modID) && recipe.entityType != null) {
                registration.addRecipe(
                        BreedingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "breeding" + "/" + recipe.modID + "/" + recipe.animalID))
                                .breedingRecipe(recipe)
                                .build()
                );
            }
        }

        List<TamingRecipe> tamingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE.get()));
        tamingRecipes.sort(Comparator.comparing(r -> r.animalID));
        for (TamingRecipe recipe : tamingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.modID) && recipe.entityType != null) {
                registration.addRecipe(
                        TamingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "taming" + "/" + recipe.modID + "/" + recipe.animalID))
                                .tamingRecipe(recipe)
                                .build()
                );
            }
        }

        List<TemperRecipe> temperRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get()));
        tamingRecipes.sort(Comparator.comparing(r -> r.animalID));
        for (TemperRecipe recipe : temperRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.modID) && recipe.entityType != null) {
                registration.addRecipe(
                        TemperCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "temper" + "/" + recipe.modID + "/" + recipe.animalID))
                                .temperRecipe(recipe)
                                .build()
                );
            }
        }

        List<TrustingRecipe> trustingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get()));
        trustingRecipes.sort(Comparator.comparing(r -> r.animalID));
        for (TrustingRecipe recipe : trustingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.modID) && recipe.entityType != null) {
                registration.addRecipe(
                        TrustingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "trusting" + "/" + recipe.modID + "/" + recipe.animalID))
                                .trustingRecipe(recipe)
                                .build()
                );
            }
        }
/*
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
                                    registration.addRecipe(
                                            BreedingCategoryEMI.builder()
                                            .id(new ResourceLocation(CommonConstants.MOD_ID, "breeding" + "/" + breedingRecipe.entityType.getDescriptionId() + "/" + breedingRecipe.spawnEgg.getDescriptionId()))
                                            .breedingRecipe(breedingRecipe)
                                            .build()
                                    );
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
                                registration.addRecipe(
                                        BreedingCategoryEMI.builder()
                                                .id(new ResourceLocation(CommonConstants.MOD_ID, "breeding" + "/" + breedingRecipe.entityType.getDescriptionId() + "/" + breedingRecipe.spawnEgg.getDescriptionId() + "_2"))
                                                .breedingRecipe(breedingRecipe)
                                                .build()
                                );
                            }
                        }
                    }
                }
            }
        }*/
    }

}
