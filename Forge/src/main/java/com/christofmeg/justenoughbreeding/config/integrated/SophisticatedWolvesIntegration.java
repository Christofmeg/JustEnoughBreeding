package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SophisticatedWolvesIntegration {

    final String MOD = "sophisticated_wolves";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();

    public SophisticatedWolvesIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimalWithTamedTag("sophisticated_wolf", CommonStrings.DOG_TREAT, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed, "dog_egg");

        CommonUtils.addTamableOnly("sophisticated_wolf", CommonStrings.BONE, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, "dog_egg");

        builder.pop(2);
    }

}
