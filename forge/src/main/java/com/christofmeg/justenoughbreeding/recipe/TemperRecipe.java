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

@SuppressWarnings("removal")
public class TemperRecipe extends BaseRecipe {

    public final EntityType<?> entityType;
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient spawnEgg;
    public @NotNull Ingredient extraInputStack;
    public final String jsonModID;
    public final String jsonAnimalID;
    public final String modFolder;
    public final String fileName;

    public TemperRecipe(EntityType<?> entityType,
                        Ingredient inputStack,
                        Ingredient spawnEgg,
                        Ingredient extraInputStack,
                        String jsonModID,
                        String jsonAnimalID,
                        String modFolder,
                        String fileName) {
        this.entityType = Objects.requireNonNull(entityType, "entityType");
        this.inputStack = CommonUtils.safe(inputStack);
        this.spawnEgg = CommonUtils.safe(spawnEgg);
        this.extraInputStack = CommonUtils.safe(extraInputStack);
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.jsonAnimalID = Objects.requireNonNull(jsonAnimalID, "jsonAnimalID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");
        validateRequired();
    }

    private void validateRequired() {
        if (inputStack.isEmpty()) throw new IllegalStateException("TemperRecipe " + getId() + " has empty input ingredient.");
        if (spawnEgg.isEmpty()) throw new IllegalStateException("TemperRecipe " + getId() + " has empty spawnEgg ingredient.");
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "temper" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID + "/" + this.jsonAnimalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.TEMPER_PROVIDER_SERIALIZER.get(); }

    @Override
    public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get(); }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = CommonUtils.safe(ingredient); }
    public void setExtraInputIngredient(Ingredient ingredient) { this.extraInputStack = CommonUtils.safe(ingredient); }
    public void setSpawnEggs(Ingredient ingredient) { this.spawnEgg = CommonUtils.safe(ingredient); }

}
