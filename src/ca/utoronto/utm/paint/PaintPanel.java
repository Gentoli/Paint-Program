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
	private Ellipse ellipse; // the ellipse we are building

	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(300,300));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.model = model;

		shapes = this.model.getShapes();
		//tempStorage = new ArrayList<Shape>();
		this.mode = "Ellipse";
		this.model.addObserver(this);
		
		this.view=view;
	}

	/**
	 *  View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
        super.paintComponent(g); //paint background
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke());
        this.model.paint(g2);
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
		if(this.mode=="Squiggle"){
			this.model.addPoint(new Point(e.getX(), e.getY()));
		} else if(this.mode=="Ellipse"){
			this.ellipse.setEndPoint(new Point(e.getX(), e.getY()));
			//this.tempStorage.add(new Ellipse(this.ellipse.getCenter(),this.ellipse.getRadius()));
			this.model.addEllipse(ellipse);
		}
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
			this.ellipse=new Ellipse(center, center, colour);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Ellipse"){
				// Problematic notion of radius and center!!
				//int radius = Math.abs(this.ellipse.getcenter().getX()-e.getX());
				//int radius = (int)(Math.sqrt(Math.pow(this.ellipse.getPoint().getX()-e.getX(),2) + Math.pow(this.ellipse.getPoint().getY()-e.getY(),2)));
				this.ellipse.setEndPoint(new Point(e.getX(), e.getY()));
				this.model.addEllipse(this.ellipse);
				this.ellipse=null;

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
