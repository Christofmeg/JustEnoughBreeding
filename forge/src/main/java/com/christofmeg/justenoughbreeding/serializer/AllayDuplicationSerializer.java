package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
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

public class AllayDuplicationSerializer implements RecipeSerializer<AllayDuplicationRecipe> {

        @Override
        public @NotNull AllayDuplicationRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            AllayDuplicationRecipe r = (AllayDuplicationRecipe) Utils.readJsonContents(jsonPath, json, "allay_duplication");
            if (r.entityType == null || r.inputStack == null || r.spawnEgg == null) {
                throw new com.google.gson.JsonParseException("AllayDuplicationRecipe invalid/null fields: " + jsonPath);
            }
            return r;
        }

        @Override
        public AllayDuplicationRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf buf) {
            ResourceLocation entityId = buf.readResourceLocation();
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(entityId);
            if (entityType == null) throw new IllegalStateException("Unknown EntityType in AllayDuplicationRecipe#fromNetwork: " + entityId);

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
            ResourceLocation entityKey = ForgeRegistries.ENTITY_TYPES.getKey(recipe.entityType);
            if (entityKey == null) throw new IllegalStateException("Unknown EntityType in AllayDuplicationRecipe: " + recipe.entityType);
            buf.writeResourceLocation(entityKey);

            CommonUtils.safe(recipe.inputStack).toNetwork(buf);
            CommonUtils.safe(recipe.spawnEgg).toNetwork(buf);

            buf.writeUtf(recipe.jsonModID);
            buf.writeUtf(recipe.jsonAnimalID);
            buf.writeUtf(recipe.modFolder);
            buf.writeUtf(recipe.fileName);
        }
}