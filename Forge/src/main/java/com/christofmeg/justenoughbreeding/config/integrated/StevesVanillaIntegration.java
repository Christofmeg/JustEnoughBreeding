package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StevesVanillaIntegration {

    final String MOD = "steves_vanilla";

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public StevesVanillaIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("boggord", CommonStrings.MOUNTAIN_BUDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalEggLaying("chest_toad", CommonStrings.SLIME_BALL, "steves_vanilla:chest_toad_eggs",
                1, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);

        builder.pop(2);

    }

}
