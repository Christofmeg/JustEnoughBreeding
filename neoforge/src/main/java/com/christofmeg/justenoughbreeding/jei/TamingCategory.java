package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.config.ModConfigManager;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class TamingCategory extends AbstractRecipeCategory<TamingRecipe> {

    public static final IRecipeType<TamingRecipe> TYPE = IRecipeType.create(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming"), TamingRecipe.class);
    private final IDrawableStatic bigSlot;
    private final int CATEGORY_WIDTH;

    public TamingCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.taming"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 166, 91);
        bigSlot = helper.getOutputSlot();
        this.CATEGORY_WIDTH = this.getWidth();
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, TamingRecipe recipe, @NotNull IFocusGroup focuses) {
        Ingredient spawnEggs = recipe.spawnEggs() == null ? (JustEnoughBreeding.getSpawnEggItem(recipe.entityType()).isPresent() ? Ingredient.of(JustEnoughBreeding.getSpawnEggItem(recipe.entityType()).get().value()) : null): recipe.spawnEggs();
        if (spawnEggs != null && !spawnEggs.isEmpty()) builder.addInputSlot(149, 1).setStandardSlotBackground().add(spawnEggs); else builder.addInputSlot(149, 1).setStandardSlotBackground();
        if (spawnEggs != null && !spawnEggs.isEmpty()) builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).add(spawnEggs);
        builder.addInputSlot(69, 58).setStandardSlotBackground().add(recipe.inputs()).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = recipe.extraInputs()!= null && !recipe.extraInputs().isEmpty();
        if (hasExtraInput) {
            builder.addInputSlot(69, 33).setStandardSlotBackground().add(recipe.extraInputs()).setPosition(63, 29, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull TamingRecipe recipe, @NotNull IFocusGroup focuses) {
        if (ModConfigManager.areConfigButtonsEnabled()) {
            JEIUtils.addButton(builder, recipe.entityType());
        }
    }

    @Override
    public void draw(@NotNull TamingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphicsExtractor stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.entityType(), stack, mouseX, recipe, CATEGORY_WIDTH);
    }

}