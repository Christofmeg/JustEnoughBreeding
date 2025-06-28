package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlexsMobsIntegration {

    static final String MOD = "alexsmobs";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, String> extraIngredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();

    static final String MEAT = Utils.getEdibleMeatItemNames(true);
    static final String MEAT_WITHOUT_FLESH = Utils.getEdibleMeatItemNames(false);

    public static void init() {
        CommonUtils.addAnimal("gelada_monkey", CommonStrings.DEAD_BUSH, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hummingbird", CommonStrings.FLOWERS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("jerboa", CommonStrings.INSECTS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("kangaroo", CommonStrings.DEAD_BUSH_GRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("laviathan", CommonStrings.LARVA, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("maned_wolf", CommonStrings.CHICKEN_RABBIT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("moose", CommonStrings.DANDELION, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("mudskipper", CommonStrings.LOBSTER_LARVA, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("mungus", CommonStrings.MUNGAL, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("mungus", CommonStrings.MUNGAL, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("orca", CommonStrings.SALMON, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("potoo", CommonStrings.INSECTS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("raccoon", CommonStrings.BREAD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rain_frog", CommonStrings.INSECTS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rattlesnake", MEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rhinoceros", CommonStrings.DEAD_BUSH_GRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("roadrunner", CommonStrings.INSECTS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("seagull", CommonStrings.COD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("seal", CommonStrings.LOBSTER_TAIL, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("skunk", CommonStrings.SWEET_BERRIES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("snow_leopard", CommonStrings.MOOSE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sugar_glider", CommonStrings.HONEYCOMB, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("tasmanian_devil", MEAT_WITHOUT_FLESH, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("tiger", CommonStrings.TIGER_BREEDABLES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("toucan", CommonStrings.EGG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("tusklin", CommonStrings.RED_MUSHROOM, animalNames, ingredients, breedingCooldown);
        
        CommonUtils.addAnimalWithTamedTag("elephant", CommonStrings.ACACIA_BLOSSOM, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("flutter", CommonStrings.BONE_MEAL, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("gorilla", CommonStrings.BANANAS_TAG, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("grizzly_bear", CommonStrings.SALMON, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("komodo_dragon", CommonStrings.FLESH, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("mantis_shrimp", CommonStrings.LOBSTER, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("mimic_octopus", CommonStrings.TROPICAL_FISH, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("tarantula_hawk", CommonStrings.FERMENTED_SPIDER_EYE, "minecraft:cave_spider_spawn_egg, minecraft:spider_spawn_egg", animalNames, ingredients, extraIngredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("warped_toad", CommonStrings.LARVA, animalNames, ingredients, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("bald_eagle", CommonStrings.BALD_EAGLE_TAMEABLES_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("capuchin_monkey", CommonStrings.BANANAS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("cosmaw", CommonStrings.COSMIC_COD, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("crow", CommonStrings.PUMPKIN_SEEDS, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("elephant", CommonStrings.ACACIA_BLOSSOM, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("flutter", CommonStrings.FLOWERS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("gorilla", CommonStrings.BANANAS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("grizzly_bear", CommonStrings.GRIZZLY_HONEY_TAG, tamableOnly, tamingIngredients, tamingChance, CommonStrings.SALMON, extraIngredients);
        CommonUtils.addTamableOnly("komodo_dragon", CommonStrings.FLESH, tamableOnly, tamingIngredients, tamingChance, CommonStrings.SALMON, extraIngredients);
        CommonUtils.addTamableOnly("mantis_shrimp", CommonStrings.TROPICAL_FISH, tamableOnly, tamingIngredients, tamingChance, CommonStrings.SALMON, extraIngredients);
        CommonUtils.addTamableOnly("mimic_octopus", CommonStrings.LOBSTER, tamableOnly, tamingIngredients, tamingChance, CommonStrings.SALMON, extraIngredients);
        CommonUtils.addTamableOnly("raccoon", CommonStrings.EGG, tamableOnly, tamingIngredients, tamingChance, CommonStrings.SALMON, extraIngredients);
        CommonUtils.addTamableOnly("warped_toad", CommonStrings.LARVA, tamableOnly, tamingIngredients, tamingChance, CommonStrings.SALMON, extraIngredients);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, "spawn_egg_", true, extraIngredients);
    }

}
