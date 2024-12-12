package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TerraFirmaCraftIntegration {

    static final String MOD = "tfc";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> extraIngredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, String> spawnEggItems = new HashMap<>();
    static final Map<String, String> entitiesFromNames = new HashMap<>();

    public static void init() {
        //TODO add (Familiarity > 30 %)
        CommonUtils.addAnimal("pig", "tfc:spawn_egg/" + "pig", "tfc:" + "pig", "#tfc:" + "pig" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("cow", "tfc:spawn_egg/" + "cow", "tfc:" + "cow", "#tfc:" + "cow" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("goat", "tfc:spawn_egg/" + "goat", "tfc:" + "goat", "#tfc:" + "goat" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("yak", "tfc:spawn_egg/" + "yak", "tfc:" + "yak", "#tfc:" + "yak" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("alpaca", "tfc:spawn_egg/" + "alpaca", "tfc:" + "alpaca", "#tfc:" + "alpaca" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("sheep", "tfc:spawn_egg/" + "sheep", "tfc:" + "sheep", "#tfc:" + "sheep" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("musk_ox", "tfc:spawn_egg/" + "musk_ox", "tfc:" + "musk_ox", "#tfc:" + "musk_ox" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("chicken", "tfc:spawn_egg/" + "chicken", "tfc:" + "chicken", "#tfc:" + "chicken" + "_food", "tfc:nest_box", CommonStrings.EGG, 1, animalNames, ingredients, extraIngredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("duck", "tfc:spawn_egg/" + "duck", "tfc:" + "duck", "#tfc:" + "duck" + "_food", "tfc:nest_box", CommonStrings.EGG, 1, animalNames, ingredients, extraIngredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("quail", "tfc:spawn_egg/" + "quail", "tfc:" + "quail", "#tfc:" + "quail" + "_food", "tfc:nest_box", CommonStrings.EGG, 1, animalNames, ingredients, extraIngredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("donkey", "tfc:spawn_egg/" + "donkey", "tfc:" + "donkey", "#tfc:" + "donkey" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("mule", "tfc:spawn_egg/" + "mule", "tfc:" + "mule", "#tfc:" + "mule" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("horse", "tfc:spawn_egg/" + "horse", "tfc:" + "horse", "#tfc:" + "horse" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("cat", "tfc:spawn_egg/" + "cat", "tfc:" + "cat", "#tfc:" + "cat" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("cat", "tfc:spawn_egg/" + "cat", "tfc:" + "cat", "#tfc:" + "cat" + "_food", tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("dog", "tfc:spawn_egg/" + "dog", "tfc:" + "dog", "#tfc:" + "dog" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("dog", "tfc:spawn_egg/" + "dog", "tfc:" + "dog", "#tfc:" + "dog" + "_food", tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("frog", "tfc:spawn_egg/" + "frog", "tfc:" + "frog", "#tfc:" + "frog" + "_food", "minecraft:frogspawn", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("rabbit", "tfc:spawn_egg/" + "rabbit", "tfc:" + "rabbit", "#tfc:" + "rabbit" + "_food", animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);

        CommonUtils.addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}