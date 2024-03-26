package com.christofmeg.justenoughbreeding.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BreedingRecipe {
    private LivingEntity currentLivingEntity = null;
    private long lastEntityCreationTime = 0;

    final EntityType<?> entityType;
    final Ingredient breedingCatalyst;
    final ItemStack spawnEgg;
    @Nullable
    final Boolean needsToBeTamed;
    final Ingredient resultItemStack;
    final @Nullable Ingredient extraInputStack;
    @Nullable
    final Boolean animalTrusting;

    public BreedingRecipe(EntityType<?> entityType, Ingredient breedingCatalyst, ItemStack spawnEgg, @Nullable Boolean needsToBeTamed, @Nullable Ingredient resultItemStack, @Nullable Ingredient extraInputStack, @Nullable Boolean animalTrusting) {
        this.entityType = entityType;
        this.breedingCatalyst = breedingCatalyst;
        this.spawnEgg = spawnEgg;
        this.needsToBeTamed = needsToBeTamed;
        this.resultItemStack = resultItemStack;
        this.extraInputStack = extraInputStack;
        this.animalTrusting = animalTrusting;
    }

    void doRendering(PoseStack stack, double mouseX) {
        long currentTime = System.currentTimeMillis();
        Level level = Minecraft.getInstance().level;

        if (level != null && (currentLivingEntity == null || currentTime - lastEntityCreationTime >= BreedingCategory.ENTITY_CREATION_INTERVAL)) {
            currentLivingEntity = (LivingEntity) entityType.create(level);
            lastEntityCreationTime = currentTime;
        }

        if (currentLivingEntity != null) {
            if (currentLivingEntity instanceof TamableAnimal tamableAnimal) {
                tamableAnimal.setTame(true);
            }
            BreedingCategory.renderEntity(stack, mouseX, currentLivingEntity);
        }
    }

}
