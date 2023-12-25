package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonUtils {

/*-----------------------------------------------------------------------------------------*/
// SINGLE INGREDIENT
    public static void addAnimal(String name, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown) {
        animalNames.add(name);
        ingredients.put(name, ingredient);
        breedingCooldown.put(name, 6000);
    }

    public static void addAnimalWithTamedTag(String name, String ingredient, List<String> animalNames, Map<String, String> ingredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        needsToBeTamed.put(name, true);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, needsToBeTamed, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName);
    }

    // CUSTOM SPAWN EGG STRING
    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String customSpawnEggString) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, null, customSpawnEggString);
    }
    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed, String customSpawnEggString) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, needsToBeTamed, customSpawnEggString);
    }
/*-----------------------------------------------------------------------------------------*/

/*-----------------------------------------------------------------------------------------*/
// EXTRA INGREDIENT

    public static void addAnimal(String name, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, Map<String, Integer> breedingCooldown) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addAnimalWithTamedTag(String name, String ingredient, String extraIngredient, List<String> animalNames, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimal(name, ingredient, extraIngredient, animalNames, ingredients, extraIngredients, breedingCooldown);
        needsToBeTamed.put(name, true);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, null, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, Boolean> needsToBeTamed) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, null, false);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName);
    }

    // CUSTOM SPAWN EGG STRING
    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String customSpawnEggString) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, null, customSpawnEggString);
    }
/*-----------------------------------------------------------------------------------------*/

    public static void addAnimalNames(
            List<String> animalNames,
            ForgeConfigSpec.Builder builder,
            Map<String, String> ingredients,
            Map<String, @Nullable String> extraIngredients,
            String MOD,
            Map<String, Integer> breedingCooldown,
            @Nullable Map<String, Boolean> needsToBeTamed,
            @Nullable String spawnEggString,
            boolean addStringBeforeAnimalName
    ) {
        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));

            String spawnEggName = spawnEggString != null ?
                    (addStringBeforeAnimalName ?
                            MOD + ":" + spawnEggString + animal :
                            MOD + ":" + animal + spawnEggString) :
                    MOD + ":" + animal + "_spawn_egg";

            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", spawnEggName);

            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);

            if (needsToBeTamed != null && needsToBeTamed.get(animal) != null) {
                CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
            }

            if (breedingCooldown.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<Integer> animalBreedingCooldown = builder.define(animal + "BreedingCooldown", breedingCooldown.get(animal));
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }

            if (extraIngredients != null) {
                if (extraIngredients.get(animal) != null) {
                    ForgeConfigSpec.ConfigValue<String> animalExtraBreedingIngredients = builder
                            .comment("Extra ingredients required for " + animal + " breeding")
                            .define(animal + "ExtraBreedingIngredients", extraIngredients.get(animal));
                    CommonConstants.extraBreedingIngredientConfigs.put(MOD + "_" + animal, animalExtraBreedingIngredients);
                }
            }


            builder.pop();
        }
    }

    // CUSTOM SPAWN EGG STRING
    public static void addAnimalNames(
            List<String> animalNames,
            ForgeConfigSpec.Builder builder,
            Map<String, String> ingredients,
            Map<String, @Nullable String> extraIngredients,
            String MOD,
            Map<String, Integer> breedingCooldown,
            @Nullable Map<String, Boolean> needsToBeTamed,
            @Nullable String customSpawnEggString
    ) {
        for (String animal : animalNames) {
            ForgeConfigSpec.ConfigValue<String> animalIngredients = builder.push(animal)
                    .comment("Ingredients required for " + animal + " breeding")
                    .define(animal + "Ingredients", ingredients.get(animal));

            String spawnEggName = MOD + ":" + customSpawnEggString;

            ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(animal + "SpawnEgg", spawnEggName);

            CommonConstants.ingredientConfigs.put(MOD + "_" + animal, animalIngredients);
            CommonConstants.spawnEggConfigs.put(MOD + "_" + animal, animalSpawnEgg);

            if (needsToBeTamed != null && needsToBeTamed.get(animal) != null) {
                CommonConstants.animalTamedConfigs.put(MOD + "_" + animal, true);
            }

            if (breedingCooldown.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<Integer> animalBreedingCooldown = builder.define(animal + "BreedingCooldown", breedingCooldown.get(animal));
                CommonConstants.breedingCooldown.put(MOD + "_" + animal, animalBreedingCooldown);
            }

            if (extraIngredients != null) {
                if (extraIngredients.get(animal) != null) {
                    ForgeConfigSpec.ConfigValue<String> animalExtraBreedingIngredients = builder
                            .comment("Extra ingredients required for " + animal + " breeding")
                            .define(animal + "ExtraBreedingIngredients", extraIngredients.get(animal));
                    CommonConstants.extraBreedingIngredientConfigs.put(MOD + "_" + animal, animalExtraBreedingIngredients);
                }
            }


            builder.pop();
        }
    }


