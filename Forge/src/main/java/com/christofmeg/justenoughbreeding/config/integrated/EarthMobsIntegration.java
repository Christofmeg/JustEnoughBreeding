package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.jei.recipe.TemperRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarthMobsIntegration {

    static final String MOD = "earthmobsmod";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();

    public static void init() {
        CommonUtils.addAnimal("albino_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cluck_shroom", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cream_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("duck", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("fancy_chicken", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("horned_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hyper_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("jolly_llama", CommonStrings.WHEAT_HAY, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("jumbo_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("magma_cow", CommonStrings.MAGMA_CREAM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("moobloom", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("moolip", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("teacup_pig", CommonStrings.VEGETABLES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("umbra_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("wooly_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("zombified_pig", CommonStrings.VEGETABLES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("zombified_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("baby_ghast", CommonStrings.CRIMSON_NYLIUM_FUNGUS, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("skelton_wolf", CommonStrings.BONE_FLESH, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("wither_skelton_wolf", CommonStrings.BONE_FLESH, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);

        CommonUtils.addTemperAnimal("jolly_llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);
        CommonUtils.addAnimalTempers(temperDataMap, MOD);
    }

}
