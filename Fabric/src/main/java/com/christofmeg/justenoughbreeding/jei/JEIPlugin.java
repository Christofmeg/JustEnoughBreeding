package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(CommonConstants.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new BreedingCategory(helper, Items.WHEAT)
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        registerMobBreedingRecipes(registration);
    }

    private void registerMobBreedingRecipes(IRecipeRegistration registration) {
        Map<String, Boolean> animalTamedConfigs = CommonConstants.animalTamedConfigs;

        List<String> sortedMobNames = new ArrayList<>(CommonConstants.ingredientConfigs.keySet());
        Collections.sort(sortedMobNames);

        for (String mobName : sortedMobNames) {
            String mobIngredients = CommonConstants.ingredientConfigs.get(mobName).get();
            String mobSpawnEgg = CommonConstants.spawnEggConfigs.get(mobName).get();

            // Check if the mob has an associated result item configuration
            String mobResultItem = CommonConstants.eggResultConfigs.get(mobName) != null ? CommonConstants.eggResultConfigs.get(mobName).get() : "";
            int mobMinResultCount = CommonConstants.eggMinAmountConfigs.get(mobName) != null ? CommonConstants.eggMinAmountConfigs.get(mobName).get() : 1;
            int mobMaxResultCount = CommonConstants.eggMaxAmountConfigs.get(mobName) != null ? CommonConstants.eggMaxAmountConfigs.get(mobName).get() : 1;

            Ingredient combinedIngredient = createCombinedIngredient(mobIngredients);
            List<Ingredient> combinedResultIngredient = createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
            Item spawnEggItem = CommonUtils.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEgg.trim()));

            if (spawnEggItem instanceof SpawnEggItem spawnEgg) {
                EntityType<?> entityType = spawnEgg.getType(null);
                Boolean needsToBeTamed = animalTamedConfigs.get(mobName);

                List<ItemStack> mergedResultItemStacks = new ArrayList<>();
                for (Ingredient resultItemStack : combinedResultIngredient) {
                    ItemStack[] stacks = resultItemStack.getItems();
                    mergedResultItemStacks.addAll(Arrays.asList(stacks));
                }

                registration.addRecipes(BreedingCategory.TYPE,
                        List.of( //TODO needsToBeTamed does not work
                                new BreedingCategory.BreedingRecipe(entityType, combinedIngredient, spawnEggItem.getDefaultInstance(), needsToBeTamed,
                                        Ingredient.of(mergedResultItemStacks.toArray(new ItemStack[0])), null)
                        ));
            }
        }
    }

    private List<Ingredient> createCombinedResultIngredients(String mobIngredients, int minCount, int maxCount) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> resultIngredients = new ArrayList<>();

        List<ItemStack> combinedItemStacks = new ArrayList<>();

        for (int count = minCount; count <= maxCount; count++) {
            for (String ingredientId : ingredientIds) {
                Item ingredientItem = CommonUtils.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedItemStacks.add(new ItemStack(ingredientItem, count));
            }
        }

        resultIngredients.add(Ingredient.of(combinedItemStacks.toArray(new ItemStack[0])));
        return resultIngredients;
    }

    private Ingredient createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = CommonUtils.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem)));
            }
        }

//TODO        return Ingredient.merge(combinedIngredients);
        return mergeIngredients(combinedIngredients);
    }

    private static Ingredient mergeIngredients(List<Ingredient> ingredients) {
        return Ingredient.of(Arrays.stream(ingredients.toArray(Ingredient[]::new))
                .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
                .distinct()
                .toArray(ItemStack[]::new));
    }

    private Ingredient createTagIngredient(String tagId) {
        String tagLocationStr = tagId.trim().substring(1);
        ResourceLocation tagLocation = new ResourceLocation(tagLocationStr);
        return Ingredient.of(TagKey.create(Registries.ITEM, tagLocation));
    }

}