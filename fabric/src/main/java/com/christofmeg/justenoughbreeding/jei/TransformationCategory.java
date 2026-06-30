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
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class TransformationCategory extends AbstractRecipeCategory<TransformationRecipe> {

    public static final IRecipeType<TransformationRecipe> TYPE = IRecipeType.create(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation"), TransformationRecipe.class);
    private final IDrawableStatic bigSlot;
    private final int CATEGORY_WIDTH;

    public TransformationCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.transformation"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 166, 91);
        bigSlot = helper.getOutputSlot();
        this.CATEGORY_WIDTH = this.getWidth();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(65, 74).setStandardSlotBackground().add(recipe.inputSpawnEggs());
        builder.addOutputSlot(85, 74).setStandardSlotBackground().add(recipe.outputSpawnEggs());
        IRecipeSlotBuilder inputStack = builder.addInputSlot(75, 22).setStandardSlotBackground().add(recipe.inputs());
        boolean hasExtraInput = recipe.extraInputs()!= null && !recipe.extraInputs().isEmpty();
        if (hasExtraInput) {
            inputStack.setPosition(65, 22);
            builder.addInputSlot(69, 33).setStandardSlotBackground().add(recipe.extraInputs()).setPosition(85, 22);
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull TransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addRecipeArrow().setPosition(72, 48);
        JEIUtils.addButton(builder, recipe.inputEntityType());
        JEIUtils.addButton(builder, recipe.outputEntityType(), 105);
    }

    @Override
    public void draw(@NotNull TransformationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.inputEntityType(), stack, mouseX, recipe, 99, 0, true, CATEGORY_WIDTH);
        JEIUtils.drawMobSlot(105, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.outputEntityType(), stack, mouseX, recipe, 61, 105, false, CATEGORY_WIDTH);
    }

}