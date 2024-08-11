package com.christofmeg.justenoughbreeding.recipe;

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

    public final EntityType<?> entityType;
    public final Ingredient breedingCatalyst;
    public final ItemStack spawnEgg;
    @Nullable
    public final Boolean needsToBeTamed;
    public final Ingredient resultItemStack;
    public final @Nullable Ingredient extraInputStack;
    @Nullable
    public final Boolean animalTrusting;
    public static final int ENTITY_CREATION_INTERVAL = 3000;

    public BreedingRecipe(EntityType<?> entityType, Ingredient breedingCatalyst, ItemStack spawnEgg, @Nullable Boolean needsToBeTamed, @Nullable Ingredient resultItemStack, @Nullable Ingredient extraInputStack, @Nullable Boolean animalTrusting) {
        this.entityType = entityType;
        this.breedingCatalyst = breedingCatalyst;
        this.spawnEgg = spawnEgg;
        this.needsToBeTamed = needsToBeTamed;
        this.resultItemStack = resultItemStack;
        this.extraInputStack = extraInputStack;
        this.animalTrusting = animalTrusting;
    }

    public LivingEntity doRendering() {
        long currentTime = System.currentTimeMillis();
        Level level = Minecraft.getInstance().level;

        if (level != null && (currentLivingEntity == null || currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL)) {
            currentLivingEntity = (LivingEntity) entityType.create(level);
            lastEntityCreationTime = currentTime;
        }

        if (currentLivingEntity != null) {
            if (currentLivingEntity instanceof TamableAnimal tamableAnimal) {
                tamableAnimal.setTame(true, true);
            }
        }

        return currentLivingEntity;
    }

}
