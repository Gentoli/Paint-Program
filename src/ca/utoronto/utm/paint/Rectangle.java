package ca.utoronto.utm.paint;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class Rectangle extends Shape{
	
	public Rectangle(Point point, Dimension dimension){
		super(point,dimension);
	}


	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(point.x, point.y, (int)dimension.getWidth(), (int)dimension.getHeight());
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
