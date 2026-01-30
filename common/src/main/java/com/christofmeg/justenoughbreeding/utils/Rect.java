package com.christofmeg.justenoughbreeding.utils;

public record Rect(int x, int y, int width, int height) {
    public int right() {
        return x + width;
    }

    public int bottom() {
        return y + height;
    }

    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
    }
}