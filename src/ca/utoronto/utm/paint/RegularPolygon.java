package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.geom.*;

/*creates a polygon that is equiangular (verticie anglues are of equal value). This can be stretched in either the x
* or y direction and flipped around.*/
public class RegularPolygon extends PaintShape {
    protected boolean center;
    protected boolean right;
    private Polygon polygon;
    private Path2D model;
    private Path2D shape;
    private AffineTransform t = new AffineTransform();
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
        model = new Path2D.Double();
        shape = new Path2D.Double();
        polygon=new Polygon();
        polygon.npoints=vertices;
	    this.center = false;
	    this.right = right;
	    calculateModel();
    }
    //draws the polygon out in model view (centered on axis with radius 1)
    private void calculateModel() {
        double angles = 2 * Math.PI / polygon.npoints;
        final double radius = 1.0;
        model.moveTo(0,1);//every polygon vertex starts from the top middle
        for (int i = 1; i < polygon.npoints; i++) {
            double x = radius * Math.sin(i * angles);
            double y = radius * Math.cos(i * angles);
            model.lineTo(x,y);
        }
        model.closePath();
    }
    private void centeredPolygonCreation(){
        t.setToTranslation(x,y);
        double mouseAngle = Math.atan2(-getWidth(),getHeight());
        t.rotate(mouseAngle);
        int dx = getWidth();
        int dy = getHeight();
        double r = Math.sqrt(dx*dx+dy*dy);
        t.scale(r,r);
        shape = (Path2D)t.createTransformedShape(model);//applies the transformations to the model
    }

    private void stretchPolygonCreation(){
        t.setToTranslation(x,y);
        t.scale(getWidth()/2,getHeight()/2);
        t.translate(1,1);//transform to account for the scale
        shape = (Path2D)t.createTransformedShape(model);
    }

    private void regularPolygonCreation(){
        t.setToTranslation(x,y);
        int dx = getWidth(); int dy = -getHeight();
        int scaleAmount = Math.min(Math.abs(dx),Math.abs(dy));
        int xflip = dx/Math.abs(dx); int yflip = dy/Math.abs(dy);
        System.out.println(xflip);
        t.scale(xflip*scaleAmount/2,-yflip*scaleAmount/2);
        t.translate(1,1);//transform to account for the scale
        shape = (Path2D)t.createTransformedShape(model);
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


    @Override
    public void mouseMoved(int x, int y) {
        xEnd=x;yEnd=y;
        System.out.println(center);
        if(center){
            centeredPolygonCreation();
        }else if (right){
            regularPolygonCreation();
        }else{
            stretchPolygonCreation();
        }
    }

    //prints the polygon to the screen
    @Override
    public void print(Graphics2D g) {
	    prepare(g);
        g.setStroke(stroke);
        if(fill)
            g.fill(shape);
        else {
            g.draw(shape);
        }
    }
    
    public void setCenter(boolean center) {
        this.center = center;
    }

    public void setRight(boolean right) {
        this.right = right;
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
