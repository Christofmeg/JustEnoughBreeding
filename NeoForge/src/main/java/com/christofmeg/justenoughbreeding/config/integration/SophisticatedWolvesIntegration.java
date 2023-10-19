package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SophisticatedWolvesIntegration {

    private static final String MOD = "sophisticated_wolves";

    private static final String DOG_TREAT = "sophisticated_wolves:dog_treat";

    private final List<String> animalNames = new ArrayList<>();
    private final Map<String, String> ingredients = new HashMap<>();
    private final Map<String, Boolean> needsToBeTamed = new HashMap<>();

    public SophisticatedWolvesIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addAnimalTamed("sophisticated_wolf", DOG_TREAT);

        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + "dog_egg");
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
