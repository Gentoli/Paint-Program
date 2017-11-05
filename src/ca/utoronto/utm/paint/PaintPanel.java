package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.PointerEvent;
import ca.utoronto.utm.pointer.PointerListener;
import ca.utoronto.utm.pointer.WindowsPointer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

class PaintPanel extends JPanel implements Observer, PointerListener {
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private View view; // So we can talk to our parent or other components of the view
	private int mode; // modifies how we interpret input (could be better?)

	private Color colour;
	private float lineThickness;
	private Stroke stroke;
	private boolean fill = false;
	private int edges = 10;

	private Shape[] shapes = new Shape[WindowsPointer.POINTER_MAX];
	//private Ellipse ellipse; // the ellipse we are building

	public PaintPanel(PaintModel model, View view){

		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(500,300));
		view.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown()) {
					switch(e.getKeyCode()) {
						case 90:
							model.undo();
							break;
						case 89:
							model.redo();
							break;
					}
				}
			}
		});
		view.setFocusable(true);
		//WindowsPointer.getInstance().addListener(this,this);
		this.model = model;
		//shapes = this.model.getShapes();
		//tempStorage = new ArrayList<Shape>();
		this.mode = 4;
		this.model.addObserver(this);
		colour=Color.black;
		this.view=view;
		addComponentListener(model);
		WindowsPointer.getInstance().addListener(this,this);
	}

	/**
	 *  View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
        super.paintComponent(g); //paint background
		Graphics2D g2 = (Graphics2D)g;
        this.model.paint(g2);

		for(Shape s:shapes) {
			if(s!=null) {
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
	 *  Controller aspect of this
	 */
	public void setMode(int mode){
		this.mode=mode;
	}

	public void setColor(Color newColor) {
		this.colour = newColor;
	}

	public void setLineThickness(float lineThickness) { this.lineThickness = lineThickness; }

	public void setStroke(Stroke stroke) { this.stroke = stroke; }

	public void setEdges(int edges) { this.edges = edges; }

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	@Override
	public void pointerUpdated(PointerEvent e) {
		switch(e.getID()){
			case MouseEvent.MOUSE_PRESSED:
				shapes[e.getPointerId()] =	new ShapeBuilder(10,e.getX(),e.getY()).setColour(colour)
						.setLineThickness(lineThickness).setFill(fill).setCenter(true).setStroke(stroke).build();
				//shapes[e.getPointerId()] = shape.build();
				break;
			case MouseEvent.MOUSE_MOVED:
				if(shapes[e.getPointerId()]!=null){
					shapes[e.getPointerId()].setEnd(e.getX(),e.getY());
				}
				//System.out.println("create");
				//shapes[e.getPointerId()].setEndPoint(new Point(e.getX(),e.getY()));
				//System.out.println("em");
				break;
			case MouseEvent.MOUSE_RELEASED:
				if(shapes[e.getPointerId()]!=null){
					shapes[e.getPointerId()].setEnd(e.getX(),e.getY());
					model.addPrint(shapes[e.getPointerId()]);
					shapes[e.getPointerId()]=null;
				}
				//shapes[e.getPointerId()].setEndPoint(new Point(e.getX(),e.getY()));
				//model.addShape(shapes[e.getPointerId()]);
				//shapes[e.getPointerId()]=null;
				break;
		}
		repaint();
	}
}
