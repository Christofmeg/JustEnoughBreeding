package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Utils {

    public static Ingredient merge(List<Ingredient> ingredients) {
        JsonArray array = new JsonArray();

        for (Ingredient ing : ingredients) {
            if (ing == null || ing == Ingredient.EMPTY) continue;

            // Preserve JSON form instead of resolving stacks
            JsonElement json = ing.toJson();
            if (json.isJsonArray()) {
                for (JsonElement e : json.getAsJsonArray()) {
                    if (!array.contains(e)) {
                        array.add(e);
                    }
                }
            } else if (!array.contains(json)) {
                array.add(json);
            }
        }

        if (array.size() == 0) {
            return Ingredient.EMPTY;
        }

        return Ingredient.fromJson(array);
    }

    public static Ingredient createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(CommonUtils.createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(ingredientId.trim()));
                if (ingredientItem != null) {
                    combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem)));
                }
            }
        }

        return Utils.merge(combinedIngredients);
    }

    public static Ingredient createCombinedIngredientFromTag(String mobIngredients) {
        List<Ingredient> combinedIngredients = new ArrayList<>();
        ResourceLocation tagLocation = new ResourceLocation(mobIngredients.trim());
        combinedIngredients.add(Ingredient.of(TagKey.create(Registries.ITEM, tagLocation)));
        return Utils.merge(combinedIngredients);
    }

    public static Ingredient createCombinedIngredient(String mobIngredients, int amount, CompoundTag nbt) {
        List<Ingredient> combinedIngredients = new ArrayList<>();
        Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobIngredients.trim()));
        if (ingredientItem != null) {
            ItemStack stack = new ItemStack(ingredientItem, amount);
            if (nbt != null) {
                stack.setTag(nbt);
            }
            combinedIngredients.add(Ingredient.of(stack));
        }
        return Utils.merge(combinedIngredients);
    }

    public static void addIngredients(JsonObject mobData, List<Ingredient> ingredientList, String memberName) {
        if (mobData.has(memberName)) {
            for (JsonElement input : mobData.getAsJsonArray(memberName)) {
                CompoundTag nbt = null;
                if (input.getAsJsonObject().has("nbt")) {
                    try {
                        nbt = TagParser.parseTag(input.getAsJsonObject().get("nbt").getAsString());
                    } catch (CommandSyntaxException e) {
                        System.err.println("Invalid NBT data: {}" + input.getAsJsonObject().get("nbt").getAsString());
                    }
                }
                if (input.getAsJsonObject().has("item")) {
                    String ingredient = input.getAsJsonObject().get("item").getAsString();
                    JsonElement amountElement = input.getAsJsonObject().get("amount");
                    if (amountElement != null && amountElement.isJsonObject()) {
                        JsonObject amountObj = amountElement.getAsJsonObject();
                        int min = amountObj.has("min") ? amountObj.get("min").getAsInt() : 1;
                        int max = amountObj.has("max") ? amountObj.get("max").getAsInt() : min;
                        for (int i = min; i <= max; i++) {
                            ingredientList.add(createCombinedIngredient(ingredient, i, nbt));
                        }
                    } else {
                        int amount = input.getAsJsonObject().has("amount") ? input.getAsJsonObject().get("amount").getAsInt() : 1;
                        ingredientList.add(createCombinedIngredient(ingredient, amount, nbt));
                    }
                } else if (input.getAsJsonObject().has("tag")) {
                    String ingredient = input.getAsJsonObject().get("tag").getAsString();
                    ingredientList.add(createCombinedIngredientFromTag(ingredient));
                } else if (input.getAsJsonObject().has("meat")) {
                    boolean meat = input.getAsJsonObject().get("meat").getAsBoolean();
                    ingredientList.add(createCombinedIngredient(CommonUtils.getEdibleMeatItemNames(meat)));
                }
            }
        }
    }

    public static BaseRecipe readJsonContents(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json, String recipeType) {
        JsonArray mobs = json.getAsJsonArray("mobs");
        JsonObject mobObject = mobs.get(0).getAsJsonObject();
        Map.Entry<String, JsonElement> mobEntry = mobObject.entrySet().iterator().next();
        String jsonModID = json.get("mod").getAsString();
        String jsonAnimalID = mobEntry.getKey();
        String modFolder = jsonPath.getNamespace();
        String fileName = jsonPath.getPath().substring(jsonPath.getPath().lastIndexOf('/') + 1);

        if (jsonPath.getNamespace().equals(CommonConstants.MOD_ID)) {
            modFolder = jsonPath.getPath().split("/")[1];
        }

        // If a required mod isn't present, skip the file cleanly with a clear message.
        if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(jsonModID)) {
            throw new JsonParseException("Skipping recipe because mod not loaded: file=" + jsonPath +
                    " mods=" + modFolder + "," + jsonModID);
        }

        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(jsonModID, jsonAnimalID));
        if (entityType == null) {
            throw new JsonParseException("Unknown entity: " + jsonModID + ":" + jsonAnimalID + " in " + jsonPath);
        }
        if (!jsonAnimalID.equals(entityType.toShortString())) {
            throw new JsonParseException("Entity id mismatch. jsonAnimalID=" + jsonAnimalID + " != " + entityType.toShortString() + " in " + jsonPath);
        }

        List<Ingredient> inputIngredients = new ArrayList<>();
        List<Ingredient> extraInputIngredients = new ArrayList<>();
        List<Ingredient> outputIngredients = new ArrayList<>();
        List<Ingredient> spawnEggs = new ArrayList<>();
        JsonObject mobData = mobEntry.getValue().getAsJsonObject();
        Utils.addIngredients(mobData, inputIngredients, "inputs");
        Utils.addIngredients(mobData, extraInputIngredients, "extra_inputs");
        Utils.addIngredients(mobData, outputIngredients, "outputs");

        if (mobData.has("spawn_eggs")) {
            Utils.addIngredients(mobData, spawnEggs, "spawn_eggs");
        } else {
            ItemStack spawnEgg = Optional.ofNullable(SpawnEggItem.byId(entityType))
                    .map(SpawnEggItem::getDefaultInstance)
                    .orElse(ItemStack.EMPTY);
            spawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
        }

        switch (recipeType) {
            case "trusting" -> {
                for (TrustingRecipe existingRecipe : JustEnoughBreeding.trustingRecipes) {
                    if (existingRecipe.jsonModID.equals(jsonModID) && existingRecipe.jsonAnimalID.equals(jsonAnimalID)) {
                        inputIngredients.add(existingRecipe.inputStack);
                        spawnEggs.add(existingRecipe.spawnEgg);
                        extraInputIngredients.add(existingRecipe.extraInputStack);
                        existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                        existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                        existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                        return existingRecipe;
                    }
                }
                TrustingRecipe trustingRecipe = new TrustingRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        Utils.deduplicateIngredients(extraInputIngredients),
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName
                );
                JustEnoughBreeding.trustingRecipes.add(trustingRecipe);
                return trustingRecipe;
            }
            case "taming" -> {
                for (TamingRecipe existingRecipe : JustEnoughBreeding.tamingRecipes) {
                    if (existingRecipe.jsonModID.equals(jsonModID) && existingRecipe.jsonAnimalID.equals(jsonAnimalID)) {
                        inputIngredients.add(existingRecipe.inputStack);
                        spawnEggs.add(existingRecipe.spawnEgg);
                        extraInputIngredients.add(existingRecipe.extraInputStack);
                        existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                        existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                        existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                        return existingRecipe;
                    }
                }
                TamingRecipe tamingRecipe = new TamingRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        Utils.deduplicateIngredients(extraInputIngredients),
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName
                );
                JustEnoughBreeding.tamingRecipes.add(tamingRecipe);
                return tamingRecipe;
            }
            case "allay_duplication" -> {
                for (AllayDuplicationRecipe existingRecipe : JustEnoughBreeding.allayDuplicationRecipes) {
                    if (existingRecipe.jsonModID.equals(jsonModID) && existingRecipe.jsonAnimalID.equals(jsonAnimalID)) {
                        inputIngredients.add(existingRecipe.inputStack);
                        spawnEggs.add(existingRecipe.spawnEgg);
                        existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                        existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                        return existingRecipe;
                    }
                }
                AllayDuplicationRecipe allayDuplicationRecipe = new AllayDuplicationRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName
                );
                JustEnoughBreeding.allayDuplicationRecipes.add(allayDuplicationRecipe);
                return allayDuplicationRecipe;
            }
            default -> {
                for (BreedingRecipe existingRecipe : JustEnoughBreeding.breedingRecipes) {
                    if (existingRecipe.jsonModID.equals(jsonModID) && existingRecipe.jsonAnimalID.equals(jsonAnimalID)) {
                        inputIngredients.add(existingRecipe.inputStack);
                        extraInputIngredients.add(existingRecipe.extraInputStack);
                        outputIngredients.add(existingRecipe.resultItemStack);
                        spawnEggs.add(existingRecipe.spawnEgg);
                        existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                        existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                        existingRecipe.setOutputIngredient(Utils.deduplicateIngredients(outputIngredients));
                        existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                        return existingRecipe;
                    }
                }
                boolean isTamed = mobData.has("tamed") && mobData.get("tamed").getAsBoolean();
                boolean isTrusting = mobData.has("trusting") && mobData.get("trusting").getAsBoolean();
                BreedingRecipe breedingRecipe = new BreedingRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        isTamed,
                        Utils.deduplicateIngredients(outputIngredients),
                        Utils.deduplicateIngredients(extraInputIngredients),
                        isTrusting,
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName
                );
                JustEnoughBreeding.breedingRecipes.add(breedingRecipe);
                return breedingRecipe;
            }
        }
    }

    public static Ingredient deduplicateIngredients(List<Ingredient> ingredientList) {
        Ingredient ingredient = Utils.merge(ingredientList);
        Set<JsonElement> seen = new HashSet<>();
        List<JsonElement> uniqueJson = new ArrayList<>();

        JsonElement json = ingredient.toJson();
        if (json.isJsonArray()) {
            for (JsonElement el : json.getAsJsonArray()) {
                if (seen.add(el)) {
                    uniqueJson.add(el);
                }
            }
        } else {
            seen.add(json);
            uniqueJson.add(json);
        }

        JsonArray result = new JsonArray();
        uniqueJson.forEach(result::add);
        return Ingredient.fromJson(result);
    }
}