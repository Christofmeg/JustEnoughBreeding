package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrittersAndCompanions {

    static final String MOD = "crittersandcompanions";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("otter", CommonStrings.FISHES_TAG + ", " + CommonStrings.CLAM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("ferret", CommonStrings.FERRET_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("red_panda", CommonStrings.BAMBOO, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("ferret", CommonStrings.RABBIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("dragonfly", CommonStrings.SPIDER_EYE, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("red_panda", CommonStrings.SWEET_BERRIES, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("shima_enaga", CommonStrings.SHIMA_ENAGA_FOOD_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("jumping_spider", CommonStrings.DRAGONFLY_WING, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}