package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

public class RegularPolygon extends Shape {
    public int[] verticiesX;
    public int[] verticiesY;
    public float[] intercepts;
    public float[] slopes;
    private boolean centered;

    public RegularPolygon(int x, int y, int xEnd, int yEnd, Color colour, float lineThickness, boolean fill, Stroke stroke,int verticies) {
        super(x, y, xEnd, yEnd, colour, lineThickness, fill, stroke);
        System.out.println(x+" "+y+" "+xEnd+" "+yEnd);
        this.verticiesX = new int[verticies];
        this.verticiesY = new int[verticies];
        this.intercepts = new float[verticies];
        this.slopes = new float[verticies];
        this.centered = centered;
        calculateVerticies();
    }

    private void calculateVerticies() {
        double angles = 2 * Math.PI / verticiesX.length;
        double radius = Math.sqrt(Math.pow(xEnd - x, 2) + Math.pow(yEnd - y, 2));
        double mouseAngle = Math.asin((xEnd - x) / radius);
        for (int i = 0; i < verticiesX.length; i++) {
            double x = radius * Math.sin(i * angles + mouseAngle);
            double y = radius * Math.cos(i * angles + mouseAngle);
            Point p = rotate(x, y, Math.PI);
            if(centered) {
                verticiesX[i] = this.x+p.x/2;
                verticiesY[i] = this.y+p.y/2;
            }else{
                verticiesX[i] = this.x;
                verticiesY[i] = this.y;
            }
        }
        calculateLines(verticiesX, verticiesY);
    }

    public void stretch(int xShift, int yShift){
        //find min
        int xMin = verticiesX[0];
        int yMin = verticiesY[0];
        for(int i = 1; i < verticiesX.length; i++){
            if(verticiesX[i] < xMin){
                xMin = verticiesX[i];
            }
            if(verticiesY[i] < yMin){
                yMin = verticiesY[i];
            }
        }
        for(int i = 0; i < verticiesX.length; i++){
            if (verticiesX[i] - xMin != 0) {
                verticiesX[i] = verticiesX[i] + xShift;
            }
            if (verticiesY[i] -yMin != 0) {
                verticiesY[i] = verticiesY[i] + yShift;
            }// bool?true:false
        }
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
            if (Math.abs(intercepts[i] + slopes[i] * p.x - p.y) < epsilon + lineThickness / 2) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void print(Graphics2D g) {
        g.setStroke(new BasicStroke(lineThickness));
        g.drawPolygon(verticiesX, verticiesY, verticiesX.length);
    }
}
