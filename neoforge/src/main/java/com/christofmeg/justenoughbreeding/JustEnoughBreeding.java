package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.serializer.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("deprecation")
@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, CommonConstants.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, CommonConstants.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, @NotNull RecipeType<@NotNull AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_TYPE = RECIPE_TYPES.register("allay_duplication", () -> new RecipeType<>() {});
    public static final DeferredHolder<RecipeSerializer<?>, @NotNull RecipeSerializer<@NotNull AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("allay_duplication", () -> new RecipeSerializer<>(AllayDuplicationSerializer.SERIALIZER.codec(), AllayDuplicationSerializer.SERIALIZER.streamCodec()));

    public static final DeferredHolder<RecipeType<?>, @NotNull RecipeType<@NotNull BreedingRecipe>> BREEDING_PROVIDER_TYPE = RECIPE_TYPES.register("breeding", () -> new RecipeType<>() {});
    public static final DeferredHolder<RecipeSerializer<?>, @NotNull RecipeSerializer<@NotNull BreedingRecipe>> BREEDING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("breeding", () -> new RecipeSerializer<>(BreedingSerializer.SERIALIZER.codec(), BreedingSerializer.SERIALIZER.streamCodec()));

    public static final DeferredHolder<RecipeType<?>, @NotNull RecipeType<@NotNull TamingRecipe>> TAMING_PROVIDER_TYPE = RECIPE_TYPES.register("taming", () -> new RecipeType<>() {});
    public static final DeferredHolder<RecipeSerializer<?>, @NotNull RecipeSerializer<@NotNull TamingRecipe>> TAMING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("taming", () -> new RecipeSerializer<>(TamingSerializer.SERIALIZER.codec(), TamingSerializer.SERIALIZER.streamCodec()));

    public static final DeferredHolder<RecipeType<?>, @NotNull RecipeType<@NotNull TemperRecipe>> TEMPER_PROVIDER_TYPE = RECIPE_TYPES.register("temper", () -> new RecipeType<>() {});
    public static final DeferredHolder<RecipeSerializer<?>, @NotNull RecipeSerializer<@NotNull TemperRecipe>> TEMPER_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("temper", () -> new RecipeSerializer<>(TemperSerializer.SERIALIZER.codec(), TemperSerializer.SERIALIZER.streamCodec()));

    public static final DeferredHolder<RecipeType<?>, @NotNull RecipeType<@NotNull TransformationRecipe>> TRANSFORMATION_PROVIDER_TYPE = RECIPE_TYPES.register("transformation", () -> new RecipeType<>() {});
    public static final DeferredHolder<RecipeSerializer<?>, @NotNull RecipeSerializer<@NotNull TransformationRecipe>> TRANSFORMATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("transformation", () -> new RecipeSerializer<>(TransformationSerializer.SERIALIZER.codec(), TransformationSerializer.SERIALIZER.streamCodec()));

    public static final DeferredHolder<RecipeType<?>, @NotNull RecipeType<@NotNull TrustingRecipe>> TRUSTING_PROVIDER_TYPE = RECIPE_TYPES.register("trusting", () -> new RecipeType<>() {});
    public static final DeferredHolder<RecipeSerializer<?>, @NotNull RecipeSerializer<@NotNull TrustingRecipe>> TRUSTING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("trusting", () -> new RecipeSerializer<>(TrustingSerializer.SERIALIZER.codec(), TrustingSerializer.SERIALIZER.streamCodec()));

    public JustEnoughBreeding(IEventBus modBus) {
        RECIPES_SERIALIZERS.register(modBus);
        RECIPE_TYPES.register(modBus);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(Identifier identifier) {
        return BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
    }

    public static Identifier getKeyLoaderRegistries(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    public static Optional<Holder<Item>> getSpawnEggItem(EntityType<?> entityType) {
        return SpawnEggItem.byId(entityType);
    }

    public static Boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }

    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

}