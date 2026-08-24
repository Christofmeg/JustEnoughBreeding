package com.christofmeg.justenoughbreeding.event;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

@EventBusSubscriber(modid = CommonConstants.MOD_ID)
public class CommonEvent {

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        event.sendRecipes(
                JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE.get(),
                JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get(),
                JustEnoughBreeding.TAMING_PROVIDER_TYPE.get(),
                JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get(),
                JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get(),
                JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get()
        );
    }

}
