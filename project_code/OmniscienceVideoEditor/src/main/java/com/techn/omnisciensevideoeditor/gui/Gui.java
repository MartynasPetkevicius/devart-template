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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Gui {

    private static final int INNER_PADDING = 5;
    private static final int OUTER_BORDER = 10;
    private final Filter filter = new Filter(100);
    private int[][] sourceImage;
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

    private JPanel initContolPanel() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setPreferredSize(new Dimension(250, 0));
        JSlider colorToleranceSlider = new JSlider(0, (int) Filter.MAX_TOLERANCE_VALUE, (int) filter.getTolerance());
        colorToleranceSlider.setMajorTickSpacing((int) (Filter.MAX_TOLERANCE_VALUE / 5));
        colorToleranceSlider.setPaintTicks(true);
        colorToleranceSlider.setPaintLabels(true);
        colorToleranceSlider.addChangeListener(new ColorFilterToleranceChangedListener());
        addControl(controlPanel, "Color tolerance", colorToleranceSlider);
        GridBagConstraints fillerConstraints = new GridBagConstraints();
        fillerConstraints.gridx = 0;
        fillerConstraints.gridwidth = 2;
        fillerConstraints.weighty = 1;
        controlPanel.add(new JPanel(), fillerConstraints);
        return controlPanel;
    }

    private void addControl(JPanel parent, String name, JComponent control) {
        JLabel label = new JLabel(name);
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.anchor = GridBagConstraints.LINE_END;
        parent.add(label, labelConstraints);
        GridBagConstraints controlConstraints = new GridBagConstraints();
        controlConstraints.gridx = 1;
        controlConstraints.fill = GridBagConstraints.HORIZONTAL;
        controlConstraints.weightx = 1;
        parent.add(control, controlConstraints);
    }

    private JPanel initMainPanel() {
        JPanel sourcePanel = initSourcePanel();
        JPanel controlPanel = initContolPanel();
        previewPanel = new PreviewPanel();
        previewPanel.setBackground(Color.BLACK);
        JPanel mainPanel = new JPanel(new BorderLayout(INNER_PADDING, INNER_PADDING));
        mainPanel.setPreferredSize(new Dimension(1_280, 768));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(OUTER_BORDER, OUTER_BORDER, OUTER_BORDER, OUTER_BORDER));
        mainPanel.add(sourcePanel, BorderLayout.PAGE_START);
        mainPanel.add(controlPanel, BorderLayout.LINE_END);
        mainPanel.add(previewPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private void refreshPreview() {
        if (sourceImage == null) {
            return;
        }
        BufferedImage filteredImage = ImageUtils.arrayToBinaryImage(filter.filter(sourceImage), Color.WHITE);
        previewPanel.setLayer(LayerKey.COLOR_FILTER, new ImageLayer(filteredImage));
        previewPanel.repaint();
    }

    private class BrowseSourceActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
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
                        sourceImage = ImageUtils.bufferedImageToArray(image);
                        refreshPreview();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }
            }
        }
    }

    private class ColorFilterToleranceChangedListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent event) {
            JSlider slider = (JSlider) event.getSource();
            if (slider.getValueIsAdjusting()) {
                return;
            }
            filter.setTolerance(slider.getValue());
            refreshPreview();
        }
    }
}
