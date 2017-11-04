package ca.utoronto.utm.paint;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class PointerEvent extends MouseEvent {

    private int x,y,id,pressure,button;
    private int xAbs;
    private int yAbs;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PointerEvent(Object source, int x, int y, int id, int pressure) {
        super(source);
        this.x = x;
        this.y = y;
        this.id = id;
        this.pressure = pressure;
    }

    public java.awt.Point getLocationOnScreen(){
        return new Point(xAbs, yAbs);
    }

    public int getXOnScreen() {
        return xAbs;
    }

    public int getYOnScreen() {
        return yAbs;
    }

    public int getX() {

        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getPressure() {
        return pressure;
    }


}
