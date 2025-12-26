package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Rect;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class BreedingCategory extends AbstractRecipeCategory<BreedingRecipe> implements IRecipeCategory<BreedingRecipe> {

    public static final IRecipeType<BreedingRecipe> TYPE = IRecipeType.create(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding"), BreedingRecipe.class);

    private final IDrawableStatic bigSlot;
    final int inputSlotItemX = 69;
    final int inputSlot1ItemY = 58;
    final int outputSlotItemX = 130;
    final int outputSlotItemY = 48;
    final int inputSlot2ItemY = 33;
    final int CATEGORY_WIDTH;

    public BreedingCategory(IJeiHelpers helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.breeding"), helper.getGuiHelper().createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 151 + 15, 91);
        bigSlot = helper.getGuiHelper().getOutputSlot();
        this.CATEGORY_WIDTH = this.getWidth();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(134 + 15, 1).setStandardSlotBackground().add(recipe.spawnEgg);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).add(recipe.spawnEgg);
        IRecipeSlotBuilder inputSlot = builder.addInputSlot(inputSlotItemX, inputSlot1ItemY).setStandardSlotBackground().add(recipe.breedingCatalyst).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);

        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        boolean hasOutput = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        if (hasOutput) {
            inputSlot.setPosition(69 + 5, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
            builder.addOutputSlot(outputSlotItemX, outputSlotItemY).setOutputSlotBackground().add(recipe.resultItemStack).setPosition(69 + 3, 38, 78, 35, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
            if (hasExtraInput) {
                inputSlot.setPosition(69 + 5, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
                builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().add(recipe.extraInputStack).setPosition(69 + 5, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
            }
        }
        else if (hasExtraInput) {
            inputSlot.setPosition(63, 20 - 10, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
            builder.addInputSlot(inputSlotItemX, inputSlot2ItemY).setStandardSlotBackground().add(recipe.extraInputStack).setPosition(63, 20 + 9, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }

    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        if (recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty()) {
            builder.addRecipeArrow().setPosition(69, 38, 78, 35, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
        builder.addWidget(new IRecipeWidget() {
            private static final int WIDGET_SIZE = 59;
            final Rect rect = new Rect((CATEGORY_WIDTH - WIDGET_SIZE) / 2, 10, WIDGET_SIZE, WIDGET_SIZE);
            final ScreenPosition position = new ScreenPosition(0, 0);

            @Override
            public void drawWidget(@NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
                LivingEntity currentLivingEntity = recipe.doRendering();
                if (currentLivingEntity != null) {
                    CommonUtils.renderEntity(currentLivingEntity, rect, guiGraphics, (int) mouseX);
                }
            }

            @NotNull
            @Override
            public ScreenPosition getPosition() {
                return position;
            }
        });
    }

    @Override
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {
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
            if (recipe.needsToBeTamed != null) {
                Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                entityNameString += " (" + tamed.getString() + ")";
            } else if (recipe.animalTrusting != null) {
                Component trusting = Component.translatable("translation.justenoughbreeding.trusting");
                entityNameString += " (" + trusting.getString() + ")";
            }

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
        }
    }

}