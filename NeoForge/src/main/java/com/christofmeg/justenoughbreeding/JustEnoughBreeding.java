package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(CommonConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = CommonConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class JustEnoughBreeding {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    @SuppressWarnings("unused")
    public static final JEBConfig GENERAL = new JEBConfig(BUILDER);

    public JustEnoughBreeding() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BUILDER.build());
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

}