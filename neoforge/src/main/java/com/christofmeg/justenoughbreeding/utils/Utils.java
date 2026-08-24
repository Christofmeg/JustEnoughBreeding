package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.recipe.*;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueInput;
import org.jetbrains.annotations.NotNull;

public class Utils {

    public static <T extends Recipe<?>> RecipeType<@NotNull T> simple(final Identifier name) {
        final String toString = name.toString();
        return new RecipeType<>() {
            @Override
            public String toString() {
                return toString;
            }
        };
    }

    public static LivingEntity getLivingEntity(LivingEntity currentLivingEntity, boolean input, Recipe<?> recipe) {
        if (currentLivingEntity != null) {
            Level level = currentLivingEntity.level();
            if (input) {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), transformationRecipe.inputEntityNbt()));
                    }
                }
                if (recipe instanceof AllayDuplicationRecipe allayDuplicationRecipe) {
                    if (allayDuplicationRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), allayDuplicationRecipe.inputEntityNbt()));
                    }
                }
                if (recipe instanceof BreedingRecipe breedingRecipe) {
                    if (breedingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), breedingRecipe.inputEntityNbt()));
                    }
                }
                if (recipe instanceof TamingRecipe tamingRecipe) {
                    if (tamingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), tamingRecipe.inputEntityNbt()));
                    }
                }
                if (recipe instanceof TemperRecipe temperRecipe) {
                    if (temperRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), temperRecipe.inputEntityNbt()));
                    }
                }
                if (recipe instanceof TrustingRecipe trustingRecipe) {
                    if (trustingRecipe.inputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), trustingRecipe.inputEntityNbt()));
                    }
                }
            } else {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.outputEntityNbt() != null) {
                        currentLivingEntity.load(TagValueInput.create(ProblemReporter.DISCARDING, level.registryAccess(), transformationRecipe.outputEntityNbt()));
                    }
                }
            }
        }
        return currentLivingEntity;
    }

}
