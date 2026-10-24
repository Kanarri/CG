package com.cgvsu.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.Arrays;

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
        int middleLine  = getMiddle(verY);

        drawHalfOfTriangle(gc, upperBorder, middleLine, leftBorder, rightBorder, verX, verY, provider);
        drawHalfOfTriangle(gc, middleLine, lowerBorder, leftBorder, rightBorder, verX, verY, provider);
    }

    public static void drawHalfOfTriangle(final GraphicsContext graphicsContext, int y1, int y2, int leftBorder, int rightBorder, int[] verX, int[] verY, ColorProvider provider){
        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int y = y1; y <= y2; y++){
            int[] bordersX = getBorders(verX, verY, y);
            int leftX = bordersX[0];
            int rightX = bordersX[1];

            for (int x = leftX; x <= rightX; x++){
                pixelWriter.setColor(x, y, provider.colorAt(x, y));
            }
        }
    }

    private static int[] getBorders(int[] verX, int[] verY, int y) {
        int leftX = Integer.MAX_VALUE;
        int rightX = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            int j = (i + 1) % 3;
            int x1 = verX[i];
            int y1 = verY[i];
            int x2 = verX[j];
            int y2 = verY[j];

            if (y1 == y2) {
                if (y == y1) {
                    leftX  = Math.min(leftX,  Math.min(x1, x2));
                    rightX = Math.max(rightX, Math.max(x1, x2));
                }
                continue;
            }

            if (y < Math.min(y1, y2) || y > Math.max(y1, y2)) {
                continue;
            }

            double t = (double) (y - y1) / (y2 - y1);
            int x = (int) Math.round(x1 + (x2 - x1) * t);

            leftX  = Math.min(leftX,  x);
            rightX = Math.max(rightX, x);
        }

        return new int[]{Math.min(leftX, rightX), Math.max(leftX, rightX)};
    }

    private static int getMiddle(int[] coordsY) {
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