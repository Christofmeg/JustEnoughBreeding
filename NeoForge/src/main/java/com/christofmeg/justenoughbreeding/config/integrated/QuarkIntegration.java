package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuarkIntegration {

    static final String MOD = "quark";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();

    static final String MEAT = Utils.getEdibleMeatItemNames(true);

    public static void init() {
        CommonUtils.addAnimal("crab", CommonStrings.CRABFOOD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("foxhound", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("shiba", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("foxhound", CommonStrings.COAL, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("shiba", CommonStrings.BONE, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}
