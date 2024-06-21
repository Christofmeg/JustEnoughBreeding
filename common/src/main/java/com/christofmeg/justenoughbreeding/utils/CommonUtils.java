package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.jei.recipe.TemperRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonUtils {

/*-----------------------------------------------------------------------------------------*/
    public static void addAnimal(String name, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

    public static void addAnimal(String name, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimal(String name, String spawnEggItem, String entityFromName, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimal(String name, String spawnEggItem, String entityFromName, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimal(name, spawnEggItem, entityFromName, ingredient, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimalWithTamedTag(String name, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        needsToBeTamed.put(name, true);
    }

    public static void addAnimalWithTamedTag(String name, String spawnEggItem, String entityFromName, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalWithTamedTag(name, ingredient, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalWithTamedTag(String name, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalWithTamedTag(name, ingredient, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimalWithTamedTag(String name, String spawnEggItem, String entityFromName, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalWithTamedTag(name, ingredient, extraIngredient, animalNames, ingredients, extraIngredients, breedingCooldown, needsToBeTamed);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalEggLaying(String name, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        resultEggs.put(name, resultEgg);
        eggsAmountMin.put(name, 1);
        eggsAmountMax.put(name, eggAmountMax);
    }

    public static void addAnimalEggLaying(String name, String spawnEggItem, String entityFromName, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalEggLaying(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalEggLayingWithTamedTag(String name, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, Boolean> needsToBeTamed) {
        addAnimalEggLaying(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        needsToBeTamed.put(name, true);
    }

    @SuppressWarnings("unused")
    public static void addAnimalEggLayingWithTamedTag(String name, String spawnEggItem, String entityFromName, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, Boolean> needsToBeTamed, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalEggLayingWithTamedTag(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, customSpawnEggString);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, customSpawnEggString);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, customSpawnEggString);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null,null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null,null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName);
        for (String animal : animalNames) {
            if (resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                String animalEggResult = resultEggs.get(animal);
                int animalMinEggAmount = eggsAmountMin.get(animal);
                int animalMaxEggAmount = eggsAmountMax.get(animal);
                CommonConstants.breedingEggResult.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.breedingEggResultMinAmount.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.breedingEggResultMaxAmount.put(MOD + "_" + animal, animalMaxEggAmount);
            }
        }
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed);
        for (String animal : animalNames) {
            if (resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                String animalEggResult = resultEggs.get(animal);
                int animalMinEggAmount = eggsAmountMin.get(animal);
                int animalMaxEggAmount = eggsAmountMax.get(animal);
                CommonConstants.breedingEggResult.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.breedingEggResultMinAmount.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.breedingEggResultMaxAmount.put(MOD + "_" + animal, animalMaxEggAmount);
            }
        }
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, extraIngredients, null, null, MOD, breedingCooldown, needsToBeTamed, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, extraIngredients, null, null, MOD, breedingCooldown, needsToBeTamed, customSpawnEggString, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, null, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String customSpawnEggString, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        for (String animal : animalNames) {
            String animalIngredients = ingredients.get(animal);
            CommonConstants.breedingIngredients.put(MOD + "_" + animal, animalIngredients);

            if (spawnEggItems != null && entitiesFromNames != null) {
                if (spawnEggItems.get(animal) != null && entitiesFromNames.get(animal) != null) {
                    CommonConstants.breedingGetSpawnEggFromItem.put(MOD + "_" + animal, spawnEggItems.get(animal));
                    CommonConstants.breedingGetMobFromString.put(MOD + "_" + animal, entitiesFromNames.get(animal));
                }
            } else {
                if (customSpawnEggString != null) {
                    String animalSpawnEgg = MOD + ":" + customSpawnEggString;
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + animal, animalSpawnEgg);
                } else if (spawnEggString != null) {
                    String animalSpawnEgg = (addStringBeforeAnimalName ?
                            MOD + ":" + spawnEggString + animal :
                            MOD + ":" + animal + spawnEggString);
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + animal, animalSpawnEgg);
                } else {
                    String animalSpawnEgg = MOD + ":" + animal + "_spawn_egg";
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + animal, animalSpawnEgg);
                }
            }

            if (needsToBeTamed != null && needsToBeTamed.get(animal) != null) {
                CommonConstants.breedingNeedsToBeTamed.put(MOD + "_" + animal, true);
            }

            if (breedingCooldown.get(animal) != null) {
                int animalBreedingCooldown = breedingCooldown.get(animal);
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }

            if (extraIngredients != null) {
                if (extraIngredients.get(animal) != null) {
                    String animalExtraBreedingIngredients = extraIngredients.get(animal);
                    CommonConstants.breedingExtraIngredients.put(MOD + "_" + animal, animalExtraBreedingIngredients);
                }
            }
        }
    }

/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addTemperAnimal(String name, String[] temperItems, int[] temperValues, Map<String, List<TemperRecipe>> temperDataMap) {
        List<TemperRecipe> temperDataList = temperDataMap.computeIfAbsent(name, k -> new ArrayList<>());

        for (int i = 0; i < temperItems.length && i < temperValues.length; i++) {
            temperDataList.add(new TemperRecipe(temperItems[i], temperValues[i]));
        }
    }

    public static void addAnimalTempers(Map<String, List<TemperRecipe>> temperDataMap, String MOD) {
        for (Map.Entry<String, List<TemperRecipe>> entry : temperDataMap.entrySet()) {
            String temperAnimal = entry.getKey();
            List<TemperRecipe> temperDataList = entry.getValue();
            List<String> temperIngredients = temperDataList.stream()
                    .map(TemperRecipe::temperIngredient)
                    .collect(Collectors.toList());
            List<Integer> temperValues = temperDataList.stream()
                    .map(TemperRecipe::temperValue)
                    .toList();

            String temperIngredientsString = String.join(", ", temperIngredients);
            String temperValuesString = temperValues.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            CommonConstants.temperIngredients.put(MOD + "_" + temperAnimal, temperIngredientsString);
            CommonConstants.temperValueIngredientsAdd.put(MOD + "_" + temperAnimal, temperValuesString);
        }
    }

/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance) {
        tamableOnly.add(name);
        tamingIngredients.put(name, tamingIngredient);
        tamingChance.put(name, 33);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance) {
        tamableOnly.add(name);
        tamingIngredients.put(name, tamingIngredient);
        tamingChance.put(name, chance);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String extraIngredient, Map<String, String> extraIngredients) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance, String extraIngredient, Map<String, String> extraIngredients) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, chance);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, chance);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String extraIngredient, Map<String, String> extraIngredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, extraIngredient, extraIngredients);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance, String extraIngredient, Map<String, String> extraIngredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, chance, extraIngredient, extraIngredients);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, false, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, false, extraIngredients);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, String customSpawnEggString) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, customSpawnEggString, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, spawnEggString, addStringBeforeAnimalName, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, String customSpawnEggString, @Nullable Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, customSpawnEggString, null, false, extraIngredients, null, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, @Nullable Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, spawnEggString, addStringBeforeAnimalName, extraIngredients, null, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, spawnEggItems, entitiesFromNames);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable Map<String, String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, null, false, extraIngredients, spawnEggItems, entitiesFromNames);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable String customSpawnEggString, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, @Nullable String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        for (String tamable : tamableOnly) {
            if (tamingIngredients.get(tamable) != null && tamingChance.get(tamable) != null) {
                String animalTamingIngredients = tamingIngredients.get(tamable);
                int animalTamingChance = tamingChance.get(tamable);
                CommonConstants.tamingIngredients.put(MOD + "_" + tamable, animalTamingIngredients);
                CommonConstants.tamingChance.put(MOD + "_" + tamable, animalTamingChance);

                if (spawnEggItems != null && entitiesFromNames != null) {
                    if (spawnEggItems.get(tamable) != null && entitiesFromNames.get(tamable) != null) {
                        CommonConstants.breedingGetSpawnEggFromItem.put(MOD + "_" + tamable, spawnEggItems.get(tamable));
                        CommonConstants.breedingGetMobFromString.put(MOD + "_" + tamable, entitiesFromNames.get(tamable));
                    }
                } else {
                    if (customSpawnEggString != null) {
                        String animalSpawnEgg = MOD + ":" + customSpawnEggString;
                        CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + tamable, animalSpawnEgg);
                    } else if (spawnEggString != null) {
                        String animalSpawnEgg = (addStringBeforeAnimalName ?
                                MOD + ":" + spawnEggString + tamable :
                                MOD + ":" + tamable + spawnEggString);
                        CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + tamable, animalSpawnEgg);
                    } else {
                        String animalSpawnEgg = MOD + ":" + tamable + "_spawn_egg";
                        CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + tamable, animalSpawnEgg);
                    }
                }

                if (extraIngredients != null) {
                    if (extraIngredients.get(tamable) != null) {
                        String animalExtraTamingIngredients = extraIngredients.get(tamable);
                        CommonConstants.tamingExtraIngredients.put(MOD + "_" + tamable, animalExtraTamingIngredients);
                    }
                }

            }
        }
    }

