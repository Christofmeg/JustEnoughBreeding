package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.*;
import com.christofmeg.justenoughbreeding.config.integration.GreekFantasyIntegration;
import net.minecraftforge.fml.ModList;

public class JEBIntegration {

    public static void init() {

 //       MinecraftIntegration.init();

        if (ModList.get().isLoaded("alexsmobs")) {
            AlexsMobsIntegration.init();
        }
        if (ModList.get().isLoaded("aqcaracal")) {
            AqcaracalIntegration.init();
        }
        if (ModList.get().isLoaded("greekfantasy")) {
            GreekFantasyIntegration.init();
        }
        if (ModList.get().isLoaded("sophisticated_wolves")) {
            SophisticatedWolvesIntegration.init();
        }
        if (ModList.get().isLoaded("steves_vanilla")) {
            StevesVanillaIntegration.init();
        }
        if (ModList.get().isLoaded("marineiguana")) {
            MarineIguanaIntegration.init();
        }
        if (ModList.get().isLoaded("blue_skies")) {
            BlueSkiesIntegration.init();
        }
        if (ModList.get().isLoaded("quark")) {
            QuarkIntegration.init();
        }
        if (ModList.get().isLoaded("earthmobsmod")) {
            EarthMobsIntegration.init();
        }
        if (ModList.get().isLoaded("recrafted_creatures")) {
            RecraftedCreaturesIntegration.init();
        }
        if (ModList.get().isLoaded("frostrealm")) {
            FrostRealmIntegration.init();
        }
        if (ModList.get().isLoaded("alexscaves")) {
            AlexsCavesIntegration.init();
        }
        if (ModList.get().isLoaded("iceandfire")) {
            IceAndFireIntegration.init();
        }
        if (ModList.get().isLoaded("cnb")) {
            CreaturesAndBeastsIntegration.init();
        }
        if (ModList.get().isLoaded("ambientadditions")) {
            AmbientAdditionsIntegration.init();
        }
        if (ModList.get().isLoaded("tfc")) {
            TerraFirmaCraftIntegration.init();
        }
        if (ModList.get().isLoaded("environmental")) {
            EnvironmentalIntegration.init();
        }
    }
}