package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.TemperRecipe;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftIntegration {

    final String MOD = "minecraft";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final List<String> trustableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();
    final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();
    final Map<String, String> trustingIngredients = new HashMap<>();
    final Map<String, Integer> trustingChance = new HashMap<>();

    final String MEAT = CommonUtils.getEdibleMeatItemNames(true);

    public MinecraftIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("vanilla");

        //TODO skip configfile and use value directly
        // CommonUtils.addTrustingeOnly("allay", ITEMS, trustableOnly, trustingIngredients, trustingChance);

        CommonUtils.addAnimal("axolotl", CommonStrings.TROPICAL_FISH_BUCKET, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("bee", CommonStrings.FLOWERS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("camel", CommonStrings.CACTUS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("cat", CommonStrings.COD_SALMON, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTamableOnly("cat", CommonStrings.COD_SALMON, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addAnimal("chicken", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalWithTamedTag("donkey", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("donkey", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimal("fox", CommonStrings.BERRIES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addTrustingOnly("fox", CommonStrings.BERRIES, trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addAnimalEggLaying("frog", CommonStrings.SLIME_BALL, "minecraft:frogspawn", 1,
                animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimal("goat", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hoglin", CommonStrings.CRIMSON_FUNGUS, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalWithTamedTag("horse", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("horse", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimalWithTamedTag("llama", CommonStrings.WHEAT_HAY, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);

        CommonUtils.addAnimal("mooshroom", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);

        CommonUtils.addTamableOnly("mule", CommonStrings.WHEAT_HAY, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTemperAnimal("mule", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        CommonUtils.addAnimal("ocelot", CommonStrings.COD_SALMON, animalNames, ingredients, breedingCooldown);
        CommonUtils.addTrustingOnly("ocelot", CommonStrings.COD_SALMON, trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addAnimal("panda", CommonStrings.BAMBOO, animalNames, ingredients, breedingCooldown);
        CommonUtils.addTamableOnly("parrot", CommonStrings.SEEDS, tamableOnly, tamingIngredients, tamingChance);

        CommonUtils.addAnimal("pig", CommonStrings.VEGETABLES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalEggLaying("sniffer", CommonStrings.TORCHFLOWERS_SEEDS, "minecraft:sniffer_egg", 1,
                animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addAnimal("strider", CommonStrings.WARPED_FUNGUS, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalWithTamedTag("trader_llama", CommonStrings.WHEAT_HAY, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("trader_llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);

        CommonUtils.addAnimalEggLaying("turtle", CommonStrings.SEAGRASS, "minecraft:turtle_egg", 4,
                animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addAnimalWithTamedTag("wolf", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTamableOnly("wolf", CommonStrings.BONE, tamableOnly, tamingIngredients, tamingChance);

        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);

        CommonUtils.addTrustingAnimalNames(trustableOnly, trustingIngredients, trustingChance, builder, MOD);

        CommonUtils.addAnimalTempers(temperDataMap, builder, MOD);

        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        builder.pop();
    }

}
