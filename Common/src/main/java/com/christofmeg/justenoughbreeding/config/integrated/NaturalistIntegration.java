package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.TemperRecipe;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaturalistIntegration {

    final String MOD = "naturalist";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, String> extraIngredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();
    final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();


    public NaturalistIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimalEggLaying("alligator", CommonStrings.ALLIGATOR_FOOD_ITEMS_TAG, "naturalist:alligator_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimal("bear", CommonStrings.BEAR_TEMPT_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("boar", CommonStrings.BOAR_FOOD_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("butterfly", CommonStrings.FLOWERS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("deer", CommonStrings.APPLE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("duck", CommonStrings.DUCK_FOOD_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("giraffe", CommonStrings.HAY_BLOCK, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hippo", CommonStrings.MELON, "minecraft:water_bucket", animalNames, ingredients, extraIngredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("snail", CommonStrings.BEETROOT, "naturalist:snail_eggs", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalEggLayingWithTamedTag("tortoise", CommonStrings.TORTOISE_TEMPT_ITEMS_TAG, "naturalist:tortoise_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("zebra", CommonStrings.GOLDEN_APPLE_CARROT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("bluejay", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("canary", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("cardinal", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("finch", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("robin", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("sparrow", CommonStrings.BIRD_FOOD_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("lizard", CommonStrings.LIZARD_TEMPT_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("tortoise", CommonStrings.TORTOISE_TEMPT_ITEMS_TAG, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);

        CommonUtils.addTemperAnimal("zebra", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);
        CommonUtils.addAnimalTempers(temperDataMap, builder, MOD);

        builder.pop(2);
    }

}
