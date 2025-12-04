package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("removal")
public class TransformationRecipe extends BaseRecipe {

    public final EntityType<?> inputEntityType;
    public @NotNull Ingredient inputStack;
    public @NotNull Ingredient inputSpawnEgg;
    public @NotNull Ingredient extraInputStack; // EMPTY if absent
    public final EntityType<?> outputEntityType;
    public @NotNull Ingredient outputSpawnEgg;
    public final @Nullable Boolean needsToBeTamed;
    public final String jsonModID;
    public final String modFolder;
    public final String fileName;
    public final @Nullable DyeColor inputColor;
    public final @Nullable DyeColor outputColor;
    public final @Nullable CompoundTag outputEntityNbt;

    public TransformationRecipe(EntityType<?> inputEntityType, Ingredient inputStack, Ingredient inputSpawnEgg, Ingredient extraInputStack, EntityType<?> outputEntityType, Ingredient outputSpawnEgg, @Nullable Boolean needsToBeTamed, String jsonModID, String modFolder, String fileName, @Nullable DyeColor inputColor, @Nullable DyeColor outputColor, @Nullable CompoundTag outputEntityNbt) {
        this.inputEntityType = inputEntityType;
        this.inputStack = CommonUtils.safe(inputStack);
        this.inputSpawnEgg = CommonUtils.safe(inputSpawnEgg);
        this.extraInputStack = CommonUtils.safe(extraInputStack);
        this.outputEntityType = outputEntityType;
        this.outputSpawnEgg = CommonUtils.safe(outputSpawnEgg);
        this.needsToBeTamed = needsToBeTamed;
        this.jsonModID = Objects.requireNonNull(jsonModID, "jsonModID");
        this.modFolder = Objects.requireNonNull(modFolder, "modFolder");
        this.fileName = Objects.requireNonNull(fileName, "fileName");
        this.inputColor = inputColor;
        this.outputColor = outputColor;
        this.outputEntityNbt = outputEntityNbt;
    }

    @Override public @NotNull ResourceLocation getId() { return new ResourceLocation(CommonConstants.MOD_ID, "transformation" + "/" + this.modFolder + "/" + this.fileName + "/" + this.jsonModID); }
    @Override public @NotNull RecipeSerializer<?> getSerializer() { return JustEnoughBreeding.TRANSFORMATION_PROVIDER_SERIALIZER.get(); }
    @Override public @NotNull RecipeType<?> getType() { return JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get(); }

    public void setInputIngredient(Ingredient ingredient) { this.inputStack = CommonUtils.safe(ingredient); }
    public void setExtraInputIngredient(Ingredient ingredient) { this.extraInputStack = CommonUtils.safe(ingredient); }
    public void setInputSpawnEggs(Ingredient ingredient) { this.inputSpawnEgg = CommonUtils.safe(ingredient); }
    public void setOutputSpawnEggs(Ingredient ingredient) { this.outputSpawnEgg = CommonUtils.safe(ingredient); }

    public static class DummyRecipe extends TransformationRecipe {
        public DummyRecipe(String jsonModID, String  modFolder, String fileName) {
            super(null,null,null,null,null,null,null, jsonModID, modFolder, fileName, null, null, null);
        }
    }

}