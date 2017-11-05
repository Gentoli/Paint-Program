package ca.utoronto.utm.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class StylePanel extends JPanel {

    private View view;
    private JPanel lineThicknessPanel;
    private JLabel lineThicknessLabel;
    private JTextField lineThicknessText;
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
        this.setPreferredSize(new Dimension(0,75));

        lineThicknessPanel = new JPanel();
        lineThicknessLabel = new JLabel("Line Thickness");
        //lineThicknessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lineThicknessText = new JTextField("1", 3);
        lineThicknessText.addActionListener(e -> {
            try {
                float value = Float.valueOf(((JTextField)e.getSource()).getText());
                if (value > 20) {
                    value = 20;
                }
                if (value < 1) {
                    value = 1;
                }
                view.getPaintPanel().setLineThickness(value);
                value = Math.round(value*51.2);
                lineThicknessSlider.setValue((int)value);
            }
            catch(Exception exception) {
                ((JTextField)e.getSource()).setText("1");
                view.getPaintPanel().setLineThickness(1);
                lineThicknessSlider.setValue(1);
            }
        });
        lineThicknessText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit((e.getKeyChar()))) {
                    if (e.getKeyChar() != ('.'))
                        e.consume();
                }
                String s = lineThicknessText.getText();
                if (s.length() >= 3 && (!s.substring(s.length()-1).equals(".") || s.substring(s.length()-2).equals(".."))) {
                    e.consume();
                }
            }
        });
        lineThicknessPanel.add(lineThicknessLabel);
        lineThicknessPanel.add(lineThicknessText);
        lineThicknessSlider = new JSlider(1, 1024, 1);
        lineThicknessSlider.addChangeListener(e -> {
            float value = (((float)((JSlider)e.getSource()).getValue())/1024)*20;
            view.getPaintPanel().setLineThickness(value);
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            lineThicknessText.setText(df.format(value));
        });

        fillLabel = new JLabel("Fill Shapes");
        fillLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fillCheckBox = new JCheckBox();
        fillCheckBox.addActionListener(e -> {
            view.getPaintPanel().setFill(((JCheckBox)e.getSource()).isSelected());
        });
        fillCheckBox.setHorizontalAlignment(SwingConstants.CENTER);

        styleLabel = new JLabel("Line Style");
        styleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        styleComboBox = new JComboBox();

        colourLabel = new JLabel("Choose Colour");
        colourLabel.setHorizontalAlignment(SwingConstants.CENTER);
        openColourPanel = new JButton("Extend Colour Panel");
        openColourPanel.addActionListener(e -> {
            if (colourPanel.isVisible()) {
                colourPanel.setVisible(false);
                openColourPanel.setText("Extend Colour Panel");
            }
            else {
                colourPanel.setLocation(this.getLocationOnScreen().x + this.getWidth() - 450, this.getLocationOnScreen().y + this.getHeight() - 285);
                colourPanel.setVisible(true);
                openColourPanel.setText("Close Colour Panel");
            }
            System.out.println(e.getActionCommand());
        });
        this.colourPanel = new ColourPanel(view, openColourPanel);


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel(), c);
        c.gridx = 1;
        this.add(lineThicknessPanel, c);
        c.gridx = 2;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(new JSeparator(SwingConstants.VERTICAL), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel(), c);
        c.gridx = 3;
        this.add(fillLabel, c);
        c.gridx = 4;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(new JSeparator(SwingConstants.VERTICAL), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel(), c);
        c.gridx = 5;
        this.add(styleLabel, c);
        c.gridx = 6;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(new JSeparator(SwingConstants.VERTICAL), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel(), c);
        c.gridx = 1;
        c.gridy = 1;
        this.add(lineThicknessSlider, c);
        c.gridx = 2;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(new JSeparator(SwingConstants.VERTICAL), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        this.add(fillCheckBox, c);
        c.gridx = 4;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(new JSeparator(SwingConstants.VERTICAL), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        this.add(styleComboBox, c);
        c.gridx = 6;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(new JSeparator(SwingConstants.VERTICAL), c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 7;
        c.gridy = 0;
        this.add(colourLabel, c);
        c.gridy = 1;
        this.add(openColourPanel, c);
        c.gridx = 8;
        c.gridy = 0;
        this.add(new JLabel(), c);


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

}
