package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBIntegration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    public JustEnoughBreeding() {
        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(()-> NetworkConstants.IGNORESERVERONLY, (remote, isServer)-> true));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> JEBIntegration::init);
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ENTITY_TYPES.getValue(resourceLocation);
    }

}