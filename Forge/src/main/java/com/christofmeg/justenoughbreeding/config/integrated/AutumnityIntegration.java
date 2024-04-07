package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutumnityIntegration {

    static final String MOD = "autumnity";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("snail", CommonStrings.SNAIL_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("turkey", CommonStrings.TURKEY_FOOD_TAG, animalNames, ingredients, breedingCooldown);
    }

}
