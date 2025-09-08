package com.christofmeg.justenoughbreeding.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public abstract class BaseRecipe implements Recipe<CraftingContainer> {

    @Override
    public boolean matches(@Nonnull CraftingContainer craftingContainer, @Nonnull Level level) {
        return false;
    }

    @Override
    public @Nonnull ItemStack assemble(@Nonnull CraftingContainer craftingContainer, @Nonnull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public @Nonnull ItemStack getResultItem(@Nonnull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public @Nonnull ResourceLocation getId() {
        return null;
    }

    @Override
    public @Nonnull RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public @Nonnull RecipeType<?> getType() {
        return null;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

}
