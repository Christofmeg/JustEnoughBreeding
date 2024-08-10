package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StevesVanillaIntegration {

    static final String MOD = "steves_vanilla";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("boggord", CommonStrings.MOUNTAIN_BUDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("chest_toad", CommonStrings.SLIME_BALL, "steves_vanilla:chest_toad_eggs",
                1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
    }

}
