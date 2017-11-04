package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Shape {
    protected Point point;
    protected Point endPoint;
    protected Color colour;

	public Shape(Point point,Point endPoint, Color colour) {
		this.point = point;
		this.endPoint = endPoint;
		this.colour = colour;
	}

	public Shape(Point point,Point endPoint) {
        this(point,endPoint,null);
    }

	public Shape(Point point) {
		this.point = point;
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


    public abstract void print(Graphics g);
    
    public abstract void mouseMoved(MouseEvent e);
    public abstract void mouseUp(MouseEvent e);
}
