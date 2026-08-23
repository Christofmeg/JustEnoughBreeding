package com.christofmeg.justenoughbreeding.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfigManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static File configFile;
    private static ModConfig config;

    public static void init() {
        configFile = FabricLoader.getInstance()
                .getConfigDir()
                .resolve("justenoughbreeding.json")
                .toFile();

        if (configFile.exists()) {
            load();
        } else {
            config = new ModConfig();
            save();
        }
    }

    public static boolean areConfigButtonsEnabled() {
        return config.enableConfigButtons;
    }

    public static void setConfigButtonsEnabled(boolean enabled) {
        config.enableConfigButtons = enabled;
        save();
    }

    private static void load() {
        try (FileReader reader = new FileReader(configFile)) {
            ModConfig loaded = GSON.fromJson(reader, ModConfig.class);

            config = loaded != null
                    ? loaded
                    : new ModConfig();

        } catch (IOException e) {
            e.printStackTrace();
            config = new ModConfig();
        }
    }

    private static void save() {
        try {
            File parent = configFile.getParentFile();

            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (FileWriter writer = new FileWriter(configFile)) {
                GSON.toJson(config, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ModConfig {
        @SerializedName("enable_config_buttons")
        boolean enableConfigButtons = true;
    }
}