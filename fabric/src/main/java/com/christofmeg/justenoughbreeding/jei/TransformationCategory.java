package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.config.ModConfigManager;
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
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
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
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, TransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        Ingredient outputSpawnEggs = recipe.outputSpawnEggs() == null ? (JustEnoughBreeding.getSpawnEggItem(recipe.outputEntityType()).isPresent() ? Ingredient.of(JustEnoughBreeding.getSpawnEggItem(recipe.outputEntityType()).get().value()) : null): recipe.outputSpawnEggs();
        IRecipeSlotBuilder outputSlot = builder.addOutputSlot(85, 74).setStandardSlotBackground();
        if (recipe.outputs() != null && !recipe.outputs().isEmpty()) outputSlot.add(recipe.outputs());
        if (outputSpawnEggs != null && !outputSpawnEggs.isEmpty()) outputSlot.add(outputSpawnEggs);

        IRecipeSlotBuilder inputStack = builder.addInputSlot(75, 22).setStandardSlotBackground().add(recipe.inputs());
        boolean hasExtraInput = recipe.extraInputs()!= null && !recipe.extraInputs().isEmpty();
        if (recipe.inputEntityType() != null) {
            if (hasExtraInput) {
                inputStack.setPosition(65, 22);
                builder.addInputSlot(85, 22).setStandardSlotBackground().add(recipe.extraInputs());
            }
        } else {
            EntityType<?> inputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.parse(recipe.inputEntity()));
            Ingredient inputSpawnEggs = recipe.inputSpawnEggs() == null ? (JustEnoughBreeding.getSpawnEggItem(inputEntityType).isPresent() ? Ingredient.of(JustEnoughBreeding.getSpawnEggItem(inputEntityType).get().value()) : null): recipe.inputSpawnEggs();
            IRecipeSlotBuilder inputSpawnEggsSlot = builder.addInputSlot(65, 74).setStandardSlotBackground();
            if (inputSpawnEggs != null && !inputSpawnEggs.isEmpty()) inputSpawnEggsSlot.add(inputSpawnEggs);
            if (hasExtraInput) {
                inputSpawnEggsSlot.setPosition(4, 43);
                inputStack.setPosition(24, 43);
                builder.addInputSlot(44, 43).setStandardSlotBackground().add(recipe.extraInputs());
            } else {
                inputSpawnEggsSlot.setPosition(24, 43);
                inputStack.setPosition(44, 43);
            }
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull TransformationRecipe recipe, @NotNull IFocusGroup focuses) {
        if (recipe.inputEntityType() != null) {
            builder.addRecipeArrowWidget().setPosition(72, 48);
            if (ModConfigManager.areConfigButtonsEnabled()) {
                JEIUtils.addButton(builder, JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.tryParse(recipe.inputEntity())));
            }
        } else {
            builder.addRecipeArrowWidget().setPosition(72, 43);
        }
        if (ModConfigManager.areConfigButtonsEnabled()) {
            JEIUtils.addButton(builder, recipe.outputEntityType(), 105);
        }
    }

    @Override
    public void draw(@NotNull TransformationRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphicsExtractor stack, double mouseX, double mouseY) {
        if (recipe.inputEntityType() != null) {
            JEIUtils.drawMobSlot(0, 10, bigSlot, stack);
            JEIUtils.drawMobNameAndEntity(JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.tryParse(recipe.inputEntity())), stack, mouseX, recipe, 99, 0, true, CATEGORY_WIDTH);
        }
        JEIUtils.drawMobSlot(105, 10, bigSlot, stack);
        JEIUtils.drawMobNameAndEntity(recipe.outputEntityType(), stack, mouseX, recipe, 61, 105, false, CATEGORY_WIDTH);
    }

}