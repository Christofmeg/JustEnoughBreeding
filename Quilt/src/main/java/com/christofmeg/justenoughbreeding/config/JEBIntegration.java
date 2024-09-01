package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.*;
import org.quiltmc.loader.api.QuiltLoader;

public class JEBIntegration {

    public static void init() {

        MinecraftIntegration.init();

        if (QuiltLoader.isModLoaded("snuffles")) {
            SnufflesIntegration.init();
        }
        if (QuiltLoader.isModLoaded("snowpig")) {
            SnowPigIntegration.init();
        }
        if (QuiltLoader.isModLoaded("aqupdcaracal")) {
            AqcaracalIntegration.init();
        }
        if (QuiltLoader.isModLoaded("theducksmod")) {
            TheDucksModIntegration.init();
        }
        if (QuiltLoader.isModLoaded("fennecfox")) {
            FennecFoxIntegration.init();
        }
        if (QuiltLoader.isModLoaded("ydms_redpanda")) {
            RedPandaIntegration.init();
        }
        if (QuiltLoader.isModLoaded("frozenup")) {
            FrozenUpIntegration.init();
        }
        if (QuiltLoader.isModLoaded("glare")) {
            GlareIntegration.init();
        }
        if (QuiltLoader.isModLoaded("lilwings")) {
            LilWingsIntegration.init();
        }
        if (QuiltLoader.isModLoaded("duckling")) {
            DucklingIntegration.init();
        }
        if (QuiltLoader.isModLoaded("twilightforest")) {
            TwilightForestIntegration.init();
        }
        if (QuiltLoader.isModLoaded("chococraft")) {
            ChocoCraftIntegration.init();
        }
        if (QuiltLoader.isModLoaded("ecologics")) {
            EcologicsIntegration.init();
        }
        if (QuiltLoader.isModLoaded("naturalist")) {
            NaturalistIntegration.init();
        }
        if (QuiltLoader.isModLoaded("friendsandfoes")) {
            FriendsAndFoesIntegration.init();
        }
        if (QuiltLoader.isModLoaded("biomemakeover")) {
            BiomeMakeoverIntegration.init();
        }
        if (QuiltLoader.isModLoaded("crittersandcompanions")) {
            CrittersAndCompanions.init();
        }
        if (QuiltLoader.isModLoaded("meadow")) {
            MeadowIntegration.init();
        }
        if (QuiltLoader.isModLoaded("fishofthieves")) {
            FishOfThievesIntegration.init();
        }
        if (QuiltLoader.isModLoaded("promenade")) {
            PromenadeIntegration.init();
        }
    }
}