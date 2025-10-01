package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
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

public class TamingSerializer implements RecipeSerializer<TamingRecipe> {

    @Override
    public @NotNull TamingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        TamingRecipe r = (TamingRecipe) Utils.readJsonContents(jsonPath, json, "taming");
        if (r == null) {
            return null;
        }
        if (r.entityType == null || r.inputStack == null || r.spawnEgg == null) {
            throw new JsonParseException("TamingRecipe invalid/null fields: " + jsonPath);
        }
        return new TamingRecipe(r.entityType, r.inputStack, r.spawnEgg, CommonUtils.safe(r.extraInputStack), r.jsonModID, r.jsonAnimalID, r.modFolder, r.fileName);
    }

    @Override
    public @NotNull TamingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf buf) {
        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(buf.readResourceLocation());
        if (entityType == null) throw new JsonParseException("Unknown EntityType in TamingRecipe#fromNetwork");

        Ingredient inputStack = Ingredient.fromNetwork(buf);
        Ingredient spawnEgg = Ingredient.fromNetwork(buf);

        boolean hasExtra = buf.readBoolean();
        Ingredient extraInputStack = hasExtra ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        return new TamingRecipe(entityType, inputStack, spawnEgg, extraInputStack, jsonModID, jsonAnimalID, modFolder, fileName);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull TamingRecipe recipe) {
        ResourceLocation entityKey = JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType);
        if (entityKey == null) throw new JsonParseException("Unknown EntityType in TamingRecipe: " + recipe.entityType);
        buf.writeResourceLocation(entityKey);

        CommonUtils.safe(recipe.inputStack).toNetwork(buf);
        CommonUtils.safe(recipe.spawnEgg).toNetwork(buf);

        boolean hasExtra = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        buf.writeBoolean(hasExtra);
        if (hasExtra) {
            recipe.extraInputStack.toNetwork(buf);
        }

        buf.writeUtf(recipe.jsonModID);
        buf.writeUtf(recipe.jsonAnimalID);
        buf.writeUtf(recipe.modFolder);
        buf.writeUtf(recipe.fileName);
    }
}