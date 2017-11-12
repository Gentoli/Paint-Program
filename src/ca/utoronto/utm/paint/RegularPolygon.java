package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*creates a polygon that is equiangular (verticie anglues are of equal value). This can be stretched in either the x
* or y direction and flipped around.*/
public class RegularPolygon extends PaintShape {
    public int[] verticiesX;
    public int[] verticiesY;
    protected boolean center;
    protected boolean right;
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
    }
    //calculates the vericies angle values of the polygon
    private void calculateVerticies() {
        double angles = 2 * Math.PI / polygon.npoints;
        int absMin = Math.min(Math.abs(getWidth()),Math.abs(getHeight()));
        double radiusSquared;
        double radius = absMin/2;
        double mouseAngle = 0;
        int offsetX;
        int offsetY;
        double rFactor = 2;
        double xFactor = Math.abs(getWidth())/(rFactor*radius);
        double yFactor = Math.abs(getHeight())/(rFactor*radius);
        int flip = 1;
        if(center){
           radiusSquared =  Math.pow(getWidth(), 2) + Math.pow(getHeight(),2);
           if(polygon.npoints == 4){
               radius = Math.sqrt(radiusSquared);
               mouseAngle = Math.atan2(-getHeight(), getWidth())-Math.PI/2;
           }
           offsetX = x;
           offsetY = y;
           if(right){
               xFactor = 1;
               yFactor = 1;
           }
        }else{
            if(polygon.npoints == 4){
                mouseAngle = Math.PI/4;
            }
            if(getHeight() < 0){
                flip = -1;
            }
            offsetX = getXMid();
            offsetY = getYMid();
        }
        if(polygon.npoints == 4){
            rFactor = Math.sqrt(2);
        }/*
        double radiusSquared = center?Math.pow(getWidth(), 2) + Math.pow(getHeight(),2):2*Math.pow(absMin/2,2);
        double radius = center||polygon.npoints==4?Math.sqrt(radiusSquared):absMin/2;
        double mouseAngle = center?Math.atan2(-getHeight(), getWidth())-Math.PI/2:(polygon.npoints==4?Math.PI/4:0);
        int offsetX = center?x:getXMid();
        int offsetY = center?y:getYMid();
        double rFactor=polygon.npoints==4?Math.sqrt(2):2;
        double xFactor=center||right?1:Math.abs(getWidth())/(rFactor*radius);
        double yFactor=center||right?1:Math.abs(getHeight())/(rFactor*radius);
        int flip = !center&&getHeight()<0?-1:1;
        */
        for (int i = 0; i < polygon.npoints; i++) {
            double x = radius * Math.sin(i * angles + mouseAngle) * xFactor;
            double y = flip*radius * Math.cos(i * angles + mouseAngle) * yFactor;
            Point p = rotate(x, y, Math.PI);
            verticiesX[i] = p.x+offsetX;
            verticiesY[i] = p.y+offsetY;
        }
    }

    private int getXMid() {
        return getWidth()/2;
    }
    private int getYMid() {
        return getHeight()/2;
    }

    private int getHeight() {
        return yEnd-y;
    }

    private int getWidth() {
        return xEnd-x;
    }

    public Point rotate(double x, double y, double angle){
        double retX = x*Math.cos(angle) - y*Math.sin(angle);
        double retY = x*Math.sin(angle) + y*Math.cos(angle);
        return new Point((int)retX,(int)retY);
    }

    @Override
    public void mouseMoved(int x, int y) {
        xEnd=x;yEnd=y;
        calculateVerticies();
    }

    //prints the polygon to the screen
    @Override

    public void print(Graphics2D g2) {
    	prepare(g2);
        g2.setStroke(stroke);
        calculateVerticies();
        if(fill)
            g2.fillPolygon(polygon);
        else
        g2.drawPolygon(polygon);
        //g.drawRect(x,y,getWidth(),getHeight());
    }
    
    public boolean setCenter(boolean center) {
        boolean orig = this.center;
        this.center = center;
        return orig != center;
    }

    public boolean setRight(boolean right) {
        boolean orig = this.right;
        this.right = right;
        return orig != right;
    }

    @Override
    public Rectangle getBounds() {
        return polygon.getBounds();
    }

    public boolean contains(Point p) {
        return polygon.contains(p);
    }

    public boolean contains(int x, int y) {
        return polygon.contains(x, y);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return polygon.getBounds2D();
    }

    @Override
    public boolean contains(double x, double y) {
        return polygon.contains(x, y);
    }

    @Override
    public boolean contains(Point2D p) {
        return polygon.contains(p);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return polygon.intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return polygon.intersects(r);
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return polygon.contains(x, y, w, h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return polygon.contains(r);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return polygon.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return polygon.getPathIterator(at, flatness);
    }
}
