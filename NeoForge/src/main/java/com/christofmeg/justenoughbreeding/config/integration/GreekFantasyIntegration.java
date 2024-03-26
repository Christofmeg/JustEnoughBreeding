package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.CommonStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreekFantasyIntegration {

    static final String MOD = "greekfantasy"; //TODO BREEDING TEMPER TAMING TRUSTING

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();

    public static void init() {
        addAnimalTamed("orthus", CommonStrings.ORTHUS_FOOD_TAG);


        /*
        greek fantasy:
tamableOnly		cerastes		#greekfantasy:cerastes_food
         */

        //TODO move to mob creation/transformation category
        // arion    gold_ram
        // CommonUtils.addAnimal("makhai", "minecraft:horse_spawn_egg", "minecraft:enchanted_golden_apple", animalNames, ingredients, extraIngredients, breedingCooldown);


        for (String animal : animalNames) {
            String animalIngredients = ingredients.get(animal);
            String animalSpawnEgg = animal + "_spawn_egg";
            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
            if(needsToBeTamed.get(animal) != null) {
                CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
            }
            if(breedingCooldown.get(animal) != null) {
                Integer animalBreedingCooldown = breedingCooldown.get(animal);
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }
        }
    }
    private static void addAnimal(String name, String ingredient) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

    private static void addAnimalTamed(String name, String ingredient) {
        addAnimal(name, ingredient);
        needsToBeTamed.put(name, true);
    }

}
