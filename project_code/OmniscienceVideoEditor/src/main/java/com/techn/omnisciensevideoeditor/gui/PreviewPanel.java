package com.techn.omnisciensevideoeditor.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.swing.JPanel;

public class PreviewPanel extends JPanel {

    private final SortedMap<LayerKey, Layer> layers = new TreeMap<>();
    private Dimension sourceDimension;

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (sourceDimension == null) {
            return;
        }
        Rectangle bounds = calculateBounds();
        for (Layer layer : layers.values()) {
            layer.paint(graphics, bounds);
        }
    }

    private Rectangle calculateBounds() {
        double ratio = Math.min((double) getWidth() / sourceDimension.getWidth(),
                (double) getHeight() / sourceDimension.getHeight());
        int width = (int) (ratio * sourceDimension.getWidth());
        int height = (int) (ratio * sourceDimension.getHeight());
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;
        return new Rectangle(x, y, width, height);
    }

    public void setSourceDimension(Dimension sourceDimension) {
        this.sourceDimension = sourceDimension;
    }

    public void setLayer(LayerKey key, Layer layer) {
        layers.put(key, layer);
    }
}
