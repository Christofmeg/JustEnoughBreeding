package com.christofmeg.justenoughbreeding.emi;

import com.christofmeg.justenoughbreeding.CommonConstants;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.widget.ButtonWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class EMIToggleButtonWidget extends ButtonWidget {

    private final Component component;
    public static boolean showChildButtons;

    public EMIToggleButtonWidget(int x, int y, ClickAction action, Component component) {
        super(x, y, 10, 10, 0, 0, ResourceLocation.fromNamespaceAndPath(CommonConstants.MOD_ID, "textures/emi_config_button.png"), () -> showChildButtons, action);
        this.component = component;
    }

    @Override
    public void render(GuiGraphics draw, int mouseX, int mouseY, float delta) {
        if (!showChildButtons) return;
        super.render(draw, mouseX, mouseY, delta);
    }
    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (!showChildButtons) return false;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
        return showChildButtons ? List.of(ClientTooltipComponent.create(EmiPort.ordered(component))) : List.of();
    }

}
