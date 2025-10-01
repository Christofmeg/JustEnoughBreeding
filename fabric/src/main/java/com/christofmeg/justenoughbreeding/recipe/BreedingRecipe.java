package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BreedingRecipe extends BaseRecipe {

    public final EntityType<?> entityType;
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient spawnEgg;
    public final @Nullable Boolean needsToBeTamed;
    public @NotNull Ingredient resultItemStack;
    public @NotNull Ingredient extraInputStack;
    public final @Nullable Boolean animalTrusting;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public BreedingRecipe(EntityType<?> entityType, @Nullable Ingredient inputStack, @Nullable Ingredient spawnEgg, @Nullable Boolean needsToBeTamed, @Nullable Ingredient resultItemStack, @Nullable Ingredient extraInputStack, @Nullable Boolean animalTrusting, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = entityType;
        this.inputStack = CommonUtils.safe(inputStack);
        this.spawnEgg = CommonUtils.safe(spawnEgg);
        this.needsToBeTamed = needsToBeTamed;
        this.resultItemStack = CommonUtils.safe(resultItemStack);
        this.extraInputStack = CommonUtils.safe(extraInputStack);
        this.animalTrusting = animalTrusting;
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.jsonAnimalID = Objects.requireNonNull(jsonAnimalID, "jsonAnimalID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");
    }

    @Override public @NotNull ResourceLocation getId() { return new ResourceLocation(CommonConstants.MOD_ID, "breeding" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID); }
    @Override public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.BREEDING_PROVIDER_SERIALIZER; }
    @Override public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.BREEDING_PROVIDER_TYPE; }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = CommonUtils.safe(ingredient); }
    public void setExtraInputIngredient(Ingredient ingredient) { this.extraInputStack = CommonUtils.safe(ingredient); }
    public void setOutputIngredient(Ingredient ingredient) { this.resultItemStack = CommonUtils.safe(ingredient); }
    public void setSpawnEggs(Ingredient ingredient) { this.spawnEgg = CommonUtils.safe(ingredient); }

    public static class DummyRecipe extends BreedingRecipe {
        public DummyRecipe(String jsonModID, String jsonAnimalID, String  modFolder, String fileName) {
            super(null, null, null, null, null, null, null, jsonModID, jsonAnimalID, modFolder, fileName);
        }
    }
}