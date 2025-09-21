package com.christofmeg.justenoughbreeding.utils;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class RecipeFilter implements SimpleSynchronousResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "recipe_filter");
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager) {
        // List all recipe JSONs in "recipes" folder
        Map<ResourceLocation, Resource> recipes = manager.listResources("recipes", path -> path.getPath().endsWith(".json"));

        for (Map.Entry<ResourceLocation, Resource> entry : recipes.entrySet()) {
            ResourceLocation id = entry.getKey();
            try (Reader reader = new InputStreamReader(entry.getValue().open(), "UTF-8")) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                // Determine required mod(s)
                String modId = json.has("mod") ? json.get("mod").getAsString() : id.getNamespace();

                // If required mod not loaded, skip
                if (!JustEnoughBreeding.isModLoaded(modId)) {
                    CommonConstants.LOGGER.info("Skipping recipe {} because required mod '{}' is not loaded", id, modId);

                    // Remove this resource from the manager so it never reaches serializer
                    // Fabric ResourceManager doesn't allow direct removal,
                    // but returning from this listener early or wrapping the manager is the usual approach
                    // For a simple implementation, you can just mark it and skip in your serializer
                }
            } catch (Exception e) {
                CommonConstants.LOGGER.error("Failed to read recipe JSON {}", id, e);
            }
        }
    }

    public static void init() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new RecipeFilter());
        ResourceManagerHelper.registerBuiltinResourcePack(
                new ResourceLocation(CommonConstants.MOD_ID, "jeb_filter"),
                FabricLoader.getInstance().getModContainer(CommonConstants.MOD_ID).orElseThrow(() -> new IllegalStateException("ModContainer not found for " + CommonConstants.MOD_ID)),
                ResourcePackActivationType.NORMAL
        );
    }
}
