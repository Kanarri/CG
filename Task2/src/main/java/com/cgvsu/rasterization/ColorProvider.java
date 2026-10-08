package com.cgvsu.rasterization;

import javafx.scene.paint.Color;

@FunctionalInterface
public interface ColorProvider {
    Color colorAt(int x, int y);
}