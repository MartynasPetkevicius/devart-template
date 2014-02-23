package com.techn.omnisciencevideoeditor;

import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

public class MediaReader {

    private final FrameGrabber frameGrabber;

    public MediaReader(File videoFile) throws FrameGrabber.Exception {
        frameGrabber = new FFmpegFrameGrabber(videoFile);
        frameGrabber.start();
    }

    public Dimension getDimension() {
        return new Dimension(frameGrabber.getImageWidth(), frameGrabber.getImageHeight());
    }

    public BufferedImage getNextFrame() throws FrameGrabber.Exception {
        IplImage nextFrame = frameGrabber.grab();
        if (nextFrame == null) {
            frameGrabber.restart();
            nextFrame = frameGrabber.grab();
        }
        return nextFrame.getBufferedImage();
    }

    public void close() {
        try {
            frameGrabber.stop();
        } catch (FrameGrabber.Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}
