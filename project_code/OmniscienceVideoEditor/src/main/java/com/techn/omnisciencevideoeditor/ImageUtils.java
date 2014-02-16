package com.techn.omnisciencevideoeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;

public class ImageUtils {

    public static Dimension getImageDimension(int[][] array) {
        if (array.length < 1) {
            throw new RuntimeException("Not an image");
        }
        return new Dimension(array.length, array[0].length);
    }

    public static Dimension getImageDimension(byte[][] array) {
        if (array.length < 1) {
            throw new RuntimeException("Not an image");
        }
        return new Dimension(array.length, array[0].length);
    }

    public static BufferedImage arrayToBinaryImage(byte[][] array, Color color) {
        Dimension dimension = getImageDimension(array);
        IndexColorModel colorModel = new IndexColorModel(1, 2,
                new byte[]{0, (byte) color.getRed()},
                new byte[]{0, (byte) color.getGreen()},
                new byte[]{0, (byte) color.getBlue()},
                new byte[]{0, (byte) 0xFF});
        SampleModel sampleModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_BYTE,
                dimension.width, dimension.height, new int[]{0xFF});
        byte[] flatArray = flattenArray(array);
        DataBuffer dataBuffer = new DataBufferByte(flatArray, flatArray.length);
        Raster raster = Raster.createRaster(sampleModel, dataBuffer, new Point(0, 0));
        BufferedImage image = new BufferedImage(dimension.width, dimension.height,
                BufferedImage.TYPE_BYTE_INDEXED, colorModel);
        image.setData(raster);
        return image;
    }

    private static byte[] flattenArray(byte[][] array) {
        Dimension dimension = getImageDimension(array);
        byte[] flatArray = new byte[dimension.width * dimension.height];
        for (int y = 0, i = 0; y < dimension.height; y++) {
            for (int x = 0; x < dimension.width; x++, i++) {
                flatArray[i] = array[x][y];
            }
        }
        return flatArray;
    }

    public static int[][] bufferedImageToArray(BufferedImage image) {
        int[][] array = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            image.getRGB(x, 0, 1, image.getHeight(), array[x], 0, 1);
        }
        return array;
    }
}
