package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class ClientUtils {

    public static final int ENTITY_CREATION_INTERVAL = 3000;
    private static final Map<String, LivingEntity> ENTITY_CACHE = new HashMap<>();
    private static final Map<String, Long> CREATION_TIMES = new HashMap<>();

    public static LivingEntity doRendering(EntityType<?> entityType, CompoundTag nbt, boolean input) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return null;

        String key = CommonUtils.makeKey(entityType, input);
        LivingEntity cachedEntity = ENTITY_CACHE.get(key);

        boolean nbtChanged = false;
        if (cachedEntity != null) {
            CompoundTag cachedNbt = new CompoundTag();
            cachedEntity.saveWithoutId(cachedNbt);

            if (nbt == null) {
                nbtChanged = !cachedNbt.isEmpty();
            } else {
                nbtChanged = !nbt.equals(cachedNbt);
            }
        }

        long currentTime = System.currentTimeMillis();
        long lastTime = CREATION_TIMES.getOrDefault(key, 0L);

        boolean refreshAllowed = !JustEnoughBreeding.isModLoaded("entity_model_features") &&
                !JustEnoughBreeding.isModLoaded("optifine");

        if (cachedEntity == null || nbtChanged || (refreshAllowed && (currentTime - lastTime >= ENTITY_CREATION_INTERVAL))) {
            cachedEntity = (LivingEntity) entityType.create(level);
            if (cachedEntity != null) {
                if (nbt != null) {
                    cachedEntity.load(nbt);
                }
                cachedEntity.setUUID(UUID.randomUUID());
            }
            ENTITY_CACHE.put(key, cachedEntity);
            CREATION_TIMES.put(key, currentTime);
        }

        return cachedEntity;
    }

    public static LivingEntity doRendering(EntityType<?> entityType) {
        return doRendering(entityType, null, false);
    }

}