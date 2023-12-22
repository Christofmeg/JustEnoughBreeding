package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChocoCraftIntegration {

    final String MOD = "chococraft";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();

    public ChocoCraftIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        /*
        TODO rework how spawnEggs gets entities from SpawnEgg
         * Split getting entity from "spawnEgg" into
         * SpawnEgg: "chococraft:black_chocobo_spawn_egg"
         * Entity:   "chococraft:chocobo"
        */

        //CommonUtils.addAnimal("chocobo", CommonStrings.GYSAHL, animalNames, ingredients, breedingCooldown);
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "black_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "blue_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "flame_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "green_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "gold_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "pink_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "purple_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "red_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "white_chocobo_spawn_egg");
        //CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "yellow_chocobo_spawn_egg");

        /*
        CommonUtils.addTamableOnly("black_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("blue_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("flame_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("green_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("gold_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("pink_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("purple_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("red_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("white_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("yellow_chocobo", CommonStrings.GYSAHL_GREEN, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);
         */

        builder.pop(2);
    }

}
