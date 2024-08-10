package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import me.shedaniel.rei.plugincompatibilities.api.REIPluginCompatIgnore;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@REIPluginCompatIgnore
@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(CommonConstants.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new BreedingCategory(helper, Items.WHEAT)
//                ,new TamingCategory(helper, Items.BONE),
//                new TemperCategory(helper, Items.GOLDEN_CARROT),
//                new TransformationCategory(helper, Items.GOLDEN_CARROT),
//                new TrustingCategory(helper, Items.SWEET_BERRIES)

        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        JEIUtils.registerMobBreedingRecipes(registration);
    }

}