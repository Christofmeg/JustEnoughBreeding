package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

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

    static final String MEAT = CommonUtils.getEdibleMeatItemNames(true);

    static final List<String> vanillaAnimalNames = new ArrayList<>();
    static final Map<String, String> vanillaIngredients = new HashMap<>();
    static final Map<String, Integer> vanillaBreedingCooldown = new HashMap<>();
    static final Map<String, Boolean> vanillaNeedsToBeTamed = new HashMap<>();
    static final Map<String, String> vanillaResultEggs = new HashMap<>();
    static final Map<String, Integer> vanillaEggsAmountMin = new HashMap<>();
    static final Map<String, Integer> vanillaEggsAmountMax = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("crab", CommonStrings.CRABFOOD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("foxhound", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("shiba", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("foxhound", CommonStrings.COAL, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("shiba", CommonStrings.BONE, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);

        CommonUtils.addAnimalEggLayingWithTamedTag("parrot", CommonStrings.BEETROOT_SEEDS,
                CommonStrings.RED_PARROT_EGG + "," +
                        CommonStrings.BLUE_PARROT_EGG + "," +
                        CommonStrings.GREEN_PARROT_EGG + "," +
                        CommonStrings.CYAN_PARROT_EGG + "," +
                        CommonStrings.GREY_PARROT_EGG,
                1, vanillaAnimalNames, vanillaIngredients, vanillaBreedingCooldown, vanillaResultEggs, vanillaEggsAmountMin, vanillaEggsAmountMax, vanillaNeedsToBeTamed);
        CommonUtils.addAnimalNames(vanillaAnimalNames, vanillaIngredients, "minecraft", vanillaBreedingCooldown, vanillaNeedsToBeTamed, vanillaResultEggs, vanillaEggsAmountMin, vanillaEggsAmountMax);
    }

}
