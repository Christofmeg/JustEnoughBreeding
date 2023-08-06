package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.FabricUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.core.Registry;
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

@SuppressWarnings("unused")
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
        // Process mob breeding recipes
        registerMobBreedingRecipes(registration);
    }

    private void registerMobBreedingRecipes(IRecipeRegistration registration) {
        Map<String, Boolean> animalTamedConfigs = CommonConstants.animalTamedConfigs;

        List<String> sortedMobNames = new ArrayList<>(JustEnoughBreeding.ingredientConfigs.keySet());
        Collections.sort(sortedMobNames);

        for (String mobName : sortedMobNames) {
            String mobIngredients = JustEnoughBreeding.ingredientConfigs.get(mobName).get();
            String mobSpawnEgg = JustEnoughBreeding.spawnEggConfigs.get(mobName).get();

            // Check if the mob has an associated result item configuration
            String mobResultItem = JustEnoughBreeding.eggResultConfigs.get(mobName) != null ? JustEnoughBreeding.eggResultConfigs.get(mobName).get() : "";
            int mobMinResultCount = JustEnoughBreeding.eggMinAmountConfigs.get(mobName) != null ? JustEnoughBreeding.eggMinAmountConfigs.get(mobName).get() : 1;
            int mobMaxResultCount = JustEnoughBreeding.eggMaxAmountConfigs.get(mobName) != null ? JustEnoughBreeding.eggMaxAmountConfigs.get(mobName).get() : 1;

            List<Ingredient>  combinedIngredient = createCombinedIngredient(mobIngredients);
            List<Ingredient> combinedResultIngredient = createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
            Item spawnEggItem = FabricUtils.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEgg.trim()));

            if (spawnEggItem instanceof SpawnEggItem spawnEgg) {
                EntityType<?> entityType = spawnEgg.getType(null);
                Boolean needsToBeTamed = animalTamedConfigs.get(mobName);
                BreedingCategory.BreedingRecipe breedingRecipe = createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient);

                registration.addRecipes(BreedingCategory.TYPE, Collections.singletonList(breedingRecipe));
            }
            System.out.println("Debug: mobName = " + mobName);
            System.out.println("Debug: mobIngredients = " + mobIngredients);
            System.out.println("Debug: mobSpawnEgg = " + mobSpawnEgg);
        }
    }

    private List<Ingredient> createCombinedResultIngredients(String mobIngredients, int minCount, int maxCount) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> resultIngredients = new ArrayList<>();

        List<ItemStack> combinedItemStacks = new ArrayList<>();

        for (int count = minCount; count <= maxCount; count++) {
            for (String ingredientId : ingredientIds) {
                Item ingredientItem = FabricUtils.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedItemStacks.add(new ItemStack(ingredientItem, count));
            }
        }

        resultIngredients.add(Ingredient.of(combinedItemStacks.toArray(new ItemStack[0])));
        return resultIngredients;
    }

    
    private List<Ingredient> createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        List<Ingredient> combinedItemStacks = new ArrayList<>();
        
        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = FabricUtils.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedItemStacks.add(Ingredient.of(new ItemStack(ingredientItem)));
            }
        }

        combinedIngredients.add(Ingredient.of(combinedItemStacks.toArray(new ItemStack[0])));
        return combinedIngredients;
    }
     

    private List<Ingredient> createCombinedIngredient2(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = FabricUtils.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem)));
            }
        }

        return combinedIngredients;
    }

    private Ingredient createTagIngredient(String tagId) {
        String tagLocationStr = tagId.trim().substring(1);
        ResourceLocation tagLocation = new ResourceLocation(tagLocationStr);
        return Ingredient.of(TagKey.create(Registry.ITEM_REGISTRY, tagLocation));
    }

    private BreedingCategory.BreedingRecipe createBreedingRecipe(EntityType<?> entityType, List<Ingredient> combinedIngredient, Item spawnEggItem, Boolean needsToBeTamed, List<Ingredient> resultItemStacks) {

        List<ItemStack> mergedIngredientItemStacks = new ArrayList<>();

        for (Ingredient resultItemStack : combinedIngredient) {
            ItemStack[] stacks = resultItemStack.getItems();
            mergedIngredientItemStacks.addAll(Arrays.asList(stacks));
        }

        List<ItemStack> mergedResultItemStacks = new ArrayList<>();
        
        for (Ingredient resultItemStack : resultItemStacks) {
            ItemStack[] stacks = resultItemStack.getItems();
            mergedResultItemStacks.addAll(Arrays.asList(stacks));
        }

        return new BreedingCategory.BreedingRecipe(
                entityType,
                Ingredient.of(mergedResultItemStacks.toArray(new ItemStack[0])),
                new ItemStack(spawnEggItem),
                needsToBeTamed,
                Ingredient.of(mergedResultItemStacks.toArray(new ItemStack[0])),
                null
        );
    }




}