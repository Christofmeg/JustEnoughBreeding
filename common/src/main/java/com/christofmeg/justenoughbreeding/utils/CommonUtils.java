package com.christofmeg.justenoughbreeding.utils;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class CommonUtils {

    public static String makeKey(EntityType<?> type, boolean input) {
        return type.toString() + ":" + input;
    }

    public static @NotNull Ingredient safe(Ingredient ing) {
        return ing == null ? Ingredient.of(Items.AIR) : ing;
    }

    public static @NotNull Ingredient safe(ItemLike itemLike) {
        return itemLike == null ? Ingredient.of(Items.AIR) : Ingredient.of(itemLike);
    }

    public static Rect getRect(boolean input, int CATEGORY_WIDTH) {
       return getRect(input, CATEGORY_WIDTH, 0, 0);
    }

    public static Rect getRect(boolean input, int CATEGORY_WIDTH, int extraX, int extraY) {
        Rect rect;
        final int WIDGET_SIZE = 61;
        if (input) {
            rect = new Rect(((CATEGORY_WIDTH - WIDGET_SIZE) / 2) - 52 - extraX, 10 + extraY, WIDGET_SIZE, WIDGET_SIZE + 20);
        } else {
            rect = new Rect(((CATEGORY_WIDTH - WIDGET_SIZE) / 2) + 53 - extraX, 10 + extraY, WIDGET_SIZE, WIDGET_SIZE + 20);
        }
        return rect;
    }

}
