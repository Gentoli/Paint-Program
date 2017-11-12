package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

import java.awt.event.MouseEvent;

public class SquiggleTool implements ITool {
    private StylePanel style;
    private PaintShape[] shapes;

    public SquiggleTool(StylePanel style, PaintShape[] shapes) {
        this.style = style;
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
                shapes[e.getPointerId()]=new Polyline(e.getX(),e.getY(),style.getColour(),
                        style.getLineThickness(),style.isFill(),style.getStrokeStyle());
                break;
            case MouseEvent.MOUSE_MOVED:
                if(shapes[e.getPointerId()]!=null) {
                    shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                shapes[e.getPointerId()].mouseMoved(e.getX(),e.getY());
                rtn =  shapes[e.getPointerId()];
                shapes[e.getPointerId()]=null;
        }
        return rtn;
    }

    @Override
    public void handleModifierUpdated(ModifierEvent e) {

    }

}
