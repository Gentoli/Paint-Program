package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Modifier extends Shape {
	public Modifier(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle) {
		super(x, y, colour, lineThickness, fill, strokeStyle);
	}

	@Override
	public void print(Graphics2D g2) {

	}
}
