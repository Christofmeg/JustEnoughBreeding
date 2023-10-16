package com.christofmeg.justenoughbreeding.platform;

import com.christofmeg.justenoughbreeding.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * The ForgePlatformHelper class provides platform-specific utility methods for Forge.
 * It implements the IPlatformHelper interface.
 */
public class ForgePlatformHelper implements IPlatformHelper {

    /**
     * Gets the name of the current platform.
     * @return The name of the current platform.
     */
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    /**
     * Checks if a mod with the given ID is loaded.
     * @param modId The ID of the mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    /**
     * Checks if the game is currently in a development environment.
     * @return True if the game is in a development environment, false otherwise.
     */
    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }
}