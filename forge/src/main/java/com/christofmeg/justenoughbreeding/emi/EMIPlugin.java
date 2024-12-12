package com.christofmeg.justenoughbreeding.emi;

import dev.emi.emi.EmiRenderHelper;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.render.EmiRenderable;
import dev.emi.emi.runtime.EmiDrawContext;

@EmiEntrypoint
public class EMIPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(BreedingCategoryEMI.TYPE);
        EMIUtils.registerMobBreedingRecipes(registry);
    }

    public static EmiRenderable simplifiedRenderer() {
        return (raw, x, y, delta) -> {
            EmiDrawContext context = EmiDrawContext.wrap(raw);
            context.drawTexture(EmiRenderHelper.WIDGETS, x, y, 208, 224, 16, 16);
        };
    }
}
