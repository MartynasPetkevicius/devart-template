package com.techn.omnisciensevideoeditor.gui;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Layer {

    public abstract void paint(Graphics graphics, Rectangle bounds);
}
