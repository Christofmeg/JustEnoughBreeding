package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.jei.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentalIntegration {

    static final String MOD = "environmental";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("slabfish", CommonStrings.SLABFISH_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("duck", CommonStrings.DUCK_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("deer", CommonStrings.DEER_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("reindeer", CommonStrings.REINDEER_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("yak", CommonStrings.YAK_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("zebra", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("zebra", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
        CommonUtils.addAnimalTempers(temperDataMap, MOD);
    }

}