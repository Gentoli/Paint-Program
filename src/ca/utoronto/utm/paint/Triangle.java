package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Triangle extends Shape{
    Point[] verticies;
    public Triangle(Point point, Dimension dimension){
        super(point,dimension);
        verticies = new Point[3];
        calculateVerticies();
    }

    private void calculateVerticies(){
        verticies[0] = new Point(point.getX() + (int)dimension.getWidth()/2, point.getY());//top
        verticies[1] = new Point(point.getX() + (int)dimension.getWidth(), point.getY() + (int)dimension.getHeight());//bottom right
        verticies[2] = new Point(point.getX(), point.getY() + (int)dimension.getHeight());//bottom left
    }
    @Override
    public void print(Graphics g) {
        g.drawLine(verticies[0].getX(), verticies[0].getY(), verticies[1].getX(), verticies[1].getY());
        g.drawLine(verticies[1].getX(), verticies[1].getY(), verticies[2].getX(), verticies[2].getY());
        g.drawLine(verticies[2].getX(), verticies[2].getY(), verticies[0].getX(), verticies[0].getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseUp(MouseEvent e) {

    }
}
