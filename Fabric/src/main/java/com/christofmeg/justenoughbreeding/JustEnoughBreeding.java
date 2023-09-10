package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBConfig;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class JustEnoughBreeding implements ClientModInitializer {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    @SuppressWarnings("unused")
    public static final JEBConfig GENERAL = new JEBConfig(BUILDER);

    @Override
    public void onInitializeClient() {
        ForgeConfigRegistry.INSTANCE.register(CommonConstants.MOD_ID, ModConfig.Type.CLIENT, BUILDER.build());
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return BuiltInRegistries.ITEM.get(resourceLocation);
    }

}