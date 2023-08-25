package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
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
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BreedingCategory implements IRecipeCategory<BreedingCategory.BreedingRecipe> {

    private static final int ENTITY_CREATION_INTERVAL = 3000;
    private static final int ENTITY_RENDER_DISTANCE = 15728880;

    private static LivingEntity currentLivingEntity = null;
    private static long lastEntityCreationTime = 0;

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
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {

        // Draw the recipe slots at specific positions
        slot.draw(stack, 148, 0);
        slot.draw(stack, breedableFoodSlotX - 1, breedableFoodSlotY - 1);

        // 2nd ingredient
        slot.draw(stack, 68, 38);

        // output slot
        outputSlot.draw(stack, 94, 43);
        mobRenderSlot.draw(stack, 0, 10);

        EntityType<?> entityType = recipe.entityType();
        if(entityType != null) {
            Minecraft instance = Minecraft.getInstance();
            Font font = instance.font;
            Component entityName = Component.translatable(entityType.getDescriptionId());

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

            Level level = instance.level;
            if (level != null) {

                LivingEntity livingEntity = (LivingEntity) entityType.create(level);
                long currentTime = System.currentTimeMillis();

                if (currentLivingEntity != null && currentLivingEntity.getType() != entityType) {
                    currentLivingEntity = (LivingEntity) entityType.create(level);
                }

                if (currentLivingEntity == null || currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL) {
                    currentLivingEntity = (LivingEntity) entityType.create(level);
                    lastEntityCreationTime = currentTime;
                }

                if (currentLivingEntity != null && currentLivingEntity != livingEntity) {
                    renderEntity(stack, mouseX, currentLivingEntity);
                }
            }

        }
    }

    private static void renderEntity(@NotNull PoseStack stack, double mouseX, LivingEntity currentLivingEntity) {
        // Set the desired position of the entity on the screen
        int entityPosX = 31;
        int entityPosY = 89;

        float yaw = (float) (60 - mouseX); // Calculate the yaw based on the mouse position

        stack.pushPose(); // Push the current pose onto the stack
        stack.translate((float) entityPosX, (float) entityPosY, 50f); // Translate the entity's position

        // Calculate the scaling factor based on the bounding box's largest dimension
        AABB boundingBox = currentLivingEntity.getBoundingBox();
        double largestDimension = Math.max(boundingBox.getXsize(), Math.max(boundingBox.getYsize(), boundingBox.getZsize()));

        float desiredWidth = 30.0F;
        float desiredHeight = 40.0F;

        // Calculate the scaling factors for width and height
        float scaleX = desiredWidth / (float) largestDimension;
        float scaleY = desiredHeight / (float) largestDimension;

        // Use the smaller of the two scaling factors to ensure the entity fits within the area
        float scalingFactor = Math.min(scaleX, scaleY);

        if (currentLivingEntity instanceof Frog) {
            scalingFactor = 50;
        }

        if (currentLivingEntity instanceof Axolotl || currentLivingEntity instanceof Cat ||
                currentLivingEntity instanceof Pig || currentLivingEntity instanceof Wolf) {
            scalingFactor = 25;
        }

        if (currentLivingEntity instanceof Ocelot || currentLivingEntity instanceof Fox
                || currentLivingEntity instanceof Turtle) {
            scalingFactor = 20;
        }

        if (currentLivingEntity instanceof Hoglin || currentLivingEntity instanceof Horse
                || currentLivingEntity instanceof Panda) {
            scalingFactor = 15;
        }

        stack.scale(scalingFactor, scalingFactor, scalingFactor); // Scale the entity to fit within the desired area
        stack.mulPose(Vector3f.ZP.rotationDegrees(180.0F)); // Rotate the entity to face a certain direction

        float yawRadians = -(yaw / 40.F) * 20.0F; // Calculate the yaw angle in radians for the entity's rotation

        // Apply the calculated yaw angle to the entity's rotation properties
        currentLivingEntity.yBodyRot = yawRadians;
        currentLivingEntity.setYRot(yawRadians);
        currentLivingEntity.yHeadRot = yawRadians;
        currentLivingEntity.yHeadRotO = yawRadians;

        stack.translate(0.0F, currentLivingEntity.getMyRidingOffset(), 0.0F); // Translate the entity vertically to adjust its position

        Minecraft instance = Minecraft.getInstance();
        EntityRenderDispatcher entityRenderDispatcher = instance.getEntityRenderDispatcher(); // Get the entity rendering dispatcher
        entityRenderDispatcher.overrideCameraOrientation(Quaternion.ONE); // Override the camera orientation for rendering
        entityRenderDispatcher.setRenderShadow(false); // Disable rendering shadows for the entity

        // Get the buffer source for rendering
        final MultiBufferSource.BufferSource bufferSource = instance.renderBuffers().bufferSource();

        // Render the currentLivingEntity using the entityRenderDispatcher
        entityRenderDispatcher.render(currentLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, stack, bufferSource, ENTITY_RENDER_DISTANCE);

        bufferSource.endBatch(); // End the rendering batch
        entityRenderDispatcher.setRenderShadow(true); // Re-enable rendering shadows

        stack.popPose(); // Pop the pose from the stack to revert transformations
    }

    public record BreedingRecipe(EntityType<?> entityType, Ingredient breedingCatalyst, ItemStack spawnEgg, @Nullable Boolean needsToBeTamed, Ingredient resultItemStack, @Nullable ItemStack extraInputStack) {
        //TODO fix flickering when multiple entities are rendered on the same page, due to GUI scale

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