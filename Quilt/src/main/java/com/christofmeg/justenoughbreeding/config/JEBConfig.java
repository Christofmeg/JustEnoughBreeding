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
import com.christofmeg.justenoughbreeding.config.integration.EcologicsIntegration;
import com.christofmeg.justenoughbreeding.config.integration.MinecraftIntegration;
import com.christofmeg.justenoughbreeding.config.integrated.NaturalistIntegration;
import com.christofmeg.justenoughbreeding.config.integration.TwilightForestIntegration;
import net.minecraftforge.common.ForgeConfigSpec;
import org.quiltmc.loader.api.QuiltLoader;

public class JEBConfig {

    @SuppressWarnings("unused")
    public JEBConfig(ForgeConfigSpec.Builder builder) {

        final MinecraftIntegration VANILLA = new MinecraftIntegration(builder);

        if (QuiltLoader.isModLoaded("snuffles")) {
            final SnufflesIntegration CONFIG = new SnufflesIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("snowpig")) {
            final SnowPigIntegration CONFIG = new SnowPigIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("aqupdcaracal")) {
            final AqcaracalIntegration CONFIG = new AqcaracalIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("theducksmod")) {
            final TheDucksModIntegration CONFIG = new TheDucksModIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("fennecfox")) {
            final FennecFoxIntegration CONFIG = new FennecFoxIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("ydms_redpanda")) {
            final RedPandaIntegration CONFIG = new RedPandaIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("frozenup")) {
            final FrozenUpIntegration CONFIG = new FrozenUpIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("glare")) {
            final GlareIntegration CONFIG = new GlareIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("lilwings")) {
            final LilWingsIntegration CONFIG = new LilWingsIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("duckling")) {
            final DucklingIntegration CONFIG = new DucklingIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("twilightforest")) {
            final TwilightForestIntegration CONFIG = new TwilightForestIntegration(builder);
        }
/*
            if (QuiltLoader.isModLoaded("chococraft")) {
                final ChocoCraftIntegration CONFIG = new ChocoCraftIntegration(builder);
            }
*/
        if (QuiltLoader.isModLoaded("ecologics")) {
            final EcologicsIntegration CONFIG = new EcologicsIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("naturalist")) {
            final NaturalistIntegration CONFIG = new NaturalistIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("friendsandfoes")) {
            final FriendsAndFoesIntegration CONFIG = new FriendsAndFoesIntegration(builder);
        }
        if (QuiltLoader.isModLoaded("biomemakeover")) {
            final BiomeMakeoverIntegration CONFIG = new BiomeMakeoverIntegration(builder);
        }

    }
}