package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RegularPolygon extends Shape {
    public int[] verticiesX;
    public int[] verticiesY;
    public float[] intercepts;
    public float[] slopes;

    public RegularPolygon(Point point, Point endPoint, int verticies, int thickness) {
        super(point, endPoint, thickness);
        this.verticiesX = new int[verticies];
        this.verticiesY = new int[verticies];
        this.intercepts = new float[verticies];
        this.slopes = new float[verticies];
        calculateVerticies();
    }

    private void calculateVerticies() {
        double angles = 2 * Math.PI / verticiesX.length;
        double radius = Math.sqrt(Math.pow(endPoint.getX() - point.getX(), 2) + Math.pow(endPoint.getY() - point.getY(), 2));
        double mouseAngle = Math.asin((endPoint.getX() - point.getX()) / radius);
        for (int i = 0; i < verticiesX.length; i++) {
            double x = radius * Math.sin(i * angles + mouseAngle);
            double y = radius * Math.cos(i * angles + mouseAngle);
            Point p = rotate(x, y, Math.PI);
            verticiesX[i] = p.getX() + point.getX();
            verticiesY[i] = p.getY() + point.getY();
        }
        calculateLines(verticiesX, verticiesY);
    }

    //updates the verticies and intercepts arrays based on the vertex points
    public void calculateLines(int[] verticiesX, int[] verticiesY) {
        for (int i = 0; i < verticiesX.length; i++) {
            if (i == verticiesX.length - 1) {
                slopes[i] = (verticiesY[0] - verticiesY[i]) / (verticiesX[0] - verticiesX[i]);
            } else {
                slopes[i] = (verticiesY[i + 1] - verticiesY[i]) / (verticiesX[i + 1] - verticiesX[i]);//m = rise/run
            }
            intercepts[i] = verticiesY[i] - slopes[i] * verticiesX[i];// b = y-mx
        }
    }

    public boolean intersectEdge(Point p) {
        for (int i = 0; i < verticiesX.length; i++) {
            float epsilon = 0.01f;
            // the epsilon term is to account for the precision error when comparing floats directly.
            // the thickness term is so that we check if the point is on the line within the thickness bounds of the line
            if (Math.abs(intercepts[i] + slopes[i] * p.getX() - p.getY()) < epsilon + thickness / 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void print(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(thickness));
        g2.drawPolygon(verticiesX, verticiesY, verticiesX.length);
    }
}
