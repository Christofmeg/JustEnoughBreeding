package com.christofmeg.justenoughbreeding.utils;

public record Rect(int x, int y, int width, int height) {
    public int right() {
        return x + width;
    }

    public int bottom() {
        return y + height;
    }

}