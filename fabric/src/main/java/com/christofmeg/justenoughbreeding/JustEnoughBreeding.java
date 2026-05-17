package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.serializer.*;
import com.christofmeg.justenoughbreeding.utils.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class JustEnoughBreeding implements ModInitializer {

    public static final RecipeType<AllayDuplicationRecipe> ALLAY_DUPLICATION_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication"), Utils.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication")));
    public static final RecipeSerializer<AllayDuplicationRecipe> ALLAY_DUPLICATION_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication"), new AllayDuplicationSerializer());

    public static final RecipeType<BreedingRecipe> BREEDING_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding"), Utils.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding")));
    public static final RecipeSerializer<BreedingRecipe> BREEDING_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding"), new BreedingSerializer());

    public static final RecipeType<TamingRecipe> TAMING_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming"), Utils.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming")));
    public static final RecipeSerializer<TamingRecipe> TAMING_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming"), new TamingSerializer());

    public static final RecipeType<TemperRecipe> TEMPER_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper"), Utils.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper")));
    public static final RecipeSerializer<TemperRecipe> TEMPER_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper"), new TemperSerializer());

    public static final RecipeType<TransformationRecipe> TRANSFORMATION_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation"), Utils.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation")));
    public static final RecipeSerializer<TransformationRecipe> TRANSFORMATION_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation"), new TransformationSerializer());

    public static final RecipeType<TrustingRecipe> TRUSTING_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting"), Utils.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting")));
    public static final RecipeSerializer<TrustingRecipe> TRUSTING_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting"), new TrustingSerializer());

    @Override
    public void onInitialize() {}

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
        return FabricLoader.getInstance().isModLoaded(modID);
    }

}