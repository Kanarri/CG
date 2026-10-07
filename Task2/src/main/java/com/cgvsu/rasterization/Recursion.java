package com.cgvsu.rasterization;

public class Recursion {
    public static double getDet(double[][] arr) {
        if (arr.length != arr[0].length) {
            return -1;
        }

        if (arr.length == 1) {
            return arr[0][0];
        }
        if (arr.length == 2) {
            return arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];
        }
        double det = 0;
        for (int i = 0; i < arr.length; i++) {
            int sign = (i % 2 == 0) ? 1 : -1;
            det += sign * arr[i][0] * getDet(sliceArray(arr, i));

        }
        return det;
    }

    private static double[][] sliceArray(double[][] arr, int skip) {
        double n = arr.length - 1;
        double[][] answer = new double[(int) n][(int) n];

        int answerRow = 0;
        for (int r = 0; r < arr.length; r++) {
            if (r == skip) {
                continue;
            }
            int currentCol = 0;
            for (int c = 1; c < arr.length; c++) {
                answer[answerRow][currentCol] = arr[r][c];
                currentCol++;
            }
            answerRow++;
        }
        return answer;
    }

    public static StringBuilder getDetString(double[][] arr) {
        if (arr.length != arr[0].length) {
            return null;
        }
        String[][] matrix = new String[arr.length][arr.length];
        for (int r = 0; r < arr.length; r++){
            for (int c = 0; c < arr.length; c++){
                if (r == c){
                    matrix[r][c] = Double.toString(arr[r][c]) + "-x";
                } else {
                    matrix[r][c] = Double.toString(arr[r][c]);
                }
            }
        }
        StringBuilder answer = buildDetExpr(matrix, null);
        return new StringBuilder("(").append(answer).append(")");
    }

    private static StringBuilder buildDetExpr(String[][] arr, StringBuilder det) {
        if (det == null) {
            det = new StringBuilder();
        }
        int n = arr.length;
        if (n == 1) {
            det.append("(").append(arr[0][0]).append(")");
            return det;
        }

        if (n == 2) {
            det.append("((")
                    .append(arr[0][0]).append(")*(").append(arr[1][1])
                    .append(")-(")
                    .append(arr[0][1]).append(")*(").append(arr[1][0])
                    .append("))");
            return det;
        }
        det.append("(");
        for (int col = 0; col < n; col++) {
            String sign = (col % 2 == 0) ? "+" : "-";
            String[][] minor = getMinor(arr, 0, col);

            StringBuilder minorExpr = new StringBuilder();
            buildDetExpr(minor, minorExpr);

            if (col == 0) {
                det.append("(").append(arr[0][col]).append(")*").append(minorExpr);
            } else {
                det.append(sign).append("(").append(arr[0][col]).append(")*").append(minorExpr);
            }
        }
        det.append(")");
        return det;
    }

    private static String[][] getMinor(String[][] arr, int skipRow, int skipCol) {
        int n = arr.length;
        String[][] minor = new String[n - 1][n - 1];

        int minorRow = 0;
        for (int r = 0; r < n; r++) {
            if (r == skipRow) continue;

            int minorCol = 0;
            for (int c = 0; c < n; c++) {
                if (c == skipCol) continue;
                minor[minorRow][minorCol] = arr[r][c];
                minorCol++;
            }
            minorRow++;
        }
        return minor;
    }

}

