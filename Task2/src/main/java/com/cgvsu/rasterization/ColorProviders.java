package com.cgvsu.rasterization;

import javafx.scene.paint.Color;

public class ColorProviders {
    public static ColorProvider solid(Color color) {
        return (x, y) -> color;
    }

    public static ColorProvider interpolated(int[] verX, int[] verY, Color[] colors) {
        return (x, y) -> {
            double[] bc = getBarCoords(verX, verY, x, y);
            double r = bc[0] * colors[0].getRed()   + bc[1] * colors[1].getRed()   + bc[2] * colors[2].getRed();
            double g = bc[0] * colors[0].getGreen() + bc[1] * colors[1].getGreen() + bc[2] * colors[2].getGreen();
            double b = bc[0] * colors[0].getBlue()  + bc[1] * colors[1].getBlue()  + bc[2] * colors[2].getBlue();
            return new Color(clamp(r), clamp(g), clamp(b), 1);
        };
    }

    private static double clamp(double v) {
        //return v < 0 ? 0 : (v > 1 ? 1 : v);
        if (v < 0){
            return 0;
        } else if (v > 1){
            return 1;
        } else {
            return v;
        }
    }

    private static double[] getBarCoords(int[] X, int[] Y, int x, int y) {
        int[][] matrix = new int[][]{
                {X[0], X[1], X[2], x},
                {Y[0], Y[1], Y[2], y},
                {1, 1, 1, 1},
        };
        return Kramer.solveKramer(matrix);
    }
}