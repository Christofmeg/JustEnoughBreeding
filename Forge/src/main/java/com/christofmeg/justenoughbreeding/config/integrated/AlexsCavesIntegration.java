package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlexsCavesIntegration {

    static final String MOD = "alexscaves";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, String> extraTamingIngredients = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimalEggLaying("relicheirus", CommonStrings.TREE_STAR, "alexscaves:relicheirus_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalWithTamedTag("raycat", CommonStrings.RADGILL, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalEggLaying("subterranodon", CommonStrings.COD_RAW_AND_COOKED, "alexscaves:subterranodon_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalEggLayingWithTamedTag("tremorsaurus", CommonStrings.TREMORSAURUS_FOOD, "alexscaves:tremorsaurus_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        CommonUtils.addAnimalEggLayingWithTamedTag("vallumraptor", CommonStrings.DINOSAUR_NUGGET, "alexscaves:vallumraptor_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed, "spawn_egg_", true, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("raycat", CommonStrings.RADGILL, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("tremorsaurus", CommonStrings.SERENE_SALAD, tamableOnly, tamingIngredients, tamingChance, CommonStrings.PRIMITIVE_CLUB, extraTamingIngredients);
        CommonUtils.addTamableOnly("vallumraptor", CommonStrings.SERENE_SALAD, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, "spawn_egg_", true, extraTamingIngredients);
    }

}
