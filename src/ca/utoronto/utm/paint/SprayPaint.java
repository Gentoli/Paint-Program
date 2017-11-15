package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.*;
import java.nio.file.Path;

public class SprayPaint extends PaintShape {

    private Path2D path;
    private Ellipse2D circle;
    private Point[] pointArray;
    private float diameter;

    public SprayPaint(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle, float radius) {
        super(x, y, colour, lineThickness, fill, strokeStyle);
        path = new Path2D.Float();
        path.moveTo(x,y);
        this.diameter = radius*2;
        circle = new Ellipse2D.Double(x - diameter/2, y - diameter/2, diameter, diameter);
        randomPoints();
    }

    @Override
    public void mouseMoved(int x, int y) {
        path.lineTo(x, y);
        circle = new Ellipse2D.Double(x - diameter/2, y - diameter/2, diameter, diameter);
    }

    @Override
    public void print(Graphics2D g2) {
        prepare(g2);
        g2.draw(this);
        for (Point point : pointArray) {
            g2.drawLine(point.x, point.y, point.x, point.y);
        }
    }

    public void randomPoints() {
        double centerX = circle.getCenterX();
        double centerY = circle.getCenterY();
        double radius = diameter/2;
        double area = Math.PI*radius*radius;
        Point[] pointArray = new Point[(int)Math.ceil(area/3)];
        for (int i = 0; i < pointArray.length; i++) {
            double angle = 2*Math.PI*Math.random();
            double absoluteDistance = Math.random()+Math.random();
            double distance;
            if (absoluteDistance > 1) {
                distance = 2-absoluteDistance;
            }
            else {
                distance = absoluteDistance;
            }
            pointArray[i] = new Point((int)(centerX +distance*Math.cos(angle)*diameter),
                    (int)(centerY + distance*Math.sin(angle)*diameter));
        }
        this.pointArray = pointArray;
    }

    @Override
    public Rectangle getBounds() {
        return path.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return path.getBounds2D();
    }

    @Override
    public boolean contains(double x, double y) {
        return path.contains(x,y);
    }

    @Override
    public boolean contains(Point2D p) {
        return path.contains(p);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return path.intersects(x,y,w,h);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return path.intersects(r);
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return path.contains(x,y,w,h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return path.contains(r);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return path.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return path.getPathIterator(at, flatness);
    }

    public Ellipse2D getCircle() {
        return circle;
    }
}
