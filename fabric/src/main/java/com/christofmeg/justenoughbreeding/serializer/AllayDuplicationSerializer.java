package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
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

public class AllayDuplicationSerializer implements RecipeSerializer<AllayDuplicationRecipe> {

    @Override
    public @NotNull AllayDuplicationRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        return (AllayDuplicationRecipe) Utils.readJsonContents(jsonPath, json, "allay_duplication");
    }

    @Override
    public @NotNull AllayDuplicationRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf buf) {
        ResourceLocation entityId = buf.readResourceLocation();
        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(entityId);
        if (entityType == null) throw new JsonParseException("Unknown EntityType in AllayDuplicationRecipe#fromNetwork: " + entityId);

        Ingredient inputStack = Ingredient.fromNetwork(buf);
        Ingredient spawnEgg = Ingredient.fromNetwork(buf);

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        return new AllayDuplicationRecipe(entityType, inputStack, spawnEgg, jsonModID, jsonAnimalID, modFolder, fileName);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull AllayDuplicationRecipe recipe) {
        ResourceLocation entityKey = JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType);
        if (entityKey == null) throw new JsonParseException("Unknown EntityType in AllayDuplicationRecipe: " + recipe.entityType);
        buf.writeResourceLocation(entityKey);

        CommonUtils.safe(recipe.inputStack).toNetwork(buf);
        CommonUtils.safe(recipe.spawnEgg).toNetwork(buf);

        buf.writeUtf(recipe.jsonModID);
        buf.writeUtf(recipe.jsonAnimalID);
        buf.writeUtf(recipe.modFolder);
        buf.writeUtf(recipe.fileName);
    }
}