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
        if (ModList.get().isLoaded("snuffles")) {
            SnufflesIntegration.init();
        }
        if (ModList.get().isLoaded("snowpig")) {
            SnowPigIntegration.init();
        }
        if (ModList.get().isLoaded("aqcaracal")) {
            AqcaracalIntegration.init();
        }
        if (ModList.get().isLoaded("theducksmod")) {
            TheDucksModIntegration.init();
        }
        if (ModList.get().isLoaded("fennecfox")) {
            FennecFoxIntegration.init();
        }
        if (ModList.get().isLoaded("apple_cows")) {
            AppleCowsIntegration.init();
        }
        if (ModList.get().isLoaded("ydms_redpanda")) {
            RedPandaIntegration.init();
        }
        if (ModList.get().isLoaded("frozenup")) {
            FrozenUpIntegration.init();
        }
        if (ModList.get().isLoaded("glare")) {
            GlareIntegration.init();
        }
        if (ModList.get().isLoaded("greekfantasy")) {
            GreekFantasyIntegration.init();
        }
        if (ModList.get().isLoaded("sophisticated_wolves")) {
            SophisticatedWolvesIntegration.init();
        }
        if (ModList.get().isLoaded("lilwings")) {
            LilWingsIntegration.init();
        }
        if (ModList.get().isLoaded("steves_vanilla")) {
            StevesVanillaIntegration.init();
        }
        if (ModList.get().isLoaded("duckling")) {
            DucklingIntegration.init();
        }
        if (ModList.get().isLoaded("ulterlands")) {
            UlterlandsIntegration.init();
        }
        if (ModList.get().isLoaded("twilightforest")) {
            TwilightForestIntegration.init();
        }
        if (ModList.get().isLoaded("chococraft")) {
            ChocoCraftIntegration.init();
        }
        if (ModList.get().isLoaded("waddles")) {
            WaddlesIntegration.init();
        }
        if (ModList.get().isLoaded("aquaculture")) {
            AquacultureIntegration.init();
        }
        if (ModList.get().isLoaded("ecologics")) {
            EcologicsIntegration.init();
        }
        if (ModList.get().isLoaded("ostrich")) {
            OstrichIntegration.init();
        }
        if (ModList.get().isLoaded("marineiguana")) {
            MarineIguanaIntegration.init();
        }
        if (ModList.get().isLoaded("blue_skies")) {
            BlueSkiesIntegration.init();
        }
        if (ModList.get().isLoaded("naturalist")) {
            NaturalistIntegration.init();
        }
        if (ModList.get().isLoaded("quark")) {
            QuarkIntegration.init();
        }
        if (ModList.get().isLoaded("friendsandfoes")) {
            FriendsAndFoesIntegration.init();
        }
        if (ModList.get().isLoaded("earthmobsmod")) {
            EarthMobsIntegration.init();
        }
        if (ModList.get().isLoaded("autumnity")) {
            AutumnityIntegration.init();
        }
        if (ModList.get().isLoaded("kiwiboi")) {
            KiwiBoiIntegration.init();
        }
        if (ModList.get().isLoaded("recrafted_creatures")) {
            RecraftedCreaturesIntegration.init();
        }
        if (ModList.get().isLoaded("chileancraft")) {
            ChileanCraftIntegration.init();
        }
        if (ModList.get().isLoaded("frostrealm")) {
            FrostRealmIntegration.init();
        }
        if (ModList.get().isLoaded("biome_backlog")) {
            BiomeBacklogIntegration.init();
        }
        if (ModList.get().isLoaded("biomemakeover")) {
            BiomeMakeoverIntegration.init();
        }
        if (ModList.get().isLoaded("alexscaves")) {
            AlexsCavesIntegration.init();
        }
        if (ModList.get().isLoaded("untamedwilds")) {
            UntamedWildsIntegration.init();
        }
        if (ModList.get().isLoaded("iceandfire")) {
            IceAndFireIntegration.init();
        }
        if (ModList.get().isLoaded("aether")) {
            AetherIntegration.init();
        }
        if (ModList.get().isLoaded("aether_redux")) {
            AetherReduxIntegration.init();
        }
        if (ModList.get().isLoaded("deep_aether")) {
            DeepAetherIntegration.init();
        }
    }
}