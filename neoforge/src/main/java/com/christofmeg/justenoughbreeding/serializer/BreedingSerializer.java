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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class BreedingSerializer implements RecipeSerializer<BreedingRecipe> {

    /*
    @Override
    public @NotNull BreedingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {
        return (BreedingRecipe) Utils.readJsonContents(jsonPath, json, "breeding");
    }

    @Override
    public @NotNull BreedingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf buf) {
        ResourceLocation entityRL = buf.readResourceLocation();
        EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(entityRL);
        if (entityType == null) {
            throw new JsonParseException("Unknown EntityType in BreedingRecipe#fromNetwork: " + entityRL);
        }

        Ingredient inputStack = Ingredient.fromNetwork(buf);    // never null
        Ingredient spawnEgg = Ingredient.fromNetwork(buf);

        boolean hasResult = buf.readBoolean();
        Ingredient resultItemStack = hasResult ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        boolean hasExtra = buf.readBoolean();
        Ingredient extraInputStack = hasExtra ? Ingredient.fromNetwork(buf) : Ingredient.EMPTY;

        Boolean needsToBeTamed = buf.readBoolean() ? buf.readBoolean() : null; // presence + value
        Boolean animalTrusting = buf.readBoolean() ? buf.readBoolean() : null;

        String jsonModID = buf.readUtf();
        String jsonAnimalID = buf.readUtf();
        String modFolder = buf.readUtf();
        String fileName = buf.readUtf();

        CompoundTag inputEntityNbt = buf.readBoolean() ? buf.readNbt() : null;

        return new BreedingRecipe(
                entityType,
                inputStack,
                spawnEgg,
                needsToBeTamed,
                resultItemStack,
                extraInputStack,
                animalTrusting,
                jsonModID,
                jsonAnimalID,
                modFolder,
                fileName,
                inputEntityNbt
        );
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull BreedingRecipe recipe) {
        ResourceLocation entityKey = JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType);
        if (entityKey == null) {
            throw new JsonParseException("Unknown EntityType in BreedingRecipe: " + recipe.entityType);
        }
        buf.writeResourceLocation(entityKey);

        CommonUtils.safe(recipe.inputStack).toNetwork(buf);
        CommonUtils.safe(recipe.spawnEgg).toNetwork(buf);

        boolean hasResult = recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty();
        buf.writeBoolean(hasResult);
        if (hasResult) {
            recipe.resultItemStack.toNetwork(buf);
        }

        boolean hasExtra = recipe.extraInputStack != null && !recipe.extraInputStack.isEmpty();
        buf.writeBoolean(hasExtra);
        if (hasExtra) {
            recipe.extraInputStack.toNetwork(buf);
        }

        if (recipe.needsToBeTamed != null) {
            buf.writeBoolean(true);
            buf.writeBoolean(recipe.needsToBeTamed);
        } else {
            buf.writeBoolean(false);
        }

        if (recipe.animalTrusting != null) {
            buf.writeBoolean(true);
            buf.writeBoolean(recipe.animalTrusting);
        } else {
            buf.writeBoolean(false);
        }

        buf.writeUtf(recipe.jsonModID);
        buf.writeUtf(recipe.jsonAnimalID);
        buf.writeUtf(recipe.modFolder);
        buf.writeUtf(recipe.fileName);

        if (recipe.inputEntityNbt != null) {
            buf.writeBoolean(true);
            buf.writeNbt(recipe.inputEntityNbt);
        } else {
            buf.writeBoolean(false);
        }
    }*/

    public static final MapCodec<BreedingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("mod").forGetter(r -> r.jsonModID),

                    // This handles the "mobs": [{"entity_name": {...}}] structure
                    Codec.list(Codec.unboundedMap(Codec.STRING, RecordCodecBuilder.<BreedingData>create(d -> d.group(
                            Ingredient.CODEC.listOf().fieldOf("inputs").forGetter(BreedingData::inputs),
                            Ingredient.CODEC.listOf().optionalFieldOf("extra_inputs", List.of()).forGetter(BreedingData::extra),
                            Ingredient.CODEC.listOf().optionalFieldOf("outputs", List.of()).forGetter(BreedingData::outputs),
                            Ingredient.CODEC.listOf().optionalFieldOf("spawn_eggs", List.of()).forGetter(BreedingData::eggs),
                            Codec.BOOL.optionalFieldOf("tamed").forGetter(BreedingData::tamed),
                            Codec.BOOL.optionalFieldOf("trusting").forGetter(BreedingData::trusting),
                            CompoundTag.CODEC.optionalFieldOf("input_entity_nbt").forGetter(BreedingData::nbt)
                    ).apply(d, BreedingData::new)))).fieldOf("mobs").forGetter(r -> {
                        // Reconstruct the nested structure for the getter
                        BreedingData data = new BreedingData(
                                List.of(r.inputStack), List.of(r.extraInputStack), List.of(r.resultItemStack), List.of(r.spawnEgg),
                                Optional.ofNullable(r.needsToBeTamed), Optional.ofNullable(r.animalTrusting), Optional.ofNullable(r.inputEntityNbt)
                        );
                        String entityKey = JustEnoughBreeding.getKeyLoaderRegistries(r.entityType).toString();
                        return List.of(java.util.Map.of(entityKey, data));
                    }),

                    Codec.STRING.fieldOf("animal_id").forGetter(r -> r.jsonAnimalID),
                    Codec.STRING.fieldOf("mod_folder").forGetter(r -> r.modFolder),
                    Codec.STRING.fieldOf("file_name").forGetter(r -> r.fileName)

            ).apply(instance, (mod, mobs, animalId, modFolder, fileName) -> {
                // Extracting from the list: [{"bee": {...}}]
                var firstMobEntry = mobs.getFirst();
                String entityKey = firstMobEntry.keySet().iterator().next();
                BreedingData data = firstMobEntry.get(entityKey);

                EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(ResourceLocation.parse(entityKey));

                return new BreedingRecipe(
                        entityType,
                        data.inputs().isEmpty() ? Ingredient.EMPTY : data.inputs().getFirst(),
                        data.eggs().isEmpty() ? Ingredient.EMPTY : data.eggs().getFirst(),
                        data.tamed().orElse(null),
                        data.outputs().isEmpty() ? Ingredient.EMPTY : data.outputs().getFirst(),
                        data.extra().isEmpty() ? Ingredient.EMPTY : data.extra().getFirst(),
                        data.trusting().orElse(null),
                        mod, animalId, modFolder, fileName,
                        data.nbt().orElse(null)
                );
            })
    );

    // A simple record to hold the temporary data structure during JSON parsing
    public record BreedingData(
            List<Ingredient> inputs,
            List<Ingredient> extra,
            List<Ingredient> outputs,
            List<Ingredient> eggs,
            Optional<Boolean> tamed,
            Optional<Boolean> trusting,
            Optional<CompoundTag> nbt
    ) {}

    public static final StreamCodec<RegistryFriendlyByteBuf, BreedingRecipe> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public @NotNull BreedingRecipe decode(@NotNull RegistryFriendlyByteBuf buf) {
            ResourceLocation entityRL = ResourceLocation.STREAM_CODEC.decode(buf);
            Ingredient inputStack = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient spawnEgg = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Boolean needsToBeTamed = ByteBufCodecs.optional(ByteBufCodecs.BOOL).decode(buf).orElse(null);
            Ingredient resultItemStack = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Ingredient extraInputStack = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            Boolean animalTrusting = ByteBufCodecs.optional(ByteBufCodecs.BOOL).decode(buf).orElse(null);
            String jsonModID = ByteBufCodecs.STRING_UTF8.decode(buf);
            String jsonAnimalID = ByteBufCodecs.STRING_UTF8.decode(buf);
            String modFolder = ByteBufCodecs.STRING_UTF8.decode(buf);
            String fileName = ByteBufCodecs.STRING_UTF8.decode(buf);
            CompoundTag inputEntityNbt = ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).decode(buf).orElse(null);

            return new BreedingRecipe(
                    JustEnoughBreeding.getEntityFromLoaderRegistries(entityRL),
                    inputStack, spawnEgg, needsToBeTamed, resultItemStack,
                    extraInputStack, animalTrusting, jsonModID, jsonAnimalID,
                    modFolder, fileName, inputEntityNbt
            );
        }

        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf buf, BreedingRecipe recipe) {
            ResourceLocation.STREAM_CODEC.encode(buf, JustEnoughBreeding.getKeyLoaderRegistries(recipe.entityType));
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputStack);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.spawnEgg);
            ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.needsToBeTamed));
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.resultItemStack);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.extraInputStack);
            ByteBufCodecs.optional(ByteBufCodecs.BOOL).encode(buf, Optional.ofNullable(recipe.animalTrusting));
            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.jsonModID);
            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.jsonAnimalID);
            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.modFolder);
            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.fileName);
            ByteBufCodecs.optional(ByteBufCodecs.COMPOUND_TAG).encode(buf, Optional.ofNullable(recipe.inputEntityNbt));
        }
    };

    @Override
    public @NotNull MapCodec<BreedingRecipe> codec() {
        return BreedingSerializer.CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, BreedingRecipe> streamCodec() {
        return BreedingSerializer.STREAM_CODEC;
    }
}