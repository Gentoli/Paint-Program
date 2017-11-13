package ca.utoronto.utm.pointer;

import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Object Handle Win32 API Calls from JNI bridge
 */
public final class WindowsPointer extends MouseAdapter {
	public static final int POINTER_MAX = 20;
	private static boolean touchSupported = true;
	private static WindowsPointer instance;

	static {
		try {
			System.loadLibrary("JNI");
		} catch(Error e) {
			System.out.println("JNI failed to load... falling back to MouseListener");
			touchSupported = false;
		}
	}

	private Frame frame;
	private int[] points = new int[POINTER_MAX];
	private Map<Component, EventFactory> listeners = new HashMap<Component, EventFactory>();

	private WindowsPointer() {
		for(int i = 0; i < points.length; i++) {
			points[i] = -1;
		}
	}

	public static boolean isTouchSupported() {
		return touchSupported;
	}

	private static void Update(int eventId, long when, int modifiers, int xAbs, int yAbs, int clickCount, int pointerId, int pressure) {
		float fPressure = pressure == 0 ? 1f : ((float) pressure / 1024);
		WindowsPointer p = getInstance();
		int index = p.getPointId(pointerId);
		for(EventFactory e : p.listeners.values()) {
			e.firePointerEvent(eventId, when, modifiers, xAbs, yAbs, clickCount, index, fPressure);
		}

		if(eventId == MouseEvent.MOUSE_EXITED)
			p.releasePoint(index);
	}

	private static void KeyUpdate(int eventId, long when, int modifiers, int keyCode, char keyChar) {
		WindowsPointer p = getInstance();
		ModifierEvent event = new ModifierEvent(p.frame, eventId, when, modifiers, keyCode, keyChar);
		for(EventFactory e : p.listeners.values()) {
			e.fireModifierEvent(event);
		}
	}

	public static synchronized WindowsPointer getInstance() {
		if(instance == null)
			instance = new WindowsPointer();
		return instance;
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

	private native void init(long hWnd);

	public void setFrame(Frame frame) {
		if(frame == null)
			throw new IllegalArgumentException("null frame");
		if(this.frame == frame || !touchSupported)
			return;
		this.frame = frame;
		try {
			init(getHWnd(frame));
			touchSupported = true;
		} catch(RuntimeException | UnsatisfiedLinkError e) {
			e.printStackTrace();
			touchSupported = false;
		}
	}

	public void addListener(PointerListener pointerListener, Component component, JScrollPane scrollPane) {
		if(touchSupported) {
			EventFactory f = listeners.get(component);
			if(f == null) {
				EventFactory eventFactory = new EventFactory(component, scrollPane);
				eventFactory.add(pointerListener);
				listeners.put(component, eventFactory);
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
		points[id] = -1;
	}
}