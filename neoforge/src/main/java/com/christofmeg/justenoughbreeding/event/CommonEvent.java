package com.christofmeg.justenoughbreeding.event;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

@EventBusSubscriber(modid = CommonConstants.MOD_ID)
public class CommonEvent {

    @SubscribeEvent
    public static void onRecipesUpdated(RecipesUpdatedEvent event) {
        RecipeManager manager = event.getRecipeManager();
/*
        JustEnoughBreeding.allayDuplicationRecipes.clear();
        JustEnoughBreeding.allayDuplicationRecipes.addAll(
                manager.getAllRecipesFor(JustEnoughBreeding.ALLAY_DUPLICATION_PROVIDER_TYPE.get())
        );
*/
        JustEnoughBreeding.breedingRecipes.clear();
        JustEnoughBreeding.breedingRecipes.addAll(
                manager.getAllRecipesFor(JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get())
        );
/*
        JustEnoughBreeding.tamingRecipes.clear();
        JustEnoughBreeding.tamingRecipes.addAll(
                manager.getAllRecipesFor(JustEnoughBreeding.TAMING_PROVIDER_TYPE.get())
        );

        JustEnoughBreeding.temperRecipes.clear();
        JustEnoughBreeding.temperRecipes.addAll(
                manager.getAllRecipesFor(JustEnoughBreeding.TEMPER_PROVIDER_TYPE.get())
        );

        JustEnoughBreeding.transformationRecipes.clear();
        JustEnoughBreeding.transformationRecipes.addAll(
                manager.getAllRecipesFor(JustEnoughBreeding.TRANSFORMATION_PROVIDER_TYPE.get())
        );

        JustEnoughBreeding.trustingRecipes.clear();
        JustEnoughBreeding.trustingRecipes.addAll(
                manager.getAllRecipesFor(JustEnoughBreeding.TRUSTING_PROVIDER_TYPE.get())
        );*/
    }

}
