package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class JustEnoughBreeding implements ClientModInitializer {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    @SuppressWarnings("unused")
    public static final JEBConfig GENERAL = new JEBConfig(BUILDER);

    @Override
    public void onInitializeClient(ModContainer mod) {
        ModLoadingContext.registerConfig(CommonConstants.MOD_ID, ModConfig.Type.CLIENT, BUILDER.build());
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return Registry.ITEM.get(resourceLocation);
    }

}