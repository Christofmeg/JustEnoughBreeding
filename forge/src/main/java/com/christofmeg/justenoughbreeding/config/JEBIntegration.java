package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.AlexsMobsIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.MinecraftIntegration;
import net.minecraftforge.fml.ModList;

public class JEBIntegration {

    public static void init() {
        MinecraftIntegration.init();
        if (ModList.get().isLoaded("alexsmobs")) {
            AlexsMobsIntegration.init();
        }
    }
}