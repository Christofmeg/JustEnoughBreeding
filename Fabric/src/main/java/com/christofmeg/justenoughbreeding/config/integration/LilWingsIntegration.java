package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LilWingsIntegration {

    private static final String MOD = "lilwings";

    public static class General {
        private static final String LILY_OF_THE_VALLEY = "minecraft:lily_of_the_valley";
        private static final String BLUE_ORCHID = "minecraft:blue_orchid";
        private static final String WHEAT_SEEDS = "minecraft:wheat_seeds";
        private static final String RED_MUSHROOM = "minecraft:red_mushroom";
        private static final String COCOA_BEANS = "minecraft:cocoa_beans";
        private static final String END_STONE = "minecraft:end_stone";
        private static final String ALLIUM = "minecraft:allium";
        private static final String AZURE_BLUET = "minecraft:azure_bluet";
        private static final String DANDELION = "minecraft:dandelion";
        private static final String BROWN_MUSHROOM = "minecraft:brown_mushroom";
        private static final String APPLE = "minecraft:apple";
        private static final String MOSS_BLOCK = "minecraft:moss_block";

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("integration");
            builder.push(MOD);

            addAnimal("white_fox", LILY_OF_THE_VALLEY);
            addAnimal("swamp_hopper", BLUE_ORCHID);
            addAnimal("swallow_tail", WHEAT_SEEDS);
            addAnimal("shroom_skipper", RED_MUSHROOM);
            addAnimal("painted_panther", COCOA_BEANS);
            addAnimal("ender_wing", END_STONE);
            addAnimal("crystal_puff", ALLIUM);
            addAnimal("cloudy_puff", AZURE_BLUET);
            addAnimal("butter_gold", DANDELION);
            addAnimal("aponi", BROWN_MUSHROOM);
            addAnimal("red_applefly", APPLE);
            addAnimal("grayling", MOSS_BLOCK);

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_egg");
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                JustEnoughBreeding.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                JustEnoughBreeding.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);

                builder.pop();
            }

            builder.pop(2);

        }
        private void addAnimal(String name, String ingredient) {
            animalNames.add(name);
            ingredients.put(name, ingredient);
        }
    }

}
