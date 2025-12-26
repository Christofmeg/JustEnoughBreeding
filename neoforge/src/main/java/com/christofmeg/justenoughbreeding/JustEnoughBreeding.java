package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBIntegration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    public JustEnoughBreeding() {
        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
//        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString(), (remote, isServer) -> true));
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            JEBIntegration.init();
        }
    }

    public static Item getItemFromLoaderRegistries(Identifier Identifier) {
        return BuiltInRegistries.ITEM.getValue(Identifier);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(Identifier Identifier) {
        return BuiltInRegistries.ENTITY_TYPE.getValue(Identifier);
    }

}