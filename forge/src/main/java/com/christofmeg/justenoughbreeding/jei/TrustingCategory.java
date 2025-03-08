package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class TrustingCategory extends AbstractRecipeCategory<TrustingRecipe> implements IRecipeCategory<TrustingRecipe> {

    public static final RecipeType<TrustingRecipe> TYPE = new RecipeType<>(new ResourceLocation(CommonConstants.MOD_ID, "trusting"), TrustingRecipe.class);

    private final IDrawableStatic bigSlot;
    final int inputSlotItemX = 69;
    final int inputSlot1ItemY = 58;
    final int inputSlot2ItemY = 33;

    public TrustingCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.trusting"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 151 + 15, 91);
        bigSlot = helper.getOutputSlot();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, TrustingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(134 + 15, 1).setStandardSlotBackground().addIngredients(recipe.spawnEgg);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEgg);
        IRecipeSlotBuilder inputSlot = builder.addInputSlot(inputSlotItemX, inputSlot1ItemY).setStandardSlotBackground().addIngredients(recipe.inputStack).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        inputSlot.setPosition(63, 20 - 10, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        if (hasExtraInput) {
            builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(63, 20 + 9, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void draw(@NotNull TrustingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
        int mobSlotX = 0;
        int mobSlotY = 10;

        bigSlot.draw(stack, mobSlotX, mobSlotY, 0, 1, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY, 0, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY, 0, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY, 0, 1, 22, 0);

        bigSlot.draw(stack, mobSlotX, mobSlotY + 24, 1, 1, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY + 24, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY + 24, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY + 24, 1, 1, 22, 0);

        bigSlot.draw(stack, mobSlotX, mobSlotY + 48, 1, 1, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY + 48, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY + 48, 1, 1, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY + 48, 1, 1, 22, 0);

        bigSlot.draw(stack, mobSlotX, mobSlotY + 55, 18, 0, 0, 1);
        bigSlot.draw(stack, mobSlotX + 18, mobSlotY + 55, 18, 0, 1, 1);
        bigSlot.draw(stack, mobSlotX + 32, mobSlotY + 55, 18, 0, 1, 1);
        bigSlot.draw(stack, mobSlotX + 35, mobSlotY + 55, 18, 0, 22, 0);

        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Font font = Minecraft.getInstance().font;
            Component entityName = Component.translatable(entityType.getDescriptionId());
            String entityNameString = entityName.getString(); // Convert Component to String

            int stringWidth = font.width(entityNameString); // Measure the width of the string in pixels
            int availableWidth = 148; // Initial available width in pixels
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / entityNameString.length();
                int maxCharacters = (int) (availableWidth / pixelWidthPerCharacter);
                entityNameString = entityNameString.substring(0, maxCharacters);
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                stack.drawString(font, abbreviatedEntityName, 0, 0, DyeColor.BLACK.getTextColor(), false);
            }

            LivingEntity currentLivingEntity = recipe.doRendering();
            if (currentLivingEntity != null) {
                Utils.renderEntity(stack.pose(), mouseX, currentLivingEntity);
            }
        }
    }

}