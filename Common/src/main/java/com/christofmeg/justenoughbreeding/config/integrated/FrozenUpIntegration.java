package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrozenUpIntegration {

    static final String MOD = "frozenup";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("chilloo", CommonStrings.CHILLOO_BREED_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("penguin", CommonStrings.FISHES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown);

        CommonUtils.addTemperAnimal("reindeer", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.CARROT,
                CommonStrings.SUGAR,
                CommonStrings.GLOW_LICHEN,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{2, 3, 1, 15, 3, 6, 10, 10}, temperDataMap);
        CommonUtils.addAnimalTempers(temperDataMap, MOD);
    }

}