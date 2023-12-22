package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutumnityIntegration {

    final String MOD = "autumnity";

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public AutumnityIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("snail", CommonStrings.SNAIL_FOOD_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("turkey", CommonStrings.TURKEY_FOOD_TAG, animalNames, ingredients, breedingCooldown);

        builder.pop(2);
    }

}
