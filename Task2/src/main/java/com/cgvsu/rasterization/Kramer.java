package ru.vsu.cs.course1;

import java.util.ArrayList;
import java.util.List;

public class Kramer {
    public static List<String> solveKramer(double[][] arr) {
        List<String> answer = new ArrayList<>(); // список хов

        if (arr[0].length - arr.length != 1) {
            answer.add("Неправильная матрица");
            return answer;
        }
        double[][] mainmatrix = sliceMatrix(arr); // матрица без последнего столбца
        double main_det = Recursion.getDet(mainmatrix);

        if (main_det == 0) {
            answer.add("Решений нет или их бесконечно много");
            return answer;
        }
        for (int i = 0; i < arr.length; i++) {

            double[][] swapmatrix = swapMatrix(mainmatrix, arr, i);
            double det = Recursion.getDet(swapmatrix);
            answer.add(String.format("%.3f", det/main_det));
            //answer[i] = Recursion.getDet(swapMatrix(mainmatrix, arr, i))/main_det;
        }
        return answer;
    }

    static double[][] sliceMatrix(double[][] arr) { //отрезает последний столбик
        double[][] answer = new double[arr.length][arr[0].length-1];
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
                    answer[r][c] = arrlastcol[r][arrlastcol[0].length-1];
                } else {
                    answer[r][c] = arrlastcol[r][c];
                }

        }
        return answer;
    }

    public static String[] listToArray(double[] doubles) {
        String[] answer = new String[doubles.length];
        int i = 0;
        for (double v: doubles) {
            answer[i] = String.format("%.3f", v);
            i++;
        }
        return answer;
    }
    public static String[] listToArray(List<String> strings) {
        return strings.toArray(new String[0]);
    }
}
