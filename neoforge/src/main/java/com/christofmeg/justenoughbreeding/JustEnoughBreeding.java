package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.serializer.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    //TODO update recipe generator to match new 1.21.1 format

    private static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, CommonConstants.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, CommonConstants.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_TYPE = RECIPE_TYPES.register("allay_duplication", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("allay_duplication", AllayDuplicationSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<BreedingRecipe>> BREEDING_PROVIDER_TYPE = RECIPE_TYPES.register("breeding", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BreedingRecipe>> BREEDING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("breeding", BreedingSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<TamingRecipe>> TAMING_PROVIDER_TYPE = RECIPE_TYPES.register("taming", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TamingRecipe>> TAMING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("taming", TamingSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<TemperRecipe>> TEMPER_PROVIDER_TYPE = RECIPE_TYPES.register("temper", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TemperRecipe>> TEMPER_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("temper", TemperSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<TransformationRecipe>> TRANSFORMATION_PROVIDER_TYPE = RECIPE_TYPES.register("transformation", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TransformationRecipe>> TRANSFORMATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("transformation", TransformationSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<TrustingRecipe>> TRUSTING_PROVIDER_TYPE = RECIPE_TYPES.register("trusting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TrustingRecipe>> TRUSTING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("trusting", TrustingSerializer::new);

    public JustEnoughBreeding(IEventBus modBus) {
        RECIPES_SERIALIZERS.register(modBus);
        RECIPE_TYPES.register(modBus);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(ResourceLocation resourceLocation) {
        return BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);
    }

    public static ResourceLocation getKeyLoaderRegistries(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    public static SpawnEggItem getSpawnEggItem(EntityType<?> entityType) {
        return SpawnEggItem.byId(entityType);
    }

    public static Boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }

}