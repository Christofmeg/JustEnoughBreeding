package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.CommonStrings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UlterlandsIntegration {

    final String MOD = "ulterlands"; //TODO BREEDING TEMPER TAMING TRUSTING

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public UlterlandsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addAnimal("mushogg", CommonStrings.VEGETABLES);

        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
            if(breedingCooldown.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<Integer> animalBreedingCooldown = builder.define(animal + "BreedingCooldown", breedingCooldown.get(animal));
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }
            builder.pop();
        }
        builder.pop(2);
    }

    private void addAnimal(String name, String ingredient) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

}
