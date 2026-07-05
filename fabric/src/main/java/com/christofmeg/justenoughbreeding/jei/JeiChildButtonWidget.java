package com.christofmeg.justenoughbreeding.jei;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class JeiChildButtonWidget implements IRecipeWidget {

    private final ScreenPosition pos;
    private final int w, h;
    private final JEIToggleButtonWidget parent;
    private final Component tooltip;

    public JeiChildButtonWidget(int x, int y, int w, int h, JEIToggleButtonWidget parent, Component tooltip) {
        this.pos = new ScreenPosition(x, y);
        this.w = w;
        this.h = h;
        this.parent = parent;
        this.tooltip = tooltip;
    }

    @Override
    public @NotNull ScreenPosition getPosition() {
        return pos;
    }

    public boolean isVisible() {
        return parent.isToggled();
    }

    @Override
    public void drawWidget(@NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        if (isVisible()) return;

        boolean hovered = mouseX >= 0 && mouseX < w && mouseY >= 0 && mouseY < h;
        graphics.fill(0, 0, w, h, hovered ? 0x80FFFFFF : 0x40FFFFFF);
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltipBuilder, double mouseX, double mouseY) {
        if (isVisible()) return;

        if (mouseX >= 0 && mouseX < w && mouseY >= 0 && mouseY < h) {
            tooltipBuilder.add(tooltip);
        }
    }
}