package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RegularPolygon extends Shape {
    public Point[] verticies;
    public RegularPolygon(Point point, Dimension dimension, int verticies){
        super(point,dimension);
        this.verticies = new Point[verticies];
        calculateVerticies();
    }

    private void calculateVerticies(){
        double angles = 2*Math.PI/verticies.length;
        double radius = dimension.getWidth()/2;
        for(int i = 0; i < verticies.length; i++){
            double x = 2*radius*Math.sin(i*angles);
            double y = 2*radius*Math.cos(i*angles);
            verticies[i] = new Point((int)x,(int)y);
        }
    }
    @Override
    public void print(Graphics g) {
        for(int i = 0; i < verticies.length-1; i++){
            if(i == verticies.length-2){
                int last = verticies.length-1;
                g.drawLine(verticies[last].getX()+point.x,verticies[last].getY()+point.y,verticies[0].getX()+point.x,verticies[0].getY() + point.y);
            }
            g.drawLine(verticies[i].getX()+point.x,verticies[i].getY()+point.y,verticies[i+1].getX()+point.x,verticies[i+1].getY()+point.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseUp(MouseEvent e) {

    }
}
