package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlexsCavesIntegration {

    final String MOD = "alexscaves";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();
    final Map<String, String> extraTamingIngredients = new HashMap<>();

    public AlexsCavesIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimalEggLaying("relicheirus", CommonStrings.TREE_STAR, "alexscaves:relicheirus_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalEggLaying("subterranodon", CommonStrings.COD_RAW_AND_COOKED, "alexscaves:subterranodon_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalEggLayingWithTamedTag("tremorsaurus", CommonStrings.TREMORSAURUS_FOOD, "alexscaves:tremorsaurus_egg", 1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        CommonUtils.addAnimalEggLayingWithTamedTag("vallumraptor", CommonStrings.DINOSAUR_NUGGET, "alexscaves:vallumraptor_egg", 4, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed, "spawn_egg_", true, resultEggs, eggsAmountMin, eggsAmountMax);

        CommonUtils.addAnimalWithTamedTag("raycat", CommonStrings.RADGILL, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed, "spawn_egg_", true);

        CommonUtils.addTamableOnly("raycat", CommonStrings.RADGILL, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("tremorsaurus", CommonStrings.SERENE_SALAD, tamableOnly, tamingIngredients, tamingChance, CommonStrings.PRIMITIVE_CLUB, extraTamingIngredients);
        CommonUtils.addTamableOnly("vallumraptor", CommonStrings.SERENE_SALAD, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, "spawn_egg_", true, extraTamingIngredients);

        builder.pop(2);
    }

}
