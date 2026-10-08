package com.cgvsu.rasterizationfxapp;

import javafx.scene.paint.Color;

public class Triangle{
    private final int[] x;
    private final int[] y;
    private final Color[] colors;

    public Triangle(int[] x, int[] y, Color[] colors){
        this.x = x;
        this.y = y;
        this.colors = colors;
    }

    public int[] getX(){
        return x;
    }
    public int[] getY(){
        return y;
    }
    public Color[] getColors(){
        return colors;
    }
}