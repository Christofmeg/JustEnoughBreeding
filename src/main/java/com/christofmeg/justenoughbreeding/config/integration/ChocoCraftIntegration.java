package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChocoCraftIntegration {

    private static final String MOD = "chococraft";

    public static class General {
        private static final String GYSAHL = "chococraft:loverly_gysahl_green, chococraft:gold_gysahl";

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();
        private final Map<String, String> resultEggs = new HashMap<>();
        private final Map<String, Integer> eggsAmountMin = new HashMap<>();
        private final Map<String, Integer> eggsAmountMax = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {   //TODO look at chococraft:chocobo with spawn eggs
            builder.push("integration");
            builder.push(MOD);

            addAnimal("yellow_chocobo", GYSAHL);
            addAnimal("green_chocobo", GYSAHL);
            addAnimal("blue_chocobo", GYSAHL);
            addAnimal("white_chocobo", GYSAHL);
            addAnimal("black_chocobo", GYSAHL);
            addAnimal("gold_chocobo", GYSAHL);
            addAnimal("pink_chocobo", GYSAHL);
            addAnimal("red_chocobo", GYSAHL);
            addEggLayingAnimal("purple_chocobo", GYSAHL, "chococraft:purple_chocobo_spawn_egg", 1, 1); //As Item
            addAnimal("flame_chocobo", GYSAHL);


            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
                net.minecraftforge.common.ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);

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
                    CommonConstants.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                    CommonConstants.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                    CommonConstants.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
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
