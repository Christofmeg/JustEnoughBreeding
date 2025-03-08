package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftIntegration {

    static final String MOD = "minecraft";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final List<String> trustableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, String> trustingIngredients = new HashMap<>();
    static final Map<String, Integer> trustingChance = new HashMap<>();

    public static void init() {
        CommonUtils.addTrustingOnly("allay", "*", trustableOnly, trustingIngredients, trustingChance);

        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
        CommonUtils.addTrustingAnimalNames(trustableOnly, trustingIngredients, trustingChance, MOD);
    }

}
