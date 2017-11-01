package ca.utoronto.utm.paint;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Circle extends Shape {
	private int radius;
	
	public Circle(Point center, int radius){
		super(center);
		this.radius = radius;
	}

	public Point getCenter() {
		return point;
	}

	public void setCentre(Point point) {
		this.point = point;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
