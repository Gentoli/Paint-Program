package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Triangle extends Shape{
    Point[] verticies;
    public Triangle(Point point, Point endPoint){
        super(point,endPoint);
        verticies = new Point[3];
        calculateVerticies();
    }

    private void calculateVerticies(){
        verticies[0] = new Point(point.x + (int)endPoint.x/2, point.y);//top
        verticies[1] = new Point(point.x + (int)endPoint.x, point.y + (int)endPoint.y);//bottom right
        verticies[2] = new Point(point.x, point.y + (int)endPoint.y);//bottom left
    }
    @Override
    public void print(Graphics g) {
        g.drawLine(verticies[0].x, verticies[0].y, verticies[1].x, verticies[1].y);
        g.drawLine(verticies[1].x, verticies[1].y, verticies[2].x, verticies[2].y);
        g.drawLine(verticies[2].x, verticies[2].y, verticies[0].x, verticies[0].y);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseUp(MouseEvent e) {

    }
}
