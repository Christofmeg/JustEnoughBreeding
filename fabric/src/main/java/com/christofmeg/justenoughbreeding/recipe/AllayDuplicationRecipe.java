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

public class AllayDuplicationRecipe extends ForgeRecipe {

    public final EntityType<?> entityType;
    public Ingredient inputStack;
    public Ingredient spawnEgg;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public AllayDuplicationRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = entityType;
        this.inputStack = inputStack;
        this.spawnEgg = spawnEgg;
        this.jsonModID = jsonModID;
        this.jsonAnimalID = jsonAnimalID;
        this.modFolder = modFolder;
        this.fileName = fileName;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "allay_duplication" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE;
    }

    public void setInputIngredient(Ingredient ingredient) {
        this.inputStack = ingredient;
    }

    public void setSpawnEggs(Ingredient ingredient) {
        this.spawnEgg = ingredient;
    }

    public static class Serializer implements RecipeSerializer<AllayDuplicationRecipe> {

        @Override
        public @NotNull AllayDuplicationRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            return (AllayDuplicationRecipe) Utils.readJsonContents(jsonPath, json, "allay_duplication");
        }

        @Override
        public @Nullable AllayDuplicationRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull AllayDuplicationRecipe recipe) {
        }

    }
}