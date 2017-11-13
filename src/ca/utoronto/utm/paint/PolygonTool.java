package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class PolygonTool implements ITool {
	private StylePanel style;
	private PaintPanel paintPanel;
	private PaintShape[] shapes;
	private Constructor<RegularPolygon> constructor;
	private static final Class[] constClasses = {int.class, int.class, Color.class, float.class, boolean.class, int.class,int.class,boolean.class,Color.class,boolean.class,boolean.class};

	public PolygonTool(StylePanel stylePanel, PaintPanel paintPanel, PaintShape[] shapes,Class type) {
		style = stylePanel;
		this.paintPanel = paintPanel;
		this.shapes = shapes;
		if(!RegularPolygon.class.isAssignableFrom(type))
			throw new IllegalArgumentException();
		try {
			constructor = type.getConstructor(constClasses);
		} catch(NoSuchMethodException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
    public Drawable deselect() {
		return null;
	}

	@Override
	public void selected() {

	}

	@Override
    public Drawable handlePointerUpdate(PointerEvent e) {
		Drawable rtn = null;
	    switch(e.getID()){
		    case MouseEvent.MOUSE_PRESSED:
			    try {
				    shapes[e.getPointerId()]=constructor.newInstance(e.getX(),e.getY(),style.getColour(),
												    style.getLineThickness(),style.isFill(),style.getStrokeStyle(),
												    paintPanel.getEdges(),style.isBorder(),style.getBorderColour(),
						                            e.isShiftDown(),e.isAltDown());
			    } catch(InstantiationException | IllegalAccessException | InvocationTargetException e1) {
				    e1.printStackTrace();
			    }
			    break;
		    case MouseEvent.MOUSE_MOVED:
		    	if(shapes[e.getPointerId()]!=null) {
				    shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
				    ((RegularPolygon)shapes[e.getPointerId()]).setCenter(e.isAltDown());
				    ((RegularPolygon)shapes[e.getPointerId()]).setRight(e.isShiftDown());
			    }
			    break;
		    case MouseEvent.MOUSE_RELEASED:
			    if(shapes[e.getPointerId()]==null)
				    break;
			    shapes[e.getPointerId()].mouseMoved(e.getX(),e.getY());
			    ((RegularPolygon)shapes[e.getPointerId()]).setCenter(e.isAltDown());
			    ((RegularPolygon)shapes[e.getPointerId()]).setRight(e.isShiftDown());
		    	rtn =  shapes[e.getPointerId()];
			    shapes[e.getPointerId()]=null;
	    }
	    return rtn;
    }

    @Override
    public boolean handleModifierUpdated(ModifierEvent e) {
		boolean changed = false;
	    for(PaintShape s : shapes) {
		    if(s != null) {
			    changed |= ((RegularPolygon)s).setCenter(e.isAltDown());
			    changed |= ((RegularPolygon)s).setRight(e.isShiftDown());
		    }
	    }
	    return changed;
    }
}
