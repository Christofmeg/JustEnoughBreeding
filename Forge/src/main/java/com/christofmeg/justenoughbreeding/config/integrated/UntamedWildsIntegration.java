package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UntamedWildsIntegration {

    final String MOD = "untamedwilds";

    //TODO check if playerBreeding is enabled

    final List<String> animalNames = new ArrayList<>();
    final Map<String, String> ingredients = new HashMap<>();
    final Map<String, Integer> breedingCooldown = new HashMap<>();

    public UntamedWildsIntegration(ForgeConfigSpec.Builder builder) {

        builder.push("integration");
        builder.push(MOD);

        CommonUtils.addAnimal("aardvark", "untamedwilds:aardvark_cucumber", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("arowana", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("baleen_whale", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("bear", "minecraft:sweet_berries, minecraft:beef, minecraft:salmon, minecraft:potato, minecraft:bamboo, untamedwilds:material_blubber, minecraft:apple, minecraft:honeycomb", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("big_cat", "minecraft:beef, minecraft:porkchop, minecraft:chicken, minecraft:rabbit", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("bison", "untamedwilds:flora_junegrass, minecraft:wheat", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("boar", "minecraft:beetroot", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("butterfly", "minecraft:dandelion", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("camel", "minecraft:cactus", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("catfish", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("giant_clam", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("giant_salamander", "untamedwilds:food_turtle_raw", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hippo", "untamedwilds:flora_water_hyacinth_item", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("hyena", "minecraft:rotten_flesh", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("king_crab", "minecraft:cod", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("large_snake", "minecraft:beef, minecraft:porkchop", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("manatee", "minecraft:sea_grass, minecraft:kelp", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("monitor", "untamedwilds:food_turtle_raw", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("newt", "untamedwilds:food_turtle_raw", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("opossum", "minecraft:chicken", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("rhino", "minecraft:melon_slice", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sawfish", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("shark", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("snake", "minecraft:rabbit", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("softshell_turtle", "minecraft:cod", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("spadefish", "minecraft:sea_grass", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("spitter", "minecraft:glow_lichen", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("sunfish", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("tarantula", "minecraft:chicken", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("terror_bird", "minecraft:rabbit", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("tortoise", "minecraft:apple", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("trevally", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("triggerfish", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("whale_shark", "untamedwilds:chum", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimal("wildebeest", "minecraft:grass", animalNames, ingredients, breedingCooldown);
        CommonUtils.addAnimalNames(animalNames, builder, ingredients, MOD, breedingCooldown);

        builder.pop(2);

    }

}
