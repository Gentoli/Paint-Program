package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.WindowsPointer;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the top level View+Controller, it contains other aspects of the View+Controller.
 * @author arnold
 * The View is comprised of the PaintPanel, ShapeChoosingPanel and StylePanel,
 * and links them together, Observing the PaintModel.
 */
public class View extends JFrame implements Observer {
	
	private static final long serialVersionUID = 1L;
	private static final String message = "Notes: \n" +
			" - Right click to break Polyline mode line\n" +
			" - Set number of edges for Polygon mode in left textfield\n" +
			"Hotkeys:\n" +
			" - Hold Alt to draw regular polygons relative to center\n" +
			" - Ctrl-Z to undo, Ctrl-Y to redo\n" +
			"Extras:\n" +
			" - Touch-Control/Multitouch if your computer supports touchscreen\n" +
			" - Pressure-Sensitive line thickness";
	
	private PaintModel model;
	
	// The components that make this up
	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;
	//private ColourDialog colourPanel;
	private StylePanel stylePanel;
	private TextBoxDialog textBoxDialog;
	private JMenuItem menuUndo;
	private JMenuItem menuRedo;
	private JMenuItem menuHelp;
	//private JButton openColourPanel;
	
	public View(PaintModel model) {
		super("Paint"); // set the title and do other JFrame init
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());

		Container c=this.getContentPane();


//		openColourPanel = new JButton("Extend Colour Panel");
//		openColourPanel.addActionListener(this);

		ColourDialog colourDialog = new ColourDialog(this,"Text Colour");
		ColourDialog borderColourDialog = new ColourDialog(this,"Border Colour");

		this.model=model;
		this.paintPanel = new PaintPanel(model);
		//c.add(this.paintPanel, BorderLayout.CENTER);

		this.stylePanel = new StylePanel(paintPanel,colourDialog,borderColourDialog);
		c.add(this.stylePanel, BorderLayout.SOUTH);

		this.textBoxDialog = new TextBoxDialog(this);
		textBoxDialog.setPreferredSize(new Dimension(300, 100));

		paintPanel.initializeTools(stylePanel, textBoxDialog);
		model.addObserver(stylePanel);

		this.shapeChooserPanel = new ShapeChooserPanel(paintPanel);
		c.add(this.shapeChooserPanel,BorderLayout.WEST);

		JScrollPane scrollPane = new JScrollPane(paintPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		c.add(scrollPane, BorderLayout.CENTER);

		model.addObserver(this);
		this.pack();

		WindowsPointer.getInstance().setFrame(this);
		this.setMinimumSize(new Dimension(624, 462));
		this.setSize(624,462);
		this.setVisible(true);
		JOptionPane.showMessageDialog(this, message);

	}

//	public PaintPanel getPaintPanel(){
//		return paintPanel;
//	}
	
	public ShapeChooserPanel getShapeChooserPanel() {
		return shapeChooserPanel;
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		menu = new JMenu("File");

		// a group of JMenuItems
		menuItem = new JMenuItem("New");
		menuItem.addActionListener(e -> {
			paintPanel.clear();
			requestFocus();
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(e -> {

		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(e -> {

		});
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(e -> {
			this.dispose();
		});
		menu.add(menuItem);

		menuBar.add(menu);

		menu = new JMenu("Edit");

		// a group of JMenuItems
		menuItem = new JMenuItem("Cut");
		menuItem.addActionListener(e -> {

		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Copy");
		menuItem.addActionListener(e -> {

		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Paste");
		menuItem.addActionListener(e -> {

		});
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuUndo = new JMenuItem("Undo");
		menuUndo.setEnabled(false);
		menuUndo.addActionListener(e -> {
			model.undo();
			menuUndo.setEnabled(model.canUndo());
			requestFocus();
		});
		menu.add(menuUndo);

		menuRedo = new JMenuItem("Redo");
		menuRedo.setEnabled(false);
		menuRedo.addActionListener(e -> {
			model.redo();
			menuRedo.setEnabled(model.canRedo());
			requestFocus();
		});
		menu.add(menuRedo);

		menuBar.add(menu);

		menu = new JMenu("Help");
		menuHelp = new JMenuItem("Help");
		menuHelp.addActionListener(e -> {

			JOptionPane.showMessageDialog(this, message);
		});
		menu.add(menuHelp);
		menuBar.add(menu);

		return menuBar;
	}

	/**
	 * checks if there are objects in the undo and redo arrays
	 * and showing if undo and redo buttons can be clicked accordingly
	 * @param o PaintModel
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		menuUndo.setEnabled(model.canUndo());
		menuRedo.setEnabled(model.canRedo());
	}
}
