package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class AllayDuplicationCategoryEMI extends AbstractRecipeCategoryEMI {

    private final AllayDuplicationRecipe recipe;
    public static final EmiRecipeCategory TYPE = new EmiRecipeCategory(
            ResourceLocation.parse(CommonConstants.MOD_ID + ":" + "allay_duplication"),
            EmiStack.of(Items.AMETHYST_SHARD), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected AllayDuplicationCategoryEMI(Builder builder, AllayDuplicationRecipe allayDuplicationRecipe) {
        super(TYPE, 168, 93, builder.id);
        this.recipe = allayDuplicationRecipe;
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
                EmiIngredient.of(recipe.spawnEggs())
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        for (ItemStack item : recipe.spawnEggs().getItems()) {
            list.add(EmiStack.of(item));
        }
        return list;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(EmiIngredient.of(recipe.spawnEggs()), 149, 1);
        widgets.addSlot(EmiIngredient.of(recipe.inputs()), 107, 32);
        widgets.addSlot(EmiIngredient.of(Ingredient.of(ItemTags.CREEPER_DROP_MUSIC_DISCS)), 97, 52);
        widgets.addSlot(EmiIngredient.of(Ingredient.of(Items.JUKEBOX)), 117, 52);
        EMIUtils.drawMobSlot(0, 10, widgets);
        EMIUtils.drawMobNameAndEntity(recipe.entityType(), widgets, recipe, getDisplayWidth());
        EMIUtils.addButton(widgets, recipe.entityType());
    }

    public static class Builder {
        private ResourceLocation id;
        private AllayDuplicationRecipe recipe;

        private Builder() {}

        public EmiRecipe build() {
            return new AllayDuplicationCategoryEMI(this, recipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder recipe(AllayDuplicationRecipe recipe) {
            this.recipe = recipe;
            return this;
        }
    }

}