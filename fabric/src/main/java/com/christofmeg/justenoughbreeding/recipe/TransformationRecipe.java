package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public record TransformationRecipe(EntityType<?> inputEntityType, @NotNull Ingredient inputs, @NotNull Ingredient inputSpawnEggs, @NotNull Ingredient extraInputs, EntityType<?> outputEntityType, @NotNull Ingredient outputSpawnEggs, String mod, String inputEntity, @Nullable CompoundTag inputEntityNbt, String outputEntity, @Nullable CompoundTag outputEntityNbt, @Nullable Boolean tamed) implements Recipe<CraftingInput> {

    @Override public boolean matches(@NotNull CraftingInput input, @NotNull Level level) { return false; }
    @Override public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider registries) { return ItemStack.EMPTY; }
    @Override public @NonNull RecipeSerializer<? extends Recipe<CraftingInput>> getSerializer() { return JustEnoughBreeding.TRANSFORMATION_PROVIDER_SERIALIZER; }
    @Override public @NonNull RecipeType<? extends Recipe<CraftingInput>> getType() { return JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE; }
    @Override public @NonNull PlacementInfo placementInfo() { return PlacementInfo.NOT_PLACEABLE; }
    @Override public @NonNull RecipeBookCategory recipeBookCategory() { return RecipeBookCategories.CRAFTING_MISC; }

}