package ca.utoronto.utm.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *  Shape base object for all shapes(Actors) on ShapePanel. Implements Drawable
 */
public abstract class Shape implements Drawable {
    protected int x,y;
    protected int xEnd,yEnd;
    protected Color colour;
    protected float lineThickness;
	protected boolean fill;
    protected Stroke stroke;
	protected boolean center;
	protected boolean right;
	protected int strokeStyle;

	public Shape(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle) {
		this.x = x;
		this.y = y;
		this.xEnd = x;
		this.yEnd = y;
		this.colour = colour;
		this.lineThickness = lineThickness;
		this.fill = fill;
		this.strokeStyle = strokeStyle;
		switch (strokeStyle) {
			case 0:  this.stroke = new BasicStroke(lineThickness);
				break;
			case 1:  this.stroke = new BasicStroke(lineThickness, 0, 0, 10.0f, new float[] {16.0f,20.0f},0.0f);
				break;
			default: this.stroke = new BasicStroke(lineThickness);
				break;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getXEnd() {
		return xEnd;
	}

	public void setXEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	public int getYEnd() {
		return yEnd;
	}

	public void setYEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	public void setEnd(int x,int y){
		xEnd=x;
		yEnd=y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getXMid(){
		return x+getWidth()/2;
	}

	public int getYMid(){
		return y+(getHeight())/2;
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

	/**
	 * Sets the colour and stroke type for the shape to be drawn
	 * @param g2 Graphics object
	 */
	protected void prepare(Graphics2D g2) {
		g2.setColor(colour);
		g2.setStroke(stroke==null?new BasicStroke(lineThickness):stroke);
	}

	/**
	 * Rotates a point around a center
	 * @param x x value of the starting point
	 * @param y y value of the starting point
	 * @param angle angle to rotate the starting point
	 * @return the ending point created by the rotation
	 */
	public Point rotate(double x, double y, double angle){
		double retX = x*Math.cos(angle) - y*Math.sin(angle);
		double retY = x*Math.sin(angle) + y*Math.cos(angle);
		return new Point((int)retX,(int)retY);
	}

	public int getWidth(){
		return xEnd-x;
	}

	public int getHeight(){
		return yEnd-y;
	}

	public boolean contains(int x, int y) {
		return (x>=this.x)&&(x<=xEnd)&&(y>=this.y)&&(y<=yEnd);
	}

    public abstract void print(Graphics2D g2);

	@Override
	public void print(Graphics g) {
		this.print((Graphics2D)g);
	}

	public void setCenter(boolean center) {
		this.center = center;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
}
