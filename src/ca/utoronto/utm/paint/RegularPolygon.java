package ca.utoronto.utm.paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class RegularPolygon extends Shape {
    public Point[] verticies;
    public RegularPolygon(Point point, Point endPoint, int verticies){
        //super(point,endPoint);
        this.verticies = new Point[verticies];
        calculateVerticies();
    }

    private void calculateVerticies(){
        double angles = 2*Math.PI/verticies.length;
        double radius = xEnd/2;
        for(int i = 0; i < verticies.length; i++){
            double x = 2*radius*Math.sin(i*angles);
            double y = 2*radius*Math.cos(i*angles);
            verticies[i] = new Point((int)x,(int)y);
        }
    }
    @Override
    public void print(Graphics2D g2) {
        for(int i = 0; i < verticies.length-1; i++){
            if(i == verticies.length-2){
                int last = verticies.length-1;
                g2.drawLine(verticies[last].x+x,verticies[last].y+y,verticies[0].x+x,verticies[0].y + y);
            }
            g2.drawLine(verticies[i].x+x,verticies[i].y+y,verticies[i+1].x+x,verticies[i+1].y+y);
        }
    }
}
