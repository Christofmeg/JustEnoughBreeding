package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.CommonStrings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcologicsIntegration {

    final String MOD = "ecologics"; //TODO BREEDING TEMPER TAMING TRUSTING

    final List<String> animalNames = new ArrayList<>();
    final List<String> trustableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> trustingIngredients = new HashMap<>();
    final Map<String, Integer> trustingChance = new HashMap<>();

    public EcologicsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addAnimalTamed("camel", CommonStrings.PRICKLY_PEAR);
        addTrustableOnly("camel", CommonStrings.CAMEL_FOOD);
        addAnimal("penguin", CommonStrings.COD_SALMON);
        addAnimal("squirrel", CommonStrings.HONEYCOMB);

        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", MOD + ":" + animal + "_spawn_egg");
            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
            if(needsToBeTamed.get(animal) != null) {
                CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
            }
            if(breedingCooldown.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<Integer> animalBreedingCooldown = builder.define(animal + "BreedingCooldown", breedingCooldown.get(animal));
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }
            builder.pop();
        }
        for (String trusting : trustableOnly) {
            if(trustingIngredients.get(trusting) != null && trustingChance.get(trusting) != null) {
                ForgeConfigSpec.ConfigValue<String> animalTamingIngredients = builder.push(trusting)
                        .comment("Ingredients required to gain " + trusting + "trust")
                        .define(trusting + "TrustingIngredients", trustingIngredients.get(trusting));
                ForgeConfigSpec.ConfigValue<Integer> animalTamingChance = builder.defineInRange(trusting + "TrustingChance", trustingChance.get(trusting), 0, 100);
                CommonConstants.tamingIngredientConfigs.put(MOD + "_" + trusting, animalTamingIngredients);
                CommonConstants.tamingChanceConfigs.put(MOD + "_" + trusting, animalTamingChance);
            }
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(trusting + "SpawnEgg", MOD + ":" + trusting + "_spawn_egg");
            CommonConstants.spawnEggConfigs.put(MOD + "_" + trusting, animalSpawnEgg);
            builder.pop();
        }
        builder.pop(2);

    }
    private void addAnimal(String name, String ingredient) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }
    private void addAnimalTamed(String name, String ingredient) {
        addAnimal(name, ingredient);
        needsToBeTamed.put(name, true);
    }
    private void addTrustableOnly(String name, String tamingIngredient) {
        trustableOnly.add(name);
        trustingIngredients.put(name, tamingIngredient);
        trustingChance.put(name, 33);
    }

}