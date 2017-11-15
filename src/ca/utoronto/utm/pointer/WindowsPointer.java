package ca.utoronto.utm.pointer;

import javax.swing.SwingUtilities;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Object Handle Win32 API Calls from JNI bridge
 */
public class WindowsPointer extends MouseAdapter {
	public static final int POINTER_MAX = 20;
	public static final int MAX_PRESSURE = 1024;
	private static final boolean TOUCH_SUPPORTED;

	static {
		boolean dllLoaded = true;
		try {
			System.loadLibrary("JNI");
		} catch(Error e) {
			System.out.println("JNI failed to load... falling back to MouseListener");
			dllLoaded = false;
		}
		TOUCH_SUPPORTED = dllLoaded;
	}

	private Frame frame;
	private int[] points = new int[POINTER_MAX];
	private Component[] components = new Component[POINTER_MAX];
	private Map<Component, WindowsEventComponent> listeners = new HashMap<Component, WindowsEventComponent>();

	public WindowsPointer(Frame frame) {
		if(frame == null)
			throw new IllegalArgumentException("null frame");
		if(TOUCH_SUPPORTED)
			try {
				init(getHWnd(frame));
				this.frame = frame;
				for(int i = 0; i < points.length; i++) {
					points[i] = -1;
				}
			} catch(RuntimeException | UnsatisfiedLinkError e) {
				e.printStackTrace();
			}
	}

	private static long getHWnd(Frame component) {
		//noinspection deprecation
		Object peer = component.getPeer();
		Class c = peer.getClass();
		try {
			for(Method m : c.getMethods()) {
				if("getHWnd".equals(m.getName())) {
					return (Long) m.invoke(peer);
				}
			}
		} catch(IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("No HWND found for " + c);
	}

	private void update(int eventId, long when, int modifiers, int xAbs, int yAbs, int clickCount,int button, int pointerId, int pressure) {
		float fPressure = pressure == 0 ? 1f : ((float) pressure / MAX_PRESSURE);
		int index = getPointId(pointerId);
		if(eventId==MouseEvent.MOUSE_PRESSED&&components[index]==null) {
			Point p = new Point(xAbs, yAbs);
			SwingUtilities.convertPointFromScreen(p,frame);
			Component comp = frame.findComponentAt(p);
			if(listeners.containsKey(comp))
				components[index] = frame.findComponentAt(xAbs, yAbs);
		}
		if(components[index]!=null){
			listeners.get(components[index]).firePointerEvent(eventId, when, modifiers, xAbs, yAbs, clickCount, button, index, fPressure);
		}


		if(eventId == MouseEvent.MOUSE_EXITED&&index!=-1)
			releasePoint(index);
	}

	private void keyUpdate(int eventId, long when, int modifiers, int keyCode, char keyChar) {
		ModifierEvent event = new ModifierEvent(frame, eventId, when, modifiers, keyCode, keyChar);
		for(WindowsEventComponent e : listeners.values()) {
			e.fireModifierEvent(event);
		}
	}

	private native void init(long hWnd);

	public void addListener(PointerListener pointerListener, Component component, ViewBorder border) {
		if(frame != null&&frame.isAncestorOf(component)) {
			WindowsEventComponent f = listeners.get(component);
			if(f == null) {
				WindowsEventComponent windowsEventComponent = new WindowsEventComponent(component, border);
				windowsEventComponent.add(pointerListener);
				listeners.put(component, windowsEventComponent);
			} else
				f.add(pointerListener);
		} else {
			InputEventProxy eventProxy = new InputEventProxy(pointerListener);
			component.addKeyListener(eventProxy);
			component.addMouseListener(eventProxy);
			component.addMouseMotionListener(eventProxy);
		}
	}

	private int getPointId(int id) {
		for(int i = 0; i < points.length; i++) {
			if(points[i] == id)
				return i;
		}

		for(int i = 0; i < points.length; i++) {
			if(points[i] == -1) {
				points[i] = id;
				return i;
			}
		}
		return -1;
	}

	private void releasePoint(int id) {
		components[id]=null;
		points[id] = -1;
	}
}