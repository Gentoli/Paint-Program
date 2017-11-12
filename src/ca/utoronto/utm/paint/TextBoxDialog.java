package ca.utoronto.utm.paint;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

public class TextBoxDialog extends JDialog {

    private JTextField textField;
    private JComboBox fontChooser;
    private JLabel fontSizeLabel;
    private JComboBox fontSizeChooser;
    private JLabel boldLabel;
    private JCheckBox boldCheck;
    private JLabel italicLabel;
    private JCheckBox italicCheck;

    private JPanel bottomPanel;

    private final static int DEFAULT_SIZE = 12;

    public TextBoxDialog(Frame view) {
        super(view);
        this.setTitle("Text Editor");
        this.setPreferredSize(new Dimension(200,150));
        Container c = getContentPane();
        c.setLayout(new GridLayout(3,1));

        textField = new JTextField("Text");
        c.add(textField);


        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontChooser = new JComboBox(fonts);
        fontChooser.setSelectedIndex(218);
        //fontName = fontChooser.getSelectedItem().toString();
        c.add(fontChooser);

        bottomPanel = new JPanel(new GridLayout(2,3));

        fontSizeLabel = new JLabel("Font Size");
        String[] sizes = new String[30];
        for (int i = 1; i <= sizes.length; i++) {
            sizes[i-1] = String.valueOf(i);
        }
        fontSizeChooser = new JComboBox(sizes);
        fontSizeChooser.setSelectedIndex(DEFAULT_SIZE-1);
        //int size = Integer.valueOf(fontSizeChooser.getSelectedItem().toString());

        boldLabel = new JLabel("Bold");
        boldCheck = new JCheckBox();
        //fontStyle += (boldCheck.isSelected()?1:-1);

        italicLabel = new JLabel("Italic");
        italicCheck = new JCheckBox();
        //fontStyle += (italicCheck.isSelected()?2:-2);

        fontSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        italicLabel.setHorizontalAlignment(SwingConstants.CENTER);
        boldCheck.setHorizontalAlignment(SwingConstants.CENTER);
        italicCheck.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(fontSizeLabel);
        bottomPanel.add(boldLabel);
        bottomPanel.add(italicLabel);
        bottomPanel.add(fontSizeChooser);
        bottomPanel.add(boldCheck);
        bottomPanel.add(italicCheck);
        c.add(bottomPanel);

        pack();
    }

    public JTextField getTextField() {
        return textField;
    }

    public JComboBox getFontChooser() {
        return fontChooser;
    }

    public JComboBox getFontSizeChooser() {
        return fontSizeChooser;
    }

    public JCheckBox getBoldCheck() {
        return boldCheck;
    }

    public JCheckBox getItalicCheck() {
        return italicCheck;
    }
}
