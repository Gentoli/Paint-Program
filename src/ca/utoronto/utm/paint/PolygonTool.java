package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseEvent;


public class PolygonTool implements ITool, ChangeListener {
	private StylePanel style;
	private PaintPanel paintPanel;
	private PaintShape[] shapes;

	public PolygonTool(StylePanel stylePanel, PaintPanel paintPanel, PaintShape[] shapes) {
		style = stylePanel;
		this.paintPanel = paintPanel;
		this.shapes = shapes;
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
			    shapes[e.getPointerId()]=new RegularPolygon(e.getX(),e.getY(),style.getColour(),
											    style.getLineThickness(),style.isFill(),style.getStrokeStyle(),
											    paintPanel.getEdges(),e.isShiftDown(),e.isAltDown());
			    break;
		    case MouseEvent.MOUSE_MOVED:
		    	if(shapes[e.getPointerId()]!=null) {
				    shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
				    ((RegularPolygon)shapes[e.getPointerId()]).setCenter(e.isAltDown());
				    ((RegularPolygon)shapes[e.getPointerId()]).setRight(e.isShiftDown());
			    }
			    break;
		    case MouseEvent.MOUSE_RELEASED:
			    shapes[e.getPointerId()].mouseMoved(e.getX(),e.getY());
			    ((RegularPolygon)shapes[e.getPointerId()]).setCenter(e.isAltDown());
			    ((RegularPolygon)shapes[e.getPointerId()]).setRight(e.isShiftDown());
		    	rtn =  shapes[e.getPointerId()];
			    shapes[e.getPointerId()]=null;
	    }
	    return rtn;
    }

    @Override
    public void handleModifierUpdated(ModifierEvent e) {
	    for(PaintShape s : shapes) {
		    if(s != null) {
			    ((RegularPolygon)s).setCenter(e.isAltDown());
			    ((RegularPolygon)s).setRight(e.isShiftDown());
		    }
	    }
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
