package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;
import ca.utoronto.utm.pointer.PointerListener;
import ca.utoronto.utm.pointer.WindowsPointer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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
	private ShapeManipulatorStrategy[] toolList;
	private int edges;

	public PaintPanel(PaintModel model){
		this.setBackground(Color.white);
		//this.setPreferredSize(new Dimension(516,300));
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		this.setPreferredSize(new Dimension(screenSize.width - 125, screenSize.height - 205));

		this.model = model;

		this.mode = 0;
		this.model.addObserver(this);
        setFocusable(true);
		addComponentListener(model);
		WindowsPointer.getInstance().addListener(this, this);
	}

	public void initializeTools(StylePanel stylePanel, TextBoxDialog textBoxDialog){
		toolList = new ShapeManipulatorStrategy[]{new SelectionTool(this.model, shapes),
                                new TextBoxTool(textBoxDialog, stylePanel, shapes),
								new PolylineTool(stylePanel, shapes),
								new SquiggleTool(stylePanel, shapes),
								new PolygonTool(stylePanel, this,shapes,ConcavePolygon.class),
								new PolygonTool(stylePanel, this,shapes,RegularPolygon.class)};
	}

	/**
	 * View aspect of this
	 */
	public void paintComponent(Graphics g) {
		// Use g to draw on the JPanel, lookup java.awt.Graphics in
		// the javadoc to see more of what this can do for you!!
		super.paintComponent(g); //paint background
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,getWidth(),getHeight());
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
		toolList[this.mode].selected();
	}

	public void setEdges(int edges) { this.edges = edges; }

	public int getEdges() {
		return edges;
	}


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

	private boolean keyDown =false;

	@Override
	public void modifierUpdated(ModifierEvent e) {
        if(e.isControlDown()&&e.getID()== KeyEvent.KEY_PRESSED){
			if(e.getKeyChar()=='Z'||(char)e.getKeyCode()=='Z'){
				undo();
				return;
			}else if(e.getKeyChar()=='Y'||(char)e.getKeyCode()=='Y'){
				redo();
				return;
			}
		}
		if(toolList[mode].handleModifierUpdated(e))
			repaint();
	}
}
