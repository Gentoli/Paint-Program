package ca.utoronto.utm.paint;

import java.awt.*;
import java.util.ArrayList;

public class Polyline extends Shape{
    ArrayList<Point> p;
    public Polyline(){
        //super()
        p = new ArrayList<Point>();
        p.add(new Point(x,y));
        p.add(new Point(xEnd,yEnd));
    }
    public void addPoint(Point point){
        p.add(point);
    }
    public void addBreak(){
        p.add(null);
    }
    @Override
    public void print(Graphics2D g2) {
        for(int i = 0; i < p.size()-1; i++){
            if(p.get(i+1) == null){
                i+=2;
            }
            g2.drawLine(p.get(i).x,p.get(i).y,p.get(i+1).x,p.get(i+1).y);
        }
    }
}
