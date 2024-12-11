package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeadowIntegration {

    static final String MOD = "meadow";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("albino" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cookie" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cream" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("dairy" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("dark" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("pinto" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sunset" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("highland" + "_cattle", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("umbra" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("warped" + "_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("water" + "_buffalo", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("meadow" + "_chicken", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("flecked" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("fuzzy" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("horned" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("inky" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("long_nosed" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("patched" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rocky" + "_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown);
    }

}