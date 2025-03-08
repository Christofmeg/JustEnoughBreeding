package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.config.JEBIntegration;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TamingRecipe;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;
import com.christofmeg.justenoughbreeding.recipe.TrustingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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

@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CommonConstants.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, CommonConstants.MOD_ID);

    public static final RegistryObject<RecipeType<BreedingRecipe>> BREEDING_PROVIDER_TYPE = RECIPE_TYPES.register("breeding_provider", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "breeding_provider")));
    public static final RegistryObject<RecipeSerializer<BreedingRecipe>> BREEDING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("breeding_provider", BreedingRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TamingRecipe>> TAMING_PROVIDER_TYPE = RECIPE_TYPES.register("taming_provider", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "taming_provider")));
    public static final RegistryObject<RecipeSerializer<TamingRecipe>> TAMING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("taming_provider", TamingRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TemperRecipe>> TEMPER_PROVIDER_TYPE = RECIPE_TYPES.register("temper_provider", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "temper_provider")));
    public static final RegistryObject<RecipeSerializer<TemperRecipe>> TEMPER_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("temper_provider", TemperRecipe.Serializer::new);

    public static final RegistryObject<RecipeType<TrustingRecipe>> TRUSTING_PROVIDER_TYPE = RECIPE_TYPES.register("trusting_provider", () -> RecipeType.simple(new ResourceLocation(CommonConstants.MOD_ID, "trusting_provider")));
    public static final RegistryObject<RecipeSerializer<TrustingRecipe>> TRUSTING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("trusting_provider", TrustingRecipe.Serializer::new);

    public static List<BreedingRecipe> breedingRecipes = new ArrayList<>();
    public static List<TamingRecipe> tamingRecipes = new ArrayList<>();
    public static List<TemperRecipe> temperRecipes = new ArrayList<>();
    public static List<TrustingRecipe> trustingRecipes = new ArrayList<>();

    public JustEnoughBreeding() {
        RECIPES_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RECIPE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(()-> NetworkConstants.IGNORESERVERONLY, (remote, isServer)-> true));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> JEBIntegration::init);
    }

    public static Item getItemFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }

    public static EntityType<?> getEntityFromLoaderRegistries(ResourceLocation resourceLocation) {
        return ForgeRegistries.ENTITY_TYPES.getValue(resourceLocation);
    }

    public static Boolean isModLoaded (String modID) {
        return ModList.get().isLoaded(modID);
    }

}