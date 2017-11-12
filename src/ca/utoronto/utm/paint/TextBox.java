package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class TextBox  extends PaintShape{

    private String textString;
    private String fontName;
    private int fontSize;
    private int fontStyle;
    private Rectangle2D bounds;

    public TextBox(int x, int y, Color colour, int fontSize, String fontName, String textString, int fontStyle) {
        super(x, y, colour, 1, false, 0);
        this.textString = textString;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.bounds = new Rectangle2D.Float(x, y, xEnd-x, yEnd-y);
    }

    @Override
    public void print(Graphics2D g2) {
        prepare(g2);
        g2.setFont(new Font(fontName, fontStyle, fontSize));
        g2.drawString(textString, x, (yEnd-y)/2);
    }

    @Override
    public Rectangle getBounds() {
        return bounds.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return bounds.getBounds2D();
    }

    @Override
    public boolean contains(double x, double y) {
        return bounds.contains(x, y);
    }

    @Override
    public boolean contains(Point2D p) {
        return bounds.contains(p);
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return bounds.intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return bounds.intersects(r);
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        return bounds.contains(x, y, w, h);
    }

    @Override
    public boolean contains(Rectangle2D r) {
        return bounds.contains(r);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return bounds.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return bounds.getPathIterator(at, flatness);
    }
}
