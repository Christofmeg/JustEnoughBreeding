package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcologicsIntegration {

    static final String MOD = "ecologics";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("camel", CommonStrings.PRICKLY_PEAR, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("penguin", CommonStrings.PENGUIN_TEMPT_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("squirrel", CommonStrings.SQUIRREL_TEMPT_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown);
    }

}