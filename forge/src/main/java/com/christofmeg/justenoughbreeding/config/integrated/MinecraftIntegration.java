package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftIntegration {

    static final String MOD = "minecraft";

    static final List<String> animalNames = new ArrayList<>();
    static final List<String> tamableOnly = new ArrayList<>();
    static final List<String> trustableOnly = new ArrayList<>();
    static final Map<String, String> ingredients = new HashMap<>();
    static final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    static final Map<String, String> resultEggs = new HashMap<>();
    static final Map<String, Integer> eggsAmountMin = new HashMap<>();
    static final Map<String, Integer> eggsAmountMax = new HashMap<>();
    static final Map<String, Integer> breedingCooldown = new HashMap<>();
    static final Map<String, String> tamingIngredients = new HashMap<>();
    static final Map<String, Integer> tamingChance = new HashMap<>();
    static final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();
    static final Map<String, String> trustingIngredients = new HashMap<>();
    static final Map<String, Integer> trustingChance = new HashMap<>();

    public static void init() {
        CommonUtils.addTrustingOnly("allay", "*", trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addTemperAnimal("donkey", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);
        CommonUtils.addTrustingOnly("fox", CommonStrings.BERRIES, trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addTemperAnimal("horse", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);
        CommonUtils.addTemperAnimal("llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);
        CommonUtils.addTemperAnimal("mule", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);
        CommonUtils.addTrustingOnly("ocelot", CommonStrings.COD_SALMON, trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addTemperAnimal("trader_llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);

        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
        CommonUtils.addTrustingAnimalNames(trustableOnly, trustingIngredients, trustingChance, MOD);
        CommonUtils.addAnimalTempers(temperDataMap, MOD);
    }

}
