package com.techn.omnisciencevideoeditor.screendetector;

import com.techn.omnisciencevideoeditor.ImageUtils;
import java.awt.Color;
import java.awt.Dimension;

public class Filter {

    private static final Color COLOR_FILTER = new Color(0, 255, 0);
    private double tolerance;

    public Filter(double tolerance) {
        this.tolerance = tolerance;
    }

    public byte[][] filter(int[][] image) {
        Dimension dimension = ImageUtils.getImageDimension(image);
        byte[][] binaryImage = new byte[dimension.width][dimension.height];
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                binaryImage[x][y] = (byte) (colorFilterFunction(image[x][y]) ? 1 : 0);
            }
        }
        return binaryImage;
    }

    private boolean colorFilterFunction(int rgb) {
        Color color = new Color(rgb);
        int redDifference = color.getRed() - COLOR_FILTER.getRed();
        int greenDifference = color.getGreen() - COLOR_FILTER.getGreen();
        int blueDifference = color.getBlue() - COLOR_FILTER.getBlue();
        return Math.sqrt(redDifference * redDifference + greenDifference * greenDifference
                + blueDifference * blueDifference) <= tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }
}
