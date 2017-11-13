package ca.utoronto.utm.paint;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

/**
 * The TextBoxDialog pops up when the TextBoxTool is selected.
 * In the dialog, a user can customize the drawable string that TextBoxTool will create.
 */
public class TextBoxDialog extends JDialog {

    private JTextField textField;
    private JComboBox fontChooser;
    private JComboBox fontSizeChooser;
    private JCheckBox boldCheck;
    private JCheckBox italicCheck;

    private final static int DEFAULT_SIZE = 12;

    /**
     * Constructs the JDialog that holds the textBox customization options
     * @param view View of the paint project
     */
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
        JEditorPane editorPane = new JEditorPane();
        String fontFamily = editorPane.getFont().getFamily();
        fontChooser.setSelectedItem(fontFamily);
        //fontChooser.setSelectedIndex(218);
        //fontName = fontChooser.getSelectedItem().toString();
        c.add(fontChooser);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 3));

        JLabel fontSizeLabel = new JLabel("Font Size");
        String[] sizes = new String[30];
        for (int i = 1; i <= sizes.length; i++) {
            sizes[i-1] = String.valueOf(i);
        }
        fontSizeChooser = new JComboBox(sizes);
        fontSizeChooser.setSelectedIndex(DEFAULT_SIZE-1);
        //int size = Integer.valueOf(fontSizeChooser.getSelectedItem().toString());

        JLabel boldLabel = new JLabel("Bold");
        boldCheck = new JCheckBox();
        //fontStyle += (boldCheck.isSelected()?1:-1);

        JLabel italicLabel = new JLabel("Italic");
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
