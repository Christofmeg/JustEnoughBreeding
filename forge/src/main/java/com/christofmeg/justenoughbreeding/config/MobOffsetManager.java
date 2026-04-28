package com.christofmeg.justenoughbreeding.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

        System.out.println("JEB: Starting MobOffsetManager Init...");

        // 1. Load Defaults first
        loadFromResources();
        int defaultsSize = CACHE.size();

        if (configFile.exists()) {
            System.out.println("JEB: Loading from existing config file...");
            loadFromConfig();

            // 2. Logic Check: If we have more items in CACHE than were in the config file,
            // it means new defaults were added that aren't in the user's file yet.
            // We also check this by tracking if we need a save.
            if (CACHE.size() > getConfigFileKeyCount()) {
                System.out.println("JEB: New defaults detected, updating config file...");
                save();
            }
        } else {
            System.out.println("JEB: No config found, saving defaults...");
            save();
        }
    }

    /**
     * Helper to check how many entries are actually in the physical file
     * so we know if we need to sync new defaults.
     */
    private static int getConfigFileKeyCount() {
        if (!configFile.exists()) return 0;
        try (FileReader reader = new FileReader(configFile)) {
            Type type = new TypeToken<Map<String, MobOffset>>() {}.getType();
            Map<String, MobOffset> map = GSON.fromJson(reader, type);
            return map != null ? map.size() : 0;
        } catch (IOException e) {
            return 0;
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

            Map<String, MobOffset> toSave = new HashMap<>();
            for (Map.Entry<ResourceLocation, MobOffset> entry : CACHE.entrySet()) {
                toSave.put(entry.getKey().toString(), entry.getValue());
            }

            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(toSave, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadFromResources() {
        String path = "assets/justenoughbreeding/offsets/mod_defaults.json";

        // We use the Mod Container's classloader directly
        // This is often more reliable than Thread.currentThread() in Forge
        try (InputStream is = MobOffsetManager.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                // Log as error to see it clearly in the console
                System.err.println("JEB: Could not find resource at " + path);
                return;
            }

            try (Reader reader = new InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8)) {
                Type type = new TypeToken<Map<String, MobOffset>>() {}.getType();
                Map<String, MobOffset> defaults = GSON.fromJson(reader, type);

                if (defaults != null) {
                    defaults.forEach((key, value) -> CACHE.put(new ResourceLocation(key), value));
                    System.out.println("JEB: Successfully loaded " + CACHE.size() + " default(s).");
                }
            }
        } catch (Exception e) {
            System.err.println("JEB: Failed to read/parse defaults file!");
            e.printStackTrace();
        }
    }

    private static void loadFromConfig() {
        if (!configFile.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {

            Type type = new TypeToken<Map<String, MobOffset>>() {}.getType();
            Map<String, MobOffset> userOverrides = GSON.fromJson(reader, type);

            if (userOverrides != null) {
                for (Map.Entry<String, MobOffset> entry : userOverrides.entrySet()) {
                    CACHE.put(new ResourceLocation(entry.getKey()), entry.getValue());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}