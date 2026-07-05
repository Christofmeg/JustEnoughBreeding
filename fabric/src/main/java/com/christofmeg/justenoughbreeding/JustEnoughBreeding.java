package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.serializer.*;
import com.christofmeg.justenoughbreeding.utils.Utils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class JustEnoughBreeding implements ModInitializer {

    public static final RecipeType<AllayDuplicationRecipe> ALLAY_DUPLICATION_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication"), Utils.simple(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication")));
    public static final RecipeSerializer<AllayDuplicationRecipe> ALLAY_DUPLICATION_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication"), new AllayDuplicationSerializer());

    public static final RecipeType<BreedingRecipe> BREEDING_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding"), Utils.simple(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding")));
    public static final RecipeSerializer<BreedingRecipe> BREEDING_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding"), new BreedingSerializer());

    public static final RecipeType<TamingRecipe> TAMING_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming"), Utils.simple(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming")));
    public static final RecipeSerializer<TamingRecipe> TAMING_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming"), new TamingSerializer());

    public static final RecipeType<TemperRecipe> TEMPER_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper"), Utils.simple(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper")));
    public static final RecipeSerializer<TemperRecipe> TEMPER_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper"), new TemperSerializer());

    public static final RecipeType<TransformationRecipe> TRANSFORMATION_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation"), Utils.simple(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation")));
    public static final RecipeSerializer<TransformationRecipe> TRANSFORMATION_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation"), new TransformationSerializer());

    public static final RecipeType<TrustingRecipe> TRUSTING_PROVIDER_TYPE = Registry.register(BuiltInRegistries.RECIPE_TYPE, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting"), Utils.simple(Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting")));
    public static final RecipeSerializer<TrustingRecipe> TRUSTING_PROVIDER_SERIALIZER = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting"), new TrustingSerializer());

    @Override
    public void onInitialize() {
        if (isModLoaded("jei") || isModLoaded("roughlyenoughitems")) {
            RecipeSynchronization.synchronizeRecipeSerializer(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_SERIALIZER);
            RecipeSynchronization.synchronizeRecipeSerializer(JustEnoughBreeding.BREEDING_PROVIDER_SERIALIZER);
            RecipeSynchronization.synchronizeRecipeSerializer(JustEnoughBreeding.TAMING_PROVIDER_SERIALIZER);
            RecipeSynchronization.synchronizeRecipeSerializer(JustEnoughBreeding.TEMPER_PROVIDER_SERIALIZER);
            RecipeSynchronization.synchronizeRecipeSerializer(JustEnoughBreeding.TRANSFORMATION_PROVIDER_SERIALIZER);
            RecipeSynchronization.synchronizeRecipeSerializer(JustEnoughBreeding.TRUSTING_PROVIDER_SERIALIZER);
        }
    }

    //TODO fix JEI buttons not working
    //TODO fix entity scaling
    //TODO entity shearing (saddle off)
    //TODO wolf armor, horse armor, nautilus armor, llama chests, pig saddle, horse saddle, llama saddle
    //TODO wolf dying + others?
    //TODO cycle through mob variants (cold, normal, warm chicken)
    //TODO fix JEI transformation category rendering mobs on top of each other

    public static EntityType<?> getEntityFromLoaderRegistries(Identifier identifier) {
        return BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
    }

    public static Identifier getKeyLoaderRegistries(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    public static SpawnEggItem getSpawnEggItem(EntityType<?> entityType) {
        return SpawnEggItem.byId(entityType);
    }

    public static Boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

}