package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import mezz.jei.common.Internal;
import mezz.jei.common.gui.elements.DrawableNineSliceTexture;
import mezz.jei.common.gui.elements.DrawableSprite;
import mezz.jei.common.gui.textures.Textures;
import mezz.jei.gui.elements.GuiIconButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class JEIBookmarkButton extends GuiIconButton implements IRecipeWidget {

    private final ScreenPosition pos;
    private static final int SIZE = 16;
    private final int w, h;
    private IDrawable icon;
    private boolean pressed = false;
    private boolean forcePressed = false;

    public static JEIBookmarkButton create(int x, int y, int w, int h) {
        Textures textures = Internal.getTextures();
        IDrawableStatic offIcon = textures.getBookmarkButtonDisabledIcon();
        IDrawableStatic onIcon = textures.getBookmarkButtonEnabledIcon();
        IDrawableStatic icon = new DrawableSprite(textures.getSpriteUploader(), new ResourceLocation(CommonConstants.MOD_ID, "config_button"), 9, 9);
        return new JEIBookmarkButton(icon, x, y, w, h);
    }

    JEIBookmarkButton(IDrawable icon, int x, int y, int w, int h) {
        super(icon, b -> {});
        this.pos = new ScreenPosition(x, y);
        this.updateBounds(new Rect2i(x, y, SIZE, SIZE));
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    @Override
    public @NotNull ScreenPosition getPosition() {
        return pos;
    }

    @Override
    public void drawWidget(@NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        boolean hovered =
                mouseX >= this.getX() &&
                        mouseY >= this.getY() &&
                        mouseX < this.getX() + this.width &&
                        mouseY < this.getY() + this.height;
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        Textures textures = Internal.getTextures();
        boolean isPressed = this.pressed || this.forcePressed;
        DrawableNineSliceTexture texture = textures.getButtonForState(isPressed, this.active, hovered);
        texture.draw(graphics, this.getX(), this.getY(), this.width, this.height);

        int color = 0xFFE0E0E0;
        if (!this.active) {
            color = 0xFFA0A0A0;
        } else if (hovered) {
            color = 0xFFFFFFFF;
        }

        float red = (color >> 16 & 255) / 255.0F;
        float blue = (color >> 8 & 255) / 255.0F;
        float green = (color & 255) / 255.0F;
        float alpha = (color >> 24 & 255) / 255.0F;
        RenderSystem.setShaderColor(red, blue, green, alpha);

        double xOffset = getX() + (width - icon.getWidth()) / 2.0;
        double yOffset = getY() + (height - icon.getHeight()) / 2.0;
        if (isPressed) {
            xOffset += 0.5;
            yOffset += 0.5;
        }
        var poseStack = graphics.pose();
        poseStack.pushPose();
        {
            poseStack.translate(xOffset, yOffset, 0);
            icon.draw(graphics);
        }
        poseStack.popPose();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, double mouseX, double mouseY) {
        if (mouseX >= 0 && mouseX < w && mouseY >= 0 && mouseY < h) {
            tooltip.add(Component.literal(
                    pressed ? "Hide options" : "Show options"
            ));
        }
    }
    
}
