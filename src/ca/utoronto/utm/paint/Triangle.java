package ca.utoronto.utm.paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Triangle extends Shape{
    Point[] verticies;
    public Triangle(Point point, Point endPoint){
        //super(point,endPoint);
        verticies = new Point[3];
        calculateVerticies();
    }

    private void calculateVerticies(){
        verticies[0] = new Point(x + xEnd/2, y);//top
        verticies[1] = new Point(x + xEnd, y + yEnd);//bottom right
        verticies[2] = new Point(x, y + yEnd);//bottom left
    }
    @Override
    public void print(Graphics2D g2) {
        g2.drawLine(verticies[0].x, verticies[0].y, verticies[1].x, verticies[1].y);
        g2.drawLine(verticies[1].x, verticies[1].y, verticies[2].x, verticies[2].y);
        g2.drawLine(verticies[2].x, verticies[2].y, verticies[0].x, verticies[0].y);
    }
}
