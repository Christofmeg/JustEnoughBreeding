package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class TrustingSerializer implements RecipeSerializer<TrustingRecipe> {

    @Override
    public @NotNull TrustingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        TrustingRecipe r = (TrustingRecipe) Utils.readJsonContents(jsonPath, json, "trusting");
        if (r == null) {
            return null;
        }
        if (r.entityType == null || r.inputStack == null || r.spawnEgg == null) {
            throw new JsonParseException("TrustingRecipe invalid/null fields: " + jsonPath);
        }
        return new TrustingRecipe(r.entityType, r.inputStack, r.spawnEgg, CommonUtils.safe(r.extraInputStack), r.jsonModID, r.jsonAnimalID, r.modFolder, r.fileName);
    }

    @Override
    public @NotNull TrustingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        ResourceLocation entityRL = buf.readResourceLocation();
        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(entityRL);
        if (entityType == null) throw new JsonParseException("Unknown EntityType in TrustingRecipe#fromNetwork: " + entityRL);

        Ingredient inputStack = Ingredient.fromNetwork(buf);
        Ingredient spawnEgg = Ingredient.fromNetwork(buf);

        boolean hasExtra = buf.readBoolean();
        Ingredient extraInputStack = hasExtra ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        return new TrustingRecipe(entityType, inputStack, spawnEgg, extraInputStack, jsonModID, jsonAnimalID, modFolder, fileName);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull TrustingRecipe recipe) {
        ResourceLocation entityKey = JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType);
        if (entityKey == null) throw new JsonParseException("Unknown EntityType in TrustingRecipe: " + recipe.entityType);
        buf.writeResourceLocation(entityKey);

        CommonUtils.safe(recipe.inputStack).toNetwork(buf);
        CommonUtils.safe(recipe.spawnEgg).toNetwork(buf);

        boolean hasExtra = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        buf.writeBoolean(hasExtra);
        if (hasExtra) recipe.extraInputStack.toNetwork(buf);

        buf.writeUtf(recipe.jsonModID);
        buf.writeUtf(recipe.jsonAnimalID);
        buf.writeUtf(recipe.modFolder);
        buf.writeUtf(recipe.fileName);
    }
}