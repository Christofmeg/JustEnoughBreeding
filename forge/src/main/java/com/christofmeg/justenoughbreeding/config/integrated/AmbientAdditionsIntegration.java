package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmbientAdditionsIntegration {

    static final String MOD = "ambientadditions";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();

    static final String MEAT = Utils.getEdibleMeatItemNames(true);

    public static void init() {
        CommonUtils.addAnimal("aye_aye", CommonStrings.WORM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("blunthead_tree_snake", CommonStrings.PINOCCHIO_ANOLE_POT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cardigan_corgi", MEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addTamableOnly("cardigan_corgi", CommonStrings.PUMPKIN_PIE, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addAnimal("giant_land_snail", CommonStrings.LEAVES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addTamableOnly("hoatzin", CommonStrings.FLOWERS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("iiwi", CommonStrings.FLOWERS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addAnimalEggLaying("leaf_frog", CommonStrings.SPIDER_EYE, "ambientadditions:leaf_frogspawn", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimal("marten", CommonStrings.EGG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("mole", CommonStrings.WORM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("moustached_tamarin", CommonStrings.APPLE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("naked_mole_rat", CommonStrings.POTATO, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("nine_banded_armadillo", CommonStrings.SPIDER_EYE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("pancake_slug", CommonStrings.LEAVES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("pembroke_corgi", MEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addTamableOnly("pembroke_corgi", CommonStrings.PUMPKIN_PIE, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addAnimal("pink_fairy_armadillo", CommonStrings.SPIDER_EYE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("red_river_hog", CommonStrings.APPLE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("ring_tailed_lemur", CommonStrings.DANDELION, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("siamang_gibbon", CommonStrings.CARROT_APPLE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sloth_bear", CommonStrings.WORM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("spider_tailed_adder", CommonStrings.CHICKEN, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("veiled_chameleon", CommonStrings.SPIDER_EYE, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
    }

}