package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;
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
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class TemperCategory extends AbstractRecipeCategory<TemperRecipe> implements IRecipeCategory<TemperRecipe> {

    public static final RecipeType<TemperRecipe> TYPE = new RecipeType<>(new ResourceLocation(CommonConstants.MOD_ID, "temper"), TemperRecipe.class);

    private final IDrawableStatic bigSlot;
    final int inputSlotItemX = 69;
    final int inputSlot1ItemY = 58;
    final int inputSlot2ItemY = 33;

    public TemperCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.temper"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 151 + 15, 91);
        bigSlot = helper.getOutputSlot();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TemperRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(134 + 15, 1).setStandardSlotBackground().addIngredients(recipe.spawnEgg);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEgg);
        builder.addInputSlot(inputSlotItemX, inputSlot1ItemY).setStandardSlotBackground().addIngredients(recipe.inputStack).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        if (hasExtraInput) {
            builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(63, 20 + 9, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void draw(@NotNull TemperRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.entityType, stack, mouseX, recipe);
    }

}