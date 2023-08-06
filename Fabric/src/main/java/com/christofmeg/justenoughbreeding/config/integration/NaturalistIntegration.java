package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaturalistIntegration {

    private static final String MOD = "naturalist";

    public static class General {
        private static final String ALLIGATOR_FOOD_ITEMS_TAG = "#naturalist:alligator_food_items";
        private static final String BEAR_TEMPT_ITEMS_TAG = "#naturalist:bear_tempt_items";
        private static final String BOAR_FOOD_ITEMS_TAG = "#naturalist:boar_food_items";
        private static final String DUCK_FOOD_ITEMS_TAG = "#naturalist:duck_food_items";
        private static final String FLOWERS_TAG = "#minecraft:flowers";
        private static final String APPLE = "minecraft:apple";
        private static final String HAY_BLOCK = "minecraft:hay_block";
        private static final String TORTOISE_TEMPT_ITEMS_TAG = "#naturalist:tortoise_tempt_items";


        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();
        private final Map<String, String> resultEggs = new HashMap<>();
        private final Map<String, Integer> eggsAmountMin = new HashMap<>();
        private final Map<String, Integer> eggsAmountMax = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("integration");
            builder.push(MOD);

            addEggLayingAnimal("alligator", ALLIGATOR_FOOD_ITEMS_TAG, "naturalist:alligator_egg", 1, 4);
            addAnimal("bear", BEAR_TEMPT_ITEMS_TAG);
            addAnimal("boar", BOAR_FOOD_ITEMS_TAG);
            addAnimal("butterfly", FLOWERS_TAG);
            addAnimal("deer", APPLE);
            addAnimal("duck", DUCK_FOOD_ITEMS_TAG);
            addAnimal("giraffe", HAY_BLOCK);
            addEggLayingAnimal("tortoise", TORTOISE_TEMPT_ITEMS_TAG, "naturalist:tortoise_egg", 1, 4);

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                JustEnoughBreeding.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                JustEnoughBreeding.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
                if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                    ForgeConfigSpec.ConfigValue<String> animalEggResult = builder
                            .comment("Egg that " + animal + " lays after breeding")
                            .define(animal + "eggResult", resultEggs.get(animal));
                    ForgeConfigSpec.ConfigValue<Integer> animalMinEggAmount = builder
                            .comment("Min amount of eggs that " + animal + " lays after breeding")
                            .defineInRange(animal + "EggMinAmount", eggsAmountMin.get(animal), 1, 64);
                    ForgeConfigSpec.ConfigValue<Integer> animalMaxEggAmount = builder
                            .comment("Max amount of eggs that " + animal + " lays after breeding")
                            .defineInRange(animal + "EggMaxAmount", eggsAmountMax.get(animal), 1, 64);
                    JustEnoughBreeding.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                    JustEnoughBreeding.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                    JustEnoughBreeding.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
                }

                builder.pop();
            }

            builder.pop(2);

        }
        private void addAnimal(String name, String ingredient) {
            animalNames.add(name);
            ingredients.put(name, ingredient);
        }

        private void addEggLayingAnimal(String name, String ingredient, String resultEgg, int eggAmountMin, int eggAmountMax) {
            addAnimal(name, ingredient);
            resultEggs.put(name, resultEgg);
            eggsAmountMin.put(name, eggAmountMin);
            eggsAmountMax.put(name, eggAmountMax);
        }
    }

}
