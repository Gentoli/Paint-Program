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
	private int i=0;
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the view
	private Circle circle;
	private String mode; // modifies how we interpret input (could be better?)
	ArrayList<Shape> shapes;
	ArrayList<Shape> tempStorage;
	
	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.blue);
		this.setPreferredSize(new Dimension(300,300));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.model = model;
		
		shapes = this.model.getShapes();
		//tempStorage = new ArrayList<Shape>();
		this.mode = "Circle";
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
        Graphics2D g2d = (Graphics2D) g; // lets use the advanced api
		// setBackground (Color.blue); 
		// Origin is at the top left of the window 50 over, 75 down
		g2d.setColor(Color.white);
        g2d.drawString ("i="+i, 50, 75);
		i=i+1;
		//System.out.println(shapes.size());
		for(Shape shape : shapes) {
			if(shape instanceof Circle) {
				Point point = ((Circle) shape).getCenter();
				int r = ((Circle) shape).getRadius();
				g.drawOval(point.getX()-2*r/2,point.getY()-2*r/2,2*r,2*r);//multiple radius by 2 because it takes in id
			}
		}
		g2d.dispose();
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
			
		} else if(this.mode=="Circle"){
			
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.mode=="Squiggle"){
			this.model.addPoint(new Point(e.getX(), e.getY()));
		} else if(this.mode=="Circle"){
			int radius = (int)(Math.sqrt(Math.pow(this.circle.getCenter().getX()-e.getX(),2) + Math.pow(this.circle.getCenter().getY()-e.getY(),2)));
			this.circle.setRadius(radius);
			//this.tempStorage.add(new Circle(this.circle.getCenter(),this.circle.getRadius()));
			this.model.addCircle(circle);
		}
	}

	// MouseListener below
	@Override
	public void mouseClicked(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
			// Problematic notion of radius and center!!
			Point center = new Point(e.getX(), e.getY());
			int radius = 0;
			this.circle=new Circle(center, 0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
				// Problematic notion of radius and center!!
				//int radius = Math.abs(this.circle.getcenter().getX()-e.getX());
				int radius = (int)(Math.sqrt(Math.pow(this.circle.getCenter().getX()-e.getX(),2) + Math.pow(this.circle.getCenter().getY()-e.getY(),2)));
				this.circle.setRadius(radius);
				this.model.addCircle(this.circle);
				this.circle=null;
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
			
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(this.mode=="Squiggle"){
			
		} else if(this.mode=="Circle"){
			
		}
	}
}
