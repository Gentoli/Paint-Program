package ca.utoronto.utm.paint;

import javax.swing.*;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;

public class TextBoxDialog extends JDialog {

    private JComboBox fontChooser;
    private JTextField fontSizeField;
    private JCheckBox boldCheck;
    private JCheckBox italicCheck;

    private String fontName;
    private int fontStyle;
    private int fontSize;

    public TextBoxDialog(Frame view) {
        super(view);

        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontChooser = new JComboBox(fonts);
        fontChooser.setSelectedIndex(0);
        fontChooser.addActionListener(e -> {

        });
        add(fontChooser);




    }

}
