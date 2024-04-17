package com.christofmeg.justenoughbreeding.rei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class REIUtils {

    public static void registerMobBreedingRecipes(DisplayRegistry registration) {
        List<String> sortedMobNames = new ArrayList<>(CommonConstants.breedingIngredients.keySet());
        Collections.sort(sortedMobNames);

        for (String mobName : sortedMobNames) {
            if (mobName != null) {
                if (CommonConstants.breedingIngredients != null) {
                    String mobIngredients = CommonConstants.breedingIngredients.get(mobName);
                    String mobResultItem = CommonConstants.breedingEggResult.get(mobName) != null ? CommonConstants.breedingEggResult.get(mobName) : "";
                    if (CommonConstants.sharedGetSpawnEggFromEntity != null) {
                        if (CommonConstants.sharedGetSpawnEggFromEntity.get(mobName) != null) {
                            String mobSpawnEgg = CommonConstants.sharedGetSpawnEggFromEntity.get(mobName);
                            int mobMinResultCount = CommonConstants.breedingEggResultMinAmount.get(mobName) != null ? CommonConstants.breedingEggResultMinAmount.get(mobName) : 1;
                            int mobMaxResultCount = CommonConstants.breedingEggResultMaxAmount.get(mobName) != null ? CommonConstants.breedingEggResultMaxAmount.get(mobName) : 1;

                            if (mobIngredients != null && mobSpawnEgg != null) {
                                Ingredient combinedIngredient = Utils.createCombinedIngredient(mobIngredients);
                                List<Ingredient> combinedResultIngredient = Utils.createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
                                Item spawnEggItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEgg.trim()));

                                if (spawnEggItem instanceof SpawnEggItem spawnEgg) {
                                    EntityType<?> entityType = spawnEgg.getType(null);
                                    Boolean needsToBeTamed = CommonConstants.breedingNeedsToBeTamed.get(mobName);
                                    Boolean animalTrusting = CommonConstants.breedingNeedsToBeTrusting.get(mobName);

                                    Ingredient combinedExtraIngredient = null;
                                    if (CommonConstants.breedingExtraIngredients != null) {
                                        if (CommonConstants.breedingExtraIngredients.get(mobName) != null) {
                                            String mobExtraIngredients = CommonConstants.breedingExtraIngredients.get(mobName);
                                            if (mobExtraIngredients != null) {
                                                combinedExtraIngredient = Utils.createCombinedIngredient(mobExtraIngredients);
                                            }
                                        }
                                    }

                                    BreedingRecipe breedingRecipe = Utils.createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
                                    registration.add(new BreedingDisplay(breedingRecipe));
                                }
                            }
                        }
                    }
                    if (CommonConstants.breedingGetSpawnEggFromItem != null && CommonConstants.breedingGetMobFromString != null) {
                        if (CommonConstants.breedingGetSpawnEggFromItem.get(mobName) != null && CommonConstants.breedingGetMobFromString.get(mobName) != null) {
                            String mobSpawnEggItem = CommonConstants.breedingGetSpawnEggFromItem.get(mobName);
                            String mobEntityName = CommonConstants.breedingGetMobFromString.get(mobName);
                            int mobMinResultCount = CommonConstants.breedingEggResultMinAmount.get(mobName) != null ? CommonConstants.breedingEggResultMinAmount.get(mobName) : 1;
                            int mobMaxResultCount = CommonConstants.breedingEggResultMaxAmount.get(mobName) != null ? CommonConstants.breedingEggResultMaxAmount.get(mobName) : 1;

                            if (mobIngredients != null && mobSpawnEggItem != null && mobEntityName != null) {
                                Ingredient combinedIngredient = Utils.createCombinedIngredient(mobIngredients);
                                List<Ingredient> combinedResultIngredient = Utils.createCombinedResultIngredients(mobResultItem, mobMinResultCount, mobMaxResultCount);
                                Item spawnEggItem = JustEnoughBreeding.getItemFromLoaderRegistries(new ResourceLocation(mobSpawnEggItem.trim()));
                                EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(mobEntityName.trim()));
                                Boolean needsToBeTamed = CommonConstants.breedingNeedsToBeTamed.get(mobName);
                                Boolean animalTrusting = CommonConstants.breedingNeedsToBeTrusting.get(mobName);

                                Ingredient combinedExtraIngredient = null;
                                if (CommonConstants.breedingExtraIngredients != null) {
                                    if (CommonConstants.breedingExtraIngredients.get(mobName) != null) {
                                        String mobExtraIngredients = CommonConstants.breedingExtraIngredients.get(mobName);
                                        if (mobExtraIngredients != null) {
                                            combinedExtraIngredient = Utils.createCombinedIngredient(mobExtraIngredients);
                                        }
                                    }
                                }

                                BreedingRecipe breedingRecipe = Utils.createBreedingRecipe(entityType, combinedIngredient, spawnEggItem, needsToBeTamed, combinedResultIngredient, animalTrusting, combinedExtraIngredient);
                                registration.add(new BreedingDisplay(breedingRecipe));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void renderEntity(PoseStack stack, double mouseX, LivingEntity currentLivingEntity) {
        // Set the desired position of the entity on the screen
        int entityPosX = 31;
        int entityPosY = 89;
        int ENTITY_RENDER_DISTANCE = 15728880;

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
        stack.mulPose(Vector3f.XP.rotationDegrees(180.0F)); // Rotate the entity 180 degrees on the X-axis

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

}
