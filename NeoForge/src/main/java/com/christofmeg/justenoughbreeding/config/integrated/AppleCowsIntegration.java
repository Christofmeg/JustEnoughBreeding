package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppleCowsIntegration {

    final String MOD = "apple_cows";

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public AppleCowsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("apple_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("golden_apple_cow", CommonStrings.GOLDEN_WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("enchanted_golden_apple_cow", CommonStrings.ENCHANTED_GOLDEN_WHEAT, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown);

        builder.pop(2);
    }

}
