package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Rectangle extends Shape{
	
	public Rectangle(Point point, Point endPoint, Color colour){
		super(point,endPoint, colour);
	}


	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(point.x, point.y, (int) endPoint.x, (int) endPoint.y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
