package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Shape {
    protected Point point;
    protected Point endPoint;
    protected Color colour;
    protected float lineThickness;
    protected Stroke stroke;

	public Shape(Point point,Point endPoint, Color colour, float lineThickness) {
		this.point = point;
		this.endPoint = endPoint;
		this.colour = colour;
		this.lineThickness = lineThickness;
	}

	public Shape(Point point,Color colour, float lineThickness) {
		this(point, point,colour, lineThickness);
	}

	public Shape(Point point,Point endPoint) {
        this(point,endPoint,null,1);
    }

	public Shape(Point point) {
		this(point, point, null,1);
	}
	
    public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public Color getColour() { return colour; }

	public void setColour(Color colour) { this.colour = colour; }

	public void translateOrigin(int x, int y) {
		point.translate(x,y);
	}

	public void translateEndPoint(int x, int y) {
		endPoint.translate(x,y);
	}

	protected void prepare(Graphics2D g2) {
		g2.setColor(colour);
		g2.setStroke(stroke);
	}

    public abstract void print(Graphics g);
    
    public abstract void mouseMoved(MouseEvent e);
    public abstract void mouseUp(MouseEvent e);
}
