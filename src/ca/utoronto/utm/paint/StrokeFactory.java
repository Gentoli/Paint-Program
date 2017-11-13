package ca.utoronto.utm.paint;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;

public class StrokeFactory {

    public Stroke createStroke(int strokeStyle, float lineThickness) {
        if(strokeStyle==0)return new BasicStroke(lineThickness);
        if(strokeStyle==1)return new BasicStroke(lineThickness, 0, 0, 10.0f, new float[] {16.0f,20.0f},0.0f);
        if(strokeStyle==2)return new ShapeStroke(new Ellipse2D.Double(0,0,lineThickness,lineThickness), lineThickness+5,false);
        if(strokeStyle==3)return new ShapeStroke(new QuadCurve2D.Double(50,100,100,170,150,100),1,false);
        if(strokeStyle==4)return new ShapeStroke(new QuadCurve2D.Double(-1,1,0,0,1,1),1,true);
        return null;
    }
}
