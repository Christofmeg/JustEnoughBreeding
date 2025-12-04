package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ClientUtils {

    public static final int ENTITY_CREATION_INTERVAL = 3000;
    private static final Map<String, LivingEntity> ENTITY_CACHE = new HashMap<>();
    private static final Map<String, Long> CREATION_TIMES = new HashMap<>();

    public static LivingEntity doRendering(EntityType<?> entityType, boolean input, DyeColor color) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return null;

        String key = CommonUtils.makeKey(entityType, input, color);
        long currentTime = System.currentTimeMillis();

        LivingEntity entity = ENTITY_CACHE.get(key);
        long lastTime = CREATION_TIMES.getOrDefault(key, 0L);

        boolean refreshAllowed =
                !JustEnoughBreeding.isModLoaded("entity_model_features") &&
                        !JustEnoughBreeding.isModLoaded("optifine");

        if (entity == null) {
            entity = createEntity(entityType, level, color);
            ENTITY_CACHE.put(key, entity);
            CREATION_TIMES.put(key, currentTime);
        } else if (refreshAllowed && (currentTime - lastTime >= ENTITY_CREATION_INTERVAL)) {
            entity = createEntity(entityType, level, color);
            ENTITY_CACHE.put(key, entity);
            CREATION_TIMES.put(key, currentTime);
        }

        return entity;
    }

    private static LivingEntity createEntity(EntityType<?> entityType, Level level, DyeColor color) {
        LivingEntity entity = (LivingEntity) entityType.create(level);
        if (entity instanceof TamableAnimal tamable) {
            tamable.setTame(true);
        }
        if (color != null && entity instanceof Sheep sheep) {
            sheep.setColor(color);
        }
        return entity;
    }

    public static LivingEntity doRendering(EntityType<?> entityType) {
        return doRendering(entityType, false, null);
    }

}