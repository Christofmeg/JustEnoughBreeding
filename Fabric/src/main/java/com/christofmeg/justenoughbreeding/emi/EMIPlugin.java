package com.christofmeg.justenoughbreeding.emi;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.render.EmiRenderable;
import net.minecraft.client.gui.GuiComponent;

public class EMIPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(BreedingCategoryEMI.TYPE);
        EMIUtils.registerMobBreedingRecipes(registry);
    }

    public static EmiRenderable simplifiedRenderer(int u, int v) {
        return (matrices, x, y, delta) -> {
            RenderSystem.setShaderTexture(0, EmiRenderHelper.WIDGETS);
            GuiComponent.blit(matrices, x, y, (float)u, (float)v, 16, 16, 256, 256);
        };
    }
}
