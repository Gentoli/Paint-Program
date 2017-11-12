package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;
import ca.utoronto.utm.pointer.PointerListener;
import ca.utoronto.utm.pointer.WindowsPointer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

// https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics2D.html
// https://docs.oracle.com/javase/tutorial/2d/

/**
 * Handles Drawing and Displaying of Shapes
 */
class PaintPanel extends JPanel implements Observer, PointerListener {
	private PaintModel model; // slight departure from MVC, because of the way painting works
	private int mode; // modifies how we interpret input (could be better?)


	private PaintShape[] shapes = new PaintShape[WindowsPointer.POINTER_MAX];
	private ITool[] toolList;
	private int edges;

	public PaintPanel(PaintModel model){
		this.setBackground(Color.white);
		//this.setPreferredSize(new Dimension(516,300));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension(screenSize.width - 125, screenSize.height - 205));

		this.model = model;

		this.mode = 0;
		this.model.addObserver(this);

		addComponentListener(model);
		WindowsPointer.getInstance().addListener(this, this);
	}

	public void initializeTools(StylePanel stylePanel){
		toolList = new ITool[]{new SelectionTool(this.model, shapes),
								new PolylineTool(stylePanel, shapes),
								new SquiggleTool(stylePanel, shapes),
								new PolygonTool(stylePanel, this,shapes)};
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

		for(PaintShape s : shapes) {
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
		model.addPrint(toolList[this.mode].deselect());
		this.mode = Math.min(mode,toolList.length-1);
	}

	public void setEdges(int edges) { this.edges = edges; }

	public int getEdges() {
		return edges;
	}

	//	@Override
//	public void handlePointerUpdate(PointerEvent e) {
//		try {
//			int shapeId = activePointer==-1?e.getPointerId():0;
//			switch(e.getID()) {
//				case MouseEvent.MOUSE_PRESSED:
//					switch(mode) {
//						case ShapeBuilder.MODIFY:
//							if(activePointer==-1){
//								if(shapes[0]==null) {
//									activePointer=e.getPointerId();
//									shapes[0] = new Selection(e.getX(), e.getY(), model);
//								}else{
//									if(shapes[0].contains(e.getX(),e.getY())){
//										activePointer=e.getPointerId();
//										((Selection) shapes[0]).move(e.getX(),e.getY());
//									}else{
//										((Selection) shapes[0]).setReleased();
//										setMode(mode);
//									}
//								}
//							}
//							break;
//						case ShapeBuilder.POLYLINE:
//							if(activePointer==-1){
//								activePointer=e.getPointerId();
//								if(shapes[0]==null) {
//									shapeId=0;
//								}else {
//									break;
//								}
//							}else
//								break;
//						default:
//							shapes[shapeId] = new ShapeBuilder(mode, e.getX(), e.getY()).setColour(colour)
//									.setCenter((e.getModifiers() & InputEvent.ALT_MASK) != 0).setLineThickness(lineThickness * e.getPressure())
//									.setFill(fill).setStrokeStyle(strokeStyle).setRight((e.getModifiers() & InputEvent.SHIFT_MASK) != 0).build();
//
//					}
//					break;
//				case MouseEvent.MOUSE_MOVED:
//					switch(mode) {
//						case ShapeBuilder.POLYLINE:
//							if(shapes[shapeId]!=null)
//							((Polyline)shapes[shapeId]).setTemp(e.getX(),e.getY());
//							break;
//						case ShapeBuilder.SQUIGGLE:
//							if(shapes[shapeId] != null)
//								shapes[shapeId].setEnd(e.getX(), e.getY());
//							break;
//						case ShapeBuilder.MODIFY:if(e.getPointerId()!=activePointer)break;
//						default:
//							if(shapes[shapeId] != null) {
//								shapes[shapeId].setEnd(e.getX(), e.getY());
//								shapes[shapeId].setRight((e.getModifiers() & InputEvent.SHIFT_MASK) != 0);
//								shapes[shapeId].setCenter((e.getModifiers() & InputEvent.ALT_MASK) != 0);
//								shapes[shapeId].setLineThickness(lineThickness * e.getPressure());
//							}
//					}
//					break;
//				case MouseEvent.MOUSE_RELEASED:
//					switch(mode) {
//						case ShapeBuilder.MODIFY:
//							if(e.getPointerId()!=activePointer||activePointer==-1)break;
//							((Selection) shapes[0]).release(e.getX(), e.getY());
//							activePointer=-1;
//							if(e.getButton()==3){
//								setMode(mode);
//							}
//							break;
//						case ShapeBuilder.POLYLINE:
//							activePointer=-1;
//							if(e.getButton()==3){
//								((Polyline)shapes[shapeId]).end();
//								model.addPrint(shapes[shapeId]);
//								shapes[shapeId] = null;
//							}else{
//								if(shapes[shapeId]!=null)
//									shapes[shapeId].setEnd(e.getX(), e.getY());
//							}
//							break;
//						default:
//							if(shapes[shapeId] != null) {
//								shapes[shapeId].setEnd(e.getX(), e.getY());
//								model.addPrint(shapes[shapeId]);
//								shapes[shapeId] = null;
//								activePointer=-1;
//							}
//					}
//					break;
//			}
//			repaint();
//		} catch(Exception e1) {
//			e1.printStackTrace();
//		}
//	}

	public void clear(){
		setMode(mode);
		model.clear();
		repaint();
	}

	public void undo() {
		setMode(mode);
		model.undo();
		repaint();
	}

	public void redo() {
		setMode(mode);
		model.redo();
		repaint();
	}

	@Override
	public void pointerUpdated(PointerEvent e) {
		//System.out.println(mode);
		model.addPrint(toolList[mode].handlePointerUpdate(e));
		repaint();
	}

	@Override
	public void modifierUpdated(ModifierEvent e) {
		if(e.getKeyChar()=='z'){
			undo();
		}else if(e.getKeyChar()=='y'){
			redo();
		}else
		toolList[mode].handleModifierUpdated(e);
		repaint();
	}
}
