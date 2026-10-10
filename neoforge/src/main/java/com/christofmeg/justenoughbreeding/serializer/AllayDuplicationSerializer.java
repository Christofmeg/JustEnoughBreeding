package com.christofmeg.justenoughbreeding.serializer;

import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.AllayDuplicationRecipe;
import com.mojang.serialization.Codec;
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

public class AllayDuplicationSerializer {

    public static final RecipeSerializer<@NotNull AllayDuplicationRecipe> SERIALIZER =
            new RecipeSerializer<>(
                    RecordCodecBuilder.mapCodec(instance -> instance.group(
                            Codec.STRING.fieldOf("mod").forGetter(AllayDuplicationRecipe::mod),
                            Codec.STRING.fieldOf("input_entity").forGetter(AllayDuplicationRecipe::inputEntity),
                            CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(r -> Optional.ofNullable(r.inputEntityNbt())),
                            Ingredient.CODEC.fieldOf("inputs").forGetter(AllayDuplicationRecipe::inputs),
                            Ingredient.CODEC.optionalFieldOf("spawn_eggs").forGetter(r -> Optional.ofNullable(r.spawnEggs()))
                    ).apply(instance, (
                            mod,
                            inputEntity,
                            inputEntityNbt,
                            inputs,
                            spawnEggs
                    ) -> {
                        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(Identifier.parse(inputEntity));
                        return new AllayDuplicationRecipe(
                                entityType,
                                inputs,
                                spawnEggs.orElse(null),
                                mod,
                                JustEnoughBreeding.getKeyLoaderRegistries(entityType).toString(),
                                inputEntityNbt.orElse(null)
                        );
                    })),

                    new StreamCodec<>() {
                        @Override
                        public @NotNull AllayDuplicationRecipe decode(@NotNull RegistryFriendlyByteBuf buf) {
                            Identifier entityRL = Identifier.STREAM_CODEC.decode(buf);
                            Ingredient inputIngredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient spawnEggIngredient = Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.decode(buf).orElse(null);
                            String modId = ByteBufCodecs.STRING_UTF8.decode(buf);
                            String entity = ByteBufCodecs.STRING_UTF8.decode(buf);
                            CompoundTag inputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);
                            return new AllayDuplicationRecipe(
                                    JustEnoughBreeding.getEntityFromLoaderRegistries(entityRL),
                                    inputIngredient,
                                    spawnEggIngredient,
                                    modId,
                                    entity,
                                    inputEntityNbt
                            );
                        }

                        @Override
                        public void encode(@NotNull RegistryFriendlyByteBuf buf, @NotNull AllayDuplicationRecipe recipe) {
                            Identifier.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType()));
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputs());
                            Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC.encode(buf, Optional.ofNullable(recipe.spawnEggs()));
                            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.mod());
                            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.inputEntity());
                            ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt()));
                        }
                    }
            );
}