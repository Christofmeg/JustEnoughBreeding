package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueSkiesIntegration {

    final String MOD = "blue_skies";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();

    public BlueSkiesIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("azulfo", CommonStrings.AZULFO_FOOD, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("crogre", CommonStrings.BUG_GUTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("crystal_camel", CommonStrings.SCALEFRUIT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalWithTamedTag("reindeer", CommonStrings.PINE_FRUIT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("shade_monitor", CommonStrings.FISHES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("stardust_ram", CommonStrings.PINE_FRUIT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNamesWithTamedTag(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("crystal_camel", CommonStrings.SCALEFRUIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("cosmic_fox", CommonStrings.CRESCENT_FRUIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("reindeer", CommonStrings.PINE_FRUIT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);

        builder.pop(2);
    }

}
