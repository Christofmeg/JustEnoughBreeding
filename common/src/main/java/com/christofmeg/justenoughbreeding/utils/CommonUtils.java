package com.christofmeg.justenoughbreeding.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class CommonUtils {

    public static String getEdibleMeatItemNames(boolean includeRottenFlesh) {
        List<String> edibleMeatItemNames = new ArrayList<>();
        for (ResourceLocation key : BuiltInRegistries.ITEM.keySet()) {
            Item item = BuiltInRegistries.ITEM.get(key);
            FoodProperties foodProperties = item.components().get(DataComponents.FOOD);
            if (foodProperties != null && item.builtInRegistryHolder().is(ItemTags.MEAT)) {
                if (includeRottenFlesh || item != Items.ROTTEN_FLESH) {
                    edibleMeatItemNames.add(key.toString());
                }
            }
        }
        return String.join(", ", edibleMeatItemNames);
    }

    public static Ingredient createTagIngredient(String tagId) {
        String tagLocationStr = tagId.trim().substring(1);
        ResourceLocation tagLocation = ResourceLocation.parse(tagLocationStr);
        return Ingredient.of(TagKey.create(Registries.ITEM, tagLocation));
    }

    public static String makeKey(EntityType<?> type, boolean input) {
        return type.toString() + ":" + input;
    }

    public static @NotNull Ingredient safe(Ingredient ing) {
        return ing == null ? Ingredient.EMPTY : ing;
    }

    public static CompoundTag parseJsonNBT(JsonElement element) {
        if (element == null || element.isJsonNull()) return null;

        // Case 1: SNBT string (already valid)
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            try {
                return TagParser.parseTag(element.getAsString());
            } catch (CommandSyntaxException e) {
                throw new RuntimeException("Invalid SNBT: " + element, e);
            }
        }

        // Case 2: A single JSON object → convert to SNBT
        if (element.isJsonObject()) {
            String snbt = element.toString().replace("\"", "\\\"");
            snbt = element.toString();
            try {
                return TagParser.parseTag(snbt);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException("Invalid JSON-as-NBT: " + element, e);
            }
        }

        // Case 3: Array — take first element (your format)
        if (element.isJsonArray()) {
            JsonArray arr = element.getAsJsonArray();
            if (arr.isEmpty()) return null;
            return parseJsonNBT(arr.get(0)); // recursive parse
        }

        throw new RuntimeException("Unsupported NBT JSON format: " + element);
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
