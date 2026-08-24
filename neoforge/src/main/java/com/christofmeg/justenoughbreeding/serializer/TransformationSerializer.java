package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
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

public class TransformationSerializer implements RecipeSerializer<@NotNull TransformationRecipe> {

    @Override
    public @NotNull MapCodec<TransformationRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.STRING.fieldOf("mod").forGetter(TransformationRecipe::mod),
                        Codec.STRING.fieldOf("input_entity").forGetter(TransformationRecipe::inputEntity),
                        CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                        Codec.STRING.fieldOf("output_entity").forGetter(TransformationRecipe::outputEntity),
                        CompoundTag.CODEC.optionalFieldOf("output_entity_nbt").forGetter(r -> Optional.ofNullable(r.outputEntityNbt())),
                        Ingredient.CODEC.fieldOf("inputs").forGetter(TransformationRecipe::inputs),
                        Ingredient.CODEC.optionalFieldOf("extra_inputs").forGetter(r -> Optional.ofNullable(r.extraInputs())),
                        Ingredient.CODEC.optionalFieldOf("input_spawn_eggs").forGetter(r -> Optional.of(r.inputSpawnEggs())),
                        Ingredient.CODEC.optionalFieldOf("output_spawn_eggs").forGetter(r -> Optional.of(r.outputSpawnEggs())),
                        Ingredient.CODEC.optionalFieldOf("outputs").forGetter(r -> Optional.ofNullable(r.outputs())),
                        Codec.BOOL.optionalFieldOf("tamed").forGetter(r -> Optional.ofNullable(r.tamed()))
                ).apply(instance, (
                        mod,
                        input_entity,
                        input_entity_nbt,
                        output_entity,
                        output_entity_nbt,
                        inputs,
                        extra_inputs,
                        input_spawn_eggs,
                        output_spawn_eggs,
                        outputs,
                        tamed
                ) -> {
                    Identifier inputEntity = Identifier.parse(input_entity);
                    EntityType<?> inputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(inputEntity);
                    EntityType<?> outputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.parse(output_entity));
                    Ingredient finalInputSpawnEggs = input_spawn_eggs.orElseGet(() -> Ingredient.of(JustEnoughBreeding.getSpawnEggItem(inputEntityType)));
                    Ingredient finalOutputSpawnEggs = output_spawn_eggs.orElseGet(() -> Ingredient.of(JustEnoughBreeding.getSpawnEggItem(outputEntityType)));
                    String inputEntityTypeCompare = inputEntityType.toString().substring("entity.".length()).replaceFirst("\\.", ":");
                    return new TransformationRecipe(
                            inputEntity.toString().equals(inputEntityTypeCompare) ? inputEntityType.toString() : null,
                            inputs,
                            finalInputSpawnEggs,
                            extra_inputs.orElse(null),
                            outputEntityType,
                            finalOutputSpawnEggs,
                            mod,
                            JustEnoughBreeding.getKeyLoaderRegistries(inputEntityType).toString(),
                            input_entity_nbt.orElse(null),
                            JustEnoughBreeding.getKeyLoaderRegistries(outputEntityType).toString(),
                            output_entity_nbt.orElse(null),
                            outputs.orElse(null),
                            tamed.orElse(null)
                    );
                })
        );
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, TransformationRecipe> streamCodec() {
        return new StreamCodec<>() {
            @Override
            public @NotNull TransformationRecipe decode(@NotNull RegistryFriendlyByteBuf buf) {
                String inputEntityType = ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).decode(buf).orElse(null);
                Ingredient inputs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient inputSpawnEggs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient extraInputs = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buf).orElse(null);
                Identifier outputEntityRL = Identifier.STREAM_CODEC.decode(buf);
                Ingredient outputSpawnEggs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                String modId = ByteBufCodecs.STRING_UTF8.decode(buf);
                String inputEntity = ByteBufCodecs.STRING_UTF8.decode(buf);
                CompoundTag inputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                String outputEntity = ByteBufCodecs.STRING_UTF8.decode(buf);
                CompoundTag outputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                Ingredient outputIngredient = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buf).orElse(null);
                Boolean needsToBeTamed = ByteBufCodecs.optional(ByteBufCodecs.BOOL).decode(buf).orElse(null);
                return new TransformationRecipe(
                        inputEntityType,
                        inputs,
                        inputSpawnEggs,
                        extraInputs,
                        JustEnoughBreeding.getEntityFromLoaderRegistries(outputEntityRL),
                        outputSpawnEggs,
                        modId,
                        inputEntity,
                        inputEntityNbt,
                        outputEntity,
                        outputEntityNbt,
                        outputIngredient,
                        needsToBeTamed
                );
            }

            @Override
            public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull TransformationRecipe recipe) {
                ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8).encode(buf, Optional.ofNullable(recipe.inputEntityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputSpawnEggs());
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buf, Optional.ofNullable(recipe.extraInputs()));
                Identifier.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.outputEntityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.outputSpawnEggs());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.mod());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.inputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt()));
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.outputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.outputEntityNbt()));
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buf, Optional.ofNullable(recipe.outputs()));
                ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.tamed()));
            }
        };
    }
}