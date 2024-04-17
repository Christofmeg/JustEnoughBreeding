package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AetherIntegration {

    static final String MOD = "aether";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("aerbunny", CommonStrings.AERBUNNY_TEMPTATION_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("flying_cow", CommonStrings.FLYING_COW_TEMPTATION_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("phyg", CommonStrings.PHYG_TEMPTATION_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sheepuff", CommonStrings.SHEEPUFF_TEMPTATION_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
    }

}
