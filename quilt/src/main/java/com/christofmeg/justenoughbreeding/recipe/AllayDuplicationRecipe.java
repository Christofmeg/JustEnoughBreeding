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

import java.util.Objects;

public class AllayDuplicationRecipe extends BaseRecipe {

    public final EntityType<?> entityType;
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient spawnEgg;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public AllayDuplicationRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, String jsonModID, String jsonAnimalID, String modFolder, String fileName) {
        this.entityType = Objects.requireNonNull(entityType, "entityType");
        this.inputStack = CommonUtils.safe(inputStack);
        this.spawnEgg = CommonUtils.safe(spawnEgg);
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.jsonAnimalID = Objects.requireNonNull(jsonAnimalID, "jsonAnimalID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");
        validateRequired();
    }

    public void validateRequired() {
        if (entityType == null) {
            throw new IllegalStateException("AllayDuplicationRecipe " + getId() + " has null entityType");
        }

        if (inputStack == null || spawnEgg == null) {
            throw new IllegalStateException("AllayDuplicationRecipe " + getId() + " has null ingredients");
        }

        if (inputStack == Ingredient.EMPTY) {
            throw new IllegalStateException("AllayDuplicationRecipe " + getId() + " has completely missing input ingredient");
        }

        if (spawnEgg == Ingredient.EMPTY) {
            throw new IllegalStateException("AllayDuplicationRecipe " + getId() + " has completely missing spawn egg ingredient");
        }
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "allay_duplication" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_SERIALIZER; }

    @Override
    public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE; }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = CommonUtils.safe(ingredient); }
    public void setSpawnEggs(Ingredient ingredient) { this.spawnEgg = CommonUtils.safe(ingredient); }

}