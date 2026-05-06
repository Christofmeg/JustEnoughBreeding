package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
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

public class BreedingSerializer implements RecipeSerializer<BreedingRecipe> {

    @Override
    public @NotNull MapCodec<BreedingRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Codec.STRING.fieldOf("mod").forGetter(r -> r.mod()),
                        Codec.STRING.fieldOf("input_entity").forGetter(r -> r.inputEntity()),
                        CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                        Ingredient.CODEC.fieldOf("inputs").forGetter(r -> r.inputs()),
                        Ingredient.CODEC.optionalFieldOf("extra_inputs").forGetter(r -> Optional.of(r.extraInputs())),
                        Ingredient.CODEC.optionalFieldOf("outputs").forGetter(r -> Optional.of(r.outputs())),
                        Ingredient.CODEC.optionalFieldOf("spawn_eggs").forGetter(r -> Optional.of(r.spawnEgg())),
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
                    EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.parse(input_entity));
                    return new BreedingRecipe(
                            entityType,
                            CommonUtils.safe(inputs),
                            spawn_eggs.orElse(CommonUtils.safe(Ingredient.of(JustEnoughBreeding.getSpawnEggItem(entityType)))),
                            tamed.orElse(null),
                            outputs.orElse(Ingredient.EMPTY),
                            extra_inputs.orElse(Ingredient.EMPTY),
                            trusting.orElse(null),
                            mod,
                            JustEnoughBreeding.getKeyLoaderRegistries(entityType).getNamespace(),
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
                ResourceLocation entityRL = ResourceLocation.STREAM_CODEC.decode(buf);
                Ingredient inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient extraInputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                Ingredient outputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
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
                ResourceLocation.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType()));
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.extraInputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.outputs());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.spawnEgg());
                ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.tamed()));
                ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.trusting()));
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.mod());
                ByteBufCodecs.STRING_UTF8.encode(buf, recipe.inputEntity());
                ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt()));
            }
        };
    }
}