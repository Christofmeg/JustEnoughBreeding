// File: src/main/java/com/christofmeg/justenoughbreeding/recipe/TransformationRecipe.java
package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SuppressWarnings("removal")
public class TransformationRecipe extends BaseRecipe {

    public final EntityType<?> inputEntityType;
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient inputSpawnEgg;
    public @NotNull Ingredient extraInputStack; // EMPTY if absent
    public final EntityType<?> outputEntityType;
    public @NotNull Ingredient outputSpawnEgg;
    public final @Nullable Boolean needsToBeTamed;
    public final String jsonModID;
    public final String modFolder;
    public final String fileName;
    public final @Nullable DyeColor inputColor;
    public final @Nullable DyeColor outputColor;

    public TransformationRecipe(EntityType<?> inputEntityType,
                                Ingredient inputStack,
                                Ingredient inputSpawnEgg,
                                Ingredient extraInputStack,
                                EntityType<?> outputEntityType,
                                Ingredient outputSpawnEgg,
                                @Nullable Boolean needsToBeTamed,
                                String jsonModID,
                                String modFolder,
                                String fileName,
                                @Nullable DyeColor inputColor,
                                @Nullable DyeColor outputColor) {
        this.inputEntityType = Objects.requireNonNull(inputEntityType, "inputEntityType");
        this.inputStack = safe(inputStack);
        this.inputSpawnEgg = safe(inputSpawnEgg);
        this.extraInputStack = safe(extraInputStack);
        this.outputEntityType = Objects.requireNonNull(outputEntityType, "outputEntityType");
        this.outputSpawnEgg = safe(outputSpawnEgg);
        this.needsToBeTamed = needsToBeTamed;
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");
        this.inputColor = inputColor;
        this.outputColor = outputColor;
        validateRequired();
    }

    private static @NotNull Ingredient safe(Ingredient ing) { return ing == null ? Ingredient.EMPTY : ing; }
    private void validateRequired() {
        if (inputStack.isEmpty()) throw new IllegalStateException("TransformationRecipe " + getId() + " has empty input ingredient.");
        if (inputSpawnEgg.isEmpty()) throw new IllegalStateException("TransformationRecipe " + getId() + " has empty inputSpawnEgg ingredient.");
        if (outputSpawnEgg.isEmpty()) throw new IllegalStateException("TransformationRecipe " + getId() + " has empty outputSpawnEgg ingredient.");
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "transformation" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.TRANSFORMATION_PROVIDER_SERIALIZER.get(); }

