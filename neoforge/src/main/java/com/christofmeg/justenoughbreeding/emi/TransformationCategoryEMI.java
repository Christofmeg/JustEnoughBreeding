package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
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
public class TransformationCategoryEMI extends AbstractRecipeCategoryEMI {

    private final TransformationRecipe recipe;
    public static final EmiRecipeCategory TYPE = new EmiRecipeCategory(
            new ResourceLocation(CommonConstants.MOD_ID + ":" + "transformation"),
            EmiStack.of(Items.GOLDEN_CARROT), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected TransformationCategoryEMI(Builder builder, TransformationRecipe recipe) {
        super(TYPE, 168, 93, builder.id);
        this.recipe = recipe;
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
                EmiIngredient.of(recipe.inputSpawnEgg)
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        for (ItemStack item : recipe.outputSpawnEgg.getItems()) {
            list.add(EmiStack.of(item));
        }
        return list;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(EmiIngredient.of(recipe.inputSpawnEgg), 65, 74);
        widgets.addSlot(EmiIngredient.of(recipe.outputSpawnEgg), 85, 74);

        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        widgets.addSlot(EmiIngredient.of(recipe.inputStack), hasExtraInput ? 65 : 75, 22);
        if (hasExtraInput) {
            widgets.addSlot(EmiIngredient.of(recipe.extraInputStack), 85, 22);
        }

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 72, 49);
        EMIUtils.drawMobSlot(0, 10, widgets);
        EMIUtils.drawMobNameAndEntity(recipe.inputEntityType, widgets, recipe, 99, 0, true, recipe.inputColor);
        EMIUtils.drawMobSlot(105, 10, widgets);
        EMIUtils.drawMobNameAndEntity(recipe.outputEntityType, widgets, recipe, 61, 105, false, recipe.outputColor);
    }

    public static class Builder {
        private ResourceLocation id;
        private TransformationRecipe recipe;

        private Builder() {}

        public EmiRecipe build() {
            return new TransformationCategoryEMI(this, recipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder recipe(TransformationRecipe recipe) {
            this.recipe = recipe;
            return this;
        }
    }

}