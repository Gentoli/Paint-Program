package ca.utoronto.utm.paint;

import java.awt.*;

public class ConcavePolygon extends RegularPolygon {
    /**
     * creates a regular polygon
     *
     * @param x             the initial x coordinate
     * @param y             the initial y coordinate
     * @param colour        the color of the polygon outline
     * @param lineThickness the value of the line thickness
     * @param fill          the color of the inside of the polygon
     * @param strokeStyle   the style of the outline of the polygon
     * @param vertices      the number of verticies in the polygon
     * @param center        if the polygon is centered or not
     * @param right         if the polygon has equilateral.
     */
    public ConcavePolygon(int x, int y, Color colour, float lineThickness, boolean fill, int strokeStyle, int vertices, boolean center, boolean right) {
        super(x, y, colour, lineThickness, fill, strokeStyle, vertices * 2, center, right);
    }
    @Override
    protected void calculateModel() {
        double angles = 2 * Math.PI / polygon.npoints;
        final double radius = 1.0;
        model.moveTo(0,1);//every polygon vertex starts from the top middle
        for (int i = 1; i < polygon.npoints; i++) {
            double x = (radius-0.5*(i%2)) * Math.sin(i * angles);
            double y = (radius-0.5*(i%2)) * Math.cos(i * angles);
            model.lineTo(x,y);
        }
        model.closePath();
    }
}
