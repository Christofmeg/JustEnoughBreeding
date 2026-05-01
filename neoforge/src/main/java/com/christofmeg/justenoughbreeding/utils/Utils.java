package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.config.MobOffset;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.recipe.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.util.*;

public class Utils {

    public static Ingredient merge(List<Ingredient> combinedIngredients) {
        return Ingredient.of(Arrays.stream(combinedIngredients.toArray(Ingredient[]::new))
                .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
                .distinct()
                .toArray(ItemStack[]::new));
    }

    public static Ingredient createCombinedIngredient(String mobIngredients) {
        String[] ingredientIds = mobIngredients.split(",");
        List<Ingredient> combinedIngredients = new ArrayList<>();

        for (String ingredientId : ingredientIds) {
            if (ingredientId.trim().startsWith("#")) {
                combinedIngredients.add(CommonUtils.createTagIngredient(ingredientId));
            } else {
                Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(ResourceLocation.parse(ingredientId.trim()));
                combinedIngredients.add(Ingredient.of(new ItemStack(ingredientItem)));
            }
        }

        return merge(combinedIngredients);
    }

    public static Ingredient createCombinedIngredientFromTag(String mobIngredients) {
        List<Ingredient> combinedIngredients = new ArrayList<>();
        ResourceLocation tagLocation = ResourceLocation.parse(mobIngredients.trim());
        combinedIngredients.add(Ingredient.of(TagKey.create(Registries.ITEM, tagLocation)));
        return merge(combinedIngredients);
    }

