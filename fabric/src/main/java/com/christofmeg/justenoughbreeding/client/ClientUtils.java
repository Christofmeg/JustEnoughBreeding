package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ClientUtils {

    public static final int ENTITY_CREATION_INTERVAL = 3000;
    private static final Map<String, LivingEntity> ENTITY_CACHE = new HashMap<>();
    private static final Map<String, Long> CREATION_TIMES = new HashMap<>();

    public static LivingEntity doRendering(EntityType<?> entityType, CompoundTag nbt, boolean input) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return null;

        String key = CommonUtils.makeKey(entityType, input);
        if (nbt != null) {
            key += "|" + nbt;
        }
        long currentTime = System.currentTimeMillis();

        LivingEntity entity = ENTITY_CACHE.get(key);
        long lastTime = CREATION_TIMES.getOrDefault(key, 0L);

        boolean refreshAllowed =
                !JustEnoughBreeding.isModLoaded("entity_model_features") &&
                        !JustEnoughBreeding.isModLoaded("optifine");

        if (entity == null) {
            entity = (LivingEntity) entityType.create(level);
            ENTITY_CACHE.put(key, entity);
            CREATION_TIMES.put(key, currentTime);
        } else if (!entity.getEntityData().isEmpty()) {
            entity = (LivingEntity) entityType.create(level);
            ENTITY_CACHE.put(key, entity);
            CREATION_TIMES.put(key, currentTime);
        } else if (refreshAllowed && (currentTime - lastTime >= ENTITY_CREATION_INTERVAL)) {
            entity = (LivingEntity) entityType.create(level);
            ENTITY_CACHE.put(key, entity);
            CREATION_TIMES.put(key, currentTime);
        }

        return entity;
    }

    public static LivingEntity doRendering(EntityType<?> entityType) {
        return doRendering(entityType, null, false);
    }

}