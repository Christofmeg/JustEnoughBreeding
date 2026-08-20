package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BreedingSerializer implements RecipeSerializer<@NotNull BreedingRecipe> {

    @Override
    public @NotNull MapCodec<BreedingRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.STRING.fieldOf("mod").forGetter(BreedingRecipe::mod),
                        Codec.STRING.fieldOf("input_entity").forGetter(BreedingRecipe::inputEntity),
                        CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                        Ingredient.CODEC.fieldOf("inputs").forGetter(BreedingRecipe::inputs),
                        Ingredient.CODEC.optionalFieldOf("extra_inputs").forGetter(r -> Optional.ofNullable(r.extraInputs())),
                        Ingredient.CODEC.optionalFieldOf("outputs").forGetter(r -> Optional.ofNullable(r.outputs())),
                        Ingredient.CODEC.optionalFieldOf("spawn_eggs").forGetter(r -> Optional.of(r.spawnEggs())),
                        Codec.BOOL.optionalFieldOf("tamed").forGetter(r -> Optional.ofNullable(r.tamed())),
                        Codec.BOOL.optionalFieldOf("trusting").forGetter(r -> Optional.ofNullable(r.trusting()))
                ).apply(instance, (
                        mod,
                        input_entity,
                        input_entity_nbt,
                        inputs,
                        extra_inputs,
                        outputs,
                        spawn_eggs,
                        tamed,
                        trusting
                ) -> {
                    EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.parse(input_entity));
                    Ingredient finalSpawnEggs = spawn_eggs.orElseGet(() -> Ingredient.of(JustEnoughBreeding.getSpawnEggItem(entityType)));
                    return new BreedingRecipe(
                            entityType,
                            inputs,
                            finalSpawnEggs,
                            tamed.orElse(null),
                            outputs.orElse(null),
                            extra_inputs.orElse(null),
                            trusting.orElse(null),
                            mod,
                            JustEnoughBreeding.getKeyLoaderRegistries(entityType).toString(),
                            input_entity_nbt.orElse(null)
                    );
                })
        );
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, BreedingRecipe> streamCodec() {
        return new StreamCodec<>() {
            @Override
            public @NotNull BreedingRecipe decode(@NotNull RegistryFriendlyByteBuf buf) {
                Identifier entityRL = Identifier.STREAM_CODEC.decode(buf);
                Ingredient inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient extraInputIngredient = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buf).orElse(null);
                Ingredient outputIngredient = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buf).orElse(null);
                Ingredient spawnEggIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Boolean needsToBeTamed = ByteBufCodecs.optional(ByteBufCodecs.BOOL).decode(buf).orElse(null);
                Boolean animalTrusting = ByteBufCodecs.optional(ByteBufCodecs.BOOL).decode(buf).orElse(null);
                String modId = ByteBufCodecs.STRING_UTF8.decode(buf);
                String entity = ByteBufCodecs.STRING_UTF8.decode(buf);
                CompoundTag inputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                return new BreedingRecipe(
                        JustEnoughBreeding.getEntityFromLoaderRegistries(entityRL),
                        inputIngredient,
                        spawnEggIngredient,
                        needsToBeTamed,
                        outputIngredient,
                        extraInputIngredient,
                        animalTrusting,
                        modId,
                        entity,
                        inputEntityNbt
                );
            }

            @Override
            public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull BreedingRecipe recipe) {
                Identifier.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputs());
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buf, Optional.ofNullable(recipe.extraInputs()));
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buf, Optional.ofNullable(recipe.outputs()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.spawnEggs());
                ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.tamed()));
                ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.trusting()));
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.mod());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.inputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt()));
            }
        };
    }
}