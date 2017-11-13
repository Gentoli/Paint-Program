package ca.utoronto.utm.paint;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.colorchooser.DefaultColorSelectionModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * StylePanel is a JPanel using GridBagLayout to format the components,
 * that holds all the style options, including undo,redo,clear,
 * fill,lineStyle,lineThickness and the colour panel button.
 */
public class StylePanel extends JPanel implements Observer, ComponentListener {

    private JButton borderColourButton;
    private JButton colourButton;
    private JButton undo;
    private JButton redo;

    private Color borderColour = Color.black;
    private Color colour = Color.black;
    private float lineThickness = 1f;
    private int strokeStyle = 0;
    private boolean fill = false;
    private boolean border = false;
    private ImageIcon[] imageIconArray;
    private final JCheckBox borderCheckBox;

    /**
     * Creates a JPanel, using GridBagLayout to format the components
     */
    public StylePanel(PaintPanel paintPanel, ColourDialog colourDialog, ColourDialog borderColourDialog) {

        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(0,100));
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

        JLabel borderLabel = new JLabel("Border");
        borderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        borderCheckBox = new JCheckBox();
        borderCheckBox.setEnabled(false);
        borderCheckBox.addActionListener(e -> {
            border = borderCheckBox.isSelected();
        });
        borderCheckBox.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel fillLabel = new JLabel("Fill");
        fillLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JCheckBox fillCheckBox = new JCheckBox();
        fillCheckBox.addActionListener(e -> {
            fill = fillCheckBox.isSelected();
            if (!fill) {
                border = false;
                borderCheckBox.setSelected(false);
                borderCheckBox.setEnabled(false);
            }
            else{
                borderCheckBox.setEnabled(true);
            }
        });
        fillCheckBox.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel fillAndBorderLabels = new JPanel(new GridLayout(1,2));
        fillAndBorderLabels.add(fillLabel);
        fillAndBorderLabels.add(borderLabel);
        JPanel fillAndBorderChecks = new JPanel(new GridLayout(1,2));
        fillAndBorderChecks.add(fillCheckBox);
        fillAndBorderChecks.add(borderCheckBox);

        JLabel styleLabel = new JLabel("Line Style");
        styleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        String[] s = {"Basic Stroke", "Dashed Stroke", "Circle Stroke", "3D Stroke", "Wave Stroke"};
        imageIconArray = new ImageIcon[s.length];
        for (int i = 0; i < s.length; i++) {
            BufferedImage bufferedImage = new BufferedImage(80,20,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.setColor(colour);
            StrokeFactory strokeFactory=new StrokeFactory();
            g2.setStroke(strokeFactory.createStroke(i, 5));
            g2.drawLine(0,(int)(bufferedImage.getHeight()/2), bufferedImage.getWidth(), bufferedImage.getHeight()/2);
            imageIconArray[i] = new ImageIcon(bufferedImage);
        }
        JComboBox styleComboBox = new JComboBox(imageIconArray);
        styleComboBox.addComponentListener(this);

        styleComboBox.setSelectedIndex(0);
        styleComboBox.addActionListener(e -> {
            strokeStyle = styleComboBox.getSelectedIndex();
        });

        JLabel colourLabel = new JLabel("Choose Colour");
        colourLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel colourPanel = new JPanel(new GridLayout(2,1));

        borderColourButton = new JButton("Border Colour");
        borderColourButton.addActionListener(e -> {
            if (borderColourDialog.isVisible()) {
                borderColourDialog.setVisible(false);
            }
            else {
                borderColourDialog.setLocation(this.getLocationOnScreen().x + this.getWidth() - 450, this.getLocationOnScreen().y + this.getHeight() - 285);
                borderColourDialog.setVisible(true);
            }
        });
        colourPanel.add(borderColourButton);
        borderColourDialog.addChangeListener(e -> {
            DefaultColorSelectionModel jccSelectionModel = (DefaultColorSelectionModel) e.getSource();
            Color newColor = jccSelectionModel.getSelectedColor();
            borderColourButton.setForeground(newColor);
            borderColour = newColor;
        });

        colourButton = new JButton("Text Colour");
        colourButton.addActionListener(e -> {
            if (colourDialog.isVisible()) {
                colourDialog.setVisible(false);
//                colourButton.setText("Extend Colour Panel");
            }
            else {
                colourDialog.setLocation(this.getLocationOnScreen().x + this.getWidth() - 450, this.getLocationOnScreen().y + this.getHeight() - 285);
                colourDialog.setVisible(true);
//                colourButton.setText(" Close Colour Panel ");
            }
        });
        colourPanel.add(colourButton);
        colourDialog.addChangeListener(e -> {
            DefaultColorSelectionModel jccSelectionModel = (DefaultColorSelectionModel) e.getSource();
            Color newColor = jccSelectionModel.getSelectedColor();
            colourButton.setForeground(newColor);
            this.colour = newColor;
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
        JButton clear = new JButton("Clear");
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
        this.add(fillAndBorderLabels, c);
        c.gridy = 1;
        this.add(fillAndBorderChecks, c);
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
        this.add(colourPanel, c);
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
             * Change the text of colourButton button if the colour panel was closed with top-right X
             * @param e ColourDialog event
             */
            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-generated method stub
                //colourButton.setText("Extend Colour Panel");
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

//    @Override
//    public void stateChanged(ChangeEvent e) {
//        DefaultColorSelectionModel jccSelectionModel = (DefaultColorSelectionModel) e.getSource();
//        Color newColor = jccSelectionModel.getSelectedColor();
//        this.colour = newColor;
//        this.colourButton.setForeground(newColor);
//    }

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

    public boolean isBorder() {
        return border;
    }

    public Color getBorderColour() {
        return borderColour;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        JComboBox comboBox = (JComboBox)e.getSource();
        int height = comboBox.getHeight();
        int width = comboBox.getWidth();
        for (int i = 0; i < imageIconArray.length; i++) {
            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.setColor(colour);
            StrokeFactory strokeFactory=new StrokeFactory();
            g2.setStroke(strokeFactory.createStroke(i, 5));
            g2.drawLine(0,height/2, width,height/2);
            imageIconArray[i].setImage(bufferedImage);
        }
        comboBox.repaint();

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
