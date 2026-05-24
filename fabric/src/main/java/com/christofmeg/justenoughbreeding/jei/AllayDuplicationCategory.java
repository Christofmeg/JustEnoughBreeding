package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class AllayDuplicationCategory extends AbstractRecipeCategory<AllayDuplicationRecipe> {

    public static final RecipeType<AllayDuplicationRecipe> TYPE = new RecipeType<>(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication"), AllayDuplicationRecipe.class);
    private final IDrawableStatic bigSlot;
    private final int CATEGORY_WIDTH;

    public AllayDuplicationCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.allay_duplication"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 166, 91);
        bigSlot = helper.getOutputSlot();
        this.CATEGORY_WIDTH = this.getWidth();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AllayDuplicationRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(149, 1).setStandardSlotBackground().addIngredients(recipe.spawnEggs());
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEggs());
        builder.addInputSlot(107, 32).setStandardSlotBackground().addIngredients(recipe.inputs());
        builder.addInputSlot(97, 52).setStandardSlotBackground().addIngredients(Ingredient.of(ItemTags.CREEPER_DROP_MUSIC_DISCS));
        builder.addInputSlot(117, 52).setStandardSlotBackground().addIngredients(Ingredient.of(Items.JUKEBOX));
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull AllayDuplicationRecipe recipe, @NotNull IFocusGroup focuses) {
        JEIUtils.addButton(builder, recipe.entityType());
    }

    @Override
    public void draw(@NotNull AllayDuplicationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.entityType(), stack, mouseX, recipe, 99, 0, CATEGORY_WIDTH);
    }

}