package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.recipe.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Recipe;

public class Utils {

    public static LivingEntity getLivingEntity(LivingEntity currentLivingEntity, boolean input, Recipe<?> recipe) {
        if (currentLivingEntity != null) {
            if (input) {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(transformationRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof AllayDuplicationRecipe allayDuplicationRecipe) {
                    if (allayDuplicationRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(allayDuplicationRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof BreedingRecipe breedingRecipe) {
                    if (breedingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(breedingRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof TamingRecipe tamingRecipe) {
                    if (tamingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(tamingRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof TemperRecipe temperRecipe) {
                    if (temperRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(temperRecipe.inputEntityNbt());
                    }
                }
                if (recipe instanceof TrustingRecipe trustingRecipe) {
                    if (trustingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(trustingRecipe.inputEntityNbt());
                    }
                }
            } else {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.outputEntityNbt() != null) {
                        currentLivingEntity.load(transformationRecipe.outputEntityNbt());
                    }
                }
            }
        }
        return currentLivingEntity;
    }

}