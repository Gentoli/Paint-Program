package ca.utoronto.utm.paint;

import java.awt.*;

public class ShapeBuilder {

    private Shape shape;
    private String mode;
    private Color colour;
    private float lineThickness;
    private int x, y, xEnd, yEnd;
    private Stroke stroke;
    private boolean fill;

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

    public ShapeBuilder setFill(boolean fill) {
        this.fill = fill;
        return this;
    }

    public ShapeBuilder setStroke(Stroke stroke) {
        this.stroke = stroke;
        return this;
    }


}


