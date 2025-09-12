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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("removal")
public class TamingRecipe extends BaseRecipe {

    public final EntityType<?> entityType;
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient spawnEgg;
    public @NotNull Ingredient extraInputStack; // EMPTY if absent
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public TamingRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, Ingredient extraInputStack, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = Objects.requireNonNull(entityType, "entityType");
        this.inputStack = safe(inputStack);
        this.spawnEgg = safe(spawnEgg);
        this.extraInputStack = safe(extraInputStack);
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.jsonAnimalID = Objects.requireNonNull(jsonAnimalID, "jsonAnimalID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");
        validateRequired();
    }

    private static @NotNull Ingredient safe(Ingredient ing) { return ing == null ? Ingredient.EMPTY : ing; }
    private void validateRequired() {
        if (inputStack.isEmpty()) throw new IllegalStateException("TamingRecipe " + getId() + " has empty input ingredient.");
        if (spawnEgg.isEmpty()) throw new IllegalStateException("TamingRecipe " + getId() + " has empty spawnEgg ingredient.");
    }

    @Override public @NotNull ResourceLocation getId() { return new ResourceLocation(CommonConstants.MOD_ID, "taming" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID); }
    @Override public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.TAMING_PROVIDER_SERIALIZER.get(); }
    @Override public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.TAMING_PROVIDER_TYPE.get(); }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = safe(ingredient); }
    public void setExtraInputIngredient(Ingredient ingredient) { this.extraInputStack = safe(ingredient); }
    public void setSpawnEggs(Ingredient ingredient) { this.spawnEgg = safe(ingredient); }

    public static class Serializer implements RecipeSerializer<TamingRecipe> {
        @Override
        public @NotNull TamingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            TamingRecipe r = (TamingRecipe) Utils.readJsonContents(jsonPath, json, "taming");
            if (r.entityType == null || r.inputStack == null || r.spawnEgg == null) {
                throw new com.google.gson.JsonParseException("TamingRecipe invalid/null fields: " + jsonPath);
            }
            return new TamingRecipe(r.entityType, r.inputStack, r.spawnEgg, safe(r.extraInputStack), r.jsonModID, r.jsonAnimalID, r.modFolder, r.fileName);
        }

        @Override
        public TamingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf buf) {
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(buf.readResourceLocation());
            if (entityType == null) throw new IllegalStateException("Unknown EntityType in TamingRecipe#fromNetwork");

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
            ResourceLocation entityKey = ForgeRegistries.ENTITY_TYPES.getKey(recipe.entityType);
            if (entityKey == null) throw new IllegalStateException("Unknown EntityType in TamingRecipe: " + recipe.entityType);
            buf.writeResourceLocation(entityKey);

            safe(recipe.inputStack).toNetwork(buf);
            safe(recipe.spawnEgg).toNetwork(buf);

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
}