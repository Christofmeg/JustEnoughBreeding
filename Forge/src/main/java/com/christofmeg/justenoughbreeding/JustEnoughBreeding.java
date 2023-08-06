package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * The FastEntityTransfer class is the main class of the mod.
 * It initializes the mod, registers event subscribers, and provides event handlers for left and right click events.
 */
@Mod(CommonConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = CommonConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class JustEnoughBreeding {

    public static Map<String, ForgeConfigSpec.ConfigValue<String>> ingredientConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<String>> spawnEggConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<Integer>> ingredientAmountConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<String>> eggResultConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<Integer>> eggMinAmountConfigs = new HashMap<>();
    public static Map<String, ForgeConfigSpec.ConfigValue<Integer>> eggMaxAmountConfigs = new HashMap<>();

    public JustEnoughBreeding() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, JEBConfig.spec);
    }

}