package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.TemperRecipe;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecraftedCreaturesIntegration {

    final String MOD = "recrafted_creatures";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();
    final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();

    public RecraftedCreaturesIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimalEggLaying("chameleon", CommonStrings.SPIDER_EYES, "recrafted_creatures:chameleon_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalWithTamedTag("giraffe", CommonStrings.GIRAFFE_FOOD, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("mammoth", CommonStrings.HAY_BLOCK, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("marmot", CommonStrings.MARMOT_FOOD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("owl", CommonStrings.RABBIT, "recrafted_creatures:owl_egg", 3, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalWithTamedTag("red_panda", CommonStrings.BAMBOO, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("giraffe", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("mammoth", CommonStrings.MAMMOTH_TAMING_FOOD, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("owl", CommonStrings.COOKED_RABBIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("red_panda", CommonStrings.RED_PANDA_FOOD, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);

        CommonUtils.addAnimalWithTamedTag("zebra", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addTemperAnimal("zebra", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);

        builder.pop(2);
    }

}
