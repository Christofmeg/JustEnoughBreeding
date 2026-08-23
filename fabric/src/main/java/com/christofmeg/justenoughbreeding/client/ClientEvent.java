package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.config.ModConfigManager;
import net.fabricmc.api.ClientModInitializer;

public class ClientEvent implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MobOffsetManager.init();
        ModConfigManager.init();
    }

}