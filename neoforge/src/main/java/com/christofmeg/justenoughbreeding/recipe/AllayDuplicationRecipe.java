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

public record AllayDuplicationRecipe(EntityType<?> entityType, @NotNull Ingredient inputs, @NotNull Ingredient spawnEgg, String mod, String inputEntity, @Nullable CompoundTag inputEntityNbt) implements Recipe<CraftingInput> {

    @Override public boolean matches(@NotNull CraftingInput input, @NotNull Level level) { return false; }
    @Override public @NotNull ItemStack assemble(@NotNull CraftingInput input, HolderLookup.@NotNull Provider registries) { return ItemStack.EMPTY; }
    @Override public boolean canCraftInDimensions(int width, int height) { return false; }
    @Override public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) { return ItemStack.EMPTY; }
    @Override public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_SERIALIZER.get(); }
    @Override public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE.get(); }

}