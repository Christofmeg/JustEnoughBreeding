package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TemperRecipe extends ForgeRecipe {

    public final EntityType<?> entityType;
    public Ingredient inputStack;
    public Ingredient spawnEgg;
    public @Nullable Ingredient extraInputStack;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public TemperRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, @Nullable Ingredient extraInputStack, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = entityType;
        this.inputStack = inputStack;
        this.spawnEgg = spawnEgg;
        this.extraInputStack = extraInputStack;
        this.jsonModID = jsonModID;
        this.jsonAnimalID = jsonAnimalID;
        this.modFolder = modFolder;
        this.fileName = fileName;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "temper" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.TEMPER_PROVIDER_SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.TEMPER_PROVIDER_TYPE;
    }

    public void setInputIngredient(Ingredient ingredient) {
        this.inputStack = ingredient;
    }

    public void setExtraInputIngredient(Ingredient ingredient) {
        this.extraInputStack = ingredient;
    }

    public void setSpawnEggs(Ingredient ingredient) {
        this.spawnEgg = ingredient;
    }

    public static class Serializer implements RecipeSerializer<TemperRecipe> {

        @Override
        public @NotNull TemperRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {

            JsonArray mobs = json.getAsJsonArray("mobs");
            JsonObject mobObject = mobs.get(0).getAsJsonObject();
            Map.Entry<String, JsonElement> mobEntry = mobObject.entrySet().iterator().next();
            String modID = json.get("mod").getAsString();
            String mobName = mobEntry.getKey();
            String modFolder = jsonPath.getNamespace();
            String fileName = jsonPath.getPath().substring(jsonPath.getPath().lastIndexOf('/') + 1);

            if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(modID)) {
                return new TemperRecipe(null, null, null, null, modID, mobName, modFolder, fileName);
            }

            EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(modID, mobName));
            if (!mobName.equals(entityType.toShortString())) {
                return new TemperRecipe(null, null, null, null, modID, mobName, modFolder, fileName);
            }

            List<Ingredient> inputIngredients = new ArrayList<>();
            List<Ingredient> extraInputIngredients = new ArrayList<>();
            List<Ingredient> outputIngredients = new ArrayList<>();
            List<Ingredient> spawnEggs = new ArrayList<>();
            JsonObject mobData = mobEntry.getValue().getAsJsonObject();

            addIngredients(mobData, inputIngredients, "inputs");
            addIngredients(mobData, extraInputIngredients, "extra_inputs");
            addIngredients(mobData, outputIngredients, "outputs");

            if (mobData.has("spawn_eggs")) {
                addIngredients(mobData, spawnEggs, "spawn_eggs");
            } else {
                ItemStack spawnEgg = Optional.ofNullable(SpawnEggItem.byId(entityType))
                        .map(SpawnEggItem::getDefaultInstance)
                        .orElse(ItemStack.EMPTY);
                spawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
            }

            for (TemperRecipe existingRecipe : JustEnoughBreeding.temperRecipes) {
                if (existingRecipe.jsonModID.equals(modID) && existingRecipe.jsonAnimalID.equals(mobName)) {

                    inputIngredients.add(existingRecipe.inputStack);
                    spawnEggs.add(existingRecipe.spawnEgg);
                    extraInputIngredients.add(existingRecipe.extraInputStack);

                    existingRecipe.setInputIngredient(Utils.merge(inputIngredients));
                    existingRecipe.setExtraInputIngredient(Utils.merge(extraInputIngredients));
                    existingRecipe.setSpawnEggs(Utils.merge(spawnEggs));

                    return existingRecipe;
                }
            }

            TemperRecipe newRecipe = new TemperRecipe(
                    entityType,
                    Utils.merge(inputIngredients),
                    Utils.merge(spawnEggs),
                    Utils.merge(extraInputIngredients),
                    modID,
                    mobName,
                    modFolder,
                    fileName
            );

            JustEnoughBreeding.temperRecipes.add(newRecipe);
            return newRecipe;
        }

        @Override
        public @Nullable TemperRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull TemperRecipe temperRecipe) {}

        private void addIngredients(JsonObject mobData, List<Ingredient> ingredientList, String memberName) {
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
                        int temperValue = input.getAsJsonObject().has("value") ? input.getAsJsonObject().get("value").getAsInt() : 1;
                        ingredientList.add(Utils.createCombinedIngredient(ingredient, temperValue, nbt));
                    }
                }
            }
        }

    }
}
