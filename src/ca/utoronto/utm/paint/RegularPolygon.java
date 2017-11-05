package ca.utoronto.utm.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public class RegularPolygon extends Shape {
    public int[] verticiesX;
    public int[] verticiesY;
    public float[] intercepts;
    public float[] slopes;
	private boolean center;

    public RegularPolygon(int x, int y, Color colour, float lineThickness, boolean fill, Stroke stroke,int vertices,boolean center) {
        super(x, y, colour, lineThickness, fill, stroke);
        //System.out.println(x+" "+y+" "+xEnd+" "+yEnd);
        this.verticiesX = new int[vertices];
        this.verticiesY = new int[vertices];
        this.intercepts = new float[vertices];
        this.slopes = new float[vertices];
	    this.center = center;
        calculateVerticies();
    }

    private void calculateVerticies() {

        double angles = 2 * Math.PI / verticiesX.length;
        double radius = center?Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)):Math.min(getWidth(),getHeight())/2;
        double mouseAngle = center?Math.atan2(-getHeight(), getWidth())-Math.PI/2:Math.PI/4;
        int offsetX = center?0:getWidth()/2;
        int offsetY = center?0:getHeight()/2;

        for (int i = 0; i < verticiesX.length; i++) {
            double x = radius * Math.sin(i * angles + mouseAngle);
            double y = radius * Math.cos(i * angles + mouseAngle);
            Point p = rotate(x, y, Math.PI);
            verticiesX[i] = this.x+p.x+offsetX;
            verticiesY[i] = this.y+p.y+offsetY;
        }
        //calculateLines(verticiesX, verticiesY);
    }
    /*
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
                verticiesX[i] = verticiesX[i] + xMin;
            }
            if (verticiesY[i] -yMin != 0) {
                verticiesY[i] = verticiesY[i] - yMin;
            }// bool?true:false
        }
    }*/

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
    	prepare(g);
        g.setStroke(new BasicStroke(lineThickness));
        calculateVerticies();
        g.drawPolygon(verticiesX, verticiesY, verticiesX.length);
    }
}


//    double angles = 2 * Math.PI / verticiesX.length;
//    double radius = centered?Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)):Math.min(getWidth(),getHeight())/2;
//    double mouseAngle = centered?Math.atan2(-getHeight(), getWidth()):0;
//    int offsetX = centered?0:getWidth()/2;
//    int offsetY = centered?0:getHeight()/2;
//
//
//        for (int i = 0; i < verticiesX.length; i++) {
//        double x = radius * Math.sin(i * angles + mouseAngle);
//        double y = radius * Math.cos(i * angles + mouseAngle);
//        Point p = rotate(x, y, Math.PI);
//        verticiesX[i] = this.x+p.x+offsetX;
//        verticiesY[i] = this.y+p.y+offsetY;
