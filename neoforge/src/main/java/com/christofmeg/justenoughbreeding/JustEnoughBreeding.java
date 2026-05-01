package com.christofmeg.justenoughbreeding;

import com.christofmeg.justenoughbreeding.recipe.*;
import com.christofmeg.justenoughbreeding.serializer.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

@Mod(CommonConstants.MOD_ID)
public class JustEnoughBreeding {

    //TODO update recipe generator to match input_entity_nbt on recipetypes
    //TODO update recipe generator to match input entity nbt on transformation

    private static final DeferredRegister<RecipeSerializer<?>> RECIPES_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CommonConstants.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, CommonConstants.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<?>> ALLAY_DUPLICATION_PROVIDER_TYPE = RECIPE_TYPES.register("allay_duplication", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "allay_duplication")));
    public static final DeferredHolder<RecipeSerializer<AllayDuplicationRecipe>, RecipeSerializer<AllayDuplicationRecipe>> ALLAY_DUPLICATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("allay_duplication", AllayDuplicationSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<?>> BREEDING_PROVIDER_TYPE = RECIPE_TYPES.register("breeding", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "breeding")));
    public static final DeferredHolder<RecipeSerializer<BreedingRecipe>, RecipeSerializer<BreedingRecipe>> BREEDING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("breeding", BreedingSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<?>> TAMING_PROVIDER_TYPE = RECIPE_TYPES.register("taming", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "taming")));
    public static final DeferredHolder<RecipeSerializer<TamingRecipe>, RecipeSerializer<TamingRecipe>> TAMING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("taming", TamingSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<?>> TEMPER_PROVIDER_TYPE = RECIPE_TYPES.register("temper", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "temper")));
    public static final DeferredHolder<RecipeSerializer<TemperRecipe>, RecipeSerializer<TemperRecipe>> TEMPER_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("temper", TemperSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<?>> TRUSTING_PROVIDER_TYPE = RECIPE_TYPES.register("trusting", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "trusting")));
    public static final DeferredHolder<RecipeSerializer<TrustingRecipe>, RecipeSerializer<TrustingRecipe>>TRUSTING_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("trusting", TrustingSerializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<?>> TRANSFORMATION_PROVIDER_TYPE = RECIPE_TYPES.register("transformation", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "transformation")));
    public static final DeferredHolder<RecipeSerializer<TransformationRecipe>, RecipeSerializer<TransformationRecipe>> TRANSFORMATION_PROVIDER_SERIALIZER = RECIPES_SERIALIZERS.register("transformation", TransformationSerializer::new);

    public static List<RecipeHolder<Recipe<?>>> allayDuplicationRecipes = new ArrayList<net.minecraft.world.item.crafting.RecipeHolder<net.minecraft.world.item.crafting.Recipe<?>>>();
    public static List<RecipeHolder<Recipe<?>>> breedingRecipes = new ArrayList<net.minecraft.world.item.crafting.RecipeHolder<net.minecraft.world.item.crafting.Recipe<?>>>();
    public static List<RecipeHolder<Recipe<?>>> tamingRecipes = new ArrayList<net.minecraft.world.item.crafting.RecipeHolder<net.minecraft.world.item.crafting.Recipe<?>>>();
    public static List<TemperRecipe> temperRecipes = new ArrayList<>();
    public static List<TransformationRecipe> transformationRecipes = new ArrayList<>();
    public static List<RecipeHolder<Recipe<?>>> trustingRecipes = new ArrayList<net.minecraft.world.item.crafting.RecipeHolder<net.minecraft.world.item.crafting.Recipe<?>>>();

    public JustEnoughBreeding() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        RECIPES_SERIALIZERS.register(modBus);
        RECIPE_TYPES.register(modBus);
        modBus.register(this);

        //Make sure the mod being absent on the other network side does not cause the client to display the server as incompatible
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(()-> NetworkConstants.IGNORESERVERONLY, (remote, isServer)-> true));
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
        return ForgeSpawnEggItem.fromEntityType(entityType);
    }

    public static Boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }

}