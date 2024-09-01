package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FIshOfThievesIntegration {

    static final String MOD = "fishofthieves";

    static final List<String> animalNames = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> spawnEggItems = new HashMap<>();
    static final Map<String, String> entitiesFromNames = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("axolotl", "minecraft:axolotl_spawn_egg", "minecraft:axolotl", CommonStrings.WORMS_TAG, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimal("chicken", "minecraft:chicken_spawn_egg", "minecraft:chicken", CommonStrings.WORMS_TAG, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalNames(animalNames, ingredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown);
    }

}