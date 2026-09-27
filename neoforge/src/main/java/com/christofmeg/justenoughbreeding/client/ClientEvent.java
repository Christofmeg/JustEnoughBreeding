package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.config.MobOffsetManager;
import com.christofmeg.justenoughbreeding.config.ModConfigManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = CommonConstants.MOD_ID, value = Dist.CLIENT)
public class ClientEvent {

    @SubscribeEvent
    public static void clientEvents(FMLClientSetupEvent event) {
        MobOffsetManager.init();
        ModConfigManager.init();
    }

}
