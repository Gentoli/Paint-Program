package ca.utoronto.utm.pointer;


import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class EventFactory {
	private Component base;
	private List<PointerListener> listeners = new ArrayList<PointerListener>();

	public EventFactory(Component component) {
		base=component;
	}

	public boolean add(PointerListener pointerListener) {
		return listeners.add(pointerListener);
	}

	public void add(int index, PointerListener element) {
		listeners.add(index, element);
	}

	public void firePointerEvent(int eventId, long when, int modifiers,int xAbs, int yAbs, int clickCount, int pointerId, int pressure){
		Point p = new Point(xAbs,yAbs);
		SwingUtilities.convertPointFromScreen(p,base);
		PointerEvent event =  new PointerEvent(base,eventId,when,modifiers,p.x,p.y,xAbs,yAbs,clickCount,0,pointerId,pressure);
		for(PointerListener l:listeners) {
			l.pointerUpdated(event);
		}
	}

}
