package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.TemperRecipe;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarthMobsIntegration {

    final String MOD = "earthmobsmod";

    final List<String> animalNames = new ArrayList<>();
    final List<String> tamableOnly = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();
    final Map<String, String> tamingIngredients = new HashMap<>();
    final Map<String, Integer> tamingChance = new HashMap<>();
    final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();

    public EarthMobsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("albino_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cluck_shroom", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("cream_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("duck", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("fancy_chicken", CommonStrings.SEEDS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("horned_sheep", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hyper_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalWithTamedTag("jolly_llama", CommonStrings.WHEAT_HAY, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        CommonUtils.addAnimal("jumbo_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("magma_cow", CommonStrings.MAGMA_CREAM, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("moobloom", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("moolip", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("teacup_pig", CommonStrings.VEGETABLES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("umbra_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("wooly_cow", CommonStrings.WHEAT, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("zombified_pig", CommonStrings.VEGETABLES, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("zombified_rabbit", CommonStrings.DANDELION_CARROTS, animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown, needsToBeTamed);

        CommonUtils.addTamableOnly("baby_ghast", CommonStrings.CRIMSON_NYLIUM_FUNGUS, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("skelton_wolf", CommonStrings.BONE_FLESH, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableOnly("wither_skelton_wolf", CommonStrings.BONE_FLESH, tamableOnly, tamingIngredients, tamingChance);
        CommonUtils.addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD);

        CommonUtils.addTemperAnimal("jolly_llama", new String[]{
                CommonStrings.WHEAT,
                CommonStrings.HAY_BLOCK
        }, new int[]{3, 6}, temperDataMap);
        CommonUtils.addAnimalTempers(temperDataMap, builder, MOD);

        builder.pop(2);
    }

}
