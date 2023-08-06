package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DucklingIntegration {

    private static final String MOD = "duckling";

    public static class General {
        private static final String BREAD = "minecraft:bread";

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("integration");
            builder.push(MOD);

            addAnimal("duck", BREAD);

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
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
