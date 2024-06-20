package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueSkiesIntegration {

    static final String MOD = "blue_skies";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("azulfo", CommonStrings.AZULFO_FOOD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("crogre", CommonStrings.BUG_GUTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("crystal_camel", CommonStrings.SCALEFRUIT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("reindeer", CommonStrings.PINE_FRUIT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("shade_monitor", CommonStrings.FISHES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("stardust_ram", CommonStrings.PINE_FRUIT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("crystal_camel", CommonStrings.SCALEFRUIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("cosmic_fox", CommonStrings.CRESCENT_FRUIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("reindeer", CommonStrings.PINE_FRUIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}
