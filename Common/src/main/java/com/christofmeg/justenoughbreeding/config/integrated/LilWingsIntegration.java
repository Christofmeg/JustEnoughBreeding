package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LilWingsIntegration {

    final String MOD = "lilwings";

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public LilWingsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("white_fox", CommonStrings.LILY_OF_THE_VALLEY, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("swamp_hopper", CommonStrings.BLUE_ORCHID, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("swallow_tail", CommonStrings.WHEAT_SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("shroom_skipper", CommonStrings.RED_MUSHROOM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("painted_panther", CommonStrings.COCOA_BEANS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("ender_wing", CommonStrings.END_STONE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("crystal_puff", CommonStrings.ALLIUM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cloudy_puff", CommonStrings.AZURE_BLUET, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("butter_gold", CommonStrings.DANDELION, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("aponi", CommonStrings.BROWN_MUSHROOM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("red_applefly", CommonStrings.APPLE, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("grayling", CommonStrings.MOSS_BLOCK, animalNames, ingredients, breedingCooldown);

        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, "_egg", false);

        builder.pop(2);
    }

}