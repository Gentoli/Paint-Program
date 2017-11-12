package ca.utoronto.utm.paint;

import javax.swing.*;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * StylePanel is a JPanel using GridBagLayout to format the components,
 * that holds all the style options, including undo,redo,clear,
 * fill,lineStyle,lineThickness and the colour panel button.
 */
public class StylePanel extends JPanel implements Observer, ChangeListener {

//    private JPanel lineThicknessPanel;
//    private JLabel lineThicknessLabel;
//    private JTextField lineThicknessText;
//    private JSlider lineThicknessSlider;
//    private JLabel fillLabel;
//    private JCheckBox fillCheckBox;
//    private JLabel styleLabel;
//    private JComboBox styleComboBox;
//    private JLabel colourLabel;
//    private ColourDialog colourDialog;
    private JButton openColourPanel;
    private JButton undo, redo, clear;
//    private JPanel buttonPanel;

    private Color colour = Color.black;
    private float lineThickness = 1f;
    private int strokeStyle = 0;
    private boolean fill = false;

    /**
     * Creates a JPanel, using GridBagLayout to format the components
     */
    public StylePanel(PaintPanel paintPanel, ColourDialog colourDialog) {

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(0,100));
        colourDialog.addChangeListener(this);
        JPanel lineThicknessPanel = new JPanel();
        JLabel lineThicknessLabel = new JLabel("Line Thickness");
        lineThicknessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField lineThicknessText = new JTextField("1", 3);
        JSlider lineThicknessSlider = new JSlider(1, 1024, 1);
        lineThicknessText.setHorizontalAlignment(SwingConstants.CENTER);
        lineThicknessText.addActionListener(e -> {
            try {
                float value = Float.valueOf(((JTextField)e.getSource()).getText());
                if (value > 20) {
                    value = 20;
                }
                if (value < 1) {
                    value = 1;
                }
                lineThickness = value;
                value = Math.round(value*51.2);
                lineThicknessSlider.setValue((int)value);
            }
            catch(Exception exception) {
                ((JTextField)e.getSource()).setText("1");
                lineThickness = 1;
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

        
        lineThicknessSlider.addChangeListener(e -> {
            float value = (((float)((JSlider)e.getSource()).getValue())/1024)*20;
            lineThickness = value;
            DecimalFormat df = new DecimalFormat("#.#");
            df.setRoundingMode(RoundingMode.CEILING);
            lineThicknessText.setText(df.format(value));
        });

        JLabel fillLabel = new JLabel("Fill Shapes");
        fillLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JCheckBox fillCheckBox = new JCheckBox();
        fillCheckBox.addActionListener(e -> {
            fill = fillCheckBox.isSelected();
        });
        fillCheckBox.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel styleLabel = new JLabel("Line Style");
        styleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String[] s = {"Basic Stroke", "Dashed Stroke"};
        JComboBox styleComboBox = new JComboBox(s);
        styleComboBox.setSelectedIndex(0);
        styleComboBox.addActionListener(e -> {
            strokeStyle = styleComboBox.getSelectedIndex();;
        });

        JLabel colourLabel = new JLabel("Choose Colour");
        colourLabel.setHorizontalAlignment(SwingConstants.CENTER);
        openColourPanel = new JButton("Extend Colour Panel");
        openColourPanel.addActionListener(e -> {
            if (colourDialog.isVisible()) {
                colourDialog.setVisible(false);
                openColourPanel.setText("Extend Colour Panel");
            }
            else {
                colourDialog.setLocation(this.getLocationOnScreen().x + this.getWidth() - 450, this.getLocationOnScreen().y + this.getHeight() - 285);
                colourDialog.setVisible(true);
                openColourPanel.setText(" Close Colour Panel ");
            }
        });

        undo = new JButton("Undo");
        undo.setEnabled(false);
        undo.addActionListener(e -> {
            paintPanel.undo();
        });
        redo = new JButton("Redo");
        redo.setEnabled(false);
        redo.addActionListener(e -> {
            paintPanel.redo();
        });
        clear = new JButton("Clear");
        clear.addActionListener(e -> {
            paintPanel.clear();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(undo);
        buttonPanel.add(redo);
        buttonPanel.add(clear);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel(), c);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridheight = 2;
        this.add(buttonPanel, c);
        c.gridheight = 1;
        c.weightx = 1;
        c.gridy = 0;
        c.gridx = 3;
        this.add(lineThicknessPanel, c);
        c.gridy = 1;
        this.add(lineThicknessSlider, c);
        c.gridy = 0;
        c.gridx = 5;
        c.weightx = 0.2;
        this.add(fillLabel, c);
        c.gridy = 1;
        this.add(fillCheckBox, c);
        c.weightx = 1;
        c.gridy = 0;
        c.gridx = 7;
        this.add(styleLabel, c);
        c.gridy = 1;
        this.add(styleComboBox, c);
        c.gridy = 0;
        c.gridx = 9;
        this.add(colourLabel, c);
        c.gridy = 1;
        this.add(openColourPanel, c);
        c.weightx = 0.1;
        c.gridy = 0;
        c.gridx = 10;
        this.add(new JLabel(), c);
        c.weightx = 0.2;
        for (int i = 2; i < 10; i+=2) {
            c.gridx = i;
            for (int j = 0; j < 3; j++) {
                c.gridy = j;
                c.fill = GridBagConstraints.VERTICAL;
                this.add(new JSeparator(SwingConstants.VERTICAL), c);
            }
        }


        colourDialog.addComponentListener(new ComponentAdapter() {
            /**
             * Change the text of openColourPanel button if the colour panel was closed with top-right X
             * @param e ColourDialog event
             */
            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-generated method stub
                openColourPanel.setText("Extend Colour Panel");
            }
        });
    }

    /**
     * checks if there are objects in the undo and redo arrays
     * and showing if undo and redo buttons can be clicked accordingly
     * @param o PaintModel
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        PaintModel paintModel = (PaintModel)o;
        undo.setEnabled(paintModel.canUndo());
        redo.setEnabled(paintModel.canRedo());
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        DefaultColorSelectionModel jccSelectionModel = (DefaultColorSelectionModel) e.getSource();
        Color newColor = jccSelectionModel.getSelectedColor();
        this.colour = newColor;
        this.openColourPanel.setForeground(newColor);
    }

    public Color getColour() {
        return colour;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public int getStrokeStyle() {
        return strokeStyle;
    }

    public boolean isFill() {
        return fill;
    }
}
