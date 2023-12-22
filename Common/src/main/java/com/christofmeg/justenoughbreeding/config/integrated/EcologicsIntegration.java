package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcologicsIntegration {

    final String MOD = "ecologics";

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public EcologicsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("camel", CommonStrings.PRICKLY_PEAR, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("penguin", CommonStrings.PENGUIN_TEMPT_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("squirrel", CommonStrings.SQUIRREL_TEMPT_ITEMS_TAG, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown);

        builder.pop(2);
    }

}