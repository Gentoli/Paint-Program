package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

import java.awt.event.MouseEvent;

public class ConcavePolygonTool implements ITool{
    private StylePanel style;
    private PaintPanel paintPanel;
    private PaintShape[] shapes;

    public ConcavePolygonTool(StylePanel stylePanel, PaintPanel paintPanel, PaintShape[] shapes) {
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
                try {
                    shapes[e.getPointerId()]=new ConcavePolygon(e.getX(),e.getY(),style.getColour(),
                            style.getLineThickness(),style.isFill(),style.getStrokeStyle(),
                            paintPanel.getEdges(),style.isBorder(),style.getBorderColour(),e.isShiftDown(),e.isAltDown());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                break;
            case MouseEvent.MOUSE_MOVED:
                if(shapes[e.getPointerId()]!=null) {
                    shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
                    ((ConcavePolygon)shapes[e.getPointerId()]).setCenter(e.isAltDown());
                    ((ConcavePolygon)shapes[e.getPointerId()]).setRight(e.isShiftDown());
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                shapes[e.getPointerId()].mouseMoved(e.getX(),e.getY());
                ((ConcavePolygon)shapes[e.getPointerId()]).setCenter(e.isAltDown());
                ((ConcavePolygon)shapes[e.getPointerId()]).setRight(e.isShiftDown());
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
                changed |= ((ConcavePolygon)s).setCenter(e.isAltDown());
                changed |= ((ConcavePolygon)s).setRight(e.isShiftDown());
            }
        }
        return changed;
    }
}
