package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Ellipse extends Shape{
	
	public Ellipse(Point centre, Color colour){
		//super(centre,centre, colour);
	}
	
	
	@Override
	public void print(Graphics2D g2) {
		// TODO Auto-generated method stub
		prepare(g2);
		int radius = (int)(Math.sqrt(Math.pow(x- xEnd,2) + Math.pow(y- yEnd,2)));
		g2.drawOval(x - radius, y - radius, 2*radius, 2*radius);
	}
//	public void printOval(Graphics g) {
//		// TODO Auto-generated method stub
//		g.drawOval(x-endPoint.x, y-endPoint.y, radius, radius);
//
//	}
}
