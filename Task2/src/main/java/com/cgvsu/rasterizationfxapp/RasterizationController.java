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

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        Rasterization.fillTriangle(canvas.getGraphicsContext2D(), new int[]{100, 200, 350}, new int[]{100, 250, 800}, new Color[]{Color.RED, Color.BLUE, Color.GREEN});
//        Rasterization.drawRectangle(canvas.getGraphicsContext2D(), 200, 300, 200, 100, Color.CHOCOLATE);
//        Rasterization.drawRectangle(canvas.getGraphicsContext2D(), 250, 250, 50, 200, Color.AQUA);
    }

}