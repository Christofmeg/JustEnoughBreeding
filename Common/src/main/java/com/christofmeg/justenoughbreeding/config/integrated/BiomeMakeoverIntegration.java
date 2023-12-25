package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeMakeoverIntegration {

    final String MOD = "biomemakeover";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();

    public BiomeMakeoverIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("helmit_crab", CommonStrings.FISHES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("owl", CommonUtils.getEdibleMeatItemNames(true), animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("scuttler", CommonStrings.PINK_PETALS, animalNames, ingredients, breedingCooldown); //TODO 33% chance to breed
        CommonUtils.addAnimal("toad", CommonStrings.SPIDER_EYE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("owl", CommonUtils.getEdibleMeatItemNames(true), tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);

        builder.pop(2);
    }

}