package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBIntegration;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class JustEnoughBreeding implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        JEBIntegration.init();
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return Registry.ITEM.get(resourceLocation);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(ResourceLocation resourceLocation) {
        return Registry.ENTITY_TYPE.get(resourceLocation);
    }

}