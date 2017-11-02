package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.colorchooser.*;

public class ColourPanel extends JFrame implements ChangeListener{

	private View view;
	private JColorChooser tcc;

	public ColourPanel(View view) {
		
		this.view = view;
		tcc = new JColorChooser();
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
        System.out.println(this.getSize());
	}
}
