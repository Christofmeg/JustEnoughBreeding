package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import net.fabricmc.api.ClientModInitializer;

public class ClientEvent implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MobOffsetManager.init();
    }

}
