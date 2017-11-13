package ca.utoronto.utm.paint;

import java.awt.*;

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

	public PaintShape(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle) {
		this.x = x;
		this.y = y;
		this.xEnd = x;
		this.yEnd = y;
		this.colour = colour;
		this.lineThickness = lineThickness;
		this.fill = fill;
		StrokeFactory strokeFactory = new StrokeFactory();
		this.stroke = strokeFactory.createStroke(strokeStyle, lineThickness);
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
