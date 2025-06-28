package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class BreedingCategory extends AbstractRecipeCategory<BreedingRecipe> implements IRecipeCategory<BreedingRecipe> {

    public static final RecipeType<BreedingRecipe> TYPE = new RecipeType<>(new ResourceLocation(CommonConstants.MOD_ID, "breeding"), BreedingRecipe.class);

    private final IDrawableStatic bigSlot;
    final int inputSlotItemX = 69;
    final int inputSlot1ItemY = 58;
    final int outputSlotItemX = 130;
    final int outputSlotItemY = 48;
    final int inputSlot2ItemY = 33;

    public BreedingCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.breeding"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 151 + 15, 91);
        bigSlot = helper.getOutputSlot();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(134 + 15, 1).setStandardSlotBackground().addIngredients(recipe.spawnEgg);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEgg);
        IRecipeSlotBuilder inputSlot = builder.addInputSlot(inputSlotItemX, inputSlot1ItemY).setStandardSlotBackground().addIngredients(recipe.inputStack).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        boolean hasOutput = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        if (hasOutput) {
            inputSlot.setPosition(69 + 5, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
            builder.addOutputSlot(outputSlotItemX, outputSlotItemY).setOutputSlotBackground().addIngredients(recipe.resultItemStack).setPosition(69 + 3, 38, 78, 35, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
            if (hasExtraInput) {
                inputSlot.setPosition(69 + 5, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
                builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(69 + 5, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
            }
        }
        else if (hasExtraInput) {
            inputSlot.setPosition(63, 20 - 10, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
            builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(63, 20 + 9, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        boolean hasOutput = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        if (hasOutput) {
            builder.addRecipeArrow().setPosition(69 + 1, 38 - 1, 78, 35, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.entityType, stack, mouseX, recipe);
    }

}