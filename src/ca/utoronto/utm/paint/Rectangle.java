package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Rectangle extends Shape{
	
	public Rectangle(Point point, Point endPoint, Color colour){
		///super(point,endPoint, colour);
	}


	@Override
	public void print(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawRect(x, y, xEnd, yEnd);
	}
}
