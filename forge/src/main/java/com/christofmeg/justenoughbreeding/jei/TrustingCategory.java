package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class TrustingCategory extends AbstractRecipeCategory<TrustingRecipe> {

    public static final RecipeType<TrustingRecipe> TYPE = new RecipeType<>(new ResourceLocation("justenoughbreeding", "trusting"), TrustingRecipe.class);
    private final IDrawableStatic bigSlot;

    public TrustingCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.trusting"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 166, 91);
        bigSlot = helper.getOutputSlot();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TrustingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(149, 1).setStandardSlotBackground().addIngredients(recipe.spawnEgg);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEgg);
        builder.addInputSlot(69, 58).setStandardSlotBackground().addIngredients(recipe.inputStack).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        if (hasExtraInput) {
            builder.addInputSlot(69, 33).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(63, 29, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void draw(@NotNull TrustingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.entityType, stack, mouseX, recipe);
    }

}