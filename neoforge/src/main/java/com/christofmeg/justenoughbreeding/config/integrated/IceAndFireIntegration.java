package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IceAndFireIntegration {

    static final String MOD = "iceandfire";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, String> spawnEggItems = new HashMap<>();
    static final Map<String, String> entitiesFromNames = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimalEggLaying("fire_dragon", "iceandfire:spawn_egg_" + "fire_dragon", "iceandfire:" + "fire_dragon", CommonStrings.FIRE_LILY_MIXTURE, CommonStrings.FIRE_DRAGON_EGGS, 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("ice_dragon", "iceandfire:spawn_egg_" + "ice_dragon", "iceandfire:" + "ice_dragon", CommonStrings.FROST_LILY_MIXTURE, CommonStrings.ICE_DRAGON_EGGS, 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLaying("lightning_dragon", "iceandfire:spawn_egg_" + "lightning_dragon", "iceandfire:" + "lightning_dragon", CommonStrings.LIGHTNING_LILY_MIXTURE, CommonStrings.LIGHTNING_DRAGON_EGGS, 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalEggLayingWithTamedTag("hippogryph", "iceandfire:spawn_egg_" + "hippogryph", "iceandfire:" + "hippogryph", CommonStrings.RABBIT_STEW, "iceandfire:hippogryph_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalWithTamedTag("amphithere", "iceandfire:spawn_egg_" + "amphithere", "iceandfire:" + "amphithere", CommonStrings.COOKIE, animalNames, ingredients, breedingCooldown, needsToBeTamed, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalWithTamedTag("hippocampus", "iceandfire:spawn_egg_" + "hippocampus", "iceandfire:" + "hippocampus", CommonStrings.PRISMARINE_CRYSTALS, animalNames, ingredients, breedingCooldown, needsToBeTamed, spawnEggItems, entitiesFromNames);
        CommonUtils.addAnimalNames(animalNames, ingredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("fire_dragon", "iceandfire:spawn_egg_" + "fire_dragon", "iceandfire:" + "fire_dragon", CommonStrings.CREATIVE_DRAGON_MEAL, tamableOnly, tamingIngredients, tamingChance, 100, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("ice_dragon", "iceandfire:spawn_egg_" + "ice_dragon", "iceandfire:" + "ice_dragon", CommonStrings.CREATIVE_DRAGON_MEAL, tamableOnly, tamingIngredients, tamingChance, 100, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("lightning_dragon", "iceandfire:spawn_egg_" + "lightning_dragon", "iceandfire:" + "lightning_dragon", CommonStrings.CREATIVE_DRAGON_MEAL, tamableOnly, tamingIngredients, tamingChance, 100, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("pixie", "iceandfire:spawn_egg_" + "pixie", "iceandfire:" + "pixie", CommonStrings.CAKE, tamableOnly, tamingIngredients, tamingChance, 100, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("hippogryph", "iceandfire:spawn_egg_" + "hippogryph", "iceandfire:" + "hippogryph", CommonStrings.RABBIT_FOOT, tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableOnly("hippocampus", "iceandfire:spawn_egg_" + "hippocampus", "iceandfire:" + "hippocampus", CommonStrings.KELP, tamableOnly, tamingIngredients, tamingChance, spawnEggItems, entitiesFromNames);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, spawnEggItems, entitiesFromNames);
    }
}
