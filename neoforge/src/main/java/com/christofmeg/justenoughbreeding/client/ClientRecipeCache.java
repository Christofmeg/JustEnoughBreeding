package com.christofmeg.justenoughbreeding.client;

import com.christofmeg.justenoughbreeding.CommonConstants;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

import java.util.Collection;

@EventBusSubscriber(modid = CommonConstants.MOD_ID, value = Dist.CLIENT)
public class ClientRecipeCache {

    private static RecipeMap CLIENT_RECIPE_MAP = RecipeMap.EMPTY;

    @SubscribeEvent
    public static void onRecipesReceived(RecipesReceivedEvent event) {
        CLIENT_RECIPE_MAP = event.getRecipeMap();
    }

    public static Collection<RecipeHolder<?>> getRecipes() {
        return CLIENT_RECIPE_MAP.values();
    }
}