package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BreedingSerializer implements RecipeSerializer<BreedingRecipe> {

    @Override
    public @NotNull BreedingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        BreedingRecipe r = (BreedingRecipe) Utils.readJsonContents(jsonPath, json, "breeding");
        if (r.entityType == null) {
            throw new com.google.gson.JsonParseException("BreedingRecipe missing entityType: " + jsonPath);
        }
        if (r.inputStack == null || r.spawnEgg == null) {
            throw new com.google.gson.JsonParseException("BreedingRecipe has null ingredients: " + jsonPath);
        }
        return r;
    }

    @Override
    public @Nullable BreedingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf buf) {
        ResourceLocation entityRL = buf.readResourceLocation();
        EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(entityRL);
        if (entityType == null) {
            throw new IllegalStateException("Unknown EntityType in BreedingRecipe#fromNetwork: " + entityRL);
        }

        Ingredient inputStack = Ingredient.fromNetwork(buf);    // never null
        Ingredient spawnEgg = Ingredient.fromNetwork(buf);

        boolean hasResult = buf.readBoolean();
        Ingredient resultItemStack = hasResult ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        boolean hasExtra = buf.readBoolean();
        Ingredient extraInputStack = hasExtra ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        Boolean needsToBeTamed = buf.readBoolean() ? buf.readBoolean() : null; // presence + value
        Boolean animalTrusting = buf.readBoolean() ? buf.readBoolean() : null;

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        return new BreedingRecipe(
                entityType,
                inputStack,
                spawnEgg,
                needsToBeTamed,
                resultItemStack,
                extraInputStack,
                animalTrusting,
                jsonModID,
                jsonAnimalID,
                modFolder,
                fileName
        );
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull BreedingRecipe recipe) {
        ResourceLocation entityKey = ForgeRegistries.ENTITY_TYPES.getKey(recipe.entityType);
        if (entityKey == null) {
            throw new IllegalStateException("Unknown EntityType in BreedingRecipe: " + recipe.entityType);
        }
        buf.writeResourceLocation(entityKey);

        CommonUtils.safe(recipe.inputStack).toNetwork(buf);
        CommonUtils.safe(recipe.spawnEgg).toNetwork(buf);

        boolean hasResult = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        buf.writeBoolean(hasResult);
        if (hasResult) {
            recipe.resultItemStack.toNetwork(buf);
        }

        boolean hasExtra = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        buf.writeBoolean(hasExtra);
        if (hasExtra) {
            recipe.extraInputStack.toNetwork(buf);
        }

        if (recipe.needsToBeTamed != null) {
            buf.writeBoolean(true);
            buf.writeBoolean(recipe.needsToBeTamed);
        } else {
            buf.writeBoolean(false);
        }

        if (recipe.animalTrusting != null) {
            buf.writeBoolean(true);
            buf.writeBoolean(recipe.animalTrusting);
        } else {
            buf.writeBoolean(false);
        }

        buf.writeUtf(recipe.jsonModID);
        buf.writeUtf(recipe.jsonAnimalID);
        buf.writeUtf(recipe.modFolder);
        buf.writeUtf(recipe.fileName);
    }
}