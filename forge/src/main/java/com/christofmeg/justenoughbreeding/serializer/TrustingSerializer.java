package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class TrustingSerializer implements RecipeSerializer<TrustingRecipe> {

    @Override
    public @NotNull TrustingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        return (TrustingRecipe) Utils.readJsonContents(jsonPath, json, "trusting");
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

        CompoundTag inputEntityNbt = buf.readBoolean() ? buf.readNbt() : null;

        return new TrustingRecipe(entityType, inputStack, spawnEgg, extraInputStack, jsonModID, jsonAnimalID, modFolder, fileName, inputEntityNbt);
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

        if (recipe.inputEntityNbt != null) {
            buf.writeBoolean(true);
            buf.writeNbt(recipe.inputEntityNbt);
        } else {
            buf.writeBoolean(false);
        }
    }
}