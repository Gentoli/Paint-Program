package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Ellipse extends Shape{
	
	public Ellipse(Point centre, Color colour){
		super(centre,centre, colour);
	}
	
	
	@Override
	public void print(Graphics2D g2) {
		// TODO Auto-generated method stub
		prepare(g2);
		int radius = (int)(Math.sqrt(Math.pow(point.x- endPoint.x,2) + Math.pow(point.y- endPoint.y,2)));
		g2.drawOval(point.x-radius, point.y-radius, 2*radius, 2*radius);
		
	}
//	public void printOval(Graphics g) {
//		// TODO Auto-generated method stub
//		g.drawOval(point.x-endPoint.x, point.y-endPoint.y, radius, radius);
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
