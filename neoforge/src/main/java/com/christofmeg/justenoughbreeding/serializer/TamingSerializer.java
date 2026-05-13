package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
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

public class TamingSerializer implements RecipeSerializer<TamingRecipe> {

    @Override
    public @NotNull MapCodec<TamingRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.STRING.fieldOf("mod").forGetter(TamingRecipe::mod),
                        Codec.STRING.fieldOf("input_entity").forGetter(TamingRecipe::inputEntity),
                        CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                        Ingredient.CODEC.fieldOf("inputs").forGetter(TamingRecipe::inputs),
                        Ingredient.CODEC.optionalFieldOf("extra_inputs").forGetter(r -> Optional.of(r.extraInputs())),
                        Ingredient.CODEC.optionalFieldOf("spawn_eggs").forGetter(r -> Optional.of(r.spawnEgg()))
                ).apply(instance, (
                        mod,
                        input_entity,
                        input_entity_nbt,
                        inputs,
                        extra_inputs,
                        spawn_eggs
                ) -> {
                    EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.parse(input_entity));
                    return new TamingRecipe(
                            entityType,
                            CommonUtils.safe(inputs),
                            spawn_eggs.orElse(CommonUtils.safe(Ingredient.of(JustEnoughBreeding.getSpawnEggItem(entityType)))),
                            extra_inputs.orElse(Ingredient.EMPTY),
                            mod,
                            JustEnoughBreeding.getKeyLoaderRegistries(entityType).getNamespace(),
                            input_entity_nbt.orElse(null)
                    );
                })
        );
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, TamingRecipe> streamCodec() {
        return new StreamCodec<>() {
            @Override
            public @NotNull TamingRecipe decode(@NotNull RegistryFriendlyByteBuf buf) {
                ResourceLocation entityRL = ResourceLocation.STREAM_CODEC.decode(buf);
                Ingredient inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient extraInputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient spawnEggIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                String modId = ByteBufCodecs.STRING_UTF8.decode(buf);
                String entity = ByteBufCodecs.STRING_UTF8.decode(buf);
                CompoundTag inputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                return new TamingRecipe(
                        JustEnoughBreeding.getEntityFromLoaderRegistries(entityRL),
                        inputIngredient,
                        spawnEggIngredient,
                        extraInputIngredient,
                        modId,
                        entity,
                        inputEntityNbt
                );
            }

            @Override
            public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull TamingRecipe recipe) {
                ResourceLocation.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.extraInputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.spawnEgg());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.mod());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.inputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt()));
            }
        };
    }
}