package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheDucksModIntegration {

    final String MOD = "theducksmod";

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public TheDucksModIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("mallard_duck", CommonStrings.SEAGRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("pekin_duck", CommonStrings.SEAGRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("duck", CommonStrings.SEAGRASS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown);

        builder.pop(2);
    }

}