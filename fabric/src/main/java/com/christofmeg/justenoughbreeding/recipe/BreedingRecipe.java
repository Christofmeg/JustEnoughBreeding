package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BreedingRecipe extends FabricRecipe {

    public final EntityType<?> entityType;
    public Ingredient inputStack;
    public Ingredient spawnEgg;
    public final @Nullable Boolean needsToBeTamed;
    public Ingredient resultItemStack;
    public @Nullable Ingredient extraInputStack;

    public final @Nullable Boolean animalTrusting;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public BreedingRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, @Nullable Boolean needsToBeTamed, @Nullable Ingredient resultItemStack, @Nullable Ingredient extraInputStack, @Nullable Boolean animalTrusting, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = entityType;
        this.inputStack = inputStack;
        this.spawnEgg = spawnEgg;
        this.needsToBeTamed = needsToBeTamed;
        this.resultItemStack = resultItemStack;
        this.extraInputStack = extraInputStack;
        this.animalTrusting = animalTrusting;
        this.jsonModID = jsonModID;
        this.jsonAnimalID = jsonAnimalID;
        this.modFolder = modFolder;
        this.fileName = fileName;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "breeding" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.BREEDING_PROVIDER_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.BREEDING_PROVIDER_TYPE;
    }

    public void setInputIngredient(Ingredient ingredient) {
        this.inputStack = ingredient;
    }

    public void setExtraInputIngredient(Ingredient ingredient) {
        this.extraInputStack = ingredient;
    }

    public void setOutputIngredient(Ingredient ingredient) {
        this.resultItemStack = ingredient;
    }

    public void setSpawnEggs(Ingredient ingredient) {
        this.spawnEgg = ingredient;
    }

    public static class Serializer implements RecipeSerializer<BreedingRecipe> {

        @Override
        public @NotNull BreedingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            return (BreedingRecipe) Utils.readJsonContents(jsonPath, json, "breeding");
        }

        @Override
        public @Nullable BreedingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull BreedingRecipe breedingRecipe) {}

    }
}
