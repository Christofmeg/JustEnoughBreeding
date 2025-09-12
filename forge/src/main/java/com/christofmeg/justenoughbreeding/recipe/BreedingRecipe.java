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
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("removal")
public class BreedingRecipe extends BaseRecipe {

    public final EntityType<?> entityType;

    // Never store null Ingredients; use Ingredient.EMPTY
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient spawnEgg;
    public final @Nullable Boolean needsToBeTamed;
    public @NotNull Ingredient resultItemStack;   // EMPTY == absent
    public @NotNull Ingredient extraInputStack;   // EMPTY == absent

    public final @Nullable Boolean animalTrusting;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public BreedingRecipe(
            EntityType<?> entityType,
            @Nullable Ingredient inputStack,
            @Nullable Ingredient spawnEgg,
            @Nullable Boolean needsToBeTamed,
            @Nullable Ingredient resultItemStack,
            @Nullable Ingredient extraInputStack,
            @Nullable Boolean animalTrusting,
            String jsonModID,
            String jsonAnimalID,
            String modFolder,
            String fileName
    ) {
        this.entityType = Objects.requireNonNull(entityType, "entityType");
        this.inputStack = safe(inputStack);
        this.spawnEgg = safe(spawnEgg);
        this.needsToBeTamed = needsToBeTamed;
        this.resultItemStack = safe(resultItemStack);
        this.extraInputStack = safe(extraInputStack);
        this.animalTrusting = animalTrusting;
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.jsonAnimalID = Objects.requireNonNull(jsonAnimalID, "jsonAnimalID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");

        validateRequired();
    }

    private static @NotNull Ingredient safe(@Nullable Ingredient ing) {
        return ing == null ? Ingredient.EMPTY : ing;
    }

    /** Fail early with a clear message if required pieces are missing. */
    private void validateRequired() {
        if (this.inputStack.isEmpty()) {
            throw new IllegalStateException("BreedingRecipe " + getId() + " has empty input ingredient.");
        }
        if (this.spawnEgg.isEmpty()) {
            throw new IllegalStateException("BreedingRecipe " + getId() + " has empty spawnEgg ingredient.");
        }
        // resultItemStack / extraInputStack are optional; EMPTY represents absent.
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(
                CommonConstants.MOD_ID,
                "breeding" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID
        );
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.BREEDING_PROVIDER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get();
    }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = safe(ingredient); }
    public void setExtraInputIngredient(Ingredient ingredient) { this.extraInputStack = safe(ingredient); }
    public void setOutputIngredient(Ingredient ingredient) { this.resultItemStack = safe(ingredient); }
    public void setSpawnEggs(Ingredient ingredient) { this.spawnEgg = safe(ingredient); }

    public static class Serializer implements RecipeSerializer<BreedingRecipe> {
        @Override
        public @NotNull BreedingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            // Utils will now throw on invalid files instead of returning placeholders.
            BreedingRecipe r = (BreedingRecipe) Utils.readJsonContents(jsonPath, json, "breeding");
            // Belt-and-suspenders: validate critical fields.
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

            // Never call toNetwork on null; use EMPTY as a safe default.
            safe(recipe.inputStack).toNetwork(buf);
            safe(recipe.spawnEgg).toNetwork(buf);

            // Optional result
            boolean hasResult = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
            buf.writeBoolean(hasResult);
            if (hasResult) {
                recipe.resultItemStack.toNetwork(buf);
            }

            // Optional extra
            boolean hasExtra = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
            buf.writeBoolean(hasExtra);
            if (hasExtra) {
                recipe.extraInputStack.toNetwork(buf);
            }

            // Nullable booleans: presence + value
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
}