package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueSkiesIntegration {

    private static final String MOD = "blue_skies";

    public static class General {
        private static final String FOOD = "minecraft:wheat, blue_skies:pine_fruit";
        private static final String BUG_GUTS = "blue_skies:bug_guts";
        private static final String SCALEFRUIT = "blue_skies:scalefruit";
        private static final String PINE_FRUIT = "blue_skies:pine_fruit";
        private static final String FISHES_TAG = "#minecraft:fishes";

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();
        private final Map<String, Boolean> needsToBeTamed = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("integration");
            builder.push(MOD);

            addAnimal("azulfo", FOOD);
            addAnimal("crogre", BUG_GUTS);
            addAnimalTamed("crystal_camel", SCALEFRUIT);
            addAnimalTamed("reindeer", PINE_FRUIT);
            addAnimal("shade_monitor", FISHES_TAG);
            addAnimal("stardust_ram", PINE_FRUIT);

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
                if(needsToBeTamed.get(animal) != null) {
                    CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
                }

                builder.pop();
            }

            builder.pop(2);

        }
        private void addAnimal(String name, String ingredient) {
            animalNames.add(name);
            ingredients.put(name, ingredient);
        }

        private void addAnimalTamed(String name, String ingredient) {
            addAnimal(name, ingredient);
            needsToBeTamed.put(name, true);
        }
    }

}
