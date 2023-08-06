package com.christofmeg.justenoughbreeding.platform;

import com.christofmeg.justenoughbreeding.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    /**
     * Gets the name of the current platform
     * @return The name of the current platform.
     */
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    /**
     * Checks if a mod with the given id is loaded.
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    /**
     * Check if the game is currently in a development environment.
     * @return True if in a development environment, false otherwise.
     */
    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
