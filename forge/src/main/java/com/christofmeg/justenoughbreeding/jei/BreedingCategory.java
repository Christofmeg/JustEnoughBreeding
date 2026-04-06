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

@SuppressWarnings("removal")
public class BreedingCategory extends AbstractRecipeCategory<BreedingRecipe> {

    public static final RecipeType<BreedingRecipe> TYPE = new RecipeType<>(new ResourceLocation("justenoughbreeding", "breeding"), BreedingRecipe.class);
    private final IDrawableStatic bigSlot;
    private final int CATEGORY_WIDTH;

    public BreedingCategory(IGuiHelper helper, ItemLike itemStack) {
        super(TYPE, Component.translatable("translation.justenoughbreeding.breeding"), helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack)), 166, 91);
        bigSlot = helper.getOutputSlot();
        this.CATEGORY_WIDTH = this.getWidth();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addInputSlot(149, 1).setStandardSlotBackground().addIngredients(recipe.spawnEgg);
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredients(recipe.spawnEgg);
        IRecipeSlotBuilder inputSlot = builder.addInputSlot(69, 58).setStandardSlotBackground().addIngredients(recipe.inputStack).setPosition(63, 20, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        boolean hasOutput = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        if (hasOutput) {
            inputSlot.setPosition(74, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
            builder.addOutputSlot(130, 48).setOutputSlotBackground().addIngredients(recipe.resultItemStack).setPosition(72, 38, 78, 35, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
            if (hasExtraInput) {
                inputSlot.setPosition(74, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM);
                builder.addInputSlot(69, 33).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(74, 38, 78, 35, HorizontalAlignment.LEFT, VerticalAlignment.TOP);
            }
        } else if (hasExtraInput) {
            inputSlot.setPosition(63, 10, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
            builder.addInputSlot(69, 33).setStandardSlotBackground().addIngredients(recipe.extraInputStack).setPosition(63, 29, 103, 71, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }
    }

    @Override
    public void createRecipeExtras(@NotNull IRecipeExtrasBuilder builder, @NotNull BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        boolean hasOutput = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        if (hasOutput) {
            builder.addRecipeArrow().setPosition(70, 37, 78, 35, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        }

        JEIBookmarkButton button = JEIBookmarkButton.create(48, 13, 10, 10);
        builder.addWidget(button);

        /*
        JeiToggleButtonWidget toggle = new JeiToggleButtonWidget(48, 13, 10, 10);
        builder.addWidget(toggle);

        builder.addInputHandler(new IJeiInputHandler() {

            private boolean clickHandled = false;

            @Override
            public @NotNull ScreenRectangle getArea() {
                return new ScreenRectangle(48, 13, 10, 10);
            }

            @Override
            public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {

                // Ignore simulated inputs
                if (input.isSimulate()) {
                    return false;
                }

                // Only react once per click
                if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_LEFT) {

                    if (!clickHandled) {
                        clickHandled = true;
                        toggle.toggle();

                        Minecraft.getInstance().player.sendSystemMessage(
                                Component.literal("JEI button clicked!")
                        );
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void handleMouseMoved(double mouseX, double mouseY) {
                // Reset when mouse moves (i.e. next click cycle)
                clickHandled = false;
            }
        });
        for (int i = 0; i < 4; i++) {
            int x = 48 + (i * 12);
            int y = 28;

            JeiChildButtonWidget child = new JeiChildButtonWidget(x, y, 10, 10, toggle, Component.literal("Option " + (i + 1)));
            builder.addWidget(child);

            int finalI = i;
            builder.addInputHandler(new IJeiInputHandler() {
                @Override
                public @NotNull ScreenRectangle getArea() {
                    return new ScreenRectangle(x, y, 10, 10);
                }

                @Override
                public boolean handleInput(double mouseX, double mouseY, @NotNull IJeiUserInput input) {
                    if (toggle.isToggled()) return false;
                    if (input.isSimulate()) return false;

                    if (input.getKey().getValue() == InputConstants.MOUSE_BUTTON_LEFT) {
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Clicked option " + (finalI + 1)));
                        return true;
                    }
                    return false;
                }
            });
        }*/

    }

    @Override
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        JEIUtils.drawMobSlot(0, 10, bigSlot, graphics);
        JEIUtils.drawMobNameAndEntity(recipe.entityType, graphics, mouseX, recipe, CATEGORY_WIDTH);
    }

}