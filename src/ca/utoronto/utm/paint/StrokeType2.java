package ca.utoronto.utm.paint;

import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class StrokeType2 implements Stroke {
    private double spaing;

    public StrokeType2(double spacing){
        this.spaing = spacing;
    }

    @Override
    public Shape createStrokedShape(Shape s) {
        GeneralPath path = new GeneralPath();
        PathIterator pi = new FlatteningPathIterator(s.getPathIterator(null),1.0f);
        double moveX = 0; double moveY = 0; double thisX = 0;double thisY = 0;double lastX = 0;double lastY = 0;
        double offset = 0;
        int state = 0;
        double[] points = new double[6];
        while(pi.isDone()){
            state = pi.currentSegment(points);
            if(state == PathIterator.SEG_MOVETO){
                offset = 0;
                moveX = lastX = points[0];
                moveY = lastY = points[1];
                path.moveTo(moveX,moveY);
            }else if(state == PathIterator.SEG_LINETO){

            }else if(state == PathIterator.SEG_CLOSE){

            }
        }
        return null;
    }
}
