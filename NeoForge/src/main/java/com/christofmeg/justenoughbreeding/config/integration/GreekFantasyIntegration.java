package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.CommonStrings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreekFantasyIntegration {

    final String MOD = "greekfantasy"; //TODO BREEDING TEMPER TAMING TRUSTING

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();

    public GreekFantasyIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addAnimalTamed("orthus", CommonStrings.ORTHUS_FOOD_TAG);


        /*
        greek fantasy:
tamableOnly		cerastes		#greekfantasy:cerastes_food
         */

        //TODO move to mob creation/transformation category
        // arion    gold_ram
        // CommonUtils.addAnimal("makhai", "minecraft:horse_spawn_egg", "minecraft:enchanted_golden_apple", animalNames, ingredients, extraIngredients, breedingCooldown);


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

}
