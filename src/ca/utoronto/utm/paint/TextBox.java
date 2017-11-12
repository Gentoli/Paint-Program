package ca.utoronto.utm.paint;

import java.awt.*;
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
    private int x,y;

    public TextBox(int x, int y, Color colour, int fontSize, String fontName, String textString, int fontStyle) {
        super(x, y, colour, 1, false, 0);
        this.textString = textString;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle2D.Float(x, y, 1, 1);
    }

    @Override
    public void mouseMoved(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void print(Graphics2D g2) {
        prepare(g2);
        Font font = new Font(fontName, fontStyle, fontSize);
        g2.setFont(font);
        g2.drawString(textString, this.x, this.y);
        FontMetrics fontMetrics = g2.getFontMetrics(font);
        this.bounds = fontMetrics.getStringBounds(textString, g2);
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
