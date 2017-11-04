package ca.utoronto.utm.paint;

import java.awt.*;

public class ShapeBuilder {

    private Shape shape;
    private String mode;
    private Color colour;
    private float lineThickness;
    private int x, y, xEnd, yEnd;
    private Stroke stroke;

    public ShapeBuilder() {

    }

    public ShapeBuilder setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public ShapeBuilder setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public ShapeBuilder setColour(Color colour) {
        this.colour = colour;
        return this;
    }

    public ShapeBuilder setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
        return this;
    }

    public ShapeBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public ShapeBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public ShapeBuilder setXEnd(int xEnd) {
        this.xEnd = xEnd;
        return this;
    }

    public ShapeBuilder setYEnd(int yEnd) {
        this.yEnd = yEnd;
        return this;
    }

    public ShapeBuilder setStroke(Stroke stroke) {
        this.stroke = stroke;
        return this;
    }


}


