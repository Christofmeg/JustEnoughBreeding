package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TransformationRecipe;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TransformationSerializer implements RecipeSerializer<TransformationRecipe> {

    @Override
    public @NotNull MapCodec<TransformationRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.STRING.fieldOf("mod").forGetter(TransformationRecipe::mod),
                        Codec.STRING.fieldOf("input_entity").forGetter(TransformationRecipe::inputEntity),
                        CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                        Codec.STRING.fieldOf("output_entity").forGetter(TransformationRecipe::outputEntity),
                        CompoundTag.CODEC.optionalFieldOf("output_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                        Ingredient.CODEC.fieldOf("inputs").forGetter(TransformationRecipe::inputs),
                        Ingredient.CODEC.optionalFieldOf("extra_inputs").forGetter(r -> Optional.of(r.extraInputs())),
                        Ingredient.CODEC.optionalFieldOf("input_spawn_eggs").forGetter(r -> Optional.of(r.inputSpawnEggs())),
                        Ingredient.CODEC.optionalFieldOf("output_spawn_eggs").forGetter(r -> Optional.of(r.outputSpawnEggs())),
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
                        tamed
                ) -> {
                    EntityType<?> inputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.parse(input_entity));
                    EntityType<?> outputEntityType = JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.parse(output_entity));
                    return new TransformationRecipe(
                            inputEntityType,
                            CommonUtils.safe(inputs),
                            input_spawn_eggs.orElse(CommonUtils.safe(Ingredient.of(JustEnoughBreeding.getSpawnEggItem(inputEntityType)))),
                            extra_inputs.orElse(Ingredient.EMPTY),
                            outputEntityType,
                            output_spawn_eggs.orElse(CommonUtils.safe(Ingredient.of(JustEnoughBreeding.getSpawnEggItem(outputEntityType)))),
                            mod,
                            JustEnoughBreeding.getKeyLoaderRegistries(inputEntityType).getNamespace(),
                            input_entity_nbt.orElse(null),
                            JustEnoughBreeding.getKeyLoaderRegistries(outputEntityType).getNamespace(),
                            output_entity_nbt.orElse(null),
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
                ResourceLocation inputEntityRL = ResourceLocation.STREAM_CODEC.decode(buf);
                Ingredient inputs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient inputSpawnEggs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient extraInputs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                ResourceLocation outputEntityRL = ResourceLocation.STREAM_CODEC.decode(buf);
                Ingredient outputSpawnEggs = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                String modId = ByteBufCodecs.STRING_UTF8.decode(buf);
                String inputEntity = ByteBufCodecs.STRING_UTF8.decode(buf);
                CompoundTag inputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                String outputEntity = ByteBufCodecs.STRING_UTF8.decode(buf);
                CompoundTag outputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                Boolean needsToBeTamed = ByteBufCodecs.optional(ByteBufCodecs.BOOL).decode(buf).orElse(null);
                return new TransformationRecipe(
                        JustEnoughBreeding.getEntityFromLoaderRegistries(inputEntityRL),
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
                        needsToBeTamed
                );
            }

            @Override
            public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull TransformationRecipe recipe) {
                ResourceLocation.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.inputEntityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputSpawnEggs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.extraInputs());
                ResourceLocation.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.outputEntityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.outputSpawnEggs());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.mod());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.inputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt()));
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.outputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.outputEntityNbt()));
                ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.tamed()));
            }
        };
    }
}