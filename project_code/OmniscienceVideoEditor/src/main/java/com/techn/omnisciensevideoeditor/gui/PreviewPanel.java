package com.techn.omnisciensevideoeditor.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class PreviewPanel extends JPanel {

    private BufferedImage image;

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (image == null) {
            return;
        }
        Rectangle bounds = calculateBounds();
        paintImage(graphics, bounds);
    }

    private Rectangle calculateBounds() {
        double ratio = Math.min((double) getWidth() / image.getWidth(),
                (double) getHeight() / image.getHeight());
        int width = (int) (ratio * image.getWidth());
        int height = (int) (ratio * image.getHeight());
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;
        return new Rectangle(x, y, width, height);
    }

    private void paintImage(Graphics graphics, Rectangle bounds) {
        graphics.drawImage(image, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
