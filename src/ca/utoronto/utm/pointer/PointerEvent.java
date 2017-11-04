package ca.utoronto.utm.pointer;

import java.awt.Component;
import java.awt.event.MouseEvent;

public class PointerEvent {

    private int pointerId,pressure;
    private final MouseEvent event;


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public PointerEvent(Component source, int eventId, long when, int modifiers,int x,int y,int xAbs, int yAbs, int clickCount,int button, int pointerId, int pressure) {
        event = new MouseEvent(source,eventId,when,modifiers,x,y,xAbs,yAbs,clickCount,false,button);
        this.pointerId = pointerId;
        this.pressure = pressure;
    }

	public PointerEvent(MouseEvent event) {
		this.event = event;
		this.pointerId = 0;
		this.pressure = 0;
	}

	public int getPointerId() {
        return pointerId;
    }

    public int getPressure() {
        return pressure;
    }

	public MouseEvent getEvent(){
    	return event;
	}
}