/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addAnimalEggLaying(
            String name,
            String ingredient,
            String resultEgg,
            int eggAmountMax,
            List<String> animalNames,
            Map<String, String> ingredients,
            Map<String, Integer> breedingCooldown,
            Map<String, String> resultEggs,
            Map<String, Integer> eggsAmountMin,
            Map<String, Integer> eggsAmountMax
    ) {
        addAnimal(name, ingredient, animalNames, ingredients, breedingCooldown);
        resultEggs.put(name, resultEgg);
        eggsAmountMin.put(name, 1);
        eggsAmountMax.put(name, eggAmountMax);
    }

    public static void addAnimalEggLayingWithTamedTag(
            String name,
            String ingredient,
            String resultEgg,
            int eggAmountMax,
            List<String> animalNames,
            Map<String, String> ingredients,
            Map<String, Integer> breedingCooldown,
            Map<String, String> resultEggs,
            Map<String, Integer> eggsAmountMin,
            Map<String, Integer> eggsAmountMax,
            Map<String, Boolean> needsToBeTamed
    ) {
        addAnimalWithTamedTag(name, ingredient, animalNames, ingredients, breedingCooldown, needsToBeTamed);
        resultEggs.put(name, resultEgg);
        eggsAmountMin.put(name, 1);
        eggsAmountMax.put(name, eggAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin,
                                      Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, null,null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, Map<String, String> resultEggs,
                                      Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, needsToBeTamed, null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString, boolean addStringBeforeAnimalName,
                                      Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, String MOD, Map<String, Integer> breedingCooldown, @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString,
                                      boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, builder, ingredients, null, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(
            List<String> animalNames,
            ForgeConfigSpec.Builder builder,
            Map<String, String> ingredients,
            Map<String, @Nullable String> extraIngredients,
            String MOD,
            Map<String, Integer> breedingCooldown,
            Map<String, String> resultEggs,
            Map<String, Integer> eggsAmountMin,
            Map<String, Integer> eggsAmountMax
    ) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, null,null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(
            List<String> animalNames,
            ForgeConfigSpec.Builder builder,
            Map<String, String> ingredients,
            Map<String, @Nullable String> extraIngredients,
            String MOD,
            Map<String, Integer> breedingCooldown,
            @Nullable Map<String, Boolean> needsToBeTamed,
            Map<String, String> resultEggs,
            Map<String, Integer> eggsAmountMin,
            Map<String, Integer> eggsAmountMax
    ) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, null, false, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown, String spawnEggString,
                                      boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, null, spawnEggString, addStringBeforeAnimalName, resultEggs, eggsAmountMin, eggsAmountMax);
    }

    public static void addAnimalNames(List<String> animalNames, ForgeConfigSpec.Builder builder, Map<String, String> ingredients, Map<String, @Nullable String> extraIngredients, String MOD, Map<String, Integer> breedingCooldown,
                                      @Nullable Map<String, Boolean> needsToBeTamed, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, String> resultEggs, Map<String, Integer> eggsAmountMin, Map<String, Integer> eggsAmountMax) {
        addAnimalNames(animalNames, builder, ingredients, extraIngredients, MOD, breedingCooldown, needsToBeTamed, spawnEggString, addStringBeforeAnimalName);

        for (String animal : animalNames) {
            if(resultEggs.get(animal) != null && eggsAmountMin.get(animal) != null && eggsAmountMax.get(animal) != null) {
                ForgeConfigSpec.ConfigValue<String> animalEggResult = builder.push(animal)
                        .comment("Egg that " + animal + " lays after breeding")
                        .define(animal + "EggResult", resultEggs.get(animal));
                ForgeConfigSpec.ConfigValue<Integer> animalMinEggAmount = builder
                        .comment("Min amount of eggs that " + animal + " lays after breeding")
                        .defineInRange(animal + "EggMinAmount", eggsAmountMin.get(animal), 1, 64);
                ForgeConfigSpec.ConfigValue<Integer> animalMaxEggAmount = builder
                        .comment("Max amount of eggs that " + animal + " lays after breeding")
                        .defineInRange(animal + "EggMaxAmount", eggsAmountMax.get(animal), 1, 64);
                CommonConstants.eggResultConfigs.put(MOD + "_" + animal, animalEggResult);
                CommonConstants.eggMinAmountConfigs.put(MOD + "_" + animal, animalMinEggAmount);
                CommonConstants.eggMaxAmountConfigs.put(MOD + "_" + animal, animalMaxEggAmount);
                builder.pop();
            }
        }
    }

