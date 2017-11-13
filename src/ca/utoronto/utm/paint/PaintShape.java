package ca.utoronto.utm.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.QuadCurve2D;

/**
 *  PaintShape base object for all shapes(Actors) on ShapePanel. Implements Drawable
 */
public abstract class PaintShape implements Drawable, Shape {
    protected int x,y;
    protected int xEnd,yEnd;
    protected Color colour;
    protected float lineThickness;
	protected boolean fill;
    protected Stroke stroke;
	protected int strokeStyle;

	public PaintShape(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle) {
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
			case 2: this.stroke = new ShapeStroke(new QuadCurve2D.Double(-0.1,0.1,0.0,0.0,0.1,0.1),10.0,true);
				break;
			case 3: this.stroke = new  ShapeStroke(new QuadCurve2D.Double(50,100,100.0,170.0,150,100),1.0,false);
			default: this.stroke = new BasicStroke(lineThickness);
				break;
		}
	}
	/**
	 * Sets the colour and stroke type for the shape to be drawn
	 * @param g2 Graphics object
	 */
	protected void prepare(Graphics2D g2) {
		g2.setColor(colour);
		g2.setStroke(stroke==null?new BasicStroke(lineThickness):stroke);
	}

	public abstract void mouseMoved(int x, int y);

    public abstract void print(Graphics2D g2);

	@Override
	public void print(Graphics g) {
		this.print((Graphics2D)g);
	}

}
