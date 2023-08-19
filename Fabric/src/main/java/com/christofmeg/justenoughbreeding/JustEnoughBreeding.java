package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

public class JustEnoughBreeding implements ClientModInitializer {

    public static Map<String, ForgeConfigSpec.ConfigValue<String>> ingredientConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<String>> spawnEggConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<Integer>> ingredientAmountConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<String>> eggResultConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<Integer>> eggMinAmountConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<Integer>> eggMaxAmountConfigs = new HashMap<>();
    @Override
    public void onInitializeClient() {
        ModLoadingContext.registerConfig(CommonConstants.MOD_ID, ModConfig.Type.CLIENT, JEBConfig.spec);
    }

}