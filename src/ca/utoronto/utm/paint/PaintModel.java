package ca.utoronto.utm.paint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Observable;

public class PaintModel extends Observable implements ComponentListener {
	private BufferedImage image = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);

	private LinkedList<Drawable> drawables = new LinkedList<Drawable>();
	private LinkedList<Drawable> undo = new LinkedList<Drawable>();
	public PaintModel(){
		Graphics2D graphics = image.createGraphics();
		graphics.setPaint(Color.white);
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight() );
		graphics.dispose();
	}
	public void addPrint(Drawable c){
		synchronized(drawables) {
			drawables.add(c);
			while(drawables.size()>5) {
				synchronized(image) {
					Graphics2D g = image.createGraphics();
					drawables.poll().print(g);
					g.dispose();
				}
			}
		}
		undo.clear();
		this.setChanged();
		this.notifyObservers();
	}

	public void clear(){
		synchronized(drawables) {
			drawables.add(new ClearMask(image.getWidth(), image.getHeight()));
		}
		this.setChanged();
		this.notifyObservers();
	}

	public void undo(){
		synchronized(drawables) {
			undo.add(drawables.removeLast());
		}
		this.setChanged();
		this.notifyObservers();
	}

	public void redo(){
		synchronized(drawables) {
			drawables.add(undo.removeLast());
		}
		this.setChanged();
		this.notifyObservers();
	}

	public boolean canUndo(){
		return !drawables.isEmpty();
	}

	public boolean canRedo(){
		return !undo.isEmpty();
	}

	public void paint(Graphics2D g2) {
		synchronized(image) {
			g2.drawImage(image, 0, 0, null);
		}
		synchronized(drawables) {
			for(Drawable d : drawables) {
				d.print(g2);
			}
		}
	}

	private void resize(int x,int y){
		System.out.println("resized");
		synchronized(image) {
			BufferedImage i = new BufferedImage(x, y, 1);
			Graphics2D g = i.createGraphics();
			g.fillRect(0, 0, i.getWidth(), i.getHeight());
			g.drawImage(image, 0, 0, null);
			g.dispose();
			image = i;
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension d = e.getComponent().getSize();
		if(d.height>image.getHeight()||d.width>image.getWidth()){
			resize(d.width,d.height);
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}
}
