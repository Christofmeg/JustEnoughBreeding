package com.christofmeg.justenoughbreeding.recipe;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;

public class ForgeRecipe extends BaseRecipe {

    private LivingEntity currentLivingEntity = null;
    private long lastEntityCreationTime = 0;
    public static final int ENTITY_CREATION_INTERVAL = 3000;

    public LivingEntity doRendering(EntityType<?> entityType) {
        long currentTime = System.currentTimeMillis();
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            if (currentLivingEntity == null) {
                currentLivingEntity = (LivingEntity) entityType.create(level);
                lastEntityCreationTime = currentTime;
            }
            if (currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL) {
                if (!ModList.get().isLoaded("entity_model_features") && !ModList.get().isLoaded("optifine")) {
                    currentLivingEntity = (LivingEntity) entityType.create(level);
                    lastEntityCreationTime = currentTime;
                }
            }
        }
        if (currentLivingEntity != null) {
            if (currentLivingEntity instanceof TamableAnimal tamableAnimal) {
                tamableAnimal.setTame(true);
            }
        }
        return currentLivingEntity;
    }

}
