package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

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

    public static void fillTriangle(final GraphicsContext graphicsContext, int[] verX, int[] verY, Color[] colors) {

        Color color = Color.GREEN;
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        // bounding box
        int leftBorder = Arrays.stream(verX).min().getAsInt();
        int rightBorder = Arrays.stream(verX).max().getAsInt();
        int upperBorder = Arrays.stream(verY).min().getAsInt();
        int lowerBorder = Arrays.stream(verX).max().getAsInt();

        //средняя линия которая делит треугольник на верхнюю и нижнюю части
        int middleLine = getMiddleY(verY);
        
        //верхняя часть
        for (int y = upperBorder; y <= middleLine; y++){
            int leftX = 0;
            int rightX = 0;

            for (int x = leftBorder; x <= rightBorder; x++){
                if (isInTriangle(verX, verY, x, y)){
                    leftX = x;
                    break;
                }
            }

            for (int x = rightBorder; x >= leftBorder; x--){
                if (isInTriangle(verX, verY, x, y)){
                    rightX = x;
                    break;
                }
            }

            for (int x = leftX; x <= rightX; x++){
                pixelWriter.setColor(x, y, color);
            }

        }

    }

    private static boolean isInTriangle(int[] X, int[] Y, int x, int y) {
        int[][] matrix = new int[][]{
                {X[0], X[1], X[2], x},
                {Y[0], Y[1], Y[2], y},
                {1, 1, 1, 1},
        };
        double[] barCoords = Kramer.solveKramer(matrix);
        for (int i = 0; i < 3; i++){
            if (barCoords[i] < 0){
                return false;
            }
        }
        return true;
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
