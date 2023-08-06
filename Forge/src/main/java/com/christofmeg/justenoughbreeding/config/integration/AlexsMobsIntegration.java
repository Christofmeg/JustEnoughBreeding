package com.christofmeg.justenoughbreeding.config.integration;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.ForgeUtils;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "JustEnoughBreeding", bus = Mod.EventBusSubscriber.Bus.MOD)
public class AlexsMobsIntegration {

    private static final String MOD = "alexsmobs";

    public static class General {
        private static final String COD = "minecraft:cod";
        private static final String CHICKEN = "minecraft:chicken, minecraft:cooked_chicken";
        private static final String LEAFCUTTER = "alexsmobs:leafcutter_ant_pupa";
        private static final String FLESH = "minecraft:rotten_flesh";
        private static final String WHEAT = "minecraft:wheat";
        private static final String SUGAR = "minecraft:sugar";
        private static final String CHORUS = "minecraft:chorus_fruit";
        private static final String MAGGOT = "alexsmobs:maggot";
        private static final String DEAD_BUSH_GRASS = "minecraft:dead_bush, minecraft:grass";
        private static final String LARVA = "alexsmobs:mosquito_larva";
        private static final String MUNGAL = "alexsmobs:mungal_spores";
        private static final String SALMON = "minecraft:salmon";
        private static final String LOBSTER = "alexsmobs:cooked_lobster_tail, alexsmobs:lobster_tail";
        private static final String BREAD = "minecraft:bread";
        private static final String DEAD_BUSH = "minecraft:dead_bush";
        private static final String SWEET_BERRIES = "minecraft:sweet_berries";
        private static final String MOOSE = "alexsmobs:cooked_moose_ribs, alexsmobs:moose_ribs";
        private static final String EGG = "minecraft:egg";
        private static final String LOBSTER_TAIL = "alexsmobs:lobster_tail";
        private static final String RED_MUSHROOM = "minecraft:red_mushroom";
        private static final String BROWN_MUSHROOM = "minecraft:brown_mushroom";
        private static final String CHICKEN_RABBIT = "minecraft:chicken, minecraft:cooked_chicken, minecraft:cooked_rabbit, minecraft:rabbit";
        private static final String LOBSTER_LARVA = "alexsmobs:cooked_lobster_tail, alexsmobs:lobster_tail, alexsmobs:mosquito_larva";
        private static final String HONEYCOMB = "minecraft:honeycomb";
        private static final String TROPICAL_FISH = "minecraft:tropical_fish";
        private static final String BONE_MEAL = "minecraft:bone_meal";
        private static final String SEA_GRASS = "minecraft:seagrass";
        private static final String PUMPKIN_SEEDS = "minecraft:pumpkin_seeds";

        private static final String INSECTS_TAG = "#alexsmobs:insect_items";
        private static final String FLOWERS_TAG = "#minecraft:flowers";
        private static final String TIGER_BREEDABLES_TAG = "#alexsmobs:tiger_breedables";
        private static final String MEAT = ForgeUtils.getEdibleMeatItemNames(true); //TODO Foodproperties.isMeat() to other mods
        private static final String MEAT_WITHOUT_FLESH = ForgeUtils.getEdibleMeatItemNames(false); //TODO Foodproperties.isMeat() to other mods

        // {@LINK} https://github.com/AlexModGuy/AlexsMobs/blob/c14c47e54bb785bb4fd026f6a90ad831df7882e3/src/main/java/com/github/alexthe666/alexsmobs/entity/EntityTasmanianDevil.java
        // {@LINK} https://wiki.enigmatica.net/enigmatica6/gameplay/how-to.../animal-taming-and-breeding
        // {@LINK} https://minecraft.fandom.com/wiki/Breeding#Breeding_foods

        private final List<String> animalNames = new ArrayList<>();
        private final Map<String, String> ingredients = new HashMap<>();
        private final Map<String, Boolean> needsToBeTamed = new HashMap<>();
        private final Map<String, String> resultEggs = new HashMap<>();
        private final Map<String, Integer> eggsAmountMin = new HashMap<>();
        private final Map<String, Integer> eggsAmountMax = new HashMap<>();

        public General(ForgeConfigSpec.Builder builder) {
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
            addEggLayingAnimal("crocodile", FLESH, "alexsmobs:crocodile_egg", 1, 1); //As Block
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
            addEggLayingAnimal("terrapin", SEA_GRASS, "alexsmobs:terrapin_egg", 1, 4);
            addAnimal("tiger", TIGER_BREEDABLES_TAG);
            addAnimal("toucan", EGG);
            addAnimal("tusklin", RED_MUSHROOM);

            //TODO add missing mobs

            for (String animal : animalNames) {
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", "alexsmobs:spawn_egg_" + animal);
                ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                        .comment("Ingredients required for " + animal + " breeding")
                        .define(animal + "Ingredients", ingredients.get(animal));
                JustEnoughBreeding.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
                JustEnoughBreeding.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);
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
                    JustEnoughBreeding.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                    JustEnoughBreeding.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                    JustEnoughBreeding.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
                }

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

        private void addEggLayingAnimal(String name, String ingredient, String resultEgg, int eggAmountMin, int eggAmountMax) {
            addAnimal(name, ingredient);
            resultEggs.put(name, resultEgg);
            eggsAmountMin.put(name, eggAmountMin);
            eggsAmountMax.put(name, eggAmountMax);
        }
    }

}
