package com.christofmeg.justenoughbreeding.rei;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import net.minecraft.network.chat.Component;

public abstract class AbstractRecipeCategoryREI<T extends BasicDisplay> implements DisplayCategory<T> {

    private final CategoryIdentifier<T> type;
    private final Component title;
    private final Renderer icon;
    private final int width;
    private final int height;

    public AbstractRecipeCategoryREI(CategoryIdentifier<T> type, Component title, Renderer icon, int width, int height) {
        this.type = type;
        this.title = title;
        this.icon = icon;
        this.width = width;
        this.height = height;
    }

    @Override
    public CategoryIdentifier<? extends T> getCategoryIdentifier() {
        return type;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public Renderer getIcon() {
        return icon;
    }

    @Override
    public int getDisplayWidth(T display) {
        return width;
    }

    @Override
    public int getDisplayHeight() {
        return height;
    }

}
