package ca.utoronto.utm.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;

/*creates a polygon that is equiangular (verticie anglues are of equal value). This can be stretched in either the x
* or y direction and flipped around.*/
public class RegularPolygon extends Shape {
    public int[] verticiesX;
    public int[] verticiesY;
    private Polygon polygon;
    /**
     * creates a regular polygon
     * @param x the initial x coordinate
     * @param y the initial y coordinate
     * @param colour the color of the polygon outline
     * @param lineThickness the value of the line thickness
     * @param fill the color of the inside of the polygon
     * @param strokeStyle the style of the outline of the polygon
     * @param vertices the number of verticies in the polygon
     * @param center if the polygon is centered or not
     * @param right if the polygon has equilateral.
     */
    public RegularPolygon(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle,int vertices,boolean center,boolean right) {
        super(x, y, colour, lineThickness, fill, strokeStyle);
        this.verticiesX = new int[vertices];
        this.verticiesY = new int[vertices];
        polygon=new Polygon();
        polygon.npoints=vertices;
        polygon.xpoints=verticiesX;
        polygon.ypoints=verticiesY;
	    this.center = center;
	    this.right = right;
        calculateVerticies();
    }
    //calculates the vericies angle values of the polygon
    private void calculateVerticies() {
        double angles = 2 * Math.PI / polygon.npoints;
        int absMin = Math.min(Math.abs(getWidth()),Math.abs(getHeight()));
        double radiusSquared = center?Math.pow(getWidth(), 2) + Math.pow(getHeight(),2):2*Math.pow(absMin/2,2);
        double radius = center||polygon.npoints==4?Math.sqrt(radiusSquared):absMin/2;
        double mouseAngle = center?Math.atan2(-getHeight(), getWidth())-Math.PI/2:(polygon.npoints==4?Math.PI/4:0);
        int offsetX = center?x:getXMid();
        int offsetY = center?y:getYMid();
        double rFactor=polygon.npoints==4?Math.sqrt(2):2;
        double xFactor=center||right?1:Math.abs(getWidth())/(rFactor*radius);
        double yFactor=center||right?1:Math.abs(getHeight())/(rFactor*radius);
        int flip = !center&&getHeight()<0?-1:1;
        for (int i = 0; i < polygon.npoints; i++) {
            double x = radius * Math.sin(i * angles + mouseAngle) * xFactor;
            double y = flip*radius * Math.cos(i * angles + mouseAngle) * yFactor;
            Point p = rotate(x, y, Math.PI);
            verticiesX[i] = p.x+offsetX;
            verticiesY[i] = p.y+offsetY;
        }
    }

    //prints the polygon to the screen
    @Override
    public void print(Graphics2D g) {
    	prepare(g);
        g.setStroke(stroke);
        calculateVerticies();
        if(fill)
            g.fillPolygon(polygon);
        else
        g.drawPolygon(polygon);
        //g.drawRect(x,y,getWidth(),getHeight());
    }
    //sets the end point of the mouse
    @Override
    public void setEnd(int x, int y) {
        super.setEnd(x, y);
        calculateVerticies();
    }
    //sets the x coordinate of the end point
    @Override
    public void setXEnd(int xEnd) {
        super.setXEnd(xEnd);
        calculateVerticies();
    }
    //sets the y coordinate of the end point
    @Override
    public void setYEnd(int yEnd) {
        super.setYEnd(yEnd);
        calculateVerticies();
    }
}
