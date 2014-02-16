package com.techn.omnisciencevideoeditor.screendetector;

import java.awt.Color;

public class Filter {
    private static final Color FILTER_COLOR = new Color(0, 255, 0);
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
        int redDifference = color.getRed() - FILTER_COLOR.getRed();
        int greenDifference = color.getGreen()- FILTER_COLOR.getGreen();
        int blueDifference = color.getBlue() - FILTER_COLOR.getBlue();
        return Math.sqrt(redDifference * redDifference + greenDifference * greenDifference
                + blueDifference * blueDifference) <= tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }
}
