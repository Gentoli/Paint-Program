package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {
	private ArrayList<Point> points=new ArrayList<Point>();
	private ArrayList<Shape> shapes=new ArrayList<Shape>();
	
	
	public void addPoint(Point p){
		this.points.add(p);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Point> getPoints(){
		return points;
	}
	
	public void addCircle(Circle c){
		this.shapes.add(c);
		this.setChanged();
		this.notifyObservers();
	}
	public ArrayList<Shape> getShapes(){
		return shapes;
	}
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g; // lets use the advanced api
		// setBackground (Color.blue); 
		// Origin is at the top left of the window 50 over, 75 down
		g2d.setColor(Color.white);
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
}
