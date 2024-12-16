package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FarmLifeIntegration {

    static final String MOD = "farmlife";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("domestic_tribull", CommonStrings.BEETROOT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("galliraptor", CommonStrings.MELON_AND_SEEDS, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown);
    }

}