package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.jei.BreedingCategory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();

        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item != null) {
                FoodProperties foodProperties = item.getFoodProperties(item.getDefaultInstance(), null);
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
        }

        return String.join(", ", edibleMeatItemNames);
    }

    public static void registerMobBreedingRecipes(IRecipeRegistration registration) {
        List<String> sortedMobNames = new ArrayList<>(CommonConstants.ingredientConfigs.keySet());
        Collections.sort(sortedMobNames);

        for (String mobName : sortedMobNames) {
            if (mobName != null) {
                if (CommonConstants.ingredientConfigs != null && CommonConstants.spawnEggConfigs != null) {
                    if (CommonConstants.spawnEggConfigs.get(mobName) != null) {
                        String mobIngredients = CommonConstants.ingredientConfigs.get(mobName);
                        String mobSpawnEgg = CommonConstants.spawnEggConfigs.get(mobName);
                        String mobResultItem = CommonConstants.eggResultConfigs.get(mobName) != null ? CommonConstants.eggResultConfigs.get(mobName) : "";
                        int mobMinResultCount = CommonConstants.eggMinAmountConfigs.get(mobName) != null ? CommonConstants.eggMinAmountConfigs.get(mobName) : 1;
                        int mobMaxResultCount = CommonConstants.eggMaxAmountConfigs.get(mobName) != null ? CommonConstants.eggMaxAmountConfigs.get(mobName) : 1;

                        if (mobIngredients != null && mobSpawnEgg != null) {
                            Ingredient combinedIngredient = createCombinedIngredient(mobIngredients);
                            List<Ingredient> combinedResultIngredient = Utils.createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
                            Item spawnEggItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEgg.trim()));

                            if (spawnEggItem instanceof SpawnEggItem spawnEgg) {
                                EntityType<?> entityType = spawnEgg.getType(null);
                                Boolean needsToBeTamed = CommonConstants.animalTamedConfigs.get(mobName);
                                Boolean animalTrusting = CommonConstants.animalTrustingConfigs.get(mobName);
                                BreedingCategory.BreedingRecipe breedingRecipe;

                                Ingredient combinedExtraIngredient = null;

                                if (CommonConstants.extraBreedingIngredientConfigs != null) {
                                    if (CommonConstants.extraBreedingIngredientConfigs.get(mobName) != null) {
                                        String mobExtraIngredients = CommonConstants.extraBreedingIngredientConfigs.get(mobName);
                                        if (mobExtraIngredients != null) {
                                            combinedExtraIngredient = createCombinedIngredient(mobExtraIngredients);
                                        }
                                    }
                                }

                                breedingRecipe = createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
                                registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(breedingRecipe));

                            }
                        }
                    }
                }
            }
        }
    }

    private static List<Ingredient> createCombinedResultIngredients(String mobIngredients, int minCount, int maxCount) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> resultIngredients = new ArrayList<>();

        List<ItemStack> combinedItemStacks = new ArrayList<>();

        for (int count = minCount; count <= maxCount; count++) {
            for (String ingredientId : ingredientIds) {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                if (ingredientItem != null) {
                    combinedItemStacks.add(new ItemStack(ingredientItem, count));
                }
            }
        }

        resultIngredients.add(Ingredient.of(combinedItemStacks.toArray(new ItemStack[0])));
        return resultIngredients;
    }

    private static BreedingCategory.BreedingRecipe createBreedingRecipe(EntityType<?> entityType, Ingredient combinedIngredient, Item spawnEggItem, Boolean needsToBeTamed, List<Ingredient> resultItemStacks, Boolean animalTrusting, @Nullable Ingredient combinedExtraIngredient) {
        List<ItemStack> mergedResultItemStacks = new ArrayList<>();

        for (Ingredient resultItemStack : resultItemStacks) {
            ItemStack[] stacks = resultItemStack.getItems();
            mergedResultItemStacks.addAll(Arrays.asList(stacks));
        }

        return new BreedingCategory.BreedingRecipe(
                entityType,
                combinedIngredient,
                new ItemStack(spawnEggItem),
                needsToBeTamed,
                Ingredient.of(mergedResultItemStacks.toArray(new ItemStack[0])),
                combinedExtraIngredient,
                animalTrusting
        );
    }

    private static Ingredient createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(CommonUtils.createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                if (ingredientItem != null) {
                    combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem)));
                }
            }
        }

        return Ingredient.merge(combinedIngredients);
    }

}
