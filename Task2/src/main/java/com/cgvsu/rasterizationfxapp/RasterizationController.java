package com.cgvsu.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.paint.Color;

public class RasterizationController {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    final Color[] basicColors = new Color[]{Color.RED, Color.BLUE, Color.GREEN};
    final Triangle t1 = new Triangle(new int[]{0, 200, 350}, new int[]{0, 500, 200}, basicColors);

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        Rasterization.fillTriangle(canvas.getGraphicsContext2D(), t1.getX(), t1.getY(), t1.getColors());


        canvas.getGraphicsContext2D().setFill(Color.RED);
        //canvas.getGraphicsContext2D().fillPolygon(listToDouble(t1.getX()), listToDouble(t1.getY()), 3);
    }

    public double[] listToDouble(int[] arr){
        double[] ans = new double[3];
        for (int i = 0; i < 3; i++){
            ans[i] = arr[i];
        }
        return ans;
    };

}