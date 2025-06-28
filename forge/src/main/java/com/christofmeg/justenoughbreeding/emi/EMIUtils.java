package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import dev.emi.emi.api.EmiRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EMIUtils {

    public static void registerRecipes(EmiRegistry registration) {
        List<BreedingRecipe> breedingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get()));
        breedingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (BreedingRecipe recipe : breedingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        BreedingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "breeding" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .breedingRecipe(recipe)
                                .build()
                );
            }
        }

        List<TamingRecipe> tamingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE.get()));
        tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (TamingRecipe recipe : tamingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        TamingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "taming" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .tamingRecipe(recipe)
                                .build()
                );
            }
        }

        List<TemperRecipe> temperRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get()));
        tamingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (TemperRecipe recipe : temperRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        TemperCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "temper" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .temperRecipe(recipe)
                                .build()
                );
            }
        }

        List<TrustingRecipe> trustingRecipes = new ArrayList<>(registration.getRecipeManager().getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get()));
        trustingRecipes.sort(Comparator.comparing(r -> r.jsonAnimalID));
        for (TrustingRecipe recipe : trustingRecipes) {
            if (JustEnoughBreeding.isModLoaded(recipe.jsonModID) && recipe.entityType != null) {
                registration.addRecipe(
                        TrustingCategoryEMI.builder()
                                .id(new ResourceLocation(CommonConstants.MOD_ID, "/" + "trusting" + "/" + recipe.jsonModID + "/" + recipe.jsonAnimalID))
                                .trustingRecipe(recipe)
                                .build()
                );
            }
        }
    }

}
