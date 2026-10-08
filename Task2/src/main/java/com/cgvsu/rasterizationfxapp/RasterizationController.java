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

    final Triangle dt1 = new Triangle(new int[]{0, 200, 350}, new int[]{0, 500, 200}, basicColors);
    final Triangle d2 = new Triangle(new int[]{10, 100, 200}, new int[]{100, 500, 20}, basicColors);
    final Triangle rt1 = new Triangle(new int[]{10, 10, 100}, new int[]{10, 200, 200}, basicColors);
    final Triangle rt2 = new Triangle(new int[]{10, 100, 10}, new int[]{10, 10, 150}, basicColors);
    final Triangle rt3 = new Triangle(new int[]{100, 100, 10}, new int[]{10, 150, 150}, basicColors);
    final Triangle rt4 = new Triangle(new int[]{10, 100, 100}, new int[]{10, 10, 150}, basicColors);
    final Triangle et1 = new Triangle(new int[]{10, 100, 200}, new int[]{100, 10, 100}, basicColors);
    final Triangle et2 = new Triangle(new int[]{10, 100, 200}, new int[]{10, 200, 10}, basicColors);
    final Triangle et3 = new Triangle(new int[]{10, 10, 150}, new int[]{10, 200, 100}, basicColors);
    final Triangle et4 = new Triangle(new int[]{10, 200, 200}, new int[]{100, 10, 250}, basicColors);



    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        parseAndDrawTriangleDIY(et4);
        parseAndDrawTriangleSYS(et4);

    }

    public void parseAndDrawTriangleDIY(Triangle t){
        Rasterization.fillTriangle(canvas.getGraphicsContext2D(), t.getX(), t.getY(), t.getColors());
    }
    public void parseAndDrawTriangleSYS(Triangle t){
        canvas.getGraphicsContext2D().setFill(Color.RED);
        canvas.getGraphicsContext2D().fillPolygon(listToDouble(t.getX()), listToDouble(t.getY()), 3);
    }

    public double[] listToDouble(int[] arr){
        double[] ans = new double[3];
        for (int i = 0; i < 3; i++){
            ans[i] = arr[i];
        }
        return ans;
    };

}