/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addTemperAnimal(String name, String[] temperItems, int[] temperValues, Map<String, List<TemperRecipe>> temperDataMap) {
        List<TemperRecipe> temperDataList = temperDataMap.computeIfAbsent(name, k -> new ArrayList<>());

        for (int i = 0; i < temperItems.length && i < temperValues.length; i++) {
            temperDataList.add(new TemperRecipe(temperItems[i], temperValues[i]));
        }
    }

    public static void addAnimalTempers(Map<String, List<TemperRecipe>> temperDataMap, ForgeConfigSpec.Builder builder, String MOD) {
        for (Map.Entry<String, List<TemperRecipe>> entry : temperDataMap.entrySet()) {
            String temperAnimal = entry.getKey();
            List<TemperRecipe> temperDataList = entry.getValue();
            List<String> temperIngredients = temperDataList.stream()
                    .map(TemperRecipe::temperIngredient)
                    .collect(Collectors.toList());
            List<Integer> temperValues = temperDataList.stream()
                    .map(TemperRecipe::temperValue)
                    .toList();

            String temperIngredientsString = String.join(", ", temperIngredients);
            String temperValuesString = temperValues.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            ForgeConfigSpec.ConfigValue<String> animalTemperIngredients = builder.push(temperAnimal)
                    .comment("Ingredients that increase " + temperAnimal + " temper")
                    .define(temperAnimal + "TemperIngredients", temperIngredientsString);
            ForgeConfigSpec.ConfigValue<String> animalTemperValues = builder
                    .comment("Values of ingredients that increase " + temperAnimal + " temper")
                    .define(temperAnimal + "TemperValues", temperValuesString);

            CommonConstants.temperIngredientConfigs.put(MOD + "_" + temperAnimal, animalTemperIngredients);
            CommonConstants.temperValueConfigs.put(MOD + "_" + temperAnimal, animalTemperValues);
            builder.pop();
        }
    }

