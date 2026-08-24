package com.christofmeg.justenoughbreeding.jei;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class JEIToggleButtonWidget implements IRecipeWidget {

    private final ScreenPosition pos;
    private final int w, h;
    private boolean toggled = false;

    public JEIToggleButtonWidget(int x, int y, int w, int h) {
        this.pos = new ScreenPosition(x, y);
        this.w = w;
        this.h = h;
    }

    @Override
    public @NotNull ScreenPosition getPosition() {
        return pos;
    }

    public void toggle() {
        toggled = !toggled;
    }

    public boolean isToggled() {
        return !toggled;
    }

    @Override
    public void drawWidget(@NotNull GuiGraphics graphics, double mouseX, double mouseY) {
        boolean hovered = mouseX >= 0 && mouseX < w && mouseY >= 0 && mouseY < h;

        int color;
        if (toggled) {
            color = 0x8080D580;
        } else {
            color = hovered ? 0x80FFFFFF : 0x40FFFFFF;
        }

        graphics.fill(0, 0, w, h, color);
    }

    @Override
    public void getTooltip(@NotNull ITooltipBuilder tooltip, double mouseX, double mouseY) {
        if (mouseX >= 0 && mouseX < w && mouseY >= 0 && mouseY < h) {
            tooltip.add(Component.translatable(
                    toggled ? "option.justenoughbreeding.hide_options" : "option.justenoughbreeding.show_options"
            ));
        }
    }
}

