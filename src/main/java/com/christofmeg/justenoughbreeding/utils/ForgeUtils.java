package com.christofmeg.justenoughbreeding.utils;

import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ForgeUtils {

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();

        for (ResourceLocation key : ForgeRegistries.ITEMS.getKeys()) {
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item != null) {
                Food foodProperties = item.getFoodProperties();
                if(includeRottenFlesh) {
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
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

}
