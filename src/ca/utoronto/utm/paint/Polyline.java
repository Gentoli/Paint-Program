package ca.utoronto.utm.paint;

import java.awt.*;
import java.util.ArrayList;

/**
 *  Shape with multiple dots connecting to each other.
 */
public class Polyline extends Shape{
    private ArrayList<Point> p;

    public Polyline(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle) {
        super(x, y, colour, lineThickness, fill, strokeStyle);

        p = new ArrayList<Point>();
        p.add(new Point(x,y));
    }

    public void addPoint(Point point){
        p.add(point);
    }
    @Override
    public void print(Graphics2D g2) {
        prepare(g2);
        for(int i = 0; i < p.size()-1; i++){
            g2.drawLine(p.get(i).x,p.get(i).y,p.get(i+1).x,p.get(i+1).y);
        }
        if(temp!=null) {
            Point p = this.p.get(this.p.size()-1);
            g2.drawLine(p.x,p.y,temp.x,temp.y);
        }
    }

    private Point temp;

    @Override
    public void setEnd(int x,int y){
        temp=null;
        addPoint(new Point(x,y));
    }

    public void setTemp(int x,int y){
//	    System.out.println("tmp");
	    temp = new Point(x,y);
    }

    public void end() {
//	    System.out.println(this.p.get(this.p.size()-1).getX()+" "+temp.getX());
	    temp = null;
    }
}
