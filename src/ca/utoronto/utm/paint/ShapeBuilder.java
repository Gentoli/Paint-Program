package ca.utoronto.utm.paint;


import java.awt.Color;
import java.awt.Stroke;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ShapeBuilder {

	private static Constructor<? extends Shape>[] classes;
	private final static Class[] shapConst = {int.class, int.class, Color.class, float.class,boolean.class,Stroke.class};
	private final static Class[] polyConst = {int.class, int.class, Color.class, float.class,boolean.class, Stroke.class,int.class ,boolean.class};
	private final static String[] subClasses = {"RegularPolygon","Ellipse","Polyline"};
	private final static String pack = "ca.utoronto.utm.paint.";
	// PolyLine, Squiggle,Polygon, Rectangle, Circle
	private final static int[] SHAPES = {-2,-1,0,4,100};

	public static int getShape(int index){
		return SHAPES[index];
	}

	public static int getShapeCount(){
		return SHAPES.length;
	}

	static{
		classes = new Constructor[Arrays.binarySearch(SHAPES, 0)+1];
		try {
			classes[0]= Class.forName(pack+subClasses[0]).asSubclass(Shape.class).getConstructor(polyConst);
		} catch(NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 1; i < classes.length; i++) {
			try {
				classes[i]=Class.forName(pack+subClasses[classes.length-i]).asSubclass(Shape.class).getConstructor(shapConst);
			} catch(ClassNotFoundException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}

	}
    private Constructor <? extends Shape>  shape;
    private Color colour;
    private float lineThickness;
    private int x, y;
    private Stroke stroke;
    private boolean fill;

	public ShapeBuilder setCenter(boolean center) {
		this.center = center;
		return this;
	}

	private boolean center;
	private int edges;


    public ShapeBuilder(int type, int x, int y) {
		System.out.println(type);
		if(type<1)
	    	shape=classes[Math.abs(type)];
	    else
	    	shape=classes[0];
	    this.x=x;
	    this.y=y;
	    edges=type;
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

    public Shape build(){
	    try {
		    if(edges<0)
			    return shape.newInstance(x,y,colour,lineThickness,fill,stroke);
		    else
			    return shape.newInstance(x,y,colour,lineThickness,fill,stroke,edges,center);
	    } catch(InstantiationException | IllegalAccessException | InvocationTargetException e) {
		    e.printStackTrace();
	    }
	    return null;
    }

	public ShapeBuilder setEdges(int edges) {
		this.edges = edges;
		return this;
	}
}


