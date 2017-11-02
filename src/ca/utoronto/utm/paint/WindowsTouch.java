package ca.utoronto.utm.paint;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WindowsTouch {
	static {
		System.out.println(System.getProperty("user.dir"));
		System.load(System.getProperty("user.dir")+"\\JNI.dll");
	}

	private static native void Init(long hWnd);
	private static WindowsTouch instance;
	public static void main(String[] args){
		JFrame jf = new JFrame();
		JButton b = new JButton();
		b.addActionListener(e ->  {
			//System.out.println(Thread.currentThread().getId());
		});
		jf.setMinimumSize(new Dimension(300,200));
		jf.setDefaultCloseOperation(3);
		jf.add(b);
		jf.pack();
		Init(getHWnd(jf));
		jf.setVisible(true);

		//JOptionPane.showMessageDialog(jf,"hahaha");
	}

	private static long pointDown(){
		System.out.println("down!");
		//b.setText(String.valueOf(Math.random()));
		return 100;
	}

	public static WindowsTouch getInstance() {
		if(instance==null)
			instance=new WindowsTouch();
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
