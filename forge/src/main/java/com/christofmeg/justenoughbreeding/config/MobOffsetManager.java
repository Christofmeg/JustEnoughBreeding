package com.christofmeg.justenoughbreeding.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("removal")
public class MobOffsetManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .create();
    private static final Map<ResourceLocation, MobOffset> CACHE = new HashMap<>();
    private static File configFile;

    public static void init() {
        configFile = FMLPaths.CONFIGDIR.get().resolve("justenoughbreeding-offsets.json").toFile();

        // 1. Load Defaults from your mod's resources (read-only)
        loadFromResources();

        if (configFile.exists()) {
            loadFromConfig();
        } else {
            // 3. If no config exists, create it now using the defaults we just loaded
            save();
        }
    }

    public static void updateOffset(ResourceLocation entityId, float scale, float x, float y) {
        CACHE.put(entityId, new MobOffset(scale, x, y));
        save(); // Save every time a button is clicked for a "live" feel
    }

    public static MobOffset get(ResourceLocation entityId) {
        return CACHE.getOrDefault(entityId, new MobOffset(1.0f, 0, 0));
    }

    private static void save() {
        if (configFile == null) {
            init(); // Attempt emergency init if somehow null
        }

        try {
            // Ensure the /config/ directory exists
            File parent = configFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(CACHE, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromResources() {
        // This looks for a file inside your mod jar at:
        // assets/justenoughbreeding/offsets/mob_defaults.json
        ResourceLocation location = new ResourceLocation("justenoughbreeding", "offsets/mob_defaults.json");

        Minecraft.getInstance().getResourceManager().getResource(location).ifPresent(resource -> {
            try (Reader reader = resource.openAsReader()) {
                Type type = new TypeToken<Map<ResourceLocation, MobOffset>>(){}.getType();
                Map<ResourceLocation, MobOffset> defaults = GSON.fromJson(reader, type);
                if (defaults != null) {
                    CACHE.putAll(defaults);
                }
            } catch (IOException e) {
                // Log error: Default offsets not found or unreadable
            }
        });
    }

    private static void loadFromConfig() {
        if (!configFile.exists()) {
            return; // No user overrides yet, keep the defaults
        }

        try (FileReader reader = new FileReader(configFile)) {
            Type type = new TypeToken<Map<ResourceLocation, MobOffset>>(){}.getType();
            Map<ResourceLocation, MobOffset> userOverrides = GSON.fromJson(reader, type);
            if (userOverrides != null) {
                // putAll will overwrite existing default keys with user values
                CACHE.putAll(userOverrides);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}