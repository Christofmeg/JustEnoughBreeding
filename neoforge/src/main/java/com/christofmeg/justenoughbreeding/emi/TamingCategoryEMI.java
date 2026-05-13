package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
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

public class TamingCategoryEMI extends AbstractRecipeCategoryEMI {

    private final TamingRecipe recipe;
    public static EmiRecipeCategory TYPE = new EmiRecipeCategory(
        ResourceLocation.parse(CommonConstants.MOD_ID + ":" + "taming"),
        EmiStack.of(Items.BONE), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected TamingCategoryEMI(Builder builder, TamingRecipe tamingRecipe) {
        super(TYPE, 168, 93, builder.id);
        this.recipe = tamingRecipe;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiIngredient.of(recipe.inputs()));
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(EmiIngredient.of(recipe.inputs()),
                EmiIngredient.of(recipe.extraInputs()),
                EmiIngredient.of(recipe.spawnEgg())
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        for (ItemStack item : recipe.spawnEgg().getItems()) {
            list.add(EmiStack.of(item));
        }
        return list;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(EmiIngredient.of(recipe.spawnEgg()), 149, 1);
        int inputX = 69 + 5;
        int inputY = 48;
        boolean hasExtraInput = !recipe.extraInputs().isEmpty();
        if (hasExtraInput) {
            widgets.addSlot(EmiIngredient.of(recipe.inputs()), inputX + 33, 38);
            widgets.addSlot(EmiIngredient.of(recipe.extraInputs()), inputX + 33, 57);
        } else {
            widgets.addSlot(EmiIngredient.of(recipe.inputs()), inputX  + 33, inputY);
        }
        EMIUtils.drawMobSlot(0, 10, widgets);
        EMIUtils.drawMobNameAndEntity(recipe.entityType(), widgets, recipe, getDisplayWidth());
        EMIUtils.addButton(widgets, recipe.entityType());
    }

    public static class Builder {
        private ResourceLocation id;
        private TamingRecipe recipe;

        private Builder() {}

        public EmiRecipe build() {
            return new TamingCategoryEMI(this, recipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder recipe(TamingRecipe recipe) {
            this.recipe = recipe;
            return this;
        }
    }

}