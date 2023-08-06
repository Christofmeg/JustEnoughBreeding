package com.christofmeg.justenoughbreeding.utils;

import net.fabricmc.fabric.api.event.registry.RegistryIdRemapCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class FabricUtils {

    public static String getEdibleMeatItemNames(boolean includRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();

        for (ResourceLocation key : Registry.ITEM.keySet()) {
            Item item = Registry.ITEM.get(key);
            if (item != null) {
                FoodProperties foodProperties = item.getFoodProperties();
                if(includRottenFlesh) {
                    if (foodProperties != null && item.isEdible() && foodProperties.isMeat()) {
                        edibleMeatItemNames.add(key.toString());
                    }
                }
                else {
                    if (foodProperties != null && item.isEdible() && foodProperties.isMeat() && item != Items.ROTTEN_FLESH) {
                        edibleMeatItemNames.add(key.toString());
                    }
                }
            }
        }

        return String.join(", ", edibleMeatItemNames);
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return Registry.ITEM.get(resourceLocation);
    }

}