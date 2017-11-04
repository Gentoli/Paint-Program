package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public abstract class Shape {
    protected int x,y;
    protected int xEnd,yEnd;
    protected Color colour;
    protected float lineThickness;
	protected boolean fill;
    protected Stroke stroke;

	public Shape(int x, int y, int xEnd, int yEnd, Color colour, float lineThickness, boolean fill, Stroke stroke) {
		this.x = x;
		this.y = y;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.colour = colour;
		this.lineThickness = lineThickness;
		this.fill = fill;
		this.stroke = stroke;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getxEnd() {
		return xEnd;
	}

	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	public int getyEnd() {
		return yEnd;
	}

	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	public Color getColour() { return colour; }

	public void setColour(Color colour) { this.colour = colour; }

	public void setLineThickness(float lineThickness) {
		this.lineThickness = lineThickness;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public void setFill(boolean fill) { this.fill = fill; }

	public void translateOrigin(int x, int y) {
		this.x+=x;
		this.y+=y;
	}

	public void translateEndPoint(int x, int y) {
		this.xEnd+=x;
		this.yEnd+=y;
	}

	protected void prepare(Graphics2D g2) {
		g2.setColor(colour);
		g2.setStroke(stroke);
	}

	public Point rotate(double x, double y, double angle){
		double retX = x*Math.cos(angle) - y*Math.sin(angle);
		double retY = x*Math.sin(angle) + y*Math.cos(angle);
		return new Point((int)retX,(int)retY);
	}

    public abstract void print(Graphics2D g2);
}
