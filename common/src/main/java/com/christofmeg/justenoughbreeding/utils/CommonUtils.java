package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    public static void addAnimal(String name, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

    public static void addAnimal(String name, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimal(String name, String spawnEggItem, String entityFromName, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimal(String name, String spawnEggItem, String entityFromName, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimal(name, spawnEggItem, entityFromName, ingredient, animalNames, ingredients, breedingCooldown, spawnEggItems, entitiesFromNames);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimalWithTamedTag(String name, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        needsToBeTamed.put(name, true);
    }

    public static void addAnimalWithTamedTag(String name, String spawnEggItem, String entityFromName, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalWithTamedTag(name, ingredient, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalWithTamedTag(String name, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalWithTamedTag(name, ingredient, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimalWithTamedTag(String name, String spawnEggItem, String entityFromName, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalWithTamedTag(name, ingredient, extraIngredient, animalNames, ingredients, extraIngredients, breedingCooldown, needsToBeTamed);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalEggLaying(String name, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        resultEggs.put(name, resultEgg);
        eggsAmountMin.put(name, 1);
        eggsAmountMax.put(name, eggAmountMax);
    }

    public static void addAnimalEggLaying(String name, String spawnEggItem, String entityFromName, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalEggLaying(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalEggLaying(String name, String spawnEggItem, String entityFromName, String ingredient, String extraIngredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalEggLaying(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        spawnEggItems.put(name, spawnEggItem);
        extraIngredients.put(name, extraIngredient);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalEggLayingWithTamedTag(String name, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, Boolean> needsToBeTamed) {
        addAnimalEggLaying(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax);
        needsToBeTamed.put(name, true);
    }

    public static void addAnimalEggLayingWithTamedTag(String name, String spawnEggItem, String entityFromName, String ingredient, String resultEgg, int eggAmountMax, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax, Map<String, Boolean> needsToBeTamed, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addAnimalEggLayingWithTamedTag(name, ingredient, resultEgg, eggAmountMax, animalNames, ingredients, breedingCooldown, resultEggs, eggsAmountMin, eggsAmountMax, needsToBeTamed);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, customSpawnEggString);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, customSpawnEggString);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, customSpawnEggString);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null,null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null,null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName);
        for (String animal : animalNames) {
            if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                String animalEggResult = resultEggs.get(animal);
                int animalMinEggAmount = eggsAmountMin.get(animal);
                int animalMaxEggAmount = eggsAmountMax.get(animal);
                CommonConstants.breedingEggResult.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.breedingEggResultMinAmount.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.breedingEggResultMaxAmount.put(MOD + "_" + animal, animalMaxEggAmount);
            }
        }
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, null, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed);
        for (String animal : animalNames) {
            if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                String animalEggResult = resultEggs.get(animal);
                int animalMinEggAmount = eggsAmountMin.get(animal);
                int animalMaxEggAmount = eggsAmountMax.get(animal);
                CommonConstants.breedingEggResult.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.breedingEggResultMinAmount.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.breedingEggResultMaxAmount.put(MOD + "_" + animal, animalMaxEggAmount);
            }
        }
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, ingredients, extraIngredients, null, null, MOD, breedingCooldown, needsToBeTamed, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String customSpawnEggString) {
        addAnimalNames(animalNames, ingredients, extraIngredients, null, null, MOD, breedingCooldown, needsToBeTamed, customSpawnEggString, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, null, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, Map<String, String> extraIngredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, null);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, ingredients, extraIngredients, spawnEggItems, entitiesFromNames, MOD, breedingCooldown, needsToBeTamed, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, Map<String, String> ingredients, @Nullable Map<String, String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String customSpawnEggString, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        for (String animal : animalNames) {
            String animalIngredients = ingredients.get(animal);
            CommonConstants.breedingIngredients.put(MOD + "_" + animal, animalIngredients);

            if (spawnEggItems != null && entitiesFromNames != null) {
                if (spawnEggItems.get(animal) != null && entitiesFromNames.get(animal) != null) {
                    CommonConstants.breedingGetSpawnEggFromItem.put(MOD + "_" + animal, spawnEggItems.get(animal));
                    CommonConstants.breedingGetMobFromString.put(MOD + "_" + animal, entitiesFromNames.get(animal));
                }
            } else {
                if (customSpawnEggString != null) {
                    String animalSpawnEgg = MOD + ":" + customSpawnEggString;
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + animal, animalSpawnEgg);
                } else if (spawnEggString != null) {
                    String animalSpawnEgg = (addStringBeforeAnimalName ?
                            MOD + ":" + spawnEggString + animal :
                            MOD + ":" + animal + spawnEggString);
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + animal, animalSpawnEgg);
                } else {
                    String animalSpawnEgg = MOD + ":" + animal + "_spawn_egg";
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + animal, animalSpawnEgg);
                }
            }

            if (needsToBeTamed != null && needsToBeTamed.get(animal) != null) {
                CommonConstants.breedingNeedsToBeTamed.put(MOD + "_" + animal, true);
            }

            if (breedingCooldown.get(animal) != null) {
                int animalBreedingCooldown = breedingCooldown.get(animal);
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }

            if (extraIngredients != null) {
                if (extraIngredients.get(animal) != null) {
                    String animalExtraBreedingIngredients = extraIngredients.get(animal);
                    CommonConstants.breedingExtraIngredients.put(MOD + "_" + animal, animalExtraBreedingIngredients);
                }
            }
        }
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance) {
        tamableOnly.add(name);
        tamingIngredients.put(name, tamingIngredient);
        tamingChance.put(name, 33);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance) {
        tamableOnly.add(name);
        tamingIngredients.put(name, tamingIngredient);
        tamingChance.put(name, chance);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String extraIngredient, Map<String, String> extraIngredients) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance, String extraIngredient, Map<String, String> extraIngredients) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, chance);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, chance);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String extraIngredient, Map<String, String> extraIngredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, extraIngredient, extraIngredients);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableOnly(String name, String spawnEggItem, String entityFromName, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, int chance, String extraIngredient, Map<String, String> extraIngredients, Map<String, String> spawnEggItems, Map<String, String> entitiesFromNames) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance, chance, extraIngredient, extraIngredients);
        spawnEggItems.put(name, spawnEggItem);
        entitiesFromNames.put(name, entityFromName);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, false, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, false, extraIngredients);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, String customSpawnEggString) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, customSpawnEggString, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, spawnEggString, addStringBeforeAnimalName, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, String customSpawnEggString, @Nullable Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, customSpawnEggString, null, false, extraIngredients, null, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, @Nullable Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, spawnEggString, addStringBeforeAnimalName, extraIngredients, null, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, spawnEggItems, entitiesFromNames);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable Map<String, String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, MOD, null, null, false, extraIngredients, spawnEggItems, entitiesFromNames);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String MOD, @Nullable String customSpawnEggString, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, @Nullable String> extraIngredients, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        for (String tamable : tamableOnly) {
            if (tamingIngredients.get(tamable) != null && tamingChance.get(tamable) != null) {
                String animalTamingIngredients = tamingIngredients.get(tamable);
                int animalTamingChance = tamingChance.get(tamable);
                CommonConstants.tamingIngredients.put(MOD + "_" + tamable, animalTamingIngredients);
                CommonConstants.tamingChance.put(MOD + "_" + tamable, animalTamingChance);

                if (spawnEggItems != null && entitiesFromNames != null) {
                    if (spawnEggItems.get(tamable) != null && entitiesFromNames.get(tamable) != null) {
                        CommonConstants.breedingGetSpawnEggFromItem.put(MOD + "_" + tamable, spawnEggItems.get(tamable));
                        CommonConstants.breedingGetMobFromString.put(MOD + "_" + tamable, entitiesFromNames.get(tamable));
                    }
                } else {
                    if (customSpawnEggString != null) {
                        String animalSpawnEgg = MOD + ":" + customSpawnEggString;
                        CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + tamable, animalSpawnEgg);
                    } else if (spawnEggString != null) {
                        String animalSpawnEgg = (addStringBeforeAnimalName ?
                                MOD + ":" + spawnEggString + tamable :
                                MOD + ":" + tamable + spawnEggString);
                        CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + tamable, animalSpawnEgg);
                    } else {
                        String animalSpawnEgg = MOD + ":" + tamable + "_spawn_egg";
                        CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + tamable, animalSpawnEgg);
                    }
                }

                if (extraIngredients != null) {
                    if (extraIngredients.get(tamable) != null) {
                        String animalExtraTamingIngredients = extraIngredients.get(tamable);
                        CommonConstants.tamingExtraIngredients.put(MOD + "_" + tamable, animalExtraTamingIngredients);
                    }
                }

            }
        }
    }

    public static void addTrustingOnly(String name, String trustingIngredient, List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance) {
        trustingOnly.add(name);
        trustingIngredients.put(name, trustingIngredient);
        trustingChance.put(name, 33);
    }

    public static void addTrustingAnimalNames(List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance, String MOD) {
        addTrustingAnimalNames(trustingOnly, trustingIngredients, trustingChance, MOD, null, null);
    }

    public static void addTrustingAnimalNames(List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance, String MOD, @Nullable Map<String, String> spawnEggItems, @Nullable Map<String, String> entitiesFromNames) {
        for (String trusting : trustingOnly) {
            if (trustingIngredients.get(trusting) != null && trustingChance.get(trusting) != null) {
                String animalTrustingIngredients = trustingIngredients.get(trusting);
                int animalTrustingChance = trustingChance.get(trusting);
                CommonConstants.trustingIngredients.put(MOD + "_" + trusting, animalTrustingIngredients);
                CommonConstants.trustingChance.put(MOD + "_" + trusting, animalTrustingChance);
                CommonConstants.breedingNeedsToBeTrusting.put(MOD + "_" + trusting, true);

                if (spawnEggItems != null && entitiesFromNames != null) {
                    if (spawnEggItems.get(trusting) != null && entitiesFromNames.get(trusting) != null) {
                        CommonConstants.breedingGetSpawnEggFromItem.put(MOD + "_" + trusting, spawnEggItems.get(trusting));
                        CommonConstants.breedingGetMobFromString.put(MOD + "_" + trusting, entitiesFromNames.get(trusting));
                    }
                } else {
                    String animalSpawnEgg = MOD + ":" + trusting + "_spawn_egg";
                    CommonConstants.sharedGetSpawnEggFromEntity.put(MOD + "_" + trusting, animalSpawnEgg);
                }
            }
        }
    }

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();
        for (ResourceLocation key : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.get(key);
            FoodProperties foodProperties = item.getFoodProperties();
            if (includeRottenFlesh) {
                if (foodProperties != null && item.isEdible() && foodProperties.isMeat()) {
                    edibleMeatItemNames.add(key.toString());
                }
            }
            else {
                if (foodProperties != null && item.isEdible() && foodProperties.isMeat() && item != Items.ROTTEN_FLESH) {
                    edibleMeatItemNames.add(key.toString());
                }
            }
        }
        return String.join(", ", edibleMeatItemNames);
    }

    public static Ingredient createTagIngredient(String tagId) {
        String tagLocationStr = tagId.trim().substring(1);
        ResourceLocation tagLocation = new ResourceLocation(tagLocationStr);
        return Ingredient.of(TagKey.create(Registries.ITEM, tagLocation));
    }

    public static void renderEntity(@NotNull PoseStack stack, double mouseX, LivingEntity currentLivingEntity) {
        renderEntity(stack, mouseX, currentLivingEntity, 31, 89);
    }

    public static void renderEntity(@NotNull PoseStack stack, double mouseX, LivingEntity currentLivingEntity, int entityPosX, int entityPosY) {
        // Set the desired position of the entity on the screen
        int ENTITY_RENDER_DISTANCE = 15728880;

        float yaw = (float) (60 - mouseX); // Calculate the yaw based on the mouse position

        stack.pushPose(); // Push the current pose onto the stack
        stack.translate((float) entityPosX, (float) entityPosY, 50f); // Translate the entity's position

        // Calculate the scaling factor based on the bounding box's largest dimension
        AABB boundingBox = currentLivingEntity.getBoundingBox();
        double largestDimension = Math.max(boundingBox.getXsize(), Math.max(boundingBox.getYsize(), boundingBox.getZsize()));

        float desiredWidth = 30.0F;
        float desiredHeight = 40.0F;

        // Calculate the scaling factors for width and height
        float scaleX = desiredWidth / (float) largestDimension;
        float scaleY = desiredHeight / (float) largestDimension;

        // Use the smaller of the two scaling factors to ensure the entity fits within the area
        float scalingFactor = Math.min(scaleX, scaleY);

        if (currentLivingEntity instanceof Frog) {
            scalingFactor = 50;
        }

        if (currentLivingEntity instanceof Axolotl || currentLivingEntity instanceof Cat ||
                currentLivingEntity instanceof Pig || currentLivingEntity instanceof Wolf) {
            scalingFactor = 25;
        }

        if (currentLivingEntity instanceof Ocelot || currentLivingEntity instanceof Fox
                || currentLivingEntity instanceof Turtle) {
            scalingFactor = 20;
        }

        if (currentLivingEntity instanceof Hoglin || currentLivingEntity instanceof Horse
                || currentLivingEntity instanceof Panda) {
            scalingFactor = 15;
        }

        if (currentLivingEntity instanceof Sniffer) {
            scalingFactor = 10;
        }

        stack.scale(scalingFactor, scalingFactor, scalingFactor); // Scale the entity to fit within the desired area
        stack.mulPose(Axis.ZP.rotationDegrees(180.0F)); // Rotate the entity to face a certain direction

        float yawRadians = -(yaw / 40.F) * 20.0F; // Calculate the yaw angle in radians for the entity's rotation

        // Apply the calculated yaw angle to the entity's rotation properties
        currentLivingEntity.yBodyRot = yawRadians;
        currentLivingEntity.setYRot(yawRadians);
        currentLivingEntity.yHeadRot = yawRadians;
        currentLivingEntity.yHeadRotO = yawRadians;

        Minecraft instance = Minecraft.getInstance();
        EntityRenderDispatcher entityRenderDispatcher = instance.getEntityRenderDispatcher(); // Get the entity rendering dispatcher
        entityRenderDispatcher.overrideCameraOrientation(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F)); // Override the camera orientation for rendering
        entityRenderDispatcher.setRenderShadow(false); // Disable rendering shadows for the entity

        // Get the buffer source for rendering
        final MultiBufferSource.BufferSource bufferSource = instance.renderBuffers().bufferSource();

        // Render the currentLivingEntity using the entityRenderDispatcher
        entityRenderDispatcher.render(currentLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, stack, bufferSource, ENTITY_RENDER_DISTANCE);

        bufferSource.endBatch(); // End the rendering batch
        entityRenderDispatcher.setRenderShadow(true); // Re-enable rendering shadows

        stack.popPose(); // Pop the pose from the stack to revert transformations
    }

}
