package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ellipse extends Shape{
	
	public Ellipse(Point centre, Point endPoint, Color colour){
		super(centre, endPoint, colour);
	}
	
	
	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		int radius = (int)(Math.sqrt(Math.pow(point.getX()- endPoint.getX(),2) + Math.pow(point.getY()- endPoint.getY(),2)));
		g.drawOval(point.getX()-radius, point.getY()-radius, 2*radius, 2*radius);
		
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
