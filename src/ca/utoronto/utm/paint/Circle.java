package ca.utoronto.utm.paint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Circle extends Shape{
	
	public Circle(Point centre, int radius){
		super(centre,new Dimension(radius,radius));
	}
	
	
	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		int radius = 2*(int)dimension.getWidth();
		g.drawOval(point.getX()-radius, point.getY()-radius, radius, radius);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setRadius(int radius) {
		dimension = new Dimension(radius,radius);
	}
}
