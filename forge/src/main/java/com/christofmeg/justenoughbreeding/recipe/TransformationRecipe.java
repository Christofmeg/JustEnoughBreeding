package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
public class TransformationRecipe extends ForgeRecipe {

    private LivingEntity inputEntity = null;
    private LivingEntity outputEntity = null;
    private long lastEntityCreationTime = 0;
    public final EntityType<?> inputEntityType;
    public Ingredient inputStack;
    public Ingredient inputSpawnEgg;
    public @Nullable Ingredient extraInputStack;
    public final EntityType<?> outputEntityType;
    public Ingredient outputSpawnEgg;
    @Nullable
    public final Boolean needsToBeTamed;
    public final String jsonModID;
    public final String modFolder;
    public final String fileName;
    public final DyeColor inputColor;
    public final DyeColor outputColor;

    public TransformationRecipe(EntityType<?> inputEntityType, Ingredient inputStack, Ingredient inputSpawnEgg, @Nullable Ingredient extraInputStack, EntityType<?> outputEntityType, Ingredient outputSpawnEgg, @Nullable Boolean needsToBeTamed, String jsonModID, String modFolder, String fileName, @Nullable DyeColor inputColor, @Nullable DyeColor outputColor) {
        this.inputEntityType = inputEntityType;
        this.inputStack = inputStack;
        this.inputSpawnEgg = inputSpawnEgg;
        this.extraInputStack = extraInputStack;
        this.outputEntityType = outputEntityType;
        this.outputSpawnEgg = outputSpawnEgg;
        this.needsToBeTamed = needsToBeTamed;
        this.jsonModID = jsonModID;
        this.modFolder = modFolder;
        this.fileName = fileName;
        this.inputColor = inputColor;
        this.outputColor = outputColor;
    }

    public LivingEntity doRendering(EntityType<?> entityType, boolean input, DyeColor color) {
        long currentTime = System.currentTimeMillis();
        Level level = Minecraft.getInstance().level;

        if (input) {
            if (level != null) {
                if (inputEntity == null) {
                    inputEntity = (LivingEntity) entityType.create(level);
                    lastEntityCreationTime = currentTime;
                }
                if (currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL) {
                    if (!ModList.get().isLoaded("entity_model_features") && !ModList.get().isLoaded("optifine")) {
                        inputEntity = (LivingEntity) entityType.create(level);
                        lastEntityCreationTime = currentTime;
                    }
                }
            }
            if (inputEntity != null) {
                if (inputEntity instanceof TamableAnimal tamableAnimal) {
                    tamableAnimal.setTame(true);
                }
            }
            if (color != null) {
                if (inputEntity instanceof Sheep sheep) {
                    sheep.setColor(color);
                }
            }
            return inputEntity;
        } else {
            if (level != null) {
                if (outputEntity == null) {
                    outputEntity = (LivingEntity) entityType.create(level);
                    lastEntityCreationTime = currentTime;
                }
                if (currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL) {
                    if (!ModList.get().isLoaded("entity_model_features") && !ModList.get().isLoaded("optifine")) {
                        outputEntity = (LivingEntity) entityType.create(level);
                        lastEntityCreationTime = currentTime;
                    }
                }
            }
            if (outputEntity != null) {
                if (outputEntity instanceof TamableAnimal tamableAnimal) {
                    tamableAnimal.setTame(true);
                }
            }
            if (color != null) {
                if (outputEntity instanceof Sheep sheep) {
                    sheep.setColor(color);
                }
            }
            return outputEntity;
        }
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "transformation" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.TRANSFORMATION_PROVIDER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get();
    }

    public void setInputIngredient(Ingredient ingredient) {
        this.inputStack = ingredient;
    }

    public void setExtraInputIngredient(Ingredient ingredient) {
        this.extraInputStack = ingredient;
    }

    public void setInputSpawnEggs(Ingredient ingredient) {
        this.inputSpawnEgg = ingredient;
    }
    public void setOutputSpawnEggs(Ingredient ingredient) {
        this.outputSpawnEgg = ingredient;
    }

    public static class Serializer implements RecipeSerializer<TransformationRecipe> {

        @Override
        public @NotNull TransformationRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
            return (TransformationRecipe) readJsonContents(jsonPath, json);
        }

        @Override
        public @Nullable TransformationRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull TransformationRecipe transformationRecipe) {}

    }

    public static BaseRecipe readJsonContents (@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        String jsonModID = json.get("mod").getAsString();
        String modFolder = jsonPath.getNamespace();
        String fileName = jsonPath.getPath().substring(jsonPath.getPath().lastIndexOf('/') + 1);

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
        String outputEntity = "";
        if (json.has("output_entity")) {
            outputEntity = json.get("output_entity").getAsString();
            outputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(outputEntity));
        }

        Utils.addIngredients(json, inputIngredients, "inputs");
        Utils.addIngredients(json, extraInputIngredients, "extra_inputs");
        Utils.addIngredients(json, inputSpawnEggs, "input_spawn_eggs");
        Utils.addIngredients(json, outputSpawnEggs, "output_spawn_eggs");

        if (jsonPath.getNamespace().equals(CommonConstants.MOD_ID)) {
            modFolder = jsonPath.getPath().split("/")[1];
        }

        if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(jsonModID)) {
            return new TransformationRecipe(null, null, null, null, null, null, null, jsonModID, modFolder, fileName, null, null);
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
