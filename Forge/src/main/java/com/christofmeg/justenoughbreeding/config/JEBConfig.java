package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.*;
import com.christofmeg.justenoughbreeding.config.integrated.FrostRealmIntegration;
import com.christofmeg.justenoughbreeding.config.integration.GreekFantasyIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.SophisticatedWolvesIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.UlterlandsIntegration;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;

public class JEBConfig {

    @SuppressWarnings("unused")
    public JEBConfig(ForgeConfigSpec.Builder builder) {

        final MinecraftIntegration VANILLA = new MinecraftIntegration(builder);

        if (ModList.get().isLoaded("alexsmobs")) {
            final AlexsMobsIntegration CONFIG = new AlexsMobsIntegration(builder);
        }
        if (ModList.get().isLoaded("snuffles")) {
            final SnufflesIntegration CONFIG = new SnufflesIntegration(builder);
        }
        if (ModList.get().isLoaded("snowpig")) {
            final SnowPigIntegration CONFIG = new SnowPigIntegration(builder);
        }
        if (ModList.get().isLoaded("aqcaracal")) {
            final AqcaracalIntegration CONFIG = new AqcaracalIntegration(builder);
        }
        if (ModList.get().isLoaded("theducksmod")) {
            final TheDucksModIntegration CONFIG = new TheDucksModIntegration(builder);
        }
        if (ModList.get().isLoaded("fennecfox")) {
            final FennecFoxIntegration CONFIG = new FennecFoxIntegration(builder);
        }
        if (ModList.get().isLoaded("apple_cows")) {
            final AppleCowsIntegration CONFIG = new AppleCowsIntegration(builder);
        }
        if (ModList.get().isLoaded("ydms_redpanda")) {
            final RedPandaIntegration CONFIG = new RedPandaIntegration(builder);
        }
        if (ModList.get().isLoaded("frozenup")) {
            final FrozenUpIntegration CONFIG = new FrozenUpIntegration(builder);
        }
        if (ModList.get().isLoaded("glare")) {
            final GlareIntegration CONFIG = new GlareIntegration(builder);
        }
        if (ModList.get().isLoaded("greekfantasy")) {
            final GreekFantasyIntegration CONFIG = new GreekFantasyIntegration(builder);
        }
        if (ModList.get().isLoaded("sophisticated_wolves")) {
            final SophisticatedWolvesIntegration CONFIG = new SophisticatedWolvesIntegration(builder);
        }
        if (ModList.get().isLoaded("lilwings")) {
            final LilWingsIntegration CONFIG = new LilWingsIntegration(builder);
        }
        if (ModList.get().isLoaded("steves_vanilla")) {
            final StevesVanillaIntegration CONFIG = new StevesVanillaIntegration(builder);
        }
        if (ModList.get().isLoaded("duckling")) {
            final DucklingIntegration CONFIG = new DucklingIntegration(builder);
        }
        if (ModList.get().isLoaded("ulterlands")) {
            final UlterlandsIntegration CONFIG = new UlterlandsIntegration(builder);
        }
        if (ModList.get().isLoaded("twilightforest")) {
            final TwilightForestIntegration CONFIG = new TwilightForestIntegration(builder);
        }
//        if (ModList.get().isLoaded("chococraft")) {
//            final ChocoCraftIntegration CONFIG = new ChocoCraftIntegration(builder);
//        }
        if (ModList.get().isLoaded("waddles")) {
            final WaddlesIntegration CONFIG = new WaddlesIntegration(builder);
        }
        if (ModList.get().isLoaded("aquaculture")) {
            final AquacultureIntegration CONFIG = new AquacultureIntegration(builder);
        }
        if (ModList.get().isLoaded("ecologics")) {
            final EcologicsIntegration CONFIG = new EcologicsIntegration(builder);
        }
        if (ModList.get().isLoaded("ostrich")) {
            final OstrichIntegration CONFIG = new OstrichIntegration(builder);
        }
        if (ModList.get().isLoaded("marineiguana")) {
            final MarineIguanaIntegration CONFIG = new MarineIguanaIntegration(builder);
        }
        if (ModList.get().isLoaded("blue_skies")) {
            final BlueSkiesIntegration CONFIG = new BlueSkiesIntegration(builder);
        }
        if (ModList.get().isLoaded("naturalist")) {
            final NaturalistIntegration CONFIG = new NaturalistIntegration(builder);
        }
        if (ModList.get().isLoaded("quark")) {
            final QuarkIntegration CONFIG = new QuarkIntegration(builder);
        }
        if (ModList.get().isLoaded("friendsandfoes")) {
            final FriendsAndFoesIntegration CONFIG = new FriendsAndFoesIntegration(builder);
        }
        if (ModList.get().isLoaded("earthmobsmod")) {
            final EarthMobsIntegration CONFIG = new EarthMobsIntegration(builder);
        }
        if (ModList.get().isLoaded("autumnity")) {
            final AutumnityIntegration CONFIG = new AutumnityIntegration(builder);
        }
        if (ModList.get().isLoaded("kiwiboi")) {
            final KiwiBoiIntegration CONFIG = new KiwiBoiIntegration(builder);
        }
        if (ModList.get().isLoaded("recrafted_creatures")) {
            final RecraftedCreaturesIntegration CONFIG = new RecraftedCreaturesIntegration(builder);
        }
        if (ModList.get().isLoaded("chileancraft")) {
            final ChileanCraftIntegration CONFIG = new ChileanCraftIntegration(builder);
        }
        if (ModList.get().isLoaded("frostrealm")) {
            final FrostRealmIntegration CONFIG = new FrostRealmIntegration(builder);
        }
        if (ModList.get().isLoaded("biome_backlog")) {
            final BiomeBacklogIntegration CONFIG = new BiomeBacklogIntegration(builder);
        }
        if (ModList.get().isLoaded("biomemakeover")) {
            final BiomeMakeoverIntegration CONFIG = new BiomeMakeoverIntegration(builder);
        }
        if (ModList.get().isLoaded("alexscaves")) {
            final AlexsCavesIntegration CONFIG = new AlexsCavesIntegration(builder);
        }
        if (ModList.get().isLoaded("untamedwilds")) {
            final UntamedWildsIntegration CONFIG = new UntamedWildsIntegration(builder);
        }

    }
}