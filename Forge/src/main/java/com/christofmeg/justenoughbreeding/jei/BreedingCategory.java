package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.MethodsReturnNonnullByDefault;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BreedingCategory implements IRecipeCategory<BreedingRecipe> {

    public static final ResourceLocation TYPE = new ResourceLocation(CommonConstants.MOD_ID, "breeding");

    final ResourceLocation slotVanilla = new ResourceLocation("jei",
            "textures/gui/slot.png");

    final ResourceLocation guiVanilla = new ResourceLocation("jei",
            "textures/gui/gui_vanilla.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable outputSlot;
    private final IDrawable mobRenderSlotTop;
    private final IDrawable mobRenderSlotBottom;
    private final IDrawable mobRenderSlotLeft;
    private final IDrawable mobRenderSlotRight;
    private final IDrawable mobRenderSlotTopCorner;
    private final IDrawable mobRenderSlotTopCenter;

    final int inputSlotItemX = 69;
    final int inputSlotFrameX = 68;
    final int inputSlot1ItemY = 52;
    final int inputSlot1FrameY = 51;
    final int inputSlot2FrameY = 32;

    final int outputSlotFrameX = 94;
    final int outputSlotFrameY = 38;


    public BreedingCategory(IGuiHelper helper, ItemLike itemStack) {
        background = helper.createBlankDrawable(151, 91);
        icon = helper.createDrawableIngredient(new ItemStack(itemStack));
        slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();
        outputSlot = helper.drawableBuilder(guiVanilla, 25, 224, 57, 26).setTextureSize(256,256).build();
        mobRenderSlotTop = helper.drawableBuilder(guiVanilla, 56, 128, 25, 1).setTextureSize(256, 256).build();
        mobRenderSlotBottom = helper.drawableBuilder(guiVanilla, 57, 153, 25, 1).setTextureSize(256, 256).build();
        mobRenderSlotLeft = helper.drawableBuilder(guiVanilla, 56, 129, 1, 24).setTextureSize(256, 256).build();
        mobRenderSlotRight = helper.drawableBuilder(guiVanilla, 81, 129, 1, 24).setTextureSize(256, 256).build();
        mobRenderSlotTopCorner = helper.drawableBuilder(guiVanilla, 81, 128, 1, 1).setTextureSize(256, 256).build();
        mobRenderSlotTopCenter = helper.drawableBuilder(guiVanilla, 57, 129, 24, 24).setTextureSize(256, 256).build();
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("translation.justenoughbreeding.breeding");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public ResourceLocation getUid() {
        return TYPE;
    }

    @Override
    public Class<? extends BreedingRecipe> getRecipeClass() {
        return BreedingRecipe.class;
    }

    @Override
    public void setIngredients(BreedingRecipe recipe, IIngredients ingredients) {
        List<ItemStack> spawnEggList = Collections.singletonList(recipe.spawnEgg);

        // Get the list of matching stacks from the breeding catalyst ingredient
        List<ItemStack> breedingCatalystStacks = Arrays.stream(recipe.breedingCatalyst.getItems()).toList();

        List<List<ItemStack>> inputList = new ArrayList<>();
        inputList.add(spawnEggList);
        inputList.add(breedingCatalystStacks);

        List<List<ItemStack>> outputList = new ArrayList<>();
        outputList.add(spawnEggList);

        // Check if there is a result item stack
        if (recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty()) {
            List<ItemStack> resultItemStacks = Arrays.stream(recipe.resultItemStack.getItems()).toList();
            outputList.add(resultItemStacks);
        }

        if (recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty()) {
            List<ItemStack> extraInputList = new ArrayList<>();
            Collections.addAll(extraInputList, recipe.extraInputStack.getItems());
            inputList.add(extraInputList);
        }

        // Add the matching stacks from the breeding catalyst ingredient
        ingredients.setInputLists(VanillaTypes.ITEM, inputList);

        // Add the matching stacks from the result item stack ingredient
        ingredients.setOutputLists(VanillaTypes.ITEM, outputList);

    }

    @Override
    public void setRecipe(IRecipeLayout builder, BreedingRecipe recipe, IIngredients ingredients) {
        builder.getItemStacks().init(0, false, 133, 0);
        builder.getItemStacks().set(0, recipe.spawnEgg);

        builder.getItemStacks().init(1, true, inputSlotItemX, inputSlot1ItemY);
        builder.getItemStacks().set(1, List.of(recipe.breedingCatalyst.getItems()));

        final int outputSlotItemX = 129;
        final int outputSlotItemY = 42;
        final int inputSlot2ItemY = 32;

        if (recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty()) {
            builder.getItemStacks().init(2, false, outputSlotItemX, outputSlotItemY);
            builder.getItemStacks().set(2, List.of(recipe.resultItemStack.getItems()));
        }
        if (recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty()) {
            builder.getItemStacks().init(3, true, 68, inputSlot2ItemY);
            List<ItemStack> extraInputList = new ArrayList<>();
            Collections.addAll(extraInputList, recipe.extraInputStack.getItems());
            builder.getItemStacks().set(3, extraInputList);
        }
    }

    @Override
    public void draw(BreedingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {

        // Spawn Egg Slot
        slot.draw(stack, 133, 0);

        // Input Slot
        slot.draw(stack, inputSlotFrameX, inputSlot1FrameY);

        // Extra Input Slot
        slot.draw(stack, inputSlotFrameX, inputSlot2FrameY);

        // Output Slot
        outputSlot.draw(stack, outputSlotFrameX, outputSlotFrameY);

        mobRenderSlotTop.draw(stack, 0, 10);
        mobRenderSlotTop.draw(stack, 25, 10);
        mobRenderSlotTop.draw(stack, 35, 10);

        mobRenderSlotTopCorner.draw(stack, 60, 10);
        mobRenderSlotTopCorner.draw(stack, 0, 90);

        mobRenderSlotBottom.draw(stack, 1, 90);
        mobRenderSlotBottom.draw(stack, 26, 90);
        mobRenderSlotBottom.draw(stack, 36, 90);

        mobRenderSlotLeft.draw(stack, 0, 11);
        mobRenderSlotLeft.draw(stack, 0, 35);
        mobRenderSlotLeft.draw(stack, 0, 59);
        mobRenderSlotLeft.draw(stack, 0, 66);

        mobRenderSlotRight.draw(stack, 60, 11);
        mobRenderSlotRight.draw(stack, 60, 35);
        mobRenderSlotRight.draw(stack, 60, 59);
        mobRenderSlotRight.draw(stack, 60, 66);

        mobRenderSlotTopCenter.draw(stack, 1, 11);
        mobRenderSlotTopCenter.draw(stack, 25, 11);
        mobRenderSlotTopCenter.draw(stack, 36, 11);
        mobRenderSlotTopCenter.draw(stack, 1, 35);
        mobRenderSlotTopCenter.draw(stack, 25, 35);
        mobRenderSlotTopCenter.draw(stack, 36, 35);
        mobRenderSlotTopCenter.draw(stack, 1, 59);
        mobRenderSlotTopCenter.draw(stack, 25, 59);
        mobRenderSlotTopCenter.draw(stack, 36, 59);
        mobRenderSlotTopCenter.draw(stack, 1, 66);
        mobRenderSlotTopCenter.draw(stack, 25, 66);
        mobRenderSlotTopCenter.draw(stack, 36, 66);

        EntityType<?> entityType = recipe.entityType;
        if(entityType != null) {
            Minecraft instance = Minecraft.getInstance();
            Font font = instance.font;
            Component entityName = new TranslatableComponent(entityType.getDescriptionId());

            String entityNameString = entityName.getString(); // Convert Component to String
            if(recipe.needsToBeTamed != null) {
                Component tamed = new TranslatableComponent("translation.justenoughbreeding.tamed");
                entityNameString += " (" + tamed.getString() + ")";
            } else if(recipe.animalTrusting != null) {
                Component trusting = new TranslatableComponent("translation.justenoughbreeding.trusting");
                entityNameString += " (" + trusting.getString() + ")";
            }

            int stringWidth = font.width(entityNameString); // Measure the width of the string in pixels

            int availableWidth = 154; // Initial available width in pixels
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / entityNameString.length();
                int maxCharacters = (int) (availableWidth / pixelWidthPerCharacter);
                entityNameString = entityNameString.substring(0, maxCharacters);
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                font.draw(stack, abbreviatedEntityName, 0, 0, DyeColor.BLACK.getTextColor());
            }

            LivingEntity currentLivingEntity = recipe.doRendering();
            if (currentLivingEntity != null) {
                Utils.renderEntity(stack, mouseX, currentLivingEntity);
            }

        }
    }

}