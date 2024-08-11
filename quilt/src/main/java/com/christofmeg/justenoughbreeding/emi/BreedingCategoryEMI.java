package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiRecipeSorting;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BreedingCategoryEMI implements EmiRecipe {
    private final ResourceLocation id;
    private final BreedingRecipe recipe;
    public static final EmiTexture TOP = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 25, 1);
    public static final EmiTexture CORNER = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 0, 1, 1);
    public static final EmiTexture LEFT = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 1, 25);
    public static final EmiTexture RIGHT = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 1, 1, 25);
    public static final EmiTexture BOTTOM = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 25, 25, 1);
    public static final EmiTexture BACKGROUND = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 1, 24, 24);

    public static EmiRecipeCategory TYPE = new EmiRecipeCategory(
        ResourceLocation.parse(CommonConstants.MOD_ID + ":" + "breeding"),
        EmiStack.of(Items.WHEAT), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected BreedingCategoryEMI(Builder builder, BreedingRecipe breedingRecipe) {
        this.id = builder.id;
        this.recipe = breedingRecipe;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return TYPE;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiIngredient.of(recipe.breedingCatalyst));
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(EmiIngredient.of(recipe.breedingCatalyst),
                EmiIngredient.of(recipe.extraInputStack),
                EmiIngredient.of(Ingredient.of(recipe.spawnEgg))
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        if (recipe.resultItemStack != null) {
            for (ItemStack item : recipe.resultItemStack.getItems()) {
                list.add(EmiStack.of(item));
            }
            list.add(EmiStack.of(recipe.spawnEgg));
        }
        return list;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public int getDisplayWidth() {
        return 151 + 17;
    }

    @Override
    public int getDisplayHeight() {
        return 91 + 2;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 94, 48);
        widgets.addSlot(EmiStack.of(recipe.spawnEgg), 149, 1);
        widgets.addSlot(EmiIngredient.of(recipe.breedingCatalyst), 69, 58);
        widgets.addSlot(EmiIngredient.of(recipe.extraInputStack), 69, 39);
        widgets.addSlot(EmiIngredient.of(recipe.resultItemStack), 126, 44).large(true).recipeContext(this);

        widgets.addTexture(TOP, 1, 11);
        widgets.addTexture(TOP, 26, 11);
        widgets.addTexture(TOP, 36, 11);
        widgets.addTexture(CORNER, 61, 11);

        widgets.addTexture(LEFT, 1, 12);
        widgets.addTexture(LEFT, 1, 37);
        widgets.addTexture(LEFT, 1, 62);
        widgets.addTexture(LEFT, 1, 66);

        widgets.addTexture(RIGHT, 61, 12);
        widgets.addTexture(RIGHT, 61, 37);
        widgets.addTexture(RIGHT, 61, 62);
        widgets.addTexture(RIGHT, 61, 66);

        widgets.addTexture(BACKGROUND, 2, 12);
        widgets.addTexture(BACKGROUND, 26, 12);
        widgets.addTexture(BACKGROUND, 37, 12);
        widgets.addTexture(BACKGROUND, 2, 12 + 24);
        widgets.addTexture(BACKGROUND, 26, 12 + 24);
        widgets.addTexture(BACKGROUND, 37, 12 + 24);
        widgets.addTexture(BACKGROUND, 2, 12 + 48);
        widgets.addTexture(BACKGROUND, 26, 12 + 48);
        widgets.addTexture(BACKGROUND, 37, 12 + 48);
        widgets.addTexture(BACKGROUND, 2, 12 + 55);
        widgets.addTexture(BACKGROUND, 26, 12 + 55);
        widgets.addTexture(BACKGROUND, 37, 12 + 55);

        widgets.addTexture(CORNER, 1, 91);
        widgets.addTexture(BOTTOM, 2, 91);
        widgets.addTexture(BOTTOM, 27, 91);
        widgets.addTexture(BOTTOM, 37, 91);

        widgets.add(new Widget() {
            @Override
            public Bounds getBounds() {
                return new Bounds(0, 0, 60, 80);
            }

            @Override
            public void render(GuiGraphics stack, int mouseX, int mouseY, float delta) {
                LivingEntity currentLivingEntity = recipe.doRendering();
                if (currentLivingEntity != null) {
                    Utils.renderEntity(stack.pose(), mouseX, currentLivingEntity);
                }
            }
        });

        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Minecraft instance = Minecraft.getInstance();
            Font font = instance.font;
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

            int availableWidth = 154; // Initial available width in pixels
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / entityNameString.length();
                int maxCharacters = (int) (availableWidth / pixelWidthPerCharacter);
                entityNameString = entityNameString.substring(0, maxCharacters);
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                widgets.addText(EmiPort.ordered(abbreviatedEntityName), 1, 1, -1, true);

            }
        }

    }

    public static class Builder {
        private ResourceLocation id = null;
        private BreedingRecipe breedingRecipe;

        private Builder() {
        }

        public EmiRecipe build() {
            return new BreedingCategoryEMI(this, breedingRecipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder breedingRecipe(BreedingRecipe recipe) {
            this.breedingRecipe = recipe;
            return this;
        }

    }

}