package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.*;
import com.christofmeg.justenoughbreeding.config.integration.GreekFantasyIntegration;
import net.minecraftforge.fml.ModList;

public class JEBIntegration {

    public static void init() {

        MinecraftIntegration.init();

        if (ModList.get().isLoaded("alexsmobs")) {
            AlexsMobsIntegration.init();
        }
        if (ModList.get().isLoaded("greekfantasy")) {
            GreekFantasyIntegration.init();
        }
        if (ModList.get().isLoaded("blue_skies")) {
            BlueSkiesIntegration.init();
        }
        if (ModList.get().isLoaded("frostrealm")) {
            FrostRealmIntegration.init();
        }
        if (ModList.get().isLoaded("cnb")) {
            CreaturesAndBeastsIntegration.init();
        }
        if (ModList.get().isLoaded("ambientadditions")) {
            AmbientAdditionsIntegration.init();
        }
    }
}