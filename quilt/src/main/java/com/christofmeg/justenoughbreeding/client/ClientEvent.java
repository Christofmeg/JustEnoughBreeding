package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ClientEvent implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        MobOffsetManager.init();
    }

}
