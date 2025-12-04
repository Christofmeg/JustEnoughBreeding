package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.serializer.*;
import com.christofmeg.justenoughbreeding.utils.RecipeFilter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

public class JustEnoughBreeding implements ModInitializer {

    //TODO look at EMI widget addDrawable for EMI transformation recipe

    public static final RecipeType<AllayDuplicationRecipe> ALLAY_DUPLICATION_PROVIDER_TYPE = RecipeType.register(new ResourceLocation(CommonConstants.MOD_ID, "allay_duplication").toString());
    public static final RecipeType<BreedingRecipe> BREEDING_PROVIDER_TYPE = RecipeType.register(new ResourceLocation(CommonConstants.MOD_ID, "breeding").toString());
    public static final RecipeType<TamingRecipe> TAMING_PROVIDER_TYPE = RecipeType.register(new ResourceLocation(CommonConstants.MOD_ID, "taming").toString());
    public static final RecipeType<TemperRecipe> TEMPER_PROVIDER_TYPE = RecipeType.register(new ResourceLocation(CommonConstants.MOD_ID, "temper").toString());
    public static final RecipeType<TrustingRecipe> TRUSTING_PROVIDER_TYPE = RecipeType.register(new ResourceLocation(CommonConstants.MOD_ID, "trusting").toString());
    public static final RecipeType<TransformationRecipe> TRANSFORMATION_PROVIDER_TYPE = RecipeType.register(new ResourceLocation(CommonConstants.MOD_ID, "transformation").toString());

    public static final RecipeSerializer<AllayDuplicationRecipe> ALLAY_DUPLICATION_PROVIDER_SERIALIZER =
            Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(CommonConstants.MOD_ID, "allay_duplication"),
                    new AllayDuplicationSerializer());

    public static final RecipeSerializer<BreedingRecipe> BREEDING_PROVIDER_SERIALIZER =
            Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(CommonConstants.MOD_ID, "breeding"),
                    new BreedingSerializer());

    public static final RecipeSerializer<TamingRecipe> TAMING_PROVIDER_SERIALIZER =
            Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(CommonConstants.MOD_ID, "taming"),
                    new TamingSerializer());

    public static final RecipeSerializer<TemperRecipe> TEMPER_PROVIDER_SERIALIZER =
            Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(CommonConstants.MOD_ID, "temper"),
                    new TemperSerializer());

    public static final RecipeSerializer<TrustingRecipe> TRUSTING_PROVIDER_SERIALIZER =
            Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(CommonConstants.MOD_ID, "trusting"),
                    new TrustingSerializer());

    public static final RecipeSerializer<TransformationRecipe> TRANSFORMATION_PROVIDER_SERIALIZER =
            Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
                    new ResourceLocation(CommonConstants.MOD_ID, "transformation"),
                    new TransformationSerializer());

    public static final List<AllayDuplicationRecipe> allayDuplicationRecipes = new ArrayList<>();
    public static final List<BreedingRecipe> breedingRecipes = new ArrayList<>();
    public static final List<TamingRecipe> tamingRecipes = new ArrayList<>();
    public static final List<TemperRecipe> temperRecipes = new ArrayList<>();
    public static final List<TransformationRecipe> transformationRecipes = new ArrayList<>();
    public static final List<TrustingRecipe> trustingRecipes = new ArrayList<>();

    @Override
    public void onInitialize() {
        RecipeFilter.init();
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return BuiltInRegistries.ITEM.get(resourceLocation);
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

    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

}
