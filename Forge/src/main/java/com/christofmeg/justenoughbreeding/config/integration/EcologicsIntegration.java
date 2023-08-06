package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "JustEnoughBreeding", bus = Mod.EventBusSubscriber.Bus.MOD)
public class EcologicsIntegration {

    private static final String MOD = "ecologics";

    public static class General {
        private static final String PRICKLY_PEAR = "ecologics:prickly_pear";
        private static final String COD_SALMON = "minecraft:cod, minecraft:salmon";
        private static final String HONEYCOMB = "minecraft:honeycomb";

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();
        private final Map<String, Boolean> needsToBeTamed = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
            builder.push("integration");
            builder.push(MOD);

            addAnimalTamed("camel", PRICKLY_PEAR);
            addAnimal("penguin", COD_SALMON);
            addAnimal("squirrel", HONEYCOMB);


            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                JustEnoughBreeding.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                JustEnoughBreeding.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
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
