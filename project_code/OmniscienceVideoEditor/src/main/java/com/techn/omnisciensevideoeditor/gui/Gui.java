package com.techn.omnisciensevideoeditor.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class Gui {

    private static final int INNER_PADDING = 5;
    private static final int OUTER_BORDER = 10;

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
        JFrame frame = new JFrame("Omnisciense Preview");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private JPanel initSourcePanel() {
        JTextField sourceTextField = new JTextField();
        sourceTextField.setEditable(false);
        JButton browseButton = new JButton("Browse...");
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
        JPanel previewPanel = new JPanel();
        previewPanel.setBackground(Color.BLACK);
        JPanel mainPanel = new JPanel(new BorderLayout(INNER_PADDING, INNER_PADDING));
        mainPanel.setPreferredSize(new Dimension(1_280, 768));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(OUTER_BORDER, OUTER_BORDER, OUTER_BORDER, OUTER_BORDER));
        mainPanel.add(sourcePanel, BorderLayout.PAGE_START);
        mainPanel.add(previewPanel, BorderLayout.CENTER);
        return mainPanel;
    }
}
