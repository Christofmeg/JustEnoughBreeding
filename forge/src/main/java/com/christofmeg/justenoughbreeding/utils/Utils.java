package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BaseRecipe;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.*;

@SuppressWarnings("removal")
public class Utils {

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();

        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item != null) {
                FoodProperties foodProperties = item.getFoodProperties(item.getDefaultInstance(), null);
                if (includeRottenFlesh) {
                    if (foodProperties != null && item.isEdible() && foodProperties.isMeat()) {
                        edibleMeatItemNames.add(key.toString());
                    }
                }
                else {
                    if (foodProperties != null && item.isEdible() && foodProperties.isMeat() && item != Items.ROTTEN_FLESH) {
                        edibleMeatItemNames.add(key.toString());
                    }
                }
            }
        }

        return String.join(", ", edibleMeatItemNames);
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

        return Ingredient.merge(combinedIngredients);
    }

    public static Ingredient createCombinedIngredientFromTag(String mobIngredients) {
        List<Ingredient> combinedIngredients = new ArrayList<>();
        ResourceLocation tagLocation = new ResourceLocation(mobIngredients.trim());
        combinedIngredients.add(Ingredient.of(TagKey.create(Registries.ITEM, tagLocation)));
        return Ingredient.merge(combinedIngredients);
    }

    public static Ingredient createCombinedIngredient(String mobIngredients, int amount, CompoundTag nbt) {
        List<Ingredient> combinedIngredients = new ArrayList<>();
        Item ingredientItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobIngredients.trim()));
        if (ingredientItem != null) {
            ItemStack stack = new ItemStack(ingredientItem, amount);
            if (nbt != null) {
                stack.setTag(nbt); //TODO fix nbt on ITEMS
            }
            combinedIngredients.add(Ingredient.of(stack));
        }
        return Ingredient.merge(combinedIngredients);
    }

    public static void renderEntity(@NotNull PoseStack stack, double mouseX, LivingEntity currentLivingEntity) {
        renderEntity(stack, mouseX, currentLivingEntity, 31, 89);
    }

    public static void renderEntity(@NotNull PoseStack stack, double mouseX, LivingEntity currentLivingEntity, int entityPosX, int entityPosY) {
        // Set the desired position of the entity on the screen
        int ENTITY_RENDER_DISTANCE = 15728880;

        float yaw = (float) (60 - mouseX); // Calculate the yaw based on the mouse position

        stack.pushPose(); // Push the current pose onto the stack
        stack.translate((float) entityPosX, (float) entityPosY, 50f); // Translate the entity's position

        // Calculate the scaling factor based on the bounding box's largest dimension
        AABB boundingBox = currentLivingEntity.getBoundingBox();
        double largestDimension = Math.max(boundingBox.getXsize(), Math.max(boundingBox.getYsize(), boundingBox.getZsize()));

        float desiredWidth = 30.0F;
        float desiredHeight = 40.0F;

        // Calculate the scaling factors for width and height
        float scaleX = desiredWidth / (float) largestDimension;
        float scaleY = desiredHeight / (float) largestDimension;

        // Use the smaller of the two scaling factors to ensure the entity fits within the area
        float scalingFactor = Math.min(scaleX, scaleY);

        if (currentLivingEntity instanceof Frog) {
            scalingFactor = 50;
        }

        if (currentLivingEntity instanceof Axolotl || currentLivingEntity instanceof Cat ||
                currentLivingEntity instanceof Pig || currentLivingEntity instanceof Wolf) {
            scalingFactor = 25;
        }

        if (currentLivingEntity instanceof Ocelot || currentLivingEntity instanceof Fox
                || currentLivingEntity instanceof Turtle) {
            scalingFactor = 20;
        }

        if (currentLivingEntity instanceof Hoglin || currentLivingEntity instanceof Horse
                || currentLivingEntity instanceof Panda) {
            scalingFactor = 15;
        }

        if (currentLivingEntity instanceof Sniffer) {
            scalingFactor = 10;
        }

        stack.scale(scalingFactor, scalingFactor, scalingFactor); // Scale the entity to fit within the desired area
        stack.mulPose(Axis.ZP.rotationDegrees(180.0F)); // Rotate the entity to face a certain direction

        float yawRadians = -(yaw / 40.F) * 20.0F; // Calculate the yaw angle in radians for the entity's rotation

        // Apply the calculated yaw angle to the entity's rotation properties
        currentLivingEntity.yBodyRot = yawRadians;
        currentLivingEntity.setYRot(yawRadians);
        currentLivingEntity.yHeadRot = yawRadians;
        currentLivingEntity.yHeadRotO = yawRadians;

        Minecraft instance = Minecraft.getInstance();
        EntityRenderDispatcher entityRenderDispatcher = instance.getEntityRenderDispatcher(); // Get the entity rendering dispatcher
        entityRenderDispatcher.overrideCameraOrientation(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F)); // Override the camera orientation for rendering
        entityRenderDispatcher.setRenderShadow(false); // Disable rendering shadows for the entity

        // Get the buffer source for rendering
        final MultiBufferSource.BufferSource bufferSource = instance.renderBuffers().bufferSource();

        // Render the currentLivingEntity using the entityRenderDispatcher
        entityRenderDispatcher.render(currentLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, stack, bufferSource, ENTITY_RENDER_DISTANCE);

        bufferSource.endBatch(); // End the rendering batch
        entityRenderDispatcher.setRenderShadow(true); // Re-enable rendering shadows

        stack.popPose(); // Pop the pose from the stack to revert transformations
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

    public static BaseRecipe readJsonContents (@NotNull ResourceLocation jsonPath, @NotNull JsonObject json, String recipeType) {
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
                case "breeding" -> {
                    return new BreedingRecipe(null, null, null, null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "taming" -> {
                    return new TamingRecipe(null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "trusting" -> {
                    return new TrustingRecipe(null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
                }
            }
        }

        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(jsonModID, jsonAnimalID));
        if (!jsonAnimalID.equals(entityType.toShortString())) {
            switch (recipeType) {
                case "breeding" -> {
                    return new BreedingRecipe(null, null, null, null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "taming" -> {
                    return new TamingRecipe(null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
                }
                case "trusting" -> {
                    return new TrustingRecipe(null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
                }
            }
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
            ItemStack spawnEgg = Optional.ofNullable(ForgeSpawnEggItem.fromEntityType(entityType)).map(SpawnEggItem::getDefaultInstance).orElse(ItemStack.EMPTY);
            spawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
        }

        return switch (recipeType) {
            case "trusting":
                for (TrustingRecipe existingRecipe : JustEnoughBreeding.trustingRecipes) {
                    if (existingRecipe.jsonModID.equals(jsonModID) && existingRecipe.jsonAnimalID.equals(jsonAnimalID)) {
                        inputIngredients.add(existingRecipe.inputStack);
                        spawnEggs.add(existingRecipe.spawnEgg);
                        extraInputIngredients.add(existingRecipe.extraInputStack);
                        existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                        existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                        existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                        yield existingRecipe;
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
                yield trustingRecipe;
            case "taming":
                for (TamingRecipe existingRecipe : JustEnoughBreeding.tamingRecipes) {
                    if (existingRecipe.jsonModID.equals(jsonModID) && existingRecipe.jsonAnimalID.equals(jsonAnimalID)) {
                        inputIngredients.add(existingRecipe.inputStack);
                        spawnEggs.add(existingRecipe.spawnEgg);
                        extraInputIngredients.add(existingRecipe.extraInputStack);
                        existingRecipe.setInputIngredient(Utils.deduplicateIngredients(inputIngredients));
                        existingRecipe.setExtraInputIngredient(Utils.deduplicateIngredients(extraInputIngredients));
                        existingRecipe.setSpawnEggs(Utils.deduplicateIngredients(spawnEggs));
                        yield existingRecipe;
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
                yield tamingRecipe;
            default:
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
                        yield existingRecipe;
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
                yield breedingRecipe;
        };
    }

    public static Ingredient deduplicateIngredients(List<Ingredient> ingredientList) {
        Ingredient ingredient = Ingredient.merge(ingredientList);
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
