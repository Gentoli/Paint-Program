package ca.utoronto.utm.paint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Dimension;

/**
 * ColourPanel is a JDialog that uses the JColorChooser component
 * and passes the chosen colour to the PaintPanel to be used for the next shapes.
 */
public class ColourPanel extends JDialog implements ChangeListener{

	private View view;
	private JColorChooser tcc;
	private JButton openColourPanel;

	/**
	 *
	 * @param view Paint View
	 * @param openColourPanel The button that opens or closes it
	 */
	public ColourPanel(View view, JButton openColourPanel) {
		super(view);
		this.view = view;
		this.openColourPanel = openColourPanel;
		tcc = new JColorChooser();
		tcc.setColor(Color.black);
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder("Choose Color"));
        add(tcc);
        tcc.setPreviewPanel(new JPanel());
        this.setSize(new Dimension(455,215));
        //this.pack();
		//this.setVisible(true);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Color newColor = tcc.getColor();
        this.view.getPaintPanel().setColor(newColor);
        this.openColourPanel.setForeground(newColor);
        view.requestFocus();
	}
}
