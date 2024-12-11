package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwilightForestIntegration {

    static final String MOD = "twilightforest";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("bighorn_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("boar", CommonStrings.VEGETABLES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("deer", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("dwarf_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("penguin", CommonStrings.COD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown);
    }

}
