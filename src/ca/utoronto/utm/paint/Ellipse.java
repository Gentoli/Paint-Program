package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ellipse extends Shape{
	
	public Ellipse(Point centre, Color colour){
		//super(centre,centre, colour);
	}
	
	
	@Override
	public void print(Graphics2D g2) {
		// TODO Auto-generated method stub
		int radius = (int)(Math.sqrt(Math.pow(x- xEnd,2) + Math.pow(y- yEnd,2)));
		g.drawOval(x-radius, y-radius, 2*radius, 2*radius);
		prepare(g2);
		int radius = (int)(Math.sqrt(Math.pow(point.x- endPoint.x,2) + Math.pow(point.y- endPoint.y,2)));
		g2.drawOval(point.x-radius, point.y-radius, 2*radius, 2*radius);

	}
//	public void printOval(Graphics g) {
//		// TODO Auto-generated method stub
//		g.drawOval(x-endPoint.x, y-endPoint.y, radius, radius);
//
//	}
}
