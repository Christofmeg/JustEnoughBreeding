package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import mezz.jei.api.MethodsReturnNonnullByDefault;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BreedingCategory implements IRecipeCategory<BreedingCategory.BreedingRecipe> {

    static final int ENTITY_CREATION_INTERVAL = 3000;
    static final int ENTITY_RENDER_DISTANCE = 15728880;

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

    final int inputSlotX = 68;
    final int inputSlot1Y = 51;
    final int inputSlot2Y = 32;

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
            List<ItemStack> extraInputList = Collections.singletonList(recipe.extraInputStack);
            inputList.add(extraInputList);
        }

        // Add the matching stacks from the breeding catalyst ingredient
        ingredients.setInputLists(VanillaTypes.ITEM, inputList);

        // Add the matching stacks from the result item stack ingredient
        ingredients.setOutputLists(VanillaTypes.ITEM, outputList);

    }

    @Override
    public void setRecipe(IRecipeLayout builder, BreedingRecipe recipe, @Nonnull IIngredients ingredients) {
        builder.getItemStacks().init(0, false, 133, 0);
        builder.getItemStacks().set(0, recipe.spawnEgg);

        builder.getItemStacks().init(1, true, inputSlotX, inputSlot1Y);
        builder.getItemStacks().set(1, List.of(recipe.breedingCatalyst.getItems()));

        final int outputSlotItemX = 129;
        final int outputSlotItemY = 42;

        if (recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty()) {
            builder.getItemStacks().init(2, false, outputSlotItemX, outputSlotItemY);
            builder.getItemStacks().set(2, List.of(recipe.resultItemStack.getItems()));
        }
        if (recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty()) {
            builder.getItemStacks().init(3, true, inputSlotX, inputSlot2Y);
            builder.getItemStacks().set(3, recipe.extraInputStack);
        }
    }

    @Override
    public void draw(BreedingRecipe recipe, PoseStack stack, double mouseX, double mouseY) {

        // Spawn Egg Slot
        slot.draw(stack, 133, 0);

        // Input Slot
        slot.draw(stack, inputSlotX, inputSlot1Y);

        // Extra Input Slot
        slot.draw(stack, inputSlotX, inputSlot2Y);

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

            recipe.doRendering(stack, mouseX);

        }
    }

    @Override
    public ResourceLocation getUid() {
        return TYPE;
    }

    @Override
    public Class<? extends BreedingRecipe> getRecipeClass() {
        return BreedingCategory.BreedingRecipe.class;
    }

    private static void renderEntity(PoseStack stack, double mouseX, LivingEntity currentLivingEntity) {
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

    public static class BreedingRecipe {
        private LivingEntity currentLivingEntity = null;
        private long lastEntityCreationTime = 0;

        private final EntityType<?> entityType;
        private final Ingredient breedingCatalyst;
        private final ItemStack spawnEgg;

        private final Boolean needsToBeTamed;
        private final Ingredient resultItemStack;
        private final ItemStack extraInputStack;

        public BreedingRecipe(EntityType<?> entityType, Ingredient breedingCatalyst, ItemStack spawnEgg, @Nullable Boolean needsToBeTamed, @Nullable Ingredient resultItemStack, @Nullable ItemStack extraInputStack) {
            this.entityType = entityType;
            this.breedingCatalyst = breedingCatalyst;
            this.spawnEgg = spawnEgg;
            this.needsToBeTamed = needsToBeTamed;
            this.resultItemStack = resultItemStack;
            this.extraInputStack = extraInputStack;
        }

        private void doRendering(PoseStack stack, double mouseX) {
            long currentTime = System.currentTimeMillis();
            Level level = Minecraft.getInstance().level;

            if (level != null && (currentLivingEntity == null || currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL)) {
                currentLivingEntity = (LivingEntity) entityType.create(level);
                lastEntityCreationTime = currentTime;
            }

            if (currentLivingEntity != null) {
                renderEntity(stack, mouseX, currentLivingEntity);
            }
        }
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