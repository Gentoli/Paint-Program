package ca.utoronto.utm.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class StylePanel extends JPanel implements ActionListener {

    private View view;
    private JLabel lineThicknessLabel;
    private JSlider lineThicknessSlider;
    private JLabel fillLabel;
    private JCheckBox fillCheckBox;
    private JLabel styleLabel;
    private JComboBox styleComboBox;
    private JLabel colourLabel;
    private ColourPanel colourPanel;
    private JButton openColourPanel;


    public StylePanel(View view) {
        this.view = view;

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(0,100));

        lineThicknessLabel = new JLabel("Line Thickness");
        lineThicknessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lineThicknessSlider = new JSlider(1, 1024);

        fillLabel = new JLabel("Fill Shapes");
        fillLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fillCheckBox = new JCheckBox();
        fillCheckBox.setHorizontalAlignment(SwingConstants.CENTER);

        styleLabel = new JLabel("Line Style");
        styleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        styleComboBox = new JComboBox();

        colourLabel = new JLabel("Choose Colour");
        colourLabel.setHorizontalAlignment(SwingConstants.CENTER);
        openColourPanel = new JButton("Extend Colour Panel");
        openColourPanel.addActionListener(this);
        this.colourPanel = new ColourPanel(view, openColourPanel);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(lineThicknessLabel, c);
        c.gridx = 1;
        this.add(new JLabel(), c);
        c.gridx = 2;
        this.add(fillLabel, c);
        c.gridx = 3;
        this.add(new JLabel(), c);
        c.gridx = 4;
        this.add(styleLabel, c);
        c.gridx = 5;
        this.add(new JLabel(), c);
        c.gridx = 0;
        c.gridy = 1;
        this.add(lineThicknessSlider, c);
        c.gridx = 2;
        this.add(fillCheckBox, c);
        c.gridx = 4;
        this.add(styleComboBox, c);
        c.gridx = 6;
        c.gridy = 0;
        this.add(colourLabel, c);
        c.gridy = 1;
        this.add(openColourPanel, c);


        colourPanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-generated method stub
                openColourPanel.setText("Extend Colour Panel");
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentResized(ComponentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentShown(ComponentEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (colourPanel.isVisible()) {
            colourPanel.setVisible(false);
            openColourPanel.setText("Extend Colour Panel");
        }
        else {
            colourPanel.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y + this.getHeight());
            colourPanel.setVisible(true);
            openColourPanel.setText("Close Colour Panel");
        }
        System.out.println(e.getActionCommand());
    }
}
