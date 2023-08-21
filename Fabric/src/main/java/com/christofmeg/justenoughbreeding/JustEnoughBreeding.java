package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBConfig;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraftforge.fml.config.ModConfig;

public class JustEnoughBreeding implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ForgeConfigRegistry.INSTANCE.register(CommonConstants.MOD_ID, ModConfig.Type.CLIENT, JEBConfig.spec);
    }

}