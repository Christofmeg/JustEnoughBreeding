package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwilightForestIntegration {

    private static final String MOD = "twilightforest";

    public static class General {
        private static final String WHEAT = "minecraft:wheat";
        private static final String VEGETABLES = "minecraft:beetroot, minecraft:carrot, minecraft:potato";
        private static final String DANDELION_CARROTS = "minecraft:dandelion, minecraft:carrot, minecraft:golden_carrot";
        private static final String COD = "minecraft:cod";

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("integration");
            builder.push(MOD);

            addAnimal("bighorn_sheep", WHEAT);
            addAnimal("boar", VEGETABLES);
            addAnimal("deer", WHEAT);
            addAnimal("dwarf_rabbit", DANDELION_CARROTS);
            addAnimal("penguin", COD);

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);

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
