package com.techn.omnisciensevideoeditor.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImageLayer implements Layer {

    private BufferedImage image;

    public ImageLayer(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics graphics, Rectangle bounds) {
        graphics.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
