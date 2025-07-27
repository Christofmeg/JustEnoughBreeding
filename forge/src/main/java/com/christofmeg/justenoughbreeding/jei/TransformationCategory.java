package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
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
public class TransformationCategory extends AbstractRecipeCategory<TransformationRecipe> implements IRecipeCategory<TransformationRecipe> {

    public static final RecipeType<TransformationRecipe> TYPE = new RecipeType<>(new ResourceLocation(CommonConstants.MOD_ID, "transformation"), TransformationRecipe.class);

    private final IDrawableStatic bigSlot;
    final int inputSlotItemX = 69;
    final int inputSlot2ItemY = 33;

    public TransformationCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.transformation"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 151 + 15, 91);
        bigSlot = helper.getOutputSlot();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(65, 74).setStandardSlotBackground().addIngredients(recipe.inputSpawnEgg);
        builder.addOutputSlot(85, 74).setStandardSlotBackground().addIngredients(recipe.outputSpawnEgg);
        IRecipeSlotBuilder inputStack = builder.addInputSlot(75, 22).setStandardSlotBackground().addIngredients(recipe.inputStack);
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        if (hasExtraInput) {
            inputStack.setPosition(65, 22);
            builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(85, 22);
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull TransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(72, 48);
    }

    @Override
    public void draw(@NotNull TransformationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.inputEntityType, stack, mouseX, recipe, 99, 0, true, recipe.inputColor);
        JEIUtils.drawMobSlot(105, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.outputEntityType, stack, mouseX, recipe, 61, 105, false, recipe.outputColor);
    }

}