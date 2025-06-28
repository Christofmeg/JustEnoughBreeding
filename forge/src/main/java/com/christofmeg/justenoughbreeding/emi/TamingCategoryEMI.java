package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TamingCategoryEMI implements EmiRecipe {
    private final ResourceLocation id;
    private final TamingRecipe recipe;
    public static final EmiTexture TOP = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 25, 1);
    public static final EmiTexture CORNER = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 0, 1, 1);
    public static final EmiTexture LEFT = new EmiTexture(EmiRenderHelper.WIDGETS, 18, 0, 1, 25);
    public static final EmiTexture RIGHT = new EmiTexture(EmiRenderHelper.WIDGETS, 43, 1, 1, 25);
    public static final EmiTexture BOTTOM = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 25, 25, 1);
    public static final EmiTexture BACKGROUND = new EmiTexture(EmiRenderHelper.WIDGETS, 19, 1, 24, 24);

    public static EmiRecipeCategory TYPE = new EmiRecipeCategory(
        new ResourceLocation(CommonConstants.MOD_ID + ":" + "taming"),
        EmiStack.of(Items.BONE), EMIPlugin.simplifiedRenderer(), EmiRecipeSorting.none());

    protected TamingCategoryEMI(Builder builder, TamingRecipe tamingRecipe) {
        this.id = builder.id;
        this.recipe = tamingRecipe;
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
        return List.of(EmiIngredient.of(recipe.inputStack));
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(EmiIngredient.of(recipe.inputStack),
                EmiIngredient.of(recipe.extraInputStack),
                EmiIngredient.of(recipe.spawnEgg)
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        List<EmiStack> list = new ArrayList<>();
        for (ItemStack item : recipe.spawnEgg.getItems()) {
            list.add(EmiStack.of(item));
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
        widgets.addSlot(EmiIngredient.of(recipe.spawnEgg), 149, 1);

        int inputX = 69 + 5;
        int inputY = 58 - 10;
        int extraY = inputY - 19;
        boolean hasExtraInput = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        if (hasExtraInput) {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX + 33, inputY + 9);
            widgets.addSlot(EmiIngredient.of(recipe.extraInputStack), inputX + 33, extraY + 9);
        } else {
            widgets.addSlot(EmiIngredient.of(recipe.inputStack), inputX  + 33, inputY);
        }

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
            public void render(@NotNull GuiGraphics stack, int mouseX, int mouseY, float delta) {
                LivingEntity currentLivingEntity = recipe.doRendering(recipe.entityType);
                if (currentLivingEntity != null) {
                    Utils.renderEntity(stack.pose(), mouseX, currentLivingEntity);
                }
            }
        });

        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Component entityName = Component.translatable(entityType.getDescriptionId());
            String entityNameString = entityName.getString(); // Convert Component to String
            int stringWidth = Minecraft.getInstance().font.width(entityNameString); // Measure the width of the string in pixels
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
        private TamingRecipe tamingRecipe;

        private Builder() {
        }

        public EmiRecipe build() {
            return new TamingCategoryEMI(this, tamingRecipe);
        }

        public Builder id(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder tamingRecipe(TamingRecipe recipe) {
            this.tamingRecipe = recipe;
            return this;
        }

    }

}