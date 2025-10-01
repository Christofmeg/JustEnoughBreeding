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

    //TODO [07:07:10] [Render thread/ERROR]:Parsing error loading recipe justenoughbreeding:temper/frozenup/reindeer
    //com.google.gson.JsonParseException: Entity id mismatch. jsonAnimalID=reindeer != pig in justenoughbreeding:temper/frozenup/reindeer
    //	at com.christofmeg.justenoughbreeding.serializer.TemperSerializer.fromJson(TemperSerializer.java:52) ~[justenoughbreeding-fabric-1.20-1.20.1-2.2.0.jar:?]
    //	at com.christofmeg.justenoughbreeding.serializer.TemperSerializer.method_8121(TemperSerializer.java:29) ~[justenoughbreeding-fabric-1.20-1.20.1-2.2.0.jar:?]
    //	at net.minecraft.class_1863.method_17720(class_1863.java:135) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_1863.method_20705(class_1863.java:56) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_1863.method_18788(class_1863.java:35) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_4080.method_18790(class_4080.java:13) ~[client-intermediary.jar:?]
    //	at java.util.concurrent.CompletableFuture$UniAccept.tryFire(Unknown Source) ~[?:?]
    //	at java.util.concurrent.CompletableFuture$Completion.run(Unknown Source) ~[?:?]
    //	at net.minecraft.class_4014.method_18365(class_4014.java:69) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_1255.method_18859(class_1255.java:156) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_4093.method_18859(class_4093.java:23) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_1255.method_16075(class_1255.java:130) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_1255.method_18857(class_1255.java:139) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_7196.method_45694(class_7196.java:188) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_7196.method_41891(class_7196.java:123) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_7196.method_41899(class_7196.java:202) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_7196.method_41894(class_7196.java:64) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_528$class_4272.method_20174(class_528.java:590) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_528$class_4272.method_20164(class_528.java:480) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_528$class_4272.method_25402(class_528.java:407) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_350.method_25402(class_350.java:327) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_4069.method_25402(class_4069.java:38) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_312.method_1611(class_312.java:98) ~[client-intermediary.jar:?]
    //	at net.minecraft.class_437.method_25412(class_437.java:409) ~[client-intermediary.jar:?]
}
