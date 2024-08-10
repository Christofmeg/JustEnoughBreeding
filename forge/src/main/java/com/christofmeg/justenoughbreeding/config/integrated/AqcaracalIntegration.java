package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AqcaracalIntegration {

    static final String MOD = "aqcaracal";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();

    public static void init() {

        CommonUtils.addAnimalWithTamedTag("caracal", CommonStrings.CARACAL_FOOD, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("caracal", CommonStrings.CARACAL_FOOD, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}