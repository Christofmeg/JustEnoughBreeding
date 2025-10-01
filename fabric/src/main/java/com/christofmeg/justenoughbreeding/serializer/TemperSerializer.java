package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TemperSerializer implements RecipeSerializer<TemperRecipe> {

    @Override
    public @NotNull TemperRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        JsonArray mobs = json.getAsJsonArray("mobs");
        JsonObject mobObject = mobs.get(0).getAsJsonObject();
        Map.Entry<String, JsonElement> mobEntry = mobObject.entrySet().iterator().next();
        String modID = json.get("mod").getAsString();
        String mobName = mobEntry.getKey();
        String modFolder = jsonPath.getNamespace();
        String fileName = jsonPath.getPath().substring(jsonPath.getPath().lastIndexOf('/') + 1);

        if (jsonPath.getNamespace().equals(CommonConstants.MOD_ID)) {
            modFolder = jsonPath.getPath().split("/")[1];
        }

        if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(modID)) {
   //         throw new JsonParseException("Skipping Temper recipe because mod not loaded: file=" + jsonPath + " mods=" + modFolder + "," + modID);
        }

        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(modID, mobName));
        if (entityType == null) throw new JsonParseException("Unknown entity: " + modID + ":" + mobName + " in " + jsonPath);
        if (!mobName.equals(entityType.toShortString())) {
            throw new JsonParseException("Entity id mismatch. jsonAnimalID=" + mobName + " != " + entityType.toShortString() + " in " + jsonPath);
        }

        List<Ingredient> inputIngredients = new ArrayList<>();
        List<Ingredient> extraInputIngredients = new ArrayList<>();
        List<Ingredient> spawnEggs = new ArrayList<>();
        JsonObject mobData = mobEntry.getValue().getAsJsonObject();

        addIngredients(mobData, inputIngredients, "inputs");
        addIngredients(mobData, extraInputIngredients, "extra_inputs");

        if (mobData.has("spawn_eggs")) {
            addIngredients(mobData, spawnEggs, "spawn_eggs");
        } else {
            ItemStack spawnEgg = Optional.ofNullable(JustEnoughBreeding.getSpawnEggItem(entityType))
                    .map(SpawnEggItem::getDefaultInstance)
                    .orElse(ItemStack.EMPTY);
            spawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
        }

        for (TemperRecipe existingRecipe : JustEnoughBreeding.temperRecipes) {
            if (existingRecipe.jsonModID.equals(modID) && existingRecipe.jsonAnimalID.equals(mobName)) {
                inputIngredients.add(existingRecipe.inputStack);
                spawnEggs.add(existingRecipe.spawnEgg);
                extraInputIngredients.add(existingRecipe.extraInputStack);

                existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                return existingRecipe;
            }
        }

        TemperRecipe r = new TemperRecipe(
                entityType,
                Utils.deduplicateIngredients(inputIngredients),
                Utils.deduplicateIngredients(spawnEggs),
                Utils.deduplicateIngredients(extraInputIngredients),
                modID,
                mobName,
                modFolder,
                fileName
        );
        JustEnoughBreeding.temperRecipes.add(r);
        return r;
    }

    @Override
    public @NotNull TemperRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
        ResourceLocation entityId = buf.readResourceLocation();
        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(entityId);
        if (entityType == null) throw new JsonParseException("Unknown EntityType in TemperRecipe#fromNetwork: " + entityId);

        Ingredient inputStack = Ingredient.fromNetwork(buf);
        Ingredient spawnEgg = Ingredient.fromNetwork(buf);

        boolean hasExtra = buf.readBoolean();
        Ingredient extraInputStack = hasExtra ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        return new TemperRecipe(entityType, inputStack, spawnEgg, extraInputStack, jsonModID, jsonAnimalID, modFolder, fileName);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull TemperRecipe recipe) {
        ResourceLocation entityKey = JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType);
        if (entityKey == null) throw new JsonParseException("Unknown EntityType in TemperRecipe: " + recipe.entityType);
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
    }

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
