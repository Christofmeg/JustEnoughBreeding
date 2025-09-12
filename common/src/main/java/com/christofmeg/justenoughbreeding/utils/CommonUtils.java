package com.christofmeg.justenoughbreeding.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();
        for (ResourceLocation key : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.get(key);
            FoodProperties foodProperties = item.getFoodProperties();
            if (includeRottenFlesh) {
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
        return String.join(", ", edibleMeatItemNames);
    }

    public static Ingredient createTagIngredient(String tagId) {
        String tagLocationStr = tagId.trim().substring(1);
        ResourceLocation tagLocation = new ResourceLocation(tagLocationStr);
        return Ingredient.of(TagKey.create(Registries.ITEM, tagLocation));
    }

    public static String makeKey(EntityType<?> type, boolean input, DyeColor color) {
        return type.toString() + ":" + input + ":" + (color != null ? color.getName() : "none");
    }

    public static @NotNull Ingredient safe(Ingredient ing) {
        return ing == null ? Ingredient.EMPTY : ing;
    }

}
