package ca.utoronto.utm.paint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Ellipse extends Shape{
	
	public Ellipse(Point centre, Point endPoint){
		super(centre, endPoint);
	}
	
	
	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		int radius = 2*(int)(Math.sqrt(Math.pow(point.getX()- endPoint.getX(),2) + Math.pow(point.getY()- endPoint.getY(),2)));
		g.drawOval(point.getX()-radius, point.getY()-radius, radius, radius);
		
	}
//	public void printOval(Graphics g) {
//		// TODO Auto-generated method stub
//		g.drawOval(point.getX()-endPoint.getX(), point.getY()-endPoint.getY(), radius, radius);
//
//	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setEndPoint(Point point) {
		endPoint = point;
	}
}
