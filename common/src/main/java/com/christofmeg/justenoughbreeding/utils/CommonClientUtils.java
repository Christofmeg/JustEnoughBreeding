package com.christofmeg.justenoughbreeding.utils;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;

public class CommonClientUtils {

    public static int getPixelColor(ResourceLocation texture, int px, int py) {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        try {
            Resource resource = resourceManager.getResource(texture).orElse(null);
            if (resource == null) return -7631989; // fallback

            try (InputStream is = resource.open()) {
                NativeImage image = NativeImage.read(is);

                // flip Y (Minecraft textures are usually top-left origin)
                int flippedY = image.getHeight() - 1 - py;

                // ABGR → ARGB conversion
                int abgr = image.getPixelRGBA(px, flippedY);
                int a = (abgr >> 24) & 0xFF;
                int b = (abgr >> 16) & 0xFF;
                int g = (abgr >> 8)  & 0xFF;
                int r = (abgr)       & 0xFF;
                return (a << 24) | (r << 16) | (g << 8) | b;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -7631989; // fallback
    }

    public static void fillSolidColor(GuiGraphics guiGraphics, int x, int y, int width, int height, int color) {
        guiGraphics.fill(x, y, x + width, y + height, color);
    }

}
