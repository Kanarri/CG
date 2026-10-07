package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class Rasterization {

    /*public static void drawRectangle(
            final GraphicsContext graphicsContext,
            final int x, final int y,
            final int width, final int height,
            final Color color)
    {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        for (int row = y; row < y + height; ++row)
            for (int col = x; col < x + width; ++col)
                pixelWriter.setColor(col, row, color);
    }*/

    public static void fillTriangle(final GraphicsContext graphicsContext, int[] coordsX, int[] coordsY, Color[] colors) {
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        // bounding box
        int leftBorder = Arrays.stream(coordsX).min().getAsInt();
        int rightBorder = Arrays.stream(coordsX).max().getAsInt();
        int upperBorder = Arrays.stream(coordsY).min().getAsInt();
        int lowerBorder = Arrays.stream(coordsX).max().getAsInt();

        //средняя линия которая делит треугольник на верхнюю и нижнюю части
        int middleLine = getMiddleY(coordsY);
    }

    private static int getMiddleY(int[] coordsY) {
        int min = Arrays.stream(coordsY).min().getAsInt();
        int max = Arrays.stream(coordsY).max().getAsInt();
        int middle = -1;
        for (int i = 0; i < 3; i++){
            if (coordsY[i] > min & coordsY[i] <= max){
                middle = coordsY[i];
            }
        }
        return middle;
    }
}
