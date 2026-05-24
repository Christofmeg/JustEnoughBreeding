package com.christofmeg.justenoughbreeding.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractRecipeCategoryEMI implements EmiRecipe {

    private final EmiRecipeCategory TYPE;
    private final int displayWidth;
    private final int displayHeight;
    private final ResourceLocation id;

    public AbstractRecipeCategoryEMI(EmiRecipeCategory type, int displayWidth, int displayHeight, ResourceLocation id) {
        this.TYPE = type;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.id = id;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return TYPE;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public int getDisplayWidth() {
        return displayWidth;
    }

    @Override
    public int getDisplayHeight() {
        return displayHeight;
    }

}
