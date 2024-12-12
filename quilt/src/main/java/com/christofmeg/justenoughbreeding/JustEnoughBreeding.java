package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBIntegration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class JustEnoughBreeding implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        JEBIntegration.init();
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return BuiltInRegistries.ITEM.get(resourceLocation);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(ResourceLocation resourceLocation) {
        return BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);
    }

}