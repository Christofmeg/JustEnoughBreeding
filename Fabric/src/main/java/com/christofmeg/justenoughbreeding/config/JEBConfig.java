package com.christofmeg.justenoughbreeding.config;

import com.christofmeg.justenoughbreeding.config.integrated.AqcaracalIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.BiomeMakeoverIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.DucklingIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.FennecFoxIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.FriendsAndFoesIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.FrozenUpIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.GlareIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.LilWingsIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.RedPandaIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.SnowPigIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.SnufflesIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.TheDucksModIntegration;
import com.christofmeg.justenoughbreeding.config.integration.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.ForgeConfigSpec;

public class JEBConfig {

    @SuppressWarnings("unused")
    public JEBConfig(ForgeConfigSpec.Builder builder) {

        final MinecraftIntegration VANILLA = new MinecraftIntegration(builder);

        if (FabricLoader.getInstance().isModLoaded("snuffles")) {
            final SnufflesIntegration CONFIG = new SnufflesIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("snowpig")) {
            final SnowPigIntegration CONFIG = new SnowPigIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("aqupdcaracal")) {
            final AqcaracalIntegration CONFIG = new AqcaracalIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("theducksmod")) {
            final TheDucksModIntegration CONFIG = new TheDucksModIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("fennecfox")) {
            final FennecFoxIntegration CONFIG = new FennecFoxIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("ydms_redpanda")) {
            final RedPandaIntegration CONFIG = new RedPandaIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("frozenup")) {
            final FrozenUpIntegration CONFIG = new FrozenUpIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("glare")) {
            final GlareIntegration CONFIG = new GlareIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("lilwings")) {
            final LilWingsIntegration CONFIG = new LilWingsIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("duckling")) {
            final DucklingIntegration CONFIG = new DucklingIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("twilightforest")) {
            final TwilightForestIntegration CONFIG = new TwilightForestIntegration(builder);
        }
/*
            if (FabricLoader.getInstance().isModLoaded("chococraft")) {
                final ChocoCraftIntegration CONFIG = new ChocoCraftIntegration(builder);
            }
*/
        if (FabricLoader.getInstance().isModLoaded("ecologics")) {
            final EcologicsIntegration CONFIG = new EcologicsIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("naturalist")) {
            final NaturalistIntegration CONFIG = new NaturalistIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("friendsandfoes")) {
            final FriendsAndFoesIntegration CONFIG = new FriendsAndFoesIntegration(builder);
        }
        if (FabricLoader.getInstance().isModLoaded("biomemakeover")) {
            final BiomeMakeoverIntegration CONFIG = new BiomeMakeoverIntegration(builder);
        }
    }
}