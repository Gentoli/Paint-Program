package ca.utoronto.utm.paint;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.awt.Color;
import java.awt.Stroke;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ShapeBuilder {

	private static Map<String, Class<? extends Shape>> classMap;
	private final static Class[] shapConst = {int.class, int.class, int.class, int.class, Color.class, float.class, Stroke.class,int.class};
	private final static Class[] polyConst = {int.class, int.class, int.class, int.class, Color.class, float.class, Stroke.class};
	private final static String[] subClasses = {};


	static{
		Map<String, Class<? extends Shape>> map = new HashMap<String, Class<? extends Shape>>();
		for(String s:subClasses) {
			try {
				map.put(s,Class.forName(s).asSubclass(Shape.class));
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			};

		}
	}
    private Class <? extends Shape>  shape;
    private Color colour;
    private float lineThickness;
    private int x, y, xEnd, yEnd;
    private Stroke stroke;

    public ShapeBuilder(String type, int x, int y) {
	    shape = classMap.get(type);

	    this.x=x;
	    this.y=y;
    }

    public ShapeBuilder setColour(Color colour) {
        this.colour = colour;
        return this;
    }

    public ShapeBuilder setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
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

    public Shape build(){
    	Constructor c;
	    Shape s;
	    try {
			if(shape==RegularPolygon.class){
				c=shape.getConstructor(polyConst);
				s=(Shape)c.newInstance(x,y,xEnd,yEnd,colour,lineThickness,stroke,edges);
			}else {
				c=shape.getConstructor(shapConst);
				s=(Shape)c.newInstance(x,y,xEnd,yEnd,colour,lineThickness,stroke);
			}
			return s;
	    } catch(NoSuchMethodException e) {
		    e.printStackTrace();
	    } catch(IllegalAccessException e) {
		    e.printStackTrace();
	    } catch(InstantiationException e) {
		    e.printStackTrace();
	    } catch(InvocationTargetException e) {
		    e.printStackTrace();
	    }
	    return null;
    }

}


