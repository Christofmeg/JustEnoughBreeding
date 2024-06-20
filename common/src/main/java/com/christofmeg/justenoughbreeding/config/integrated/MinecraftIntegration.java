package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.jei.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;

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
        CommonUtils.addAnimal("armadillo", "#armadillo" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("axolotl", "#axolotl" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("bee", "#bee" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("camel", "#camel" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("cat", "#cat" + "_food", animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTamableOnly("cat", "#cat" + "_food", tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addAnimal("chicken", "#chicken" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cow", "#cow" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("donkey", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("donkey", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimal("fox", "#fox" + "_food", animalNames, ingredients, breedingCooldown);
//        CommonUtils.addTrustingOnly("fox", "#fox" + "_food", trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addAnimalEggLaying("frog", "#frog" + "_food", "minecraft:frogspawn", 1,
                animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimal("goat", "#goat" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hoglin", "#hoglin" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("horse", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("horse", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimalWithTamedTag("llama", CommonStrings.HAY_BLOCK, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);

        CommonUtils.addAnimal("mooshroom", "#cow" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addTemperAnimal("mule", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimal("ocelot", "#ocelot" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addTrustingOnly("ocelot", "#ocelot" + "_food", trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addAnimal("panda", "#panda" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addTamableOnly("parrot", "#parrot" + "_food", tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addAnimal("pig", "#pig" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rabbit", "#rabbit" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sheep", "#sheep" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("sniffer", "#sniffer" + "_food", "minecraft:sniffer_egg", 1,
                animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addAnimal("strider", "#strider" + "_food", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("trader_llama", CommonStrings.HAY_BLOCK, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("trader_llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);

        CommonUtils.addAnimalEggLaying("turtle", "#turtle" + "_food", "minecraft:turtle_egg", 4,
                animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalWithTamedTag("wolf", "#wolf" + "_food", animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTamableOnly("wolf", CommonStrings.BONE, tamableOnly, tamingIngredients, tamingChance);

        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, needsToBeTamed);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD);
        CommonUtils.addTrustingAnimalNames(trustableOnly, trustingIngredients, trustingChance, MOD);
        CommonUtils.addAnimalTempers(temperDataMap, MOD);
        CommonUtils.addAnimalNames(animalNames, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
    }

}
