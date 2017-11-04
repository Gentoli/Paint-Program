package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RegularPolygon extends Shape {
    public Point[] verticies;
    public RegularPolygon(Point point, Point endPoint, int verticies){
        super(point,endPoint);
        this.verticies = new Point[verticies];
        calculateVerticies();
    }

    private void calculateVerticies(){
        double angles = 2*Math.PI/verticies.length;
        double radius = endPoint.x/2;
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
                g.drawLine(verticies[last].x+point.x,verticies[last].y+point.y,verticies[0].x+point.x,verticies[0].y + point.y);
            }
            g.drawLine(verticies[i].x+point.x,verticies[i].y+point.y,verticies[i+1].x+point.x,verticies[i+1].y+point.y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseUp(MouseEvent e) {

    }
}
