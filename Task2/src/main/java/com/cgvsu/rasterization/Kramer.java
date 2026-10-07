package com.cgvsu.rasterization;

import java.util.Arrays;

public class Kramer {
    public static double[] solveKramer(int[][] array) {
        if (array[0].length - array.length != 1) {
            return null;
        }

        double[][] arr = Arrays.stream(array)
                .map(row -> Arrays.stream(row).asDoubleStream().toArray())
                .toArray(double[][]::new);
        double[][] mainmatrix = sliceMatrix(arr);
        double main_det = Recursion.getDet(mainmatrix);

        if (main_det == 0) {
            return null;
        }

        double[] answer = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            double[][] swapmatrix = swapMatrix(mainmatrix, arr, i);
            double det = Recursion.getDet(swapmatrix);
            answer[i] = det / main_det;
        }
        return answer;
    }

    static double[][] sliceMatrix(double[][] arr) {
        double[][] answer = new double[arr.length][arr[0].length - 1];
        for (int r = 0; r < answer.length; r++) {
            for (int c = 0; c < answer[0].length; c++) {
                answer[r][c] = arr[r][c];
            }
        }
        return answer;
    }

    private static double[][] swapMatrix(double[][] matrix, double[][] arrlastcol, int column) {
        double[][] answer = new double[matrix.length][matrix[0].length];
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++)
                if (c == column) {
                    answer[r][c] = arrlastcol[r][arrlastcol[0].length - 1];
                } else {
                    answer[r][c] = arrlastcol[r][c];
                }
        }
        return answer;
    }

    public static String[] listToArray(double[] doubles) {
        String[] answer = new String[doubles.length];
        int i = 0;
        for (double v : doubles) {
            answer[i] = String.format("%.3f", v);
            i++;
        }
        return answer;
    }
}