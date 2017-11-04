package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.colorchooser.*;

public class ColourPanel extends JFrame implements ChangeListener{

	private View view;
	private JColorChooser tcc;
	private JButton openColourPanel;

	public ColourPanel(View view, JButton openColourPanel) {
		
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
        System.out.println(this.getSize());
	}
}
