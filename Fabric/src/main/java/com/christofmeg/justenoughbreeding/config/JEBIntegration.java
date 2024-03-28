package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.*;
import net.fabricmc.loader.api.FabricLoader;

public class JEBIntegration {

    public static void init() {

        MinecraftIntegration.init();

        if (FabricLoader.getInstance().isModLoaded("snuffles")) {
            SnufflesIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("snowpig")) {
            SnowPigIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("aqupdcaracal")) {
            AqcaracalIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("theducksmod")) {
            TheDucksModIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("fennecfox")) {
            FennecFoxIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("ydms_redpanda")) {
            RedPandaIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("frozenup")) {
            FrozenUpIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("glare")) {
            GlareIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("lilwings")) {
            LilWingsIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("duckling")) {
            DucklingIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("twilightforest")) {
            TwilightForestIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("chococraft")) {
            ChocoCraftIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("ecologics")) {
            EcologicsIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("naturalist")) {
            NaturalistIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("friendsandfoes")) {
            FriendsAndFoesIntegration.init();
        }
        if (FabricLoader.getInstance().isModLoaded("biomemakeover")) {
            BiomeMakeoverIntegration.init();
        }
    }
}