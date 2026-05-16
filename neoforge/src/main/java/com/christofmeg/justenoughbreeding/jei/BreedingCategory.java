package com.christofmeg.justenoughbreeding.jei;

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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class BreedingCategory extends AbstractRecipeCategory<BreedingRecipe> {

    public static final RecipeType<BreedingRecipe> TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath("justenoughbreeding", "breeding"), BreedingRecipe.class);
    private final IDrawableStatic bigSlot;
    private final int CATEGORY_WIDTH;

    public BreedingCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.breeding"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 166, 91);
        bigSlot = helper.getOutputSlot();
        this.CATEGORY_WIDTH = this.getWidth();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(149, 1).setStandardSlotBackground().addIngredients(recipe.spawnEggs());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEggs());
        IRecipeSlotBuilder inputSlot = builder.addInputSlot(69, 58).setStandardSlotBackground().addIngredients(recipe.inputs()).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = !recipe.extraInputs().isEmpty();
        boolean hasOutput = !recipe.outputs().isEmpty();
        if (hasOutput) {
            inputSlot.setPosition(74, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
            builder.addOutputSlot(130, 48).setOutputSlotBackground().addIngredients(recipe.outputs()).setPosition(72, 38, 78, 35, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
            if (hasExtraInput) {
                inputSlot.setPosition(74, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
                builder.addInputSlot(69, 33).setStandardSlotBackground().addIngredients(recipe.extraInputs()).setPosition(74, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
            }
        } else if (hasExtraInput) {
            inputSlot.setPosition(63, 10, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
            builder.addInputSlot(69, 33).setStandardSlotBackground().addIngredients(recipe.extraInputs()).setPosition(63, 29, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        boolean hasOutput = !recipe.outputs().isEmpty();
        if (hasOutput) {
            builder.addRecipeArrow().setPosition(70, 37, 78, 35, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
        JEIUtils.addButton(builder, recipe.entityType());
    }

    @Override
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, graphics);
        JEIUtils.drawMobNameAndEntity(recipe.entityType(), graphics, mouseX, recipe, CATEGORY_WIDTH);
    }

}