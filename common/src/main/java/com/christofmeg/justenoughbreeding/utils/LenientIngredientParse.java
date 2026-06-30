package com.christofmeg.justenoughbreeding.utils;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LenientIngredientParse {

    // A helper record to mirror {"item": "..."} or {"tag": "..."}
    private record IngredientValue(Optional<String> item, Optional<String> tag) {}

    // Codec to parse a single object entry
    private static final Codec<IngredientValue> VALUE_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("item").forGetter(IngredientValue::item),
            Codec.STRING.optionalFieldOf("tag").forGetter(IngredientValue::tag)
    ).apply(instance, IngredientValue::new));

    // The master codec that accepts EITHER a single object OR a list of objects
    public static final Codec<Ingredient> LENIENT_CODEC = Codec.either(
            VALUE_CODEC,
            VALUE_CODEC.listOf()
    ).flatXmap(
            either -> {
                // Normalize single entry or list of entries into one list
                List<IngredientValue> entries = either.map(List::of, list -> list);
                List<Item> resolvedItems = new ArrayList<>();

                for (IngredientValue entry : entries) {
                    // 1. Handle Tag Object: {"tag": "minecraft:flowers"}
                    if (entry.tag().isPresent()) {
                        var tagKey = TagKey.create(BuiltInRegistries.ITEM.key(), Identifier.parse(entry.tag().get()));
                        var tagOptional = BuiltInRegistries.ITEM.get(tagKey);
                        tagOptional.ifPresent(holders -> holders.forEach(holder -> resolvedItems.add(holder.value())));
                    }
                    // 2. Handle Item Object: {"item": "minecraft:apple"}
                    else if (entry.item().isPresent()) {
                        BuiltInRegistries.ITEM.get(Identifier.parse(entry.item().get())).ifPresent(holder ->
                                resolvedItems.add(holder.value())
                        );
                    }
                }

                if (resolvedItems.isEmpty()) {
                    return DataResult.error(() -> "Ingredient entries could not resolve to any valid items!");
                }

                return DataResult.success(Ingredient.of(resolvedItems.stream()));
            },
            ingredient -> {
                // Fallback serializing back to list format for data-generation/saving
                List<IngredientValue> serializedValues = ingredient.items()
                        .map(holder -> new IngredientValue(Optional.of(BuiltInRegistries.ITEM.getKey(holder.value()).toString()), Optional.empty()))
                        .toList();
                return DataResult.success(Either.right(serializedValues));
            }
    );
}