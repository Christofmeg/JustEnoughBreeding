package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BreedingCategory implements IRecipeCategory<BreedingCategory.BreedingRecipe> {

    public static final RecipeType<BreedingRecipe> TYPE = new RecipeType<>(
            new ResourceLocation(CommonConstants.MOD_ID, "breeding"), BreedingRecipe.class);

    public static final ResourceLocation slotVanilla = new ResourceLocation("jei",
            "textures/gui/slot.png");

    public static final ResourceLocation breedingSlot = new ResourceLocation("justenoughbreeding",
            "textures/gui/breeding.png");

    public static final ResourceLocation eggSlot = new ResourceLocation("jei",
            "textures/gui/gui_vanilla.png");

    public static IDrawable background;
    public static IDrawable icon;
    public static IDrawable slot;
    public static IDrawable mobRenderSlot;
    public static IDrawable outputSlot;

    private final int breedableFoodSlotX = 69; //The hover slot
    private final int breedableFoodSlotY = 58; //The hover slot

    public BreedingCategory(IGuiHelper helper, ItemLike itemStack) {
        background = helper.createBlankDrawable(166, 91);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack));
        slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();
        mobRenderSlot = helper.drawableBuilder(breedingSlot, 1, 13, 61, 81).setTextureSize(256,256).build();
        outputSlot = helper.drawableBuilder(eggSlot, 25, 224, 57, 26).setTextureSize(256,256).build();
    }

    @Override
    public @NotNull RecipeType<BreedingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("translation.justenoughbreeding.breeding");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 149, 1).addItemStack((recipe.spawnEgg()));
        builder.addSlot(RecipeIngredientRole.INPUT, breedableFoodSlotX, breedableFoodSlotY).addIngredients((recipe.breedingCatalyst()));
        if(recipe.resultItemStack() != null) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 48).addIngredients(recipe.resultItemStack());
        }
        if(recipe.extraInputStack() != null) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 69, 39).addItemStack(recipe.extraInputStack());
        }
    }

    @Override
    @SuppressWarnings("deprecated")
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {

        // Draw the recipe slots at specific positions
        slot.draw(stack, 148, 0);
        slot.draw(stack, breedableFoodSlotX - 1, breedableFoodSlotY - 1);

        // 2nd ingredient
        slot.draw(stack, 68, 38);

        // output slot
        outputSlot.draw(stack, 94, 43);

        mobRenderSlot.draw(stack, 0, 10);

        if(recipe.entityType() != null) {
            Font font = Minecraft.getInstance().font;
            Component entityName = Component.translatable(recipe.entityType().getDescriptionId());

            String entityNameString = entityName.getString(); // Convert Component to String
            if(recipe.needsToBeTamed() != null) {
                entityNameString += " (Tamed)";
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
                font.draw(stack, abbreviatedEntityName, 0.0F, 0.0F, DyeColor.BLACK.getTextColor());
            }

            if (Minecraft.getInstance().level != null) {
                // Create a LivingEntity from the recipe's entity type
                LivingEntity livingEntity = (LivingEntity) recipe.entityType().create(Minecraft.getInstance().level);

                if (livingEntity != null) {
                    int entityPosX = 31; // Adjust the X position as desired
                    int entityPosY = 89; // Adjust the Y position as desired
                    float targetSize = 30.0F; // Adjust the desired size of the entities
                    float entityYaw = 0.0F; // Update the yaw (vertical rotation) angle based on the cursor's position
                    float entityPitch = 180.0F; // Set the pitch (horizontal rotation) angle to 0.0F
                    float partialTicks = 0; // Set to 0 to ensure rendering is done without interpolation or animation

                    stack.pushPose(); // Pushes the current transformation matrix onto the matrix stack

                    // Translate the matrix to the center position specified by entityPosX and entityPosY
                    stack.translate(entityPosX, entityPosY, 0);

                    // Calculate the scaling factor based on the longest dimension of the entity's bounding box
                    AABB boundingBox = livingEntity.getBoundingBox();
                    double longestDimension = Math.max(boundingBox.getXsize(), Math.max(boundingBox.getYsize(), boundingBox.getZsize()));
                    float scalingFactor = targetSize / (float) longestDimension;
                    stack.scale(scalingFactor, scalingFactor, scalingFactor); // Apply the scaling transformation

                    // Calculate the rotation angle based on the cursor's position
                    double dx = mouseX - entityPosX;
                    double dz = mouseY - entityPosY;
                    double angle = Math.atan2(dz, dx) * (180.0 / Math.PI) - 90.0;

                    stack.mulPose(Vector3f.YP.rotationDegrees(entityYaw)); // Apply the yaw rotation
                    stack.mulPose(Vector3f.XP.rotationDegrees(entityPitch)); // Apply the pitch rotation
                    stack.mulPose(Vector3f.YP.rotationDegrees((float) angle)); // Applies a rotation transformation around the Y-axis by the specified angle in degrees.

                    EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher(); // Get the entity render dispatcher from the Minecraft instance.
                    entityRenderDispatcher.setRenderShadow(false); // Disable rendering of shadows for the entity.
                    MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource(); // Get the buffer source for rendering.

                    // Render the livingEntity using the entityRenderDispatcher
                    RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, stack, bufferSource, 15728880));

                    bufferSource.endBatch(); // End the batch for the buffer source.
                    entityRenderDispatcher.setRenderShadow(true); // Enable rendering of shadows for the entity.
                    stack.popPose(); // Pop the current pose from the stack.
                    RenderSystem.applyModelViewMatrix(); // Apply the model view matrix.
                }
            }
        }
    }

    public record BreedingRecipe(EntityType<?> entityType, Ingredient breedingCatalyst, ItemStack spawnEgg, @Nullable Boolean needsToBeTamed, Ingredient resultItemStack, @Nullable ItemStack extraInputStack) {
    }

    //TODO https://www.curseforge.com/minecraft/mc-mods/deeperdarker
    //TODO https://www.curseforge.com/minecraft/mc-mods/spirit
    //TODO https://www.curseforge.com/minecraft/mc-mods/betteranimalsplus
    //TODO https://www.curseforge.com/minecraft/mc-mods/upgrade-aquatic
    //TODO https://www.curseforge.com/minecraft/mc-mods/galosphere
    //TODO https://www.curseforge.com/minecraft/mc-mods/earth-mobs
    //TODO https://www.curseforge.com/minecraft/mc-mods/buzzier-bees
    //TODO https://www.curseforge.com/minecraft/mc-mods/environmental
    //TODO https://www.curseforge.com/minecraft/mc-mods/autumnity
    //TODO https://www.curseforge.com/minecraft/mc-mods/exotic-birds
    //TODO https://www.curseforge.com/minecraft/mc-mods/creatures-and-beasts
    //TODO https://www.curseforge.com/minecraft/mc-mods/extended-mushrooms
    //TODO https://www.curseforge.com/minecraft/mc-mods/more-babies
    //TODO https://www.curseforge.com/minecraft/mc-mods/goodall
    //TODO https://www.curseforge.com/minecraft/mc-mods/energeticsheep
    //TODO https://www.curseforge.com/minecraft/mc-mods/feywild
    //TODO https://www.curseforge.com/minecraft/mc-mods/earth2java
    //TODO https://www.curseforge.com/minecraft/mc-mods/unusual-end
    //TODO https://www.curseforge.com/minecraft/mc-mods/vanilla-degus
    //TODO https://www.curseforge.com/minecraft/mc-mods/fins-and-tails
    //TODO https://www.curseforge.com/minecraft/mc-mods/realistic-horse-genetics
    //TODO https://www.curseforge.com/minecraft/mc-mods/critters-and-companions
    //TODO https://www.curseforge.com/minecraft/mc-mods/friends-and-foes-forge
    //TODO https://www.curseforge.com/minecraft/mc-mods/the-undergarden
    //TODO https://www.curseforge.com/minecraft/mc-mods/productivebees
    //TODO https://www.curseforge.com/minecraft/mc-mods/roost-ultimate
    //TODO https://www.curseforge.com/minecraft/mc-mods/ender-zoology
    //TODO https://www.curseforge.com/minecraft/mc-mods/primal-reservation








}
