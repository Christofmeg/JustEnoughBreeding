package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class AllayDuplicationSerializer implements RecipeSerializer<AllayDuplicationRecipe> {
/*
    public AllayDuplicationRecipe fromNetwork(ResourceLocation id, RegistryFriendlyByteBuf buf) {
        ResourceLocation entityId = buf.readResourceLocation();
        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(entityId);
        if (entityType == null) throw new JsonParseException("Unknown EntityType: " + entityId);

        Ingredient inputStack = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
        Ingredient spawnEgg = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        CompoundTag inputEntityNbt = buf.readBoolean() ? buf.readNbt() : null;

        return new AllayDuplicationRecipe(entityType, inputStack, spawnEgg, jsonModID, jsonAnimalID, modFolder, fileName, inputEntityNbt);
    }

    public void toNetwork(RegistryFriendlyByteBuf buf, AllayDuplicationRecipe recipe) {
        ResourceLocation entityKey = JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType);
        if (entityKey == null) throw new JsonParseException("Unknown EntityType: " + recipe.entityType);

        buf.writeResourceLocation(entityKey);

        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputStack);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.spawnEgg);

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
*/
    @Override
    public @NotNull MapCodec<AllayDuplicationRecipe> codec() {
        return null;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, AllayDuplicationRecipe> streamCodec() {
        return null;
    }
}