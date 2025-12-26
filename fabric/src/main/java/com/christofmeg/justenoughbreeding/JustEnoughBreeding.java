package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBIntegration;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class JustEnoughBreeding implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        JEBIntegration.init();
    }

    public static Item getItemFromLoaderRegistries(Identifier resourceLocation) {
        return BuiltInRegistries.ITEM.getValue(resourceLocation);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(Identifier resourceLocation) {
        return BuiltInRegistries.ENTITY_TYPE.getValue(resourceLocation);
    }

}