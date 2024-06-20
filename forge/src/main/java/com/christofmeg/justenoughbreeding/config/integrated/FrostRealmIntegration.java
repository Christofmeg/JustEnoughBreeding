package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrostRealmIntegration {

    static final String MOD = "frostrealm";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final List<String> trustableOnly = new ArrayList<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> trustingIngredients = new HashMap<>();
    static final Map<String, Integer> trustingChance = new HashMap<>();

    static final String MEAT = CommonUtils.getEdibleMeatItemNames(true);

    public static void init() {
        CommonUtils.addAnimal("crystal_fox", CommonStrings.BEARBERRY, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("crystal_tortoise", CommonStrings.COLD_GRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("frost_wolf", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("seal", CommonStrings.FISHES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("snowpile_quail", CommonStrings.SNOWPILE_QUAIL_FOOD, "frostrealm:snowpile_quail_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("frost_wolf", MEAT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, "spawn_egg_", true);

        CommonUtils.addTrustingOnly("crystal_fox", CommonStrings.BEARBERRY, trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addTrustingAnimalNames(trustableOnly, trustingIngredients, trustingChance, MOD);
    }

}
