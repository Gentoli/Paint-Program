package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.PointerEvent;
import ca.utoronto.utm.pointer.PointerListener;
import ca.utoronto.utm.pointer.WindowsPointer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

/**
 * Handles Drawing and Displaying of Shapes
 */
class PaintPanel extends JPanel implements Observer, PointerListener {
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the view
	private int mode; // modifies how we interpret input (could be better?)

	private Color colour;
	private float lineThickness;
	private int strokeStyle;
	private boolean fill = false;

	private PaintShape[] paintShapes = new PaintShape[WindowsPointer.POINTER_MAX];

	public PaintPanel(PaintModel model, View view){
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(516,300));
		this.model = model;

		this.mode = 4;
		this.model.addObserver(this);
		colour = Color.black;
		this.view = view;
		addComponentListener(model);
		WindowsPointer.getInstance().addListener(this, this);
	}

	/**
	 * View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
		super.paintComponent(g); //paint background
		Graphics2D g2 = (Graphics2D) g;
		this.model.paint(g2);

		for(PaintShape s : paintShapes) {
			if(s != null) {
				s.print(g2);
			}
		}
		g2.dispose();
	}

	@Override
	public void update(Observable o, Object arg) {
		// Not exactly how MVC works, but similar.
		this.repaint(); // Schedule a call to paintComponent
	}

	/**
	 * Controller aspect of this
	 */
	public void setMode(int mode) {
		this.mode = mode;

		if(paintShapes[0]!=null&& paintShapes[0] instanceof Polyline){
			((Polyline) paintShapes[0]).end();
			model.addPrint(paintShapes[0]);
			paintShapes[0] = null;
			activePointer = -1;
		}else if(paintShapes[0]!=null&& paintShapes[0] instanceof Modifier){
			((Modifier) paintShapes[0]).setReleased();
			model.addPrint(paintShapes[0]);
			paintShapes[0] = null;
			activePointer = -1;
		}
	}

	public void setColor(Color newColor) {
		this.colour = newColor;
	}

	public void setLineThickness(float lineThickness) {
		this.lineThickness = lineThickness;
	}

	public void setStrokeStyle(int strokeStyle) {
		this.strokeStyle = strokeStyle;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	private int activePointer = -1;

	@Override
	public void pointerUpdated(PointerEvent e) {
		try {
			int shapeId = activePointer==-1?e.getPointerId():0;
			switch(e.getID()) {
				case MouseEvent.MOUSE_PRESSED:
					switch(mode) {
						case ShapeBuilder.MODIFY:
							if(activePointer==-1){
								if(paintShapes[0]==null) {
									activePointer=e.getPointerId();
									paintShapes[0] = new Modifier(e.getX(), e.getY(), model);
								}else{
									if(paintShapes[0].contains(e.getX(),e.getY())){
										activePointer=e.getPointerId();
										((Modifier) paintShapes[0]).move(e.getX(),e.getY());
									}else{
										((Modifier) paintShapes[0]).setReleased();
										setMode(mode);
									}
								}
							}
							break;
						case ShapeBuilder.POLYLINE:
							if(activePointer==-1){
								activePointer=e.getPointerId();
								if(paintShapes[0]==null) {
									shapeId=0;
								}else {
									break;
								}
							}else
								break;
						default:
							paintShapes[shapeId] = new ShapeBuilder(mode, e.getX(), e.getY()).setColour(colour)
									.setCenter((e.getModifiers() & InputEvent.ALT_MASK) != 0).setLineThickness(lineThickness * e.getPressure())
									.setFill(fill).setStrokeStyle(strokeStyle).setRight((e.getModifiers() & InputEvent.SHIFT_MASK) != 0).build();
					}
					break;
				case MouseEvent.MOUSE_MOVED:
					switch(mode) {
						case ShapeBuilder.POLYLINE:
							if(paintShapes[shapeId]!=null)
							((Polyline) paintShapes[shapeId]).setTemp(e.getX(),e.getY());
							break;
						case ShapeBuilder.SQUIGGLE:
							if(paintShapes[shapeId] != null)
								paintShapes[shapeId].setEnd(e.getX(), e.getY());
							break;
						case ShapeBuilder.MODIFY:if(e.getPointerId()!=activePointer)break;
						default:
							if(paintShapes[shapeId] != null) {
								paintShapes[shapeId].setEnd(e.getX(), e.getY());
								paintShapes[shapeId].setRight((e.getModifiers() & InputEvent.SHIFT_MASK) != 0);
								paintShapes[shapeId].setCenter((e.getModifiers() & InputEvent.ALT_MASK) != 0);
								paintShapes[shapeId].setLineThickness(lineThickness * e.getPressure());
							}
					}
					break;
				case MouseEvent.MOUSE_RELEASED:
					switch(mode) {
						case ShapeBuilder.MODIFY:
							if(e.getPointerId()!=activePointer||activePointer==-1)break;
							((Modifier) paintShapes[0]).release(e.getX(), e.getY());
							activePointer=-1;
							if(e.getButton()==3){
								setMode(mode);
							}
							break;
						case ShapeBuilder.POLYLINE:
							activePointer=-1;
							if(e.getButton()==3){
								((Polyline) paintShapes[shapeId]).end();
								model.addPrint(paintShapes[shapeId]);
								paintShapes[shapeId] = null;
							}else{
								if(paintShapes[shapeId]!=null)
									paintShapes[shapeId].setEnd(e.getX(), e.getY());
							}
							break;
						default:
							if(paintShapes[shapeId] != null) {
								paintShapes[shapeId].setEnd(e.getX(), e.getY());
								model.addPrint(paintShapes[shapeId]);
								paintShapes[shapeId] = null;
								activePointer=-1;
							}
					}
					break;
			}
			repaint();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}

	public void clear(){
		setMode(mode);
		model.clear();
	}

}
