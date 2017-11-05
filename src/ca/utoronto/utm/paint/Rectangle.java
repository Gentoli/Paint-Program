package ca.utoronto.utm.paint;

import java.awt.*;

public class Rectangle extends Shape{


	public Rectangle(int x, int y, int xEnd, int yEnd, Color colour, float lineThickness, boolean fill, Stroke stroke) {
		super(x, y, xEnd, yEnd, colour, lineThickness, fill, stroke);
	}

	@Override
	public void print(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawRect(x, y, xEnd, yEnd);
	}
}
