package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.utils.Utils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlexsMobsIntegration {

    final String MOD = "alexsmobs"; //TODO BREEDING TEMPER TAMING TRUSTING

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Boolean> needsToBeTamed = new HashMap<>();
    final Map<String, String> resultEggs = new HashMap<>();
    final Map<String, Integer> eggsAmountMin = new HashMap<>();
    final Map<String, Integer> eggsAmountMax = new HashMap<>();

    final String COD = "minecraft:cod";
    final String CHICKEN = "minecraft:chicken, minecraft:cooked_chicken";
    final String LEAFCUTTER = "alexsmobs:leafcutter_ant_pupa";
    final String FLESH = "minecraft:rotten_flesh";
    final String WHEAT = "minecraft:wheat";
    final String SUGAR = "minecraft:sugar";
    final String CHORUS = "minecraft:chorus_fruit";
    final String MAGGOT = "alexsmobs:maggot";
    final String DEAD_BUSH_GRASS = "minecraft:dead_bush, minecraft:grass";
    final String LARVA = "alexsmobs:mosquito_larva";
    final String MUNGAL = "alexsmobs:mungal_spores";
    final String SALMON = "minecraft:salmon";
    final String LOBSTER = "alexsmobs:cooked_lobster_tail, alexsmobs:lobster_tail";
    final String BREAD = "minecraft:bread";
    final String DEAD_BUSH = "minecraft:dead_bush";
    final String SWEET_BERRIES = "minecraft:sweet_berries";
    final String MOOSE = "alexsmobs:cooked_moose_ribs, alexsmobs:moose_ribs";
    final String EGG = "minecraft:egg";
    final String LOBSTER_TAIL = "alexsmobs:lobster_tail";
    final String RED_MUSHROOM = "minecraft:red_mushroom";
    final String BROWN_MUSHROOM = "minecraft:brown_mushroom";
    final String CHICKEN_RABBIT = "minecraft:chicken, minecraft:cooked_chicken, minecraft:cooked_rabbit, minecraft:rabbit";
    final String LOBSTER_LARVA = "alexsmobs:cooked_lobster_tail, alexsmobs:lobster_tail, alexsmobs:mosquito_larva";
    final String HONEYCOMB = "minecraft:honeycomb";
    final String TROPICAL_FISH = "minecraft:tropical_fish";
    final String BONE_MEAL = "minecraft:bone_meal";
    final String SEA_GRASS = "minecraft:seagrass";
    final String PUMPKIN_SEEDS = "minecraft:pumpkin_seeds";

    final String INSECTS_TAG = "#alexsmobs:insect_items";
    final String FLOWERS_TAG = "#minecraft:flowers";
    final String TIGER_BREEDABLES_TAG = "#alexsmobs:tiger_breedables";
    final String MEAT = Utils.getEdibleMeatItemNames(true); //TODO Foodproperties.isMeat() to other mods
    final String MEAT_WITHOUT_FLESH = Utils.getEdibleMeatItemNames(false); //TODO Foodproperties.isMeat() to other mods

    // {@LINK} https://github.com/AlexModGuy/AlexsMobs/blob/c14c47e54bb785bb4fd026f6a90ad831df7882e3/src/main/java/com/github/alexthe666/alexsmobs/entity/EntityTasmanianDevil.java
    // {@LINK} https://wiki.enigmatica.net/enigmatica6/gameplay/how-to.../animal-taming-and-breeding
    // {@LINK} https://minecraft.fandom.com/wiki/Breeding#Breeding_foods

    public AlexsMobsIntegration(ForgeConfigSpec.Builder builder) {
        builder.push("integration");
        builder.push(MOD);

        addAnimal("alligator_snapping_turtle", COD);
        addAnimal("anaconda", CHICKEN);
        addAnimal("anteater", LEAFCUTTER);
        addAnimal("bald_eagle", FLESH);
        addAnimal("banana_slug", BROWN_MUSHROOM);
        addAnimal("bison", WHEAT);
        addAnimal("blue_jay", INSECTS_TAG);
        addAnimalTamed("capuchin_monkey", INSECTS_TAG);
        addAnimal("cockroach", SUGAR);
        addEggLayingAnimal("crocodile", FLESH, "alexsmobs:crocodile_egg", 1); //As Block
        addAnimal("crow", PUMPKIN_SEEDS);
        addAnimal("emu", WHEAT);
        addAnimal("endergrade", CHORUS);
        addAnimalTamed("flutter", BONE_MEAL);
        addAnimal("fly", FLESH);
        addAnimal("gazelle", WHEAT);
        addAnimal("gelada_monkey", DEAD_BUSH);
        addAnimal("hummingbird", FLOWERS_TAG);
        addAnimal("jerboa", MAGGOT);
        addAnimal("kangaroo", DEAD_BUSH_GRASS);
        addAnimal("laviathan", LARVA);  //TODO fix partial ticks
        addAnimal("maned_wolf", CHICKEN_RABBIT);
        addAnimalTamed("mantis_shrimp", LOBSTER);
        addAnimalTamed("mimic_octopus", TROPICAL_FISH);
        addAnimal("mudskipper", LOBSTER_LARVA);
        addAnimal("mungus", MUNGAL);
        addAnimal("orca", SALMON);
        addAnimal("platypus", LOBSTER);
        addAnimal("potoo", INSECTS_TAG);
        addAnimal("raccoon", BREAD);
        addAnimal("rattlesnake", MEAT);
        addAnimal("rhinoceros", DEAD_BUSH);
        addAnimal("roadrunner", INSECTS_TAG);
        addAnimal("seagull", COD);
        addAnimal("seal", LOBSTER_TAIL);
        addAnimal("skunk", SWEET_BERRIES);
        addAnimal("snow_leopard", MOOSE);
        addAnimal("sugar_glider", HONEYCOMB);
        addAnimal("tasmanian_devil", MEAT_WITHOUT_FLESH);
        addEggLayingAnimal("terrapin", SEA_GRASS, "alexsmobs:terrapin_egg", 4);
        addAnimal("tiger", TIGER_BREEDABLES_TAG);
        addAnimal("toucan", EGG);
        addAnimal("tusklin", RED_MUSHROOM);

        //TODO add missing mobs

        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));
            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", "alexsmobs:spawn_egg_" + animal);
            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
            if(needsToBeTamed.get(animal) != null) {
                CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
            }
            if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<String> animalEggResult = builder
                        .comment("Egg that " + animal + " lays after breeding")
                        .define(animal + "eggResult", resultEggs.get(animal));
                ForgeConfigSpec.ConfigValue<Integer> animalMinEggAmount = builder
                        .comment("Min amount of eggs that " + animal + " lays after breeding")
                        .defineInRange(animal + "EggMinAmount", eggsAmountMin.get(animal), 1, 64);
                ForgeConfigSpec.ConfigValue<Integer> animalMaxEggAmount = builder
                        .comment("Max amount of eggs that " + animal + " lays after breeding")
                        .defineInRange(animal + "EggMaxAmount", eggsAmountMax.get(animal), 1, 64);
                CommonConstants.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
            }
            //TODO ADD TAMING & BREEDING COOLDOWN
            builder.pop();
        }

        builder.pop(2);

    }
    private void addAnimal(String name, String ingredient) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
    }

    private void addAnimalTamed(String name, String ingredient) {
        addAnimal(name, ingredient);
        needsToBeTamed.put(name, true);
    }

    private void addEggLayingAnimal(String name, String ingredient, String resultEgg, int eggAmountMax) {
        addAnimal(name, ingredient);
        resultEggs.put(name, resultEgg);
        eggsAmountMin.put(name, 1);
        eggsAmountMax.put(name, eggAmountMax);
    }

}
