package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmbientAdditionsIntegration {

    static final String MOD = "ambientadditions";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("aye_aye", "ambientadditions:worm", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("blunthead_tree_snake", "ambientadditions:pinocchio_anole_pot", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cardigan_corgi", "MEAT", animalNames, ingredients, breedingCooldown);
//  cardigan_corgi tamable with pumpkin pie             does not need to be tamed to breed
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
    }

}
