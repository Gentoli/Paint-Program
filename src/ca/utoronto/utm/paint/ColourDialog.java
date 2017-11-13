package ca.utoronto.utm.paint;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

/**
 * ColourDialog is a JDialog that uses the JColorChooser component
 * and passes the chosen colour to the PaintPanel to be used for the next shapes.
 */
public class ColourDialog extends JDialog{

	private JColorChooser jcc;

	/**
	 *
	 * @param view Paint View
	 */
	public ColourDialog(Frame view, String title) {
		super(view);
		this.setTitle(title);
		jcc = new JColorChooser();
		jcc.setColor(Color.black);
        jcc.setBorder(BorderFactory.createTitledBorder("Choose Color"));
        add(jcc);
        jcc.setPreviewPanel(new JPanel());
        this.setSize(new Dimension(455,215));
	}

	public void addChangeListener(ChangeListener changeListener) {
		jcc.getSelectionModel().addChangeListener(changeListener);
	}

}
