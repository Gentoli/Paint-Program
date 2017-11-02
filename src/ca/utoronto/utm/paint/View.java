package ca.utoronto.utm.paint;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
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
	private ColourPanel colourPanel;
	private JButton openColourPanel;
	
	public View(PaintModel model) {
		super("Paint"); // set the title and do other JFrame init
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());
		
		Container c=this.getContentPane();
		this.shapeChooserPanel = new ShapeChooserPanel(this);
		c.add(this.shapeChooserPanel,BorderLayout.WEST);

		openColourPanel = new JButton("Extend Colour Panel");
		openColourPanel.addActionListener(this);
		c.add(openColourPanel, BorderLayout.SOUTH);

		//this.colourPanel = new ColourPanel(this);
		//c.add(this.colourPanel,BorderLayout.SOUTH);

		this.model=model;
		this.paintPanel = new PaintPanel(model, this);
		c.add(this.paintPanel, BorderLayout.CENTER);
		
		//this.setLocationRelativeTo(null);
		this.colourPanel = new ColourPanel(this);

		colourPanel.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				openColourPanel.setText("Extend Colour Panel");
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.pack();
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
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		menu = new JMenu("Edit");

		// a group of JMenuItems
		menuItem = new JMenuItem("Cut");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Copy");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Paste");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.addSeparator();// -------------

		menuItem = new JMenuItem("Undo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Redo");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuBar.add(menu);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {
		if (colourPanel.isVisible()) {
			colourPanel.setVisible(false);
			openColourPanel.setText("Extend Colour Panel");
		}
		else {
			colourPanel.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y + this.getHeight());
			colourPanel.setVisible(true);
			openColourPanel.setText("Close Colour Panel");
		}
		System.out.println(e.getActionCommand());
	}
}
