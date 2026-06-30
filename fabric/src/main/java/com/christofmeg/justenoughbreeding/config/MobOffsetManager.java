package com.christofmeg.justenoughbreeding.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MobOffsetManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(Identifier.class, new IdentifierAdapter())
            .create();
    private static final Map<Identifier, MobOffset> CACHE = new HashMap<>();
    private static File configFile;

    public static void init() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("justenoughbreeding-offsets.json").toFile();
        loadFromResources();
        if (configFile.exists()) {
            loadFromConfig();
            if (CACHE.size() > getConfigFileKeyCount()) {
                save();
            }
        } else {
            save();
        }
    }

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

    public static void updateOffset(Identifier entityId, float scale, float x, float y) {
        CACHE.put(entityId, new MobOffset(scale, x, y));
        save();
    }

    public static MobOffset get(Identifier entityId) {
        return CACHE.getOrDefault(entityId, new MobOffset(1.0f, 0, 0));
    }

    private static void save() {
        if (configFile == null) {
            init();
        }

        try {
            File parent = configFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            Map<String, MobOffset> toSave = new HashMap<>();
            for (Map.Entry<Identifier, MobOffset> entry : CACHE.entrySet()) {
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

        try (InputStream is = MobOffsetManager.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                return;
            }

            try (Reader reader = new InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8)) {
                Type type = new TypeToken<Map<String, MobOffset>>() {}.getType();
                Map<String, MobOffset> defaults = GSON.fromJson(reader, type);

                if (defaults != null) {
                    defaults.forEach((key, value) -> CACHE.put(Identifier.parse(key), value));
                }
            }
        } catch (Exception e) {
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
                    CACHE.put(Identifier.parse(entry.getKey()), entry.getValue());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}