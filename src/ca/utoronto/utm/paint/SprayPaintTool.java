package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

import java.awt.event.MouseEvent;

public class SprayPaintTool implements ShapeManipulatorStrategy {

    private StylePanel style;
    private PaintShape[] shapes;
    private SprayPaintThread sprayPaintThread;

    public SprayPaintTool(StylePanel style, PaintShape[] shapes) {
        this.style = style;
        this.shapes = shapes;
        sprayPaintThread = new SprayPaintThread(null);
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
        switch(e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                shapes[e.getPointerId()] = new SprayPaint(e.getX(), e.getY(), style.getColour(),
                        1f, style.isFill(), style.getStrokeStyle(), style.getLineThickness());
                sprayPaintThread = new SprayPaintThread((SprayPaint)shapes[e.getPointerId()]);
                sprayPaintThread.start();
                //start thread that loops random points and print
                break;
            case MouseEvent.MOUSE_MOVED:
                if(shapes[e.getPointerId()] != null) {
                    shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                if(shapes[e.getPointerId()] == null)
                    break;
                sprayPaintThread.interrupt();
                shapes[e.getPointerId()].mouseMoved(e.getX(), e.getY());
                rtn = shapes[e.getPointerId()];
                shapes[e.getPointerId()] = null;
        }
        return rtn;
    }

    @Override
    public boolean handleModifierUpdated(ModifierEvent e) {

        return false;
    }


}