/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addTrustingOnly(String name, String trustingIngredient, List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance) {
        trustingOnly.add(name);
        trustingIngredients.put(name, trustingIngredient);
        trustingChance.put(name, 33); //TODO rework this to display that foxes are breedable without trusting the player, but can trust the player if bred
    }

    @SuppressWarnings("unused")
    public static void addTrustingOnly(String name, String spawnEggItem, String entityFromName, String trustingIngredient, List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTrustingOnly(name, trustingIngredient, trustingOnly, trustingIngredients, trustingChance);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTrustingAnimalNames(List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance, String MOD) {
        addTrustingAnimalNames(trustingOnly, trustingIngredients, trustingChance, MOD, null, null);
    }

    public static void addTrustingAnimalNames(List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance, String MOD, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        for (String trusting : trustingOnly) {
            if (trustingIngredients.get(trusting) != null && trustingChance.get(trusting) != null) {
                String animalTrustingIngredients = trustingIngredients.get(trusting);
                int animalTrustingChance = trustingChance.get(trusting);
                CommonConstants.trustingIngredients.put(MOD + "_" + trusting, animalTrustingIngredients);
                CommonConstants.trustingChance.put(MOD + "_" + trusting, animalTrustingChance);
                CommonConstants.breedingNeedsToBeTrusting.put(MOD + "_" + trusting, true);

                if (spawnEggItems != null && entitiesFromNames != null) {
                    if (spawnEggItems.get(trusting) != null && entitiesFromNames.get(trusting) != null) {
                        CommonConstants.breedingGetSpawnEggFromItem.put(MOD + "_" + trusting, spawnEggItems.get(trusting));
                        CommonConstants.breedingGetMobFromString.put(MOD + "_" + trusting, entitiesFromNames.get(trusting));
                    }
                } else {
                    String animalSpawnEgg = MOD + ":" + trusting + "_spawn_egg";
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + trusting, animalSpawnEgg);
                }
            }
        }
    }

 /*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        return "#" + ItemTags.MEAT.location(); //TODO fix with or without Rotten Flesh
    }

    public static Ingredient createTagIngredient(String tagId) {
        String tagLocationStr = tagId.trim().substring(1);
        ResourceLocation tagLocation = new ResourceLocation(tagLocationStr);
        return Ingredient.of(TagKey.create(Registries.ITEM, tagLocation));
    }

}