/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance) {
        tamableOnly.add(name);
        tamingIngredients.put(name, tamingIngredient);
        tamingChance.put(name, 33);
    }

    public static void addTamableOnly(String name, String tamingIngredient, List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, String extraIngredient, Map<String, String> extraIngredients) {
        addTamableOnly(name, tamingIngredient, tamableOnly, tamingIngredients, tamingChance);
        extraIngredients.put(name, extraIngredient);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, ForgeConfigSpec.Builder builder, String MOD) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, null, false, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, ForgeConfigSpec.Builder builder, String MOD, Map<String, String> extraIngredients) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, null, false, extraIngredients);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, ForgeConfigSpec.Builder builder, String MOD, String customSpawnEggString) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, customSpawnEggString, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, ForgeConfigSpec.Builder builder, String MOD, @Nullable String spawnEggString, boolean addStringBeforeAnimalName) {
        addTamableAnimalNames(tamableOnly, tamingIngredients, tamingChance, builder, MOD, spawnEggString, addStringBeforeAnimalName, null);
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, ForgeConfigSpec.Builder builder, String MOD, String customSpawnEggString, Map<String, @Nullable String> extraIngredients) {
        for (String tamable : tamableOnly) {
            if (tamingIngredients.get(tamable) != null && tamingChance.get(tamable) != null) {
                ForgeConfigSpec.ConfigValue<String> animalTamingIngredients = builder.push(tamable)
                        .comment("Ingredients required for " + tamable + " taming")
                        .define(tamable + "TamingIngredients", tamingIngredients.get(tamable));
                ForgeConfigSpec.ConfigValue<Integer> animalTamingChance = builder.defineInRange(tamable + "TamingChance", tamingChance.get(tamable), 0, 100);
                CommonConstants.tamingIngredientConfigs.put(MOD + "_" + tamable, animalTamingIngredients);
                CommonConstants.tamingChanceConfigs.put(MOD + "_" + tamable, animalTamingChance);

                String spawnEggName = MOD + ":" + customSpawnEggString;

                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(tamable + "SpawnEgg", spawnEggName);
                CommonConstants.spawnEggConfigs.put(MOD + "_" + tamable, animalSpawnEgg);

                if (extraIngredients != null) {
                    if (extraIngredients.get(tamable) != null) {
                        ForgeConfigSpec.ConfigValue<String> animalExtraTamingIngredients = builder
                                .comment("Extra ingredients required for " + tamable + " taming")
                                .define(tamable + "ExtraTamingIngredients", extraIngredients.get(tamable));
                        CommonConstants.extraTamingIngredientConfigs.put(MOD + "_" + tamable, animalExtraTamingIngredients);
                    }
                }

            }
            builder.pop();
        }
    }

    public static void addTamableAnimalNames(List<String> tamableOnly, Map<String, String> tamingIngredients, Map<String, Integer> tamingChance, ForgeConfigSpec.Builder builder, String MOD, @Nullable String spawnEggString, boolean addStringBeforeAnimalName, Map<String, @Nullable String> extraIngredients) {
        for (String tamable : tamableOnly) {
            if (tamingIngredients.get(tamable) != null && tamingChance.get(tamable) != null) {
                ForgeConfigSpec.ConfigValue<String> animalTamingIngredients = builder.push(tamable)
                        .comment("Ingredients required for " + tamable + " taming")
                        .define(tamable + "TamingIngredients", tamingIngredients.get(tamable));
                ForgeConfigSpec.ConfigValue<Integer> animalTamingChance = builder.defineInRange(tamable + "TamingChance", tamingChance.get(tamable), 0, 100);
                CommonConstants.tamingIngredientConfigs.put(MOD + "_" + tamable, animalTamingIngredients);
                CommonConstants.tamingChanceConfigs.put(MOD + "_" + tamable, animalTamingChance);

                String spawnEggName = spawnEggString != null ?
                        (addStringBeforeAnimalName ?
                                MOD + ":" + spawnEggString + tamable :
                                MOD + ":" + tamable + spawnEggString) :
                        MOD + ":" + tamable + "_spawn_egg";

                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(tamable + "SpawnEgg", spawnEggName);
                CommonConstants.spawnEggConfigs.put(MOD + "_" + tamable, animalSpawnEgg);

                if (extraIngredients != null) {
                    if (extraIngredients.get(tamable) != null) {
                        ForgeConfigSpec.ConfigValue<String> animalExtraTamingIngredients = builder
                                .comment("Extra ingredients required for " + tamable + " taming")
                                .define(tamable + "ExtraTamingIngredients", extraIngredients.get(tamable));
                        CommonConstants.extraTamingIngredientConfigs.put(MOD + "_" + tamable, animalExtraTamingIngredients);
                    }
                }

            }
            builder.pop();
        }
    }

/*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static void addTrustingOnly(String name, String trustingIngredient, List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance) {
        trustingOnly.add(name);
        trustingIngredients.put(name, trustingIngredient);
        trustingChance.put(name, 33);
    }

    public static void addTrustingAnimalNames(List<String> trustingOnly, Map<String, String> trustingIngredients, Map<String, Integer> trustingChance, ForgeConfigSpec.Builder builder, String MOD) {
        for (String trusting : trustingOnly) {
            if (trustingIngredients.get(trusting) != null && trustingChance.get(trusting) != null) {
                ForgeConfigSpec.ConfigValue<String> animalTrustingIngredients = builder.push(trusting)
                        .comment("Ingredients required for " + trusting + " trusting")
                        .define(trusting + "TrustingIngredients", trustingIngredients.get(trusting));
                ForgeConfigSpec.ConfigValue<Integer> animalTrustingChance = builder.defineInRange(trusting + "TrustingChance", trustingChance.get(trusting), 0, 100);
                CommonConstants.trustingIngredientConfigs.put(MOD + "_" + trusting, animalTrustingIngredients);
                CommonConstants.trustingChanceConfigs.put(MOD + "_" + trusting, animalTrustingChance);
                CommonConstants.animalTrustingConfigs.put(MOD + "_" + trusting, true);
                ForgeConfigSpec.ConfigValue<String> animalSpawnEgg = builder.define(trusting + "SpawnEgg", MOD + ":" + trusting + "_spawn_egg");
                CommonConstants.spawnEggConfigs.put(MOD + "_" + trusting, animalSpawnEgg);
            }
            builder.pop();
        }
    }

 /*
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();

        for (ResourceLocation key : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.get(key);
            FoodProperties foodProperties = item.getFoodProperties();
            if(includeRottenFlesh) {
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

}
