package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChocoCraftIntegration {

    static final String MOD = "chococraft";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, String> spawnEggItems = new HashMap<>();
    static final Map<String, String> entitiesFromNames = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("black" + "_" + "chocobo", "chococraft:" + "black" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("blue" + "_" + "chocobo", "chococraft:" + "blue" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("flame" + "_" + "chocobo", "chococraft:" + "flame" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("green" + "_" + "chocobo", "chococraft:" + "green" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("gold" + "_" + "chocobo", "chococraft:" + "gold" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("pink" + "_" + "chocobo", "chococraft:" + "pink" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("purple" + "_" + "chocobo", "chococraft:" + "purple" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("red" + "_" + "chocobo", "chococraft:" + "red" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("white" + "_" + "chocobo", "chococraft:" + "white" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("yellow" + "_" + "chocobo", "chococraft:" + "yellow" + "_chocobo_spawn_egg", "chococraft:chocobo", CommonStrings.LOVERLY_GYSAHL, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalNames(animalNames, ingredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("black_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("blue_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("flame_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("green_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("gold_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("pink_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("purple_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("red_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("white_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("yellow_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}
