package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TrustingRecipe extends ForgeRecipe {

    public final EntityType<?> entityType;
    public Ingredient inputStack;
    public Ingredient spawnEgg;
    public @Nullable Ingredient extraInputStack;
    public static final int ENTITY_CREATION_INTERVAL = 3000;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public TrustingRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, @Nullable Ingredient extraInputStack, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = entityType;
        this.inputStack = inputStack;
        this.spawnEgg = spawnEgg;
        this.extraInputStack = extraInputStack;
        this.jsonModID = jsonModID;
        this.jsonAnimalID = jsonAnimalID;
        this.modFolder = modFolder;
        this.fileName = fileName;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "trusting" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.TRUSTING_PROVIDER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get();
    }

    public void setInputIngredient(Ingredient ingredient) {
        this.inputStack = ingredient;
    }

    public void setExtraInputIngredient(Ingredient ingredient) {
        this.extraInputStack = ingredient;
    }

    public void setSpawnEggs(Ingredient ingredient) {
        this.spawnEgg = ingredient;
    }

    public static class Serializer implements RecipeSerializer<TrustingRecipe> {

        @Override
        public @NotNull TrustingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            return (TrustingRecipe) Utils.readJsonContents(jsonPath, json, "trusting");
        }

        @Override
        public @Nullable TrustingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull TrustingRecipe trustingRecipe) {}

    }
}
