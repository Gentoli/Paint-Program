package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class Shape {
    protected Point point;
    protected Dimension dimension;
    
	public Shape(Point point,Dimension dimension) {
        this.point = point;
        this.dimension = dimension;
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

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public void translateOrigin(int x, int y) {
		point.setX(point.getX()+x);
		point.setY(point.getY()+y);
	}

	public void translateDimension(int x, int y) {
		dimension.setSize(dimension.getWidth()+x, dimension.getHeight()+y);
	}


    public abstract void print(Graphics g);
    
    public abstract void mouseMoved(MouseEvent e);
    public abstract void mouseUp(MouseEvent e);
}
