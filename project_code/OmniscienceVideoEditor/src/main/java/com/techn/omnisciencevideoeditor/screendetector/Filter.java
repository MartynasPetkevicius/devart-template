package com.techn.omnisciencevideoeditor.screendetector;

import java.awt.Color;

public class Filter {

    private double tolerance;

    public Filter(double tolerance) {
        this.tolerance = tolerance;
    }

    public boolean[][] filter(int[][] image) {
        if (image.length < 1) {
            throw new RuntimeException("Not an image");
        }
        int n = image.length;
        int m = image[0].length;
        boolean[][] binaryImage = new boolean[image.length][image[0].length];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                binaryImage[i][j] = colorFilterFunction(image[i][j]);
            }
        }
        return binaryImage;
    }

    private boolean colorFilterFunction(int rgb) {
        Color color = new Color(rgb);
        return Math.sqrt(color.getRed() * color.getRed() + color.getGreen() * color.getGreen()
                + color.getBlue() * color.getBlue()) <= tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }
}
