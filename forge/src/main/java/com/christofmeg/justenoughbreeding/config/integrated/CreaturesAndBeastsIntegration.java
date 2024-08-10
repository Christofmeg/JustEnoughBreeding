package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreaturesAndBeastsIntegration {

    static final String MOD = "cnb";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> spawnEggItems = new HashMap<>();
    static final Map<String, String> entitiesFromNames = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("little_grebe", "cnb:little_grebe_spawn_egg", "cnb:little_grebe", CommonStrings.LITTLE_GREBE_FOOD_TAG, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("cindershell", "cnb:cindershell_spawn_egg", "cnb:cindershell", CommonStrings.CINDERSHEEL_FOOD_TAG, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("yeti", "cnb:yeti_spawn_egg", "cnb:yeti", CommonStrings.SWEET_BERRIES, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);

        CommonUtils.addAnimalEggLaying("lizard", "cnb:lizard_spawn_egg", "cnb:lizard", CommonStrings.APPLE_SLICE, "cnb:lizard_egg", 6, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("lizard_item_desert", "cnb:lizard_item_desert", "cnb:lizard", CommonStrings.APPLE_SLICE, "cnb:lizard_egg", 6, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("lizard_item_desert_2", "cnb:lizard_item_desert_2", "cnb:lizard", CommonStrings.APPLE_SLICE, "cnb:lizard_egg", 6, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("lizard_item_jungle", "cnb:lizard_item_jungle", "cnb:lizard", CommonStrings.APPLE_SLICE, "cnb:lizard_egg", 6, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("lizard_item_jungle_2", "cnb:lizard_item_jungle_2", "cnb:lizard", CommonStrings.APPLE_SLICE, "cnb:lizard_egg", 6, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("lizard_item_mushroom", "cnb:lizard_item_mushroom", "cnb:lizard", CommonStrings.APPLE_SLICE, "cnb:lizard_egg", 6, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalNames(animalNames, ingredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("end_whale", "cnb:end_whale_spawn_egg", "cnb:end_whale", CommonStrings.END_WHALE_FOOD_TAG, tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("yeti", "cnb:yeti_spawn_egg", "cnb:yeti",CommonStrings.MELON_SLICE, tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("sporeling", "cnb:sporeling_overworld_egg", "cnb:" + "sporeling_overworld_egg", CommonStrings.SPORELING_FOOD_TAG, tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, spawnEggItems, entitiesFromNames);

    }

}