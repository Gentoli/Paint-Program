package ca.utoronto.utm.pointer;


import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Create A PointerEvent Base on a Component
 */
public class WindowsEventComponent {
	private Component base;
	private ViewBorder bounds;
	private List<PointerListener> listeners = new ArrayList<PointerListener>();

	public WindowsEventComponent(Component component, ViewBorder bounds) {
		base = component;
		this.bounds = bounds;
	}

	public boolean add(PointerListener pointerListener) {
		return listeners.add(pointerListener);
	}

	public void firePointerEvent(int eventId, long when, int modifiers, int xAbs, int yAbs, int clickCount, int button, int pointerId, float pressure) {
		Point p = new Point(xAbs, yAbs);
		SwingUtilities.convertPointFromScreen(p, base);
		if(eventId == MouseEvent.MOUSE_PRESSED && (!base.contains(p) || !isVisible(xAbs, yAbs))) {
			return;
		}
		PointerEvent event = new PointerEvent(base, eventId, when, modifiers, p.x, p.y, xAbs, yAbs, clickCount, button, pointerId, pressure);
		for(PointerListener l : listeners) {
			l.pointerUpdated(event);
		}
	}

	private boolean isVisible(int xAbs, int yAbs) {
		if(bounds == null)
			return true;
		return bounds.contains(new Point(xAbs, yAbs));
	}

	public void fireModifierEvent(ModifierEvent event) {
		for(PointerListener l : listeners) {
			l.modifierUpdated(event);
		}
	}
}