    @Override
    public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get(); }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = safe(ingredient); }
    public void setExtraInputIngredient(Ingredient ingredient) { this.extraInputStack = safe(ingredient); }
    public void setInputSpawnEggs(Ingredient ingredient) { this.inputSpawnEgg = safe(ingredient); }
    public void setOutputSpawnEggs(Ingredient ingredient) { this.outputSpawnEgg = safe(ingredient); }

    public static class Serializer implements RecipeSerializer<TransformationRecipe> {
        @Override
        public @NotNull TransformationRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            return (TransformationRecipe) readJsonContents(jsonPath, json);
        }

        @Override
        public TransformationRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            ResourceLocation inputEntityId = buf.readResourceLocation();
            EntityType<?> inputEntityType = ForgeRegistries.ENTITY_TYPES.getValue(inputEntityId);
            if (inputEntityType == null) throw new IllegalStateException("Unknown input EntityType in TransformationRecipe#fromNetwork: " + inputEntityId);

            ResourceLocation outputEntityId = buf.readResourceLocation();
            EntityType<?> outputEntityType = ForgeRegistries.ENTITY_TYPES.getValue(outputEntityId);
            if (outputEntityType == null) throw new IllegalStateException("Unknown output EntityType in TransformationRecipe#fromNetwork: " + outputEntityId);

            Ingredient inputStack = Ingredient.fromNetwork(buf);
            Ingredient inputSpawnEgg = Ingredient.fromNetwork(buf);

            boolean hasExtraInput = buf.readBoolean();
            Ingredient extraInputStack = hasExtraInput ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

            Ingredient outputSpawnEgg = Ingredient.fromNetwork(buf);

            Boolean needsToBeTamed = buf.readBoolean() ? buf.readBoolean() : null;

            String jsonModID = buf.readUtf();
            String modFolder = buf.readUtf();
            String fileName = buf.readUtf();

            DyeColor inputColor = buf.readBoolean() ? DyeColor.byId(buf.readVarInt()) : null;
            DyeColor outputColor = buf.readBoolean() ? DyeColor.byId(buf.readVarInt()) : null;

            return new TransformationRecipe(
                    inputEntityType,
                    inputStack,
                    inputSpawnEgg,
                    extraInputStack,
                    outputEntityType,
                    outputSpawnEgg,
                    needsToBeTamed,
                    jsonModID,
                    modFolder,
                    fileName,
                    inputColor,
                    outputColor
            );
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull TransformationRecipe recipe) {
            ResourceLocation inKey = ForgeRegistries.ENTITY_TYPES.getKey(recipe.inputEntityType);
            ResourceLocation outKey = ForgeRegistries.ENTITY_TYPES.getKey(recipe.outputEntityType);
            if (inKey == null || outKey == null) throw new IllegalStateException("Unknown EntityType in TransformationRecipe: " + recipe.inputEntityType + " / " + recipe.outputEntityType);
            buf.writeResourceLocation(inKey);
            buf.writeResourceLocation(outKey);

            safe(recipe.inputStack).toNetwork(buf);
            safe(recipe.inputSpawnEgg).toNetwork(buf);

            boolean hasExtra = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
            buf.writeBoolean(hasExtra);
            if (hasExtra) recipe.extraInputStack.toNetwork(buf);

            safe(recipe.outputSpawnEgg).toNetwork(buf);

            if (recipe.needsToBeTamed != null) { buf.writeBoolean(true); buf.writeBoolean(recipe.needsToBeTamed); } else { buf.writeBoolean(false); }

            buf.writeUtf(recipe.jsonModID);
            buf.writeUtf(recipe.modFolder);
            buf.writeUtf(recipe.fileName);

            if (recipe.inputColor != null) { buf.writeBoolean(true); buf.writeVarInt(recipe.inputColor.getId()); } else { buf.writeBoolean(false); }
            if (recipe.outputColor != null) { buf.writeBoolean(true); buf.writeVarInt(recipe.outputColor.getId()); } else { buf.writeBoolean(false); }
        }
    }

    public static BaseRecipe readJsonContents(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        String jsonModID = json.get("mod").getAsString();
        String modFolder = jsonPath.getNamespace();
        String fileName = jsonPath.getPath().substring(jsonPath.getPath().lastIndexOf('/') + 1);

        if (jsonPath.getNamespace().equals(CommonConstants.MOD_ID)) {
            modFolder = jsonPath.getPath().split("/")[1];
        }
        if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(jsonModID)) {
            throw new JsonParseException("Skipping Transformation recipe because mod not loaded: file=" + jsonPath + " mods=" + modFolder + "," + jsonModID);
        }

        List<Ingredient> inputIngredients = new ArrayList<>();
        List<Ingredient> extraInputIngredients = new ArrayList<>();
        List<Ingredient> inputSpawnEggs = new ArrayList<>();
        List<Ingredient> outputSpawnEggs = new ArrayList<>();

        EntityType<?> inputEntityType = null;
        if (json.has("input_entity")) {
            String input_entity = json.get("input_entity").getAsString();
            inputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(input_entity));
        }
        EntityType<?> outputEntityType = null;
        if (json.has("output_entity")) {
            String outputEntity = json.get("output_entity").getAsString();
            outputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(outputEntity));
        }
        if (inputEntityType == null || outputEntityType == null) {
            throw new JsonParseException("Transformation recipe missing/unknown entities in " + jsonPath);
        }

        Utils.addIngredients(json, inputIngredients, "inputs");
        Utils.addIngredients(json, extraInputIngredients, "extra_inputs");
        Utils.addIngredients(json, inputSpawnEggs, "input_spawn_eggs");
        Utils.addIngredients(json, outputSpawnEggs, "output_spawn_eggs");

        if (inputSpawnEggs.isEmpty()) {
            ItemStack spawnEgg = Optional.ofNullable(ForgeSpawnEggItem.fromEntityType(inputEntityType)).map(SpawnEggItem::getDefaultInstance).orElse(ItemStack.EMPTY);
            inputSpawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
        }
        if (outputSpawnEggs.isEmpty()) {
            ItemStack spawnEgg = Optional.ofNullable(ForgeSpawnEggItem.fromEntityType(outputEntityType)).map(SpawnEggItem::getDefaultInstance).orElse(ItemStack.EMPTY);
            outputSpawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
        }

        for (TransformationRecipe existingRecipe : JustEnoughBreeding.transformationRecipes) {
            if (existingRecipe.getId().equals(jsonPath)) {
                inputIngredients.add(existingRecipe.inputStack);
                extraInputIngredients.add(existingRecipe.extraInputStack);
                inputSpawnEggs.add(existingRecipe.inputSpawnEgg);
                outputSpawnEggs.add(existingRecipe.outputSpawnEgg);
                existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                existingRecipe.setInputSpawnEggs(Utils.deduplicateIngredients(inputSpawnEggs));
                existingRecipe.setOutputSpawnEggs(Utils.deduplicateIngredients(outputSpawnEggs));
                return existingRecipe;
            }
        }

        boolean isTamed = json.has("tamed") && json.get("tamed").getAsBoolean();
        DyeColor inputColor = json.has("input_color") ? DyeColor.valueOf(json.get("input_color").getAsString().toUpperCase()) : null;
        DyeColor outputColor = json.has("output_color") ? DyeColor.valueOf(json.get("output_color").getAsString().toUpperCase()) : null;

        TransformationRecipe transformationRecipe = new TransformationRecipe(
                inputEntityType,
                Utils.deduplicateIngredients(inputIngredients),
                Utils.deduplicateIngredients(inputSpawnEggs),
                Utils.deduplicateIngredients(extraInputIngredients),
                outputEntityType,
                Utils.deduplicateIngredients(outputSpawnEggs),
                isTamed,
                jsonModID,
                modFolder,
                fileName,
                inputColor,
                outputColor
        );
        JustEnoughBreeding.transformationRecipes.add(transformationRecipe);
        return transformationRecipe;
    }
}
