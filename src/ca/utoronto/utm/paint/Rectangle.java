package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rectangle extends Shape{
	
	public Rectangle(Point point, Point endPoint, Color colour){
		///super(point,endPoint, colour);
	}


	@Override
	public void print(Graphics g) {
		// TODO Auto-generated method stub
		g.drawRect(x, y, xEnd, yEnd);
	}
}
