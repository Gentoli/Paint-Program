package ca.utoronto.utm.pointer;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WindowsPointer {
	public static final boolean IS_TOUCH_SUPPORTED;
	static {
		boolean success = true;
		try {
			System.loadLibrary("JNI");
		} catch(Error e) {
			success=false;
		}
		IS_TOUCH_SUPPORTED = success;
	}

	//SwingUtilities.convertPointFromScreen

	private WindowsPointer(){};

	private static native void Init(long hWnd);

	private Frame frame;

	private void setFrame(Frame frame){
		if(this.frame==frame)
			return;
		this.frame = frame;
		Init(getHWnd(frame));
		listeners.clear();
	}

	private static WindowsPointer instance;

	private int[] points = new int[20];

	private Map<Component,EventFactory> listeners = new HashMap<Component,EventFactory>();
	static JButton  b;
	public static void main(String[] args){
		WindowsPointer w = getInstance();
		JFrame jf = new JFrame();

		b = new JButton();
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

	private static void Update(int eventId, long when, int modifiers,int xAbs, int yAbs ,int clickCount, int pointerId, int pressure){
		WindowsPointer p = getInstance();
		int index = p.getPointId(pointerId);

		for(EventFactory e:p.listeners.values()){
			e.firePointerEvent(eventId,when,modifiers,xAbs,yAbs,clickCount,index,pressure);
		}
		//InputEvent.SHIFT_DOWN_MASK;
	}

	private static void release(int id){
		getInstance().releasePoint(id);
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
		for(int i = 0; i < points.length; i++) {
			if(points[i]==id)
				points[i]=-1;
		}
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