package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
public class BreedingCategoryEMI extends AbstractRecipeCategoryEMI {

    private final BreedingRecipe recipe;
    public static final EmiRecipeCategory TYPE = new EmiRecipeCategory(
            new ResourceLocation(CommonConstants.MOD_ID + ":" + "breeding"),
            EmiStack.of(Items.WHEAT), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected BreedingCategoryEMI(Builder builder, BreedingRecipe breedingRecipe) {
        super(TYPE, 168, 93, builder.id);
        this.recipe = breedingRecipe;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiIngredient.of(recipe.inputStack));
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(EmiIngredient.of(recipe.inputStack),
                EmiIngredient.of(recipe.extraInputStack),
                EmiIngredient.of(recipe.spawnEgg)
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        if (recipe.resultItemStack != null) {
            for (ItemStack item : recipe.resultItemStack.getItems()) {
                list.add(EmiStack.of(item));
            }
            for (ItemStack item : recipe.spawnEgg.getItems()) {
                list.add(EmiStack.of(item));
            }
        }
        return list;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(EmiIngredient.of(recipe.spawnEgg), 149, 1);

        int inputX = 69 + 5;
        int inputY = 48;
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        boolean hasOutput = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        if (hasExtraInput && hasOutput) {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX, 38);
            widgets.addSlot(EmiIngredient.of(recipe.extraInputStack), inputX, 57);
        } else if (hasExtraInput) {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX + 33, 38);
            widgets.addSlot(EmiIngredient.of(recipe.extraInputStack), inputX + 33, 57);
        } else if (!hasOutput) {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX  + 33, inputY);
        } else {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX, inputY);
        }
        if (hasOutput) {
            widgets.addTexture(EmiTexture.EMPTY_ARROW, 94 + 4, 48);
            widgets.addSlot(EmiIngredient.of(recipe.resultItemStack), 126 + 4, 44).large(true).recipeContext(this);
        }

        EMIUtils.drawMobSlot(0, 10, widgets);
        EMIUtils.drawMobNameAndEntity(recipe.entityType, widgets, recipe);
    }

    public static class Builder {
        private ResourceLocation id;
        private BreedingRecipe recipe;

        private Builder() {}

        public EmiRecipe build() {
            return new BreedingCategoryEMI(this, recipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder recipe(BreedingRecipe recipe) {
            this.recipe = recipe;
            return this;
        }
    }

}