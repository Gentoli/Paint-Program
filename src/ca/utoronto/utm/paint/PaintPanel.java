package ca.utoronto.utm.paint;

import javax.swing.*;  
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class PaintPanel extends JPanel implements Observer, MouseMotionListener, MouseListener  {
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the view
	private String mode; // modifies how we interpret input (could be better?)
	ArrayList<Shape> shapes;
	ArrayList<Shape> tempStorage;
	private Color colour;

	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.red);
		this.setPreferredSize(new Dimension(300,300));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.model = model;

		shapes = this.model.getShapes();
		//tempStorage = new ArrayList<Shape>();
		this.mode = "Ellipse";
		this.model.addObserver(this);
		
		this.view=view;
		shapes.add(new RegularPolygon(new Point(200,200),new Point(200,150),3,3));
		//shapes.add(new Triangle(new Point(100,100),new Dimension(20,20)));
		this.repaint();
	}

	/**
	 *  View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
        super.paintComponent(g); //paint background

        this.model.paint(g);
		g.setColor(Color.black);
	}

	@Override
	public void update(Observable o, Object arg) {
		// Not exactly how MVC works, but similar.
		this.repaint(); // Schedule a call to paintComponent

	}
	
	/**
	 *  Controller aspect of this
	 */
	public void setMode(String mode){
		this.mode=mode;
	}
	
	// MouseMotionListener below
	@Override
	public void mouseMoved(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){
			
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	// MouseListener below
	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){
			// Problematic notion of radius and center!!
			Point center = new Point(e.getX(), e.getY());
			int radius = 0;
			//this.ellipse=new Ellipse(center, 0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){

		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){
			
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){
			
		}
	}

	public void setColor(Color newColor) {
		this.colour = newColor;
	}
}
