package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.WindowsPointer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the top level View+Controller, it contains other aspects of the View+Controller.
 * @author arnold
 *
 */
public class View extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private PaintModel model;
	
	// The components that make this up
	private PaintPanel paintPanel;
	private ShapeChooserPanel shapeChooserPanel;
	//private ColourPanel colourPanel;
	private StylePanel stylePanel;
	//private JButton openColourPanel;
	
	public View(PaintModel model) {
		super("Paint"); // set the title and do other JFrame init
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());



		Container c=this.getContentPane();
		this.shapeChooserPanel = new ShapeChooserPanel(this);
		c.add(this.shapeChooserPanel,BorderLayout.WEST);

//		openColourPanel = new JButton("Extend Colour Panel");
//		openColourPanel.addActionListener(this);
		this.stylePanel = new StylePanel(this, model);
		c.add(this.stylePanel, BorderLayout.SOUTH);

		//this.colourPanel = new ColourPanel(this);
		//c.add(this.colourPanel,BorderLayout.SOUTH);

		this.model=model;
		this.paintPanel = new PaintPanel(model, this);
		c.add(this.paintPanel, BorderLayout.CENTER);
		
		//this.setLocationRelativeTo(null);

		this.pack();

			WindowsPointer.getInstance().setFrame(this);
		// this.setSize(200,200);
		this.setVisible(true);
	}

	public PaintPanel getPaintPanel(){
		return paintPanel;
	}
	
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
			model.clear();
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

		menuItem = new JMenuItem("Undo");
		menuItem.addActionListener(e -> {
			model.undo();
		});
		menu.add(menuItem);

		menuItem = new JMenuItem("Redo");
		menuItem.addActionListener(e -> {
			model.redo();
		});
		menu.add(menuItem);

		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {

	}

}
