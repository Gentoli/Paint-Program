package ca.utoronto.utm.pointer;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WindowsPointer extends MouseAdapter {
	public static final boolean IS_TOUCH_SUPPORTED;
	static {
		boolean success = true;
		try {
			System.loadLibrary("JNI");
		} catch(Error e) {
			success=false;
			System.out.println("JNI failed to load\n falling back to MouseListener");
		}
		IS_TOUCH_SUPPORTED = success;
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
		if(this.frame==frame)
			return;
		this.frame = frame;
		Init(getHWnd(frame));
		//listeners.clear();
	}

	private static WindowsPointer instance;

	public static final int POINTER_MAX=20;

	private int[] points = new int[POINTER_MAX];

	private Map<Component,EventFactory> listeners = new HashMap<Component,EventFactory>();
	public static void main(String[] args){
		WindowsPointer w = getInstance();
		JFrame jf = new JFrame();

		JButton b = new JButton();
		b.addActionListener(e ->  {
			//System.out.println(Thread.currentThread().getId());
		});

		jf.setMinimumSize(new Dimension(300,200));
		jf.setDefaultCloseOperation(3);
		jf.add(b);
		jf.pack();

		w.setFrame(jf);
		w.addListener(e -> {
			System.out.println("Button: "+e.getEvent().getButton());
			System.out.println("Mod: "+Integer.toBinaryString(e.getEvent().getModifiers()));
		},jf);

		jf.setVisible(true);
	}

	static boolean debug = true;
	private static void Update(int eventId, long when, int modifiers,int xAbs, int yAbs ,int clickCount, int pointerId, int pressure){
		if(debug){
			System.out.println("update");
			debug=false;
		}

		WindowsPointer p = getInstance();
		int index = p.getPointId(pointerId);
//		System.out.print("[");
//		for(int i = 0; i < p.points.length; i++) {
//			System.out.print(p.points[i]);
//			System.out.print(" ");
//		}
//		System.out.println("]");
		for(EventFactory e:p.listeners.values()){
			e.firePointerEvent(eventId,when,modifiers,xAbs,yAbs,clickCount,index,pressure);
		}

		if(eventId == MouseEvent.MOUSE_EXITED)
			p.releasePoint(index);
		//InputEvent.SHIFT_DOWN_MASK;
	}

	public void addListener(PointerListener pointerListener,Component component){
		EventFactory f = listeners.get(component);
		if(f==null) {
			f = new EventFactory(component);
			f.add(pointerListener);
			listeners.put(component, f);
		}else
			f.add(pointerListener);
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