    public static Ingredient createCombinedIngredient(String mobIngredients, int amount, CompoundTag nbt) {
        List<Ingredient> combinedIngredients = new ArrayList<>();
        Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(ResourceLocation.parse(mobIngredients.trim()));
        if (ingredientItem != null) {
            ItemStack stack = new ItemStack(ingredientItem, amount);
            if (nbt != null && !nbt.isEmpty()) {
                stack.applyComponents(DataComponentPatch.CODEC.parse(NbtOps.INSTANCE, nbt).result().orElseThrow());
            }
            combinedIngredients.add(Ingredient.of(stack));
        }
        return merge(combinedIngredients);
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

    public static RecipeHolder<BaseRecipe> readJsonContents(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json, String recipeType) {
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

        if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(jsonModID)) {
            switch (recipeType) {
                case "allay_duplication" -> {
                    return new AllayDuplicationRecipe.DummyRecipe(jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "breeding" -> {
                    return new BreedingRecipe.DummyRecipe(jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "taming" -> {
                    return new TamingRecipe.DummyRecipe(jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "temper" -> {
                    return new TemperRecipe.DummyRecipe(jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "transformation" -> {
                    return new TransformationRecipe.DummyRecipe(jsonModID, modFolder, fileName);
                }
                case "trusting" -> {
                    return new TrustingRecipe.DummyRecipe(jsonModID, jsonAnimalID, modFolder, fileName);
                }
            }
        }

        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.fromNamespaceAndPath(jsonModID, jsonAnimalID));
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

        CompoundTag inputEntityNbt = json.has("input_entity_nbt")
                ? CommonUtils.parseJsonNBT(json.get("input_entity_nbt"))
                : null;

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
                return new TrustingRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        Utils.deduplicateIngredients(extraInputIngredients),
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName,
                        inputEntityNbt
                );
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
                return new TamingRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        Utils.deduplicateIngredients(extraInputIngredients),
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName,
                        inputEntityNbt
                );
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
                return new AllayDuplicationRecipe(
                        entityType,
                        Utils.deduplicateIngredients(inputIngredients),
                        Utils.deduplicateIngredients(spawnEggs),
                        jsonModID,
                        jsonAnimalID,
                        modFolder,
                        fileName,
                        inputEntityNbt
                );
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
                return new BreedingRecipe(
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
                        fileName,
                        inputEntityNbt
                );
            }
        }
    }

    public static Ingredient deduplicateIngredients(List<Ingredient> ingredientList) {
        Ingredient ingredient = merge(ingredientList);
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

    public static LivingEntity getLivingEntity(LivingEntity currentLivingEntity, boolean input, BaseRecipe recipe) {
        if (currentLivingEntity != null) {
            if (input) {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.inputEntityNbt != null) {
                        currentLivingEntity.load(transformationRecipe.inputEntityNbt);
                    }
                }
                if (recipe instanceof AllayDuplicationRecipe allayDuplicationRecipe) {
                    if (allayDuplicationRecipe.inputEntityNbt != null) {
                        currentLivingEntity.load(allayDuplicationRecipe.inputEntityNbt);
                    }
                }
                if (recipe instanceof BreedingRecipe breedingRecipe) {
                    if (breedingRecipe.inputEntityNbt != null) {
                        currentLivingEntity.load(breedingRecipe.inputEntityNbt);
                    }
                }
                if (recipe instanceof TamingRecipe tamingRecipe) {
                    if (tamingRecipe.inputEntityNbt != null) {
                        currentLivingEntity.load(tamingRecipe.inputEntityNbt);
                    }
                }
                if (recipe instanceof TemperRecipe temperRecipe) {
                    if (temperRecipe.inputEntityNbt != null) {
                        currentLivingEntity.load(temperRecipe.inputEntityNbt);
                    }
                }
                if (recipe instanceof TrustingRecipe trustingRecipe) {
                    if (trustingRecipe.inputEntityNbt != null) {
                        currentLivingEntity.load(trustingRecipe.inputEntityNbt);
                    }
                }
            } else {
                if (recipe instanceof TransformationRecipe transformationRecipe) {
                    if (transformationRecipe.outputEntityNbt != null) {
                        currentLivingEntity.load(transformationRecipe.outputEntityNbt);
                    }
                }
            }
        }
        return currentLivingEntity;
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int left, int bottom, float mouseX, LivingEntity entity, Rect bounds, EntityType<?> entityType) {
        guiGraphics.pose().pushPose();
        int x = bounds.x();
        int y = bounds.y();
        int renderLeft = left + x + 31;
        int renderBottom = bottom + y + 79;

        Minecraft minecraft = Minecraft.getInstance();
        Window window = minecraft.getWindow();
        PoseStack poseStack = guiGraphics.pose();

        // Get the model-view matrix (combined) from the PoseStack
        Matrix4f modelViewMatrix = new Matrix4f(poseStack.last().pose());
        // Get the projection matrix
        Matrix4f projectionMatrix = new Matrix4f(RenderSystem.getProjectionMatrix());
        // Combine model-view and projection
        Matrix4f mvpMatrix = projectionMatrix.mul(modelViewMatrix);
        // Define the 3D coordinates of the top-left and bottom-right corners of your element
        // Since it's a 2D element in GUI, Z can be 0.
        Vector4f topLeftWorld = new Vector4f(0, 0, 0, 1);
        // Project to clip space
        Vector4f topLeftClip = mvpMatrix.transform(topLeftWorld);
        // Perspective divide
        Vector4f topLeftNDC = new Vector4f(topLeftClip.x / topLeftClip.w, topLeftClip.y / topLeftClip.w, 0, 1);

        // Convert to screen coordinates (pixels)
        int screenX = Math.round((topLeftNDC.x + 1) / 2f * window.getGuiScaledWidth());
        int screenY = Math.round((1 - topLeftNDC.y) / 2f * window.getGuiScaledHeight());

        EntityDimensions dimensions = entity.getType().getDimensions();
        int scale = (int) (Math.min(50 / dimensions.height, 50 / dimensions.width));

        float yaw = 60 - mouseX;
        float yawRadians = -(yaw / 40.F) * 20.0F;

        guiGraphics.enableScissor(screenX + bounds.x() + 1, screenY + bounds.y() + 1, screenX + bounds.right() - 1, screenY + bounds.bottom() - 1);
        //        guiGraphics.fill(-guiGraphics.guiWidth(), -guiGraphics.guiHeight(), guiGraphics.guiWidth(), guiGraphics.guiHeight(), -15536);
        MobOffset mobOffset = MobOffsetManager.get(JustEnoughBreeding.getKeyLoaderRegistries(entityType));
        InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics,
                renderLeft+ (int) mobOffset.x(),
                renderBottom + (int) mobOffset.y(),
                scale + (int) mobOffset.scale(),
                -yawRadians, 0, entity);
        guiGraphics.disableScissor();
        guiGraphics.pose().popPose();
    }

}