package com.techn.omnisciensevideoeditor.gui;

import com.techn.omnisciencevideoeditor.ImageUtils;
import com.techn.omnisciencevideoeditor.screendetector.Filter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Gui {

    private static final int INNER_PADDING = 5;
    private static final int OUTER_BORDER = 10;
    private JFrame frame;
    private JTextField sourceTextField;
    private PreviewPanel previewPanel;

    public Gui() {
        initLookAndFeel();
        initFrame(initMainPanel());
    }

    public static void main(String[] args) {
        new Gui();
    }

    private void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void initFrame(JPanel mainPanel) {
        frame = new JFrame("Omnisciense Preview");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel initSourcePanel() {
        sourceTextField = new JTextField();
        sourceTextField.setEditable(false);
        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(new BrowseSourceActionListener());
        JPanel sourcePanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        sourcePanel.add(sourceTextField, constraints);
        constraints.weightx = 0;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, INNER_PADDING, 0, 0);
        sourcePanel.add(browseButton, constraints);
        return sourcePanel;
    }

    private JPanel initMainPanel() {
        JPanel sourcePanel = initSourcePanel();
        previewPanel = new PreviewPanel();
        previewPanel.setBackground(Color.BLACK);
        JPanel mainPanel = new JPanel(new BorderLayout(INNER_PADDING, INNER_PADDING));
        mainPanel.setPreferredSize(new Dimension(1_280, 768));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(OUTER_BORDER, OUTER_BORDER, OUTER_BORDER, OUTER_BORDER));
        mainPanel.add(sourcePanel, BorderLayout.PAGE_START);
        mainPanel.add(previewPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private class BrowseSourceActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage image;
                    if ((image = ImageIO.read(fileChooser.getSelectedFile())) == null) {
                        JOptionPane.showMessageDialog(frame, "Invalid image file", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        sourceTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
                        previewPanel.setSourceDimension(new Dimension(image.getWidth(), image.getHeight()));
                        previewPanel.setLayer(LayerKey.IMAGE, new ImageLayer(image));
                        int[][] imageArray = ImageUtils.bufferedImageToArray(image);
                        Filter filter = new Filter(100);
                        BufferedImage filteredImage = ImageUtils.arrayToBinaryImage(filter.filter(imageArray), Color.WHITE);
                        previewPanel.setLayer(LayerKey.COLOR_FILTER, new ImageLayer(filteredImage));
                        previewPanel.repaint();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }
    }
}
