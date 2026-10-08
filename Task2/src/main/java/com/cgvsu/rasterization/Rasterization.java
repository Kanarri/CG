package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class Rasterization {

    public static void fillTriangle(final GraphicsContext gc, int[] verX, int[] verY, Color color) {
        fillTriangle(gc, verX, verY, ColorProviders.solid(color));
    }

    public static void fillTriangle(final GraphicsContext gc, int[] verX, int[] verY, Color[] colors) {
        fillTriangle(gc, verX, verY, ColorProviders.interpolated(verX, verY, colors));
    }

    public static void fillTriangle(final GraphicsContext gc, int[] verX, int[] verY, ColorProvider provider) {
        int leftBorder  = Arrays.stream(verX).min().getAsInt();
        int rightBorder = Arrays.stream(verX).max().getAsInt();
        int upperBorder = Arrays.stream(verY).min().getAsInt();
        int lowerBorder = Arrays.stream(verY).max().getAsInt();
        int middleLine  = getMiddleY(verY);

        drawHalfOfTriangle(gc, upperBorder, middleLine, leftBorder, rightBorder, verX, verY, provider);
        drawHalfOfTriangle(gc, middleLine, lowerBorder, leftBorder, rightBorder, verX, verY, provider);
    }

    public static void drawHalfOfTriangle(final GraphicsContext graphicsContext, int y1, int y2, int leftBorder, int rightBorder, int[] verX, int[] verY, ColorProvider provider){
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int y = y1; y <= y2; y++){
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
                pixelWriter.setColor(x, y, provider.colorAt(x, y));
            }
        }
    }

    private static boolean isInTriangle(int[] X, int[] Y, int x, int y) {
        double[] barCoords = ColorProviders.getBarCoords(X, Y, x, y);
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
