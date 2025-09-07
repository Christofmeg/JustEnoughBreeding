package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("removal")
@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CommonConstants.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, CommonConstants.MOD_ID);

    public static final RegistryObject<RecipeType<AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_TYPE = RECIPE_TYPES.register("allay_duplication", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "allay_duplication")));
    public static final RegistryObject<RecipeSerializer<AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("allay_duplication", AllayDuplicationRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<BreedingRecipe>> BREEDING_PROVIDER_TYPE = RECIPE_TYPES.register("breeding", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "breeding")));
    public static final RegistryObject<RecipeSerializer<BreedingRecipe>> BREEDING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("breeding", BreedingRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TamingRecipe>> TAMING_PROVIDER_TYPE = RECIPE_TYPES.register("taming", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "taming")));
    public static final RegistryObject<RecipeSerializer<TamingRecipe>> TAMING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("taming", TamingRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TemperRecipe>> TEMPER_PROVIDER_TYPE = RECIPE_TYPES.register("temper", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "temper")));
    public static final RegistryObject<RecipeSerializer<TemperRecipe>> TEMPER_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("temper", TemperRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TrustingRecipe>> TRUSTING_PROVIDER_TYPE = RECIPE_TYPES.register("trusting", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "trusting")));
    public static final RegistryObject<RecipeSerializer<TrustingRecipe>> TRUSTING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("trusting", TrustingRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TransformationRecipe>> TRANSFORMATION_PROVIDER_TYPE = RECIPE_TYPES.register("transformation", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "transformation")));
    public static final RegistryObject<RecipeSerializer<TransformationRecipe>> TRANSFORMATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("transformation", TransformationRecipe.Serializer::new);

    public static List<AllayDuplicationRecipe> allayDuplicationRecipes = new ArrayList<>();
    public static List<BreedingRecipe> breedingRecipes = new ArrayList<>();
    public static List<TamingRecipe> tamingRecipes = new ArrayList<>();
    public static List<TemperRecipe> temperRecipes = new ArrayList<>();
    public static List<TransformationRecipe> transformationRecipes = new ArrayList<>();
    public static List<TrustingRecipe> trustingRecipes = new ArrayList<>();

    public JustEnoughBreeding() {
        RECIPES_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(()-> NetworkConstants.IGNORESERVERONLY, (remote, isServer)-> true));
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ENTITY_TYPES.getValue(resourceLocation);
    }

    public static Boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }

}