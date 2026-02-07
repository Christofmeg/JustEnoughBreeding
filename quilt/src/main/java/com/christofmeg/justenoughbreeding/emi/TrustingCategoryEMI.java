package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class TrustingCategoryEMI extends AbstractRecipeCategoryEMI {

    private final TrustingRecipe recipe;
    public static EmiRecipeCategory TYPE = new EmiRecipeCategory(
        new ResourceLocation(CommonConstants.MOD_ID + ":" + "trusting"),
        EmiStack.of(Items.SWEET_BERRIES), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected TrustingCategoryEMI(Builder builder, TrustingRecipe trustingRecipe) {
        super(TYPE, 168, 93, builder.id);
        this.recipe = trustingRecipe;
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
        for (ItemStack item : recipe.spawnEgg.getItems()) {
            list.add(EmiStack.of(item));
        }
        return list;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(EmiIngredient.of(recipe.spawnEgg), 149, 1);

        int inputX = 69 + 5;
        int inputY = 48;
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        if (hasExtraInput) {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX + 33, 38);
            widgets.addSlot(EmiIngredient.of(recipe.extraInputStack), inputX + 33, 57);
        } else {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX  + 33, inputY);
        }

        EMIUtils.drawMobSlot(0, 10, widgets);
        EMIUtils.drawMobNameAndEntity(recipe.entityType, widgets, recipe, getDisplayWidth());
    }

    public static class Builder {
        private ResourceLocation id;
        private TrustingRecipe recipe;

        private Builder() {}

        public EmiRecipe build() {
            return new TrustingCategoryEMI(this, recipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder recipe(TrustingRecipe recipe) {
            this.recipe = recipe;
            return this;
        }
    }

}