package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrostRealmIntegration {

    final String MOD = "frostrealm";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();
    final List<String> trustableOnly = new ArrayList<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> trustingIngredients = new HashMap<>();
    final Map<String, Integer> trustingChance = new HashMap<>();

    final String MEAT = Utils.getEdibleMeatItemNames(true);

    public FrostRealmIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("crystal_fox", CommonStrings.BEARBERRY, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("crystal_tortoise", CommonStrings.COLD_GRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("frost_wolf", MEAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("seal", CommonStrings.FISHES_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("snowpile_quail", CommonStrings.SNOWPILE_QUAIL_FOOD, "frostrealm:snowpile_quail_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addTamableOnly("frost_wolf", MEAT, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, "spawn_egg_", true);

        CommonUtils.addTrustingOnly("crystal_fox", CommonStrings.BEARBERRY, trustableOnly, trustingIngredients, trustingChance);
        CommonUtils.addTrustingAnimalNames(trustableOnly, trustingIngredients, trustingChance, builder, MOD);

        builder.pop(2);
    }

}
