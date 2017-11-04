package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Shape {
    protected Point point;
    protected Point endPoint;
    protected int thickness;

	public Shape(Point point,Point endPoint,int thickness) {
        this.point = point;
        this.endPoint = endPoint;
        this.thickness = thickness;
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

	public void translateOrigin(int x, int y) {
		point.setX(point.getX()+x);
		point.setY(point.getY()+y);
	}

	public void translateEndPoint(int x, int y) {
		endPoint.setX(endPoint.getX()+x);
		endPoint.setY(endPoint.getY()+y);
	}

	public Point rotate(double x, double y, double angle){
		double retX = x*Math.cos(angle) - y*Math.sin(angle);
		double retY = x*Math.sin(angle) + y*Math.cos(angle);
		return new Point((int)retX,(int)retY);
	}

    public abstract void print(Graphics g);
    
    public abstract void mouseMoved(MouseEvent e);
    public abstract void mouseUp(MouseEvent e);
}
