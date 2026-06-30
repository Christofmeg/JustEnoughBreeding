package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRuntimeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.fabricmc.fabric.api.recipe.v1.sync.SynchronizedRecipes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
@JeiPlugin
public class JEIPlugin implements IModPlugin {

    public static SynchronizedRecipes recipeMap = null;

    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(CommonConstants.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new AllayDuplicationCategory(helper, Items.AMETHYST_SHARD),
                new BreedingCategory(helper, Items.WHEAT),
                new TamingCategory(helper, Items.BONE),
                new TemperCategory(helper, Items.GOLDEN_APPLE),
                new TransformationCategory(helper, Items.GOLDEN_CARROT),
                new TrustingCategory(helper, Items.SWEET_BERRIES)
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        JEIUtils.registerRecipes(registration);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        // This method fires when connection is active and data is fully ready!
        var connection = net.minecraft.client.Minecraft.getInstance().getConnection();
        if (connection == null) return;

        // Pull safely from the 1.21.11 network storage layer
        var recipeAccess = connection.recipes();
        var breedingRecipes = new ArrayList<>(
                recipeAccess.getSynchronizedRecipes().getAllOfType(JustEnoughBreeding.BREEDING_PROVIDER_TYPE)
        );

        // Sort recipes
        breedingRecipes.sort(Comparator.comparing(r -> r.value().entityType() == null ? "" : r.value().entityType().toShortString()));

        // Push directly into the live JEI manager view
        for (var recipeHold : breedingRecipes) {
            BreedingRecipe recipe = recipeHold.value();
            if (JustEnoughBreeding.isModLoaded(recipe.mod()) && recipe.entityType() != null) {
                jeiRuntime.getRecipeManager().addRecipes(BreedingCategory.TYPE, Collections.singletonList(recipe));
            }
        }

        System.out.println("====== [JustEnoughBreeding] Successfully injected runtime recipes: " + breedingRecipes.size());

    }

}