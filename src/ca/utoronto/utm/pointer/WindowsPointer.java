package ca.utoronto.utm.pointer;

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
public class WindowsPointer extends MouseAdapter {
	private static boolean TOUCH_SUPPORTED = true;
	static {
		try {
			System.loadLibrary("JNI");
		} catch(Error e) {
			System.out.println("JNI failed to load\n falling back to MouseListener");
			TOUCH_SUPPORTED=false;
		}
	}

	public boolean isIsTouchSupported(){
		return TOUCH_SUPPORTED;
	}

	//SwingUtilities.convertPointFromScreen

	private WindowsPointer(){
		for(int i = 0; i < points.length; i++) {
			points[i]=-1;
		}
	};

	private static native void Init(long hWnd);

	private Frame frame;

	public void setFrame(Frame frame){
		if(frame==null)
			throw new IllegalArgumentException("null frame");
		if(this.frame==frame||!TOUCH_SUPPORTED)
			return;
		this.frame = frame;
		try {
			Init(getHWnd(frame));
			TOUCH_SUPPORTED=true;
		} catch(RuntimeException|UnsatisfiedLinkError e) {
			e.printStackTrace();
			TOUCH_SUPPORTED=false;
		}
		//listeners.clear();
	}

	private static WindowsPointer instance;

	public static final int POINTER_MAX=20;

	private int[] points = new int[POINTER_MAX];

	private Map<Component,EventFactory> listeners = new HashMap<Component,EventFactory>();

	private static void Update(int eventId, long when, int modifiers,int xAbs, int yAbs ,int clickCount, int pointerId, int pressure){
		float fPressure = pressure==0?1f:((float)pressure/1024);
		WindowsPointer p = getInstance();
		int index = p.getPointId(pointerId);
		for(EventFactory e:p.listeners.values()){
			e.firePointerEvent(eventId,when,modifiers,xAbs,yAbs,clickCount,index,fPressure);
		}

		if(eventId == MouseEvent.MOUSE_EXITED)
			p.releasePoint(index);
	}

	public void addListener(PointerListener pointerListener,Component component){
		if(TOUCH_SUPPORTED) {
			EventFactory f = listeners.get(component);
			if(f==null) {
				f = new EventFactory(component);
				f.add(pointerListener);
				listeners.put(component, f);
			}else
				f.add(pointerListener);
		}else{
			MouseEventProxy mp = new MouseEventProxy(pointerListener);
			component.addMouseListener(mp);
			component.addMouseMotionListener(mp);
		}
	}

	private int getPointId(int id){
		for(int i = 0; i < points.length; i++) {
			if(points[i]==id)
				return i;
		}

		for(int i = 0; i < points.length; i++) {
			if(points[i]==-1){
				points[i]=id;
				return i;
			}
		}
		return -1;
	}

	private void releasePoint(int id){
		points[id] = -1;
	}

	public synchronized static WindowsPointer getInstance() {
		if(instance==null)
			instance=new WindowsPointer();
		return instance;
	}

	private static long getHWnd(Frame component){
		//noinspection deprecation
		Object peer = component.getPeer();
		Class c = peer.getClass();
		try {
			for (Method m : c.getMethods()) {
				if (m.getName().equals("getHWnd")) {
					return (Long)m.invoke(peer);
				}
			}
		} catch(IllegalAccessException|InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("No HWND found for "+ c);
	}